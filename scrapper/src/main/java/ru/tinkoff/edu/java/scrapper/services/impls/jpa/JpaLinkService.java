package ru.tinkoff.edu.java.scrapper.services.impls.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.link_parser.parsers.GlobalLinkParser;
import ru.tinkoff.edu.java.scrapper.clients.GitHubClient;
import ru.tinkoff.edu.java.scrapper.clients.StackoverflowClient;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.dtos.responses.GithubRepoResponse;
import ru.tinkoff.edu.java.scrapper.dtos.responses.StackoverflowQuestionResponse;
import ru.tinkoff.edu.java.scrapper.repo.jpa.JpaChatRepo;
import ru.tinkoff.edu.java.scrapper.repo.jpa.JpaLinkRepo;
import ru.tinkoff.edu.java.scrapper.services.LinkService;

@Service
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
@RequiredArgsConstructor
@Log4j2
public class JpaLinkService implements LinkService {
    private final JpaLinkRepo linkRepo;
    private final JpaChatRepo chatRepo;
    private final GlobalLinkParser globalLinkParser;
    private final GitHubClient gitHubClient;
    private final StackoverflowClient stackoverflowClient;
    private final ObjectMapper objectMapper;

    private static final String OWNER = "owner";
    private static final String REPO = "repo";
    private static final String QUESTION_ID = "questionId";

    @Override
    public Optional<Link> add(long tgChatId, String url) {
        try {
            Link link = linkRepo.findByUrlLike(url);
            if (link == null) {
                link = new Link();
                link.setLastUpdated(OffsetDateTime.now());
                link.setUrl(url);
            }

            URI uri = URI.create(url);
            Map<String, String> map = globalLinkParser.parse(uri);
            log.info("map = {}", map);
            if (map == null) {
                return Optional.empty();
            }
            if (map.containsKey(OWNER) && map.containsKey(REPO)) {
                log.info("map.get(\"owner\") = {}", map.get(OWNER));
                log.info("map.get(\"repo\") = {}", map.get(REPO));
                GithubRepoResponse githubRepoResponse = gitHubClient.getRepo(map.get(OWNER), map.get(REPO));
                String jsonProps = objectMapper.writeValueAsString(githubRepoResponse);
                link.setJsonProps(jsonProps);
            } else if (map.containsKey(QUESTION_ID)) {
                log.info("map.get(\"questionId\") = {}", map.get(QUESTION_ID));
                StackoverflowQuestionResponse stackoverflowQuestionResponse =
                    stackoverflowClient.getQuestionById(Long.parseLong(map.get(QUESTION_ID)));
                String jsonProps = objectMapper.writeValueAsString(stackoverflowQuestionResponse);
                link.setJsonProps(jsonProps);
            } else {
                log.warn("Unknown link type");
                return Optional.empty();
            }

            Link savedLink = linkRepo.save(link);

            Chat currentChat = chatRepo.findChatByChatId(tgChatId);

            if (currentChat == null) {
                currentChat = new Chat();
                currentChat.setChatId(tgChatId);
                currentChat.setLinks(new ArrayList<>());
            }

            currentChat.getLinks().add(savedLink);
            chatRepo.save(currentChat);

            return Optional.of(savedLink);
        } catch (Exception e) {
            throw new RuntimeException("Error while adding link", e);
        }
    }

    @Override
    public boolean remove(long tgChatId, String url) {
        try {
            Chat chatToDeleteFrom = chatRepo.findChatByChatId(tgChatId);
            boolean isLinkDeleted = chatToDeleteFrom.getLinks().removeIf(link -> link.getUrl().contains(url));
            chatRepo.save(chatToDeleteFrom);
            return isLinkDeleted;
        } catch (Exception e) {
            throw new RuntimeException("Error while removing link", e);
        }
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        Chat chat = chatRepo.findChatByChatId(tgChatId);
        if (chat == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(chat.getLinks());
    }

    @Override
    public Collection<Link> findAll() {
        return linkRepo.findAll();
    }

    @Override
    public List<Chat> findFollowers(String url) {
        return chatRepo.findChatsByLinksUrlLike(url);
    }

    @Override
    public List<Link> findLinksToScrapSince(Duration checkInterval) {
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime lastScrapped = now.minus(checkInterval);
        return linkRepo.findLinksByLastScrappedBefore(lastScrapped);
    }
}
