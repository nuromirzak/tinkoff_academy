package ru.tinkoff.edu.java.scrapper.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.link_parser.parsers.GlobalLinkParser;
import ru.tinkoff.edu.java.scrapper.clients.GitHubClient;
import ru.tinkoff.edu.java.scrapper.clients.StackoverflowClient;
import ru.tinkoff.edu.java.scrapper.configurations.ApplicationConfiguration;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.dtos.requests.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.dtos.responses.GithubRepoResponse;
import ru.tinkoff.edu.java.scrapper.dtos.responses.StackoverflowQuestionResponse;
import ru.tinkoff.edu.java.scrapper.services.LinkService;
import ru.tinkoff.edu.java.scrapper.services.sender.LinkUpdateSender;

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
    private final GitHubClient gitHubClient;
    private final StackoverflowClient stackoverflowClient;
    private final GlobalLinkParser globalLinkParser;
    private final ApplicationConfiguration applicationConfiguration;
    private final LinkUpdateSender linkUpdateSender;

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        int currentIteration = ++iteration;
        log.info("{}th iteration of link update process started", currentIteration);

        Collection<Link> links = linkService.findLinksToScrap(applicationConfiguration.scheduler().checkInterval());
        Map<Link, String> updatedLinksWithDescription = new HashMap<>();
        for (Link link : links) {
            String linkString = link.getUrl();
            URI uri = URI.create(linkString);

            if (link.getJsonProps() instanceof GithubRepoResponse oldGithubRepoResponse) {
                Map<String, String> parsedLink = globalLinkParser.parse(uri);
                String owner = parsedLink.get("owner");
                String repo = parsedLink.get("repo");
                GithubRepoResponse githubRepoResponse = gitHubClient.getRepo(owner, repo);
                if (githubRepoResponse.getUpdatedAt().isAfter(link.getLastUpdated())) {
                    String updateMessage = oldGithubRepoResponse.getDifferenceMessageBetween(githubRepoResponse);
                    updatedLinksWithDescription.put(link, updateMessage);
                }
            } else if (link.getJsonProps() instanceof StackoverflowQuestionResponse oldStackoverflowQuestionResponse) {
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
            LinkUpdateRequest linkUpdateRequest =
                    new LinkUpdateRequest(link.getLinkId(), link.getUrl(), description, tgChatIds);
            linkUpdateSender.send(linkUpdateRequest);
        }

        log.info("{}th iteration of link update process finished", currentIteration);
    }
}
