package ru.tinkoff.edu.java.scrapper.services.impls.jooq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import ru.tinkoff.edu.java.scrapper.repo.jooq.JooqChatLinkRepo;
import ru.tinkoff.edu.java.scrapper.repo.jooq.JooqChatRepo;
import ru.tinkoff.edu.java.scrapper.repo.jooq.JooqLinkRepo;
import ru.tinkoff.edu.java.scrapper.services.LinkService;

@Service
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
@RequiredArgsConstructor
public class JooqLinkService implements LinkService {
    private static final String OWNER = "owner";
    private static final String REPO = "repo";
    private static final String QUESTION_ID = "questionId";
    private final JooqLinkRepo linkRepo;
    private final JooqChatRepo chatRepo;
    private final JooqChatLinkRepo chatLinkRepo;
    private final GlobalLinkParser globalLinkParser;
    private final GitHubClient gitHubClient;
    private final StackoverflowClient stackoverflowClient;
    private final ObjectMapper objectMapper;

    @Override
    public Optional<Link> add(long tgChatId, String url) {
        try {
            URI uri = URI.create(url);
            Map<String, String> map = globalLinkParser.parse(uri);

            if (map == null) {
                return Optional.empty();
            }

            Link link = createLinkFromUrlMap(url, map);

            if (link == null) {
                return Optional.empty();
            }

            Link savedLink = linkRepo.saveIfAbsentOrReturnExisting(link);
            chatLinkRepo.addLinkToChat(tgChatId, savedLink.getLinkId());

            return Optional.of(savedLink);
        } catch (Exception e) {
            throw new RuntimeException("Error while adding link", e);
        }
    }

    private Link createLinkFromUrlMap(String url, Map<String, String> map) throws JsonProcessingException {
        Link link = new Link();
        link.setUrl(url);

        OffsetDateTime lastUpdated;
        if (map.containsKey(OWNER) && map.containsKey(REPO)) {
            GithubRepoResponse githubRepoResponse = gitHubClient.getRepo(map.get(OWNER), map.get(REPO));
            String jsonProps = objectMapper.writeValueAsString(githubRepoResponse);
            link.setJsonProps(jsonProps);
            lastUpdated = githubRepoResponse.getUpdatedAt();
        } else if (map.containsKey(QUESTION_ID)) {
            StackoverflowQuestionResponse stackoverflowQuestionResponse =
                stackoverflowClient.getQuestionById(Long.parseLong(map.get(QUESTION_ID)));
            String jsonProps = objectMapper.writeValueAsString(stackoverflowQuestionResponse);
            link.setJsonProps(jsonProps);
            lastUpdated = stackoverflowQuestionResponse.getLastActivityDate();
        } else {
            return null;
        }

        link.setLastUpdated(lastUpdated);
        link.setLastScrapped(OffsetDateTime.now());

        return link;
    }

    @Override
    public boolean remove(long tgChatId, String url) {
        try {
            URI uri = URI.create(url);
            Optional<Link> linkToDelete = findLinkByChatIdAndUrl(tgChatId, uri);

            if (linkToDelete.isPresent()) {
                Link link = linkToDelete.get();
                return chatLinkRepo.removeLinkFromChat(tgChatId, link.getLinkId());
            }

            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error while removing link", e);
        }
    }

    private Optional<Link> findLinkByChatIdAndUrl(long tgChatId, URI uri) {
        List<Link> links = linkRepo.findLinksByChatId(tgChatId);
        return links.stream()
            .filter(link -> link.getUrl().equals(uri.toString()))
            .findFirst();
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        return linkRepo.findLinksByChatId(tgChatId);
    }

    @Override
    public Collection<Link> findAll() {
        return linkRepo.findAll();
    }

    @Override
    public List<Chat> findFollowers(String url) {
        return chatRepo.findChatsByLikeLink(url);
    }

    @Override
    public List<Link> findLinksToScrapSince(Duration checkInterval) {
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime lastScrapped = now.minus(checkInterval);
        return linkRepo.findLinksByLastScrappedBefore(lastScrapped);
    }

    @Override
    public void batchUpdate(Collection<Link> links) {
        linkRepo.updateAll(links);
    }
}
