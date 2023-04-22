package ru.tinkoff.edu.java.scrapper.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.link_parser.parsers.GlobalLinkParser;
import ru.tinkoff.edu.java.scrapper.clients.GitHubClient;
import ru.tinkoff.edu.java.scrapper.clients.StackoverflowClient;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.dtos.responses.GithubRepoResponse;
import ru.tinkoff.edu.java.scrapper.dtos.responses.StackoverflowQuestionResponse;
import ru.tinkoff.edu.java.scrapper.repo.ChatRepo;
import ru.tinkoff.edu.java.scrapper.repo.LinkRepo;
import ru.tinkoff.edu.java.scrapper.services.LinkService;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {
    private final LinkRepo linkRepo;
    private final ChatRepo chatRepo;
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
            if (map.containsKey("owner") && map.containsKey("repo")) {
                GithubRepoResponse githubRepoResponse = gitHubClient.getRepo(map.get("owner"), map.get("repo"));
                link.setJsonProps(githubRepoResponse);
            } else if (map.containsKey("questions")) {
                StackoverflowQuestionResponse stackoverflowQuestionResponse = stackoverflowClient.getQuestionById(Long.parseLong(map.get("questionId")));
                link.setJsonProps(stackoverflowQuestionResponse);
            }
        } catch (Exception e) {
            System.out.println("Error while parsing url: " + url + " " + e.getMessage());
            return null;
        }

        long linkId = linkRepo.add(link);
        link.setLinkId(linkId);

        System.out.println("link=" + link);

        chatRepo.addLinkToChat(tgChatId, linkId);

        return link;
    }

    @Override
    public boolean remove(long tgChatId, String url) {
        Link link = new Link();
        link.setUrl(url);

        long linkId = linkRepo.add(link);

        return chatRepo.removeLinkFromChat(tgChatId, linkId);
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        return chatRepo.findLinksByChatId(tgChatId);
    }

    @Override
    public Collection<Link> findAll() {
        return linkRepo.findAll();
    }

    @Override
    public List<Chat> findFollowers(String url) {
        return chatRepo.findChatsByLikeLink(url);
    }
}
