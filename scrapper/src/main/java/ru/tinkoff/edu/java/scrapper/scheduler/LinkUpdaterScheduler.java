package ru.tinkoff.edu.java.scrapper.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.link_parser.parsers.GlobalLinkParser;
import ru.tinkoff.edu.java.scrapper.clients.BotClient;
import ru.tinkoff.edu.java.scrapper.clients.GitHubClient;
import ru.tinkoff.edu.java.scrapper.clients.StackoverflowClient;
import ru.tinkoff.edu.java.scrapper.clients.responses.GithubRepoResponse;
import ru.tinkoff.edu.java.scrapper.clients.responses.StackoverflowQuestionResponse;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.services.LinkService;
import ru.tinkoff.edu.java.scrapper.services.TgChatService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Component
@EnableScheduling
@Log4j2
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private int iteration = 0;
    private final LinkService linkService;
    private final TgChatService tgChatService;
    private final BotClient botClient;
    private final GitHubClient gitHubClient;
    private final StackoverflowClient stackoverflowClient;
    private final GlobalLinkParser globalLinkParser;

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        int currentIteration = ++iteration;
        log.debug("{}th iteration of link update process started", currentIteration);

        Collection<Link> links = linkService.findAll();
        Map<Link, String> updatedLinksWithDescription = new HashMap<>();
        for (Link link : links) {
            String linkString = link.getUrl();
            try {
                URL url = new URL(linkString);
                String host = url.getHost();
                if (host.equals("github.com")) {
                    Map<String, String> parsedLink = globalLinkParser.parse(linkString);
                    String owner = parsedLink.get("owner");
                    String repo = parsedLink.get("repo");
                    GithubRepoResponse githubRepoResponse = gitHubClient.getRepo(owner, repo);
                    if (githubRepoResponse.getUpdatedAt().isAfter(link.getLastUpdated())) {
                        StringBuilder updateDescription = new StringBuilder();
                        updateDescription.append("Repository ").append(githubRepoResponse.getFullName()).append(" has a new update!\n");
                        // TODO: add more info about update
                        updatedLinksWithDescription.put(link, updateDescription.toString());
                    }
                } else if (host.equals("stackoverflow.com")) {
                    Map<String, String> parsedLink = globalLinkParser.parse(linkString);
                    String questionId = parsedLink.get("questionId");
                    Long questionIdLong = Long.parseLong(questionId);
                    StackoverflowQuestionResponse stackoverflowQuestionResponse = stackoverflowClient.getQuestionById(questionIdLong);
                    if (stackoverflowQuestionResponse.getLastActivityDate().isAfter(link.getLastUpdated())) {
                        StringBuilder updateDescription = new StringBuilder();
                        updateDescription.append("Question ").append(stackoverflowQuestionResponse.getTitle()).append(" has a new update!\n");
                        // TODO: add more info about update
                        updatedLinksWithDescription.put(link, updateDescription.toString());
                    }
                } else {
                    log.warn("Link {} is not supported", linkString);
                }
            } catch (MalformedURLException e) {
                log.warn("Link {} is malformed", linkString);
            }
        }

        for (Map.Entry<Link, String> entry : updatedLinksWithDescription.entrySet()) {
            Link link = entry.getKey();
            String description = entry.getValue();
            List<Chat> chats = linkService.findFollowers(link.getUrl());
            List<Long> tgChatIds = chats.stream().map(Chat::getChatId).toList();
            botClient.updateLink(link.getLinkId(), link.getUrl(), description, tgChatIds);
        }

        log.debug("{}th iteration of link update process finished", currentIteration);
    }
}
