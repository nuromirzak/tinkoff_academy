package ru.tinkoff.edu.java.scrapper.services.impls.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final JpaLinkRepo linkRepo;
    private final JpaChatRepo chatRepo;
    private final GlobalLinkParser globalLinkParser;
    private final GitHubClient gitHubClient;
    private final StackoverflowClient stackoverflowClient;
    private final ObjectMapper objectMapper;

    @Override
    public Optional<Link> add(long tgChatId, String url) {
        try {
            Link link = linkRepo.findByUrlLike(url);
            if (link == null) {
                link = new Link();
                link.setLastUpdated(OffsetDateTime.now());
                link.setUrl(url);
                link.setChats(new ArrayList<>());
            }

            URI uri = URI.create(url);
            Map<String, String> map = globalLinkParser.parse(uri);
            System.out.println("map = " + map);
            if (map == null) {
                return Optional.empty();
            }
            if (map.containsKey("owner") && map.containsKey("repo")) {
                GithubRepoResponse githubRepoResponse = gitHubClient.getRepo(map.get("owner"), map.get("repo"));
                String jsonProps = objectMapper.writeValueAsString(githubRepoResponse);
                link.setJsonProps(jsonProps);
            } else if (map.containsKey("questionId")) {
                System.out.println("map.get(\"questionId\") = " + map.get("questionId"));
                StackoverflowQuestionResponse stackoverflowQuestionResponse =
                        stackoverflowClient.getQuestionById(Long.parseLong(map.get("questionId")));
                System.out.println("stackoverflowQuestionResponse = " + stackoverflowQuestionResponse);
                String jsonProps = objectMapper.writeValueAsString(stackoverflowQuestionResponse);
                link.setJsonProps(jsonProps);
            } else {
                System.out.println("Unknown link type");
                return Optional.empty();
            }

            Chat currentChat = chatRepo.findChatByChatId(tgChatId);

            if (currentChat == null) {
                currentChat = new Chat();
                currentChat.setChatId(tgChatId);
                currentChat.setLinks(new ArrayList<>());
            }

            link.getChats().add(currentChat);
            currentChat.getLinks().add(link);

            Link savedLink = linkRepo.save(link);

            return Optional.of(savedLink);
        } catch (Exception e) {
            throw new RuntimeException("Error while adding link", e);
        }
    }

    @Override
    public boolean remove(long tgChatId, String url) {
        try {
            URI uri = URI.create(url);
            List<Link> links = linkRepo.findLinksByChatsChatId(tgChatId);
            Optional<Link> linkToDelete =
                    links.stream().filter(link -> link.getUrl().equals(uri.toString())).findFirst();
            if (linkToDelete.isPresent()) {
                Link link = linkToDelete.get();
                boolean isLinkUnlinked = link.getChats().removeIf(chat -> chat.getChatId() == tgChatId);
                linkRepo.save(link);

                Chat chat = chatRepo.findChatByChatId(tgChatId);
                boolean isChatUnlinked = chat.getLinks().removeIf(l -> l.getUrl().contains(url));
                chatRepo.save(chat);

                return isLinkUnlinked && isChatUnlinked;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error while removing link", e);
        }
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        return linkRepo.findLinksByChatsChatId(tgChatId);
    }

    @Override
    public Collection<Link> findAll() {
        return linkRepo.findAll();
    }

    @Override
    public List<Chat> findFollowers(String url) {
        return chatRepo.findChatsByLinks_UrlLike(url);
    }

    @Override
    public List<Link> findLinksToScrapSince(Duration checkInterval) {
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime lastScrapped = now.minus(checkInterval);
        return linkRepo.findLinksByLastScrappedBefore(lastScrapped);
    }
}
