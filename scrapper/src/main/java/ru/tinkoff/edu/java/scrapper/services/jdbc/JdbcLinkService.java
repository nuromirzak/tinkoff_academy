package ru.tinkoff.edu.java.scrapper.services.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.link_parser.parsers.GlobalLinkParser;
import ru.tinkoff.edu.java.scrapper.clients.GitHubClient;
import ru.tinkoff.edu.java.scrapper.clients.StackoverflowClient;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.dtos.responses.GithubRepoResponse;
import ru.tinkoff.edu.java.scrapper.dtos.responses.StackoverflowQuestionResponse;
import ru.tinkoff.edu.java.scrapper.repo.JdbcChatRepo;
import ru.tinkoff.edu.java.scrapper.repo.JdbcLinkRepo;
import ru.tinkoff.edu.java.scrapper.services.LinkService;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcLinkRepo jdbcLinkRepo;
    private final JdbcChatRepo jdbcChatRepo;
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

        long linkId = jdbcLinkRepo.add(link);
        link.setLinkId(linkId);

        System.out.println("link=" + link);

        jdbcChatRepo.addLinkToChat(tgChatId, linkId);

        return link;
    }

    @Override
    public boolean remove(long tgChatId, String url) {
        Link link = new Link();
        link.setUrl(url);

        long linkId = jdbcLinkRepo.add(link);

        return jdbcChatRepo.removeLinkFromChat(tgChatId, linkId);
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        return jdbcChatRepo.findLinksByChatId(tgChatId);
    }

    @Override
    public Collection<Link> findAll() {
        return jdbcLinkRepo.findAll();
    }

    @Override
    public List<Chat> findFollowers(String url) {
        return jdbcChatRepo.findChatsByLikeLink(url);
    }

    public static void main(String[] args) {
        String hello = "Hello, world!";
        URI uri = URI.create(hello);
        System.out.println(uri);
    }
}
