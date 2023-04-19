package ru.tinkoff.edu.java.scrapper.services.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.JSON;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.link_parser.parsers.GlobalLinkParser;
import ru.tinkoff.edu.java.scrapper.clients.GitHubClient;
import ru.tinkoff.edu.java.scrapper.clients.StackoverflowClient;
import ru.tinkoff.edu.java.scrapper.domain.jooq.Tables;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.services.LinkService;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JooqLinkService implements LinkService {
    private final DSLContext context;
    private final GlobalLinkParser globalLinkParser;
    private final GitHubClient gitHubClient;
    private final StackoverflowClient stackoverflowClient;

    @Override
    public Link add(long tgChatId, String url) {
        Object jsonProps = null;
        URI uri = null;
        try {
            uri = URI.create(url);
            Map<String, String> map = globalLinkParser.parse(uri);
            if (map == null) {
                return null;
            }
            if (map.containsKey("owner") && map.containsKey("repo")) {
                jsonProps = gitHubClient.getRepo(map.get("owner"), map.get("repo"));
            } else if (map.containsKey("questions")) {
                jsonProps = stackoverflowClient.getQuestionById(Long.parseLong(map.get("questionId")));
            }
        } catch (Exception e) {
            System.out.println("Error while parsing url: " + url + " " + e.getMessage());
            return null;
        }
        long id = context.insertInto(Tables.LINK)
                .set(Tables.LINK.URL, url)
                .set(Tables.LINK.JSON_PROPS, JSON.json(jsonProps.toString()))
                .execute();

        Link link = new Link();
        link.setLinkId(id);
        link.setUrl(url);
        link.setJsonProps(jsonProps);

        return link;
    }

    @Override
    public boolean remove(long tgChatId, String url) {
        // TODO: implement
        return false;
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        // TODO: implement
        return null;
    }

    @Override
    public Collection<Link> findAll() {
        // TODO: implement
        return null;
    }

    @Override
    public List<Chat> findFollowers(String url) {
        // TODO: implement
        return null;
    }
}
