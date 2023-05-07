package ru.tinkoff.edu.java.scrapper.services.impls;

import java.net.URI;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.link_parser.parsers.GlobalLinkParser;
import ru.tinkoff.edu.java.scrapper.clients.GitHubClient;
import ru.tinkoff.edu.java.scrapper.clients.StackoverflowClient;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.dtos.responses.GithubRepoResponse;
import ru.tinkoff.edu.java.scrapper.dtos.responses.StackoverflowQuestionResponse;
import ru.tinkoff.edu.java.scrapper.repo.ChatLinkRepo;
import ru.tinkoff.edu.java.scrapper.repo.ChatRepo;
import ru.tinkoff.edu.java.scrapper.repo.LinkRepo;
import ru.tinkoff.edu.java.scrapper.services.LinkService;

@Service
@RequiredArgsConstructor
@Log4j2
public class LinkServiceImpl implements LinkService {
    private static final String OWNER_KEY = "owner";
    private static final String REPO_KEY = "repo";
    private final LinkRepo linkRepo;
    private final ChatRepo chatRepo;
    private final ChatLinkRepo chatLinkRepo;
    private final GlobalLinkParser globalLinkParser;
    private final GitHubClient gitHubClient;
    private final StackoverflowClient stackoverflowClient;

    @Override
    public Link add(long tgChatId, String url) {
        Link link = new Link();
        link.setUrl(url);

        URI uri = null;
        try {
            uri = URI.create(url);
            Map<String, String> map = globalLinkParser.parse(uri);
            if (map == null) {
                return null;
            }
            if (map.containsKey(OWNER_KEY) && map.containsKey(REPO_KEY)) {
                GithubRepoResponse githubRepoResponse = gitHubClient.getRepo(map.get(OWNER_KEY), map.get(REPO_KEY));
                link.setJsonProps(githubRepoResponse);
            } else if (map.containsKey("questions")) {
                StackoverflowQuestionResponse stackoverflowQuestionResponse =
                    stackoverflowClient.getQuestionById(Long.parseLong(map.get("questionId")));
                link.setJsonProps(stackoverflowQuestionResponse);
            }
        } catch (Exception e) {
            log.error("Error while parsing url: " + url + " " + e.getMessage());
            return null;
        }

        long linkId = linkRepo.add(link);
        link.setLinkId(linkId);

        log.info("link=" + link);

        chatLinkRepo.addLinkToChat(tgChatId, linkId);

        return link;
    }

    @Override
    public boolean remove(long tgChatId, String url) {
        Link link = new Link();
        link.setUrl(url);

        long linkId = linkRepo.add(link);

        return chatLinkRepo.removeLinkFromChat(tgChatId, linkId);
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
    public List<Link> findLinksToScrap(Duration checkInterval) {
        return linkRepo.findLinksToScrap(checkInterval);
    }
}
