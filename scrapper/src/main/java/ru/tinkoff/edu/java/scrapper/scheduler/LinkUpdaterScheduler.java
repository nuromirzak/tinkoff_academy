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
import ru.tinkoff.edu.java.scrapper.configurations.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.dtos.responses.GithubRepoResponse;
import ru.tinkoff.edu.java.scrapper.dtos.responses.StackoverflowQuestionResponse;
import ru.tinkoff.edu.java.scrapper.services.LinkService;
import ru.tinkoff.edu.java.scrapper.services.TgChatService;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final ApplicationConfig applicationConfig;

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        int currentIteration = ++iteration;
        log.info("{}th iteration of link update process started", currentIteration);

        Collection<Link> links = linkService.findLinksToScrap(applicationConfig.scheduler().checkInterval());
        Map<Link, String> updatedLinksWithDescription = new HashMap<>();
        for (Link link : links) {
            String linkString = link.getUrl();
            URI uri = URI.create(linkString);
            String host = uri.getHost();
            if (host.equals("github.com")) {
                GithubRepoResponse oldGithubRepoResponse = (GithubRepoResponse) link.getJsonProps();
                Map<String, String> parsedLink = globalLinkParser.parse(uri);
                String owner = parsedLink.get("owner");
                String repo = parsedLink.get("repo");
                GithubRepoResponse githubRepoResponse = gitHubClient.getRepo(owner, repo);
                if (githubRepoResponse.getUpdatedAt().isAfter(link.getLastUpdated())) {
                    String updateMessage = oldGithubRepoResponse.getDifferenceMessageBetween(githubRepoResponse);
                    updatedLinksWithDescription.put(link, updateMessage);
                }
            } else if (host.equals("stackoverflow.com")) {
                StackoverflowQuestionResponse oldStackoverflowQuestionResponse = (StackoverflowQuestionResponse) link.getJsonProps();
                Map<String, String> parsedLink = globalLinkParser.parse(uri);
                String questionId = parsedLink.get("questionId");
                Long questionIdLong = Long.parseLong(questionId);
                StackoverflowQuestionResponse stackoverflowQuestionResponse = stackoverflowClient.getQuestionById(questionIdLong);
                if (stackoverflowQuestionResponse.getLastActivityDate().isAfter(link.getLastUpdated())) {
                    String updateMessage = oldStackoverflowQuestionResponse.getDifferenceMessageBetween(stackoverflowQuestionResponse);
                    updatedLinksWithDescription.put(link, updateMessage);
                }
            } else {
                log.warn("Link {} is not supported", linkString);
            }
        }

        for (Map.Entry<Link, String> entry : updatedLinksWithDescription.entrySet()) {
            Link link = entry.getKey();
            String description = entry.getValue();
            List<Chat> chats = linkService.findFollowers(link.getUrl());
            List<Long> tgChatIds = chats.stream().map(Chat::getChatId).toList();
            botClient.updateLink(link.getLinkId(), link.getUrl(), description, tgChatIds);
        }

        log.info("{}th iteration of link update process finished", currentIteration);
    }
}
