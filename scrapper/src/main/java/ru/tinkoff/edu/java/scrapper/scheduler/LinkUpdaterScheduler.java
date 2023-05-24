package ru.tinkoff.edu.java.scrapper.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

@Component
@EnableScheduling
@Log4j2
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    public static final String LINK_WAS_UPDATED = "link {} was updated";
    public static final String LINK_WAS_NOT_UPDATED = "link {} was not updated";
    private static final String LINK_NOT_SUPPORTED = "Link {} is not supported";
    private final LinkService linkService;
    private final GitHubClient gitHubClient;
    private final StackoverflowClient stackoverflowClient;
    private final GlobalLinkParser globalLinkParser;
    private final ApplicationConfiguration applicationConfiguration;
    private final LinkUpdateSender linkUpdateSender;
    private final ObjectMapper objectMapper;
    private int iteration = 0;

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() throws JsonProcessingException {
        int currentIteration = ++iteration;
        log.info("{}th iteration of link update process started", currentIteration);

        Collection<Link> links = getLinksToScrap();
        log.info("links to scrap: {}", links);

        Map<Link, String> updatedLinksWithDescription = updateLinksAndGetDescriptions(links);
        linkService.batchUpdate(links);

        sendLinkUpdateRequests(updatedLinksWithDescription);

        log.info("{}th iteration of link update process finished", currentIteration);
    }

    private Collection<Link> getLinksToScrap() {
        return linkService.findLinksToScrapSince(applicationConfiguration.scheduler().checkInterval());
    }

    private Map<Link, String> updateLinksAndGetDescriptions(Collection<Link> links) throws JsonProcessingException {
        Map<Link, String> updatedLinksWithDescription = new HashMap<>();
        for (Link link : links) {
            String linkString = link.getUrl();
            URI uri = URI.create(linkString);
            String host = uri.getHost();
            String updateMessage;

            if (host.equals("github.com")) {
                updateMessage = updateGithubLink(link, uri);
            } else if (host.equals("stackoverflow.com")) {
                updateMessage = updateStackoverflowLink(link, uri);
            } else {
                log.warn(LINK_NOT_SUPPORTED, linkString);
                continue;
            }
            link.setLastScrapped(OffsetDateTime.now());

            if (updateMessage != null) {
                updatedLinksWithDescription.put(link, updateMessage);
            }
        }
        return updatedLinksWithDescription;
    }

    private String updateGithubLink(Link link, URI uri) throws JsonProcessingException {
        String oldGithubRepoResponseString = link.getJsonProps();
        GithubRepoResponse oldGithubRepoResponse =
            objectMapper.readValue(oldGithubRepoResponseString, GithubRepoResponse.class);

        Map<String, String> parsedLink = globalLinkParser.parse(uri);
        String owner = parsedLink.get("owner");
        String repo = parsedLink.get("repo");
        GithubRepoResponse githubRepoResponse = gitHubClient.getRepo(owner, repo);

        if (githubRepoResponse.getUpdatedAt().isAfter(link.getLastUpdated())) {
            String newGithubRepoResponseString = objectMapper.writeValueAsString(githubRepoResponse);
            link.setJsonProps(newGithubRepoResponseString);
            link.setLastUpdated(githubRepoResponse.getUpdatedAt());

            log.info(LINK_WAS_UPDATED, link.getUrl());
            return oldGithubRepoResponse.getDifferenceMessageBetween(githubRepoResponse);
        } else {
            log.info(LINK_WAS_NOT_UPDATED, link.getUrl());
            return null;
        }
    }

    private String updateStackoverflowLink(Link link, URI uri) throws JsonProcessingException {
        String oldStackoverflowQuestionResponseString = link.getJsonProps();
        StackoverflowQuestionResponse oldStackoverflowQuestionResponse =
            objectMapper.readValue(oldStackoverflowQuestionResponseString, StackoverflowQuestionResponse.class);

        Map<String, String> parsedLink = globalLinkParser.parse(uri);
        String questionId = parsedLink.get("questionId");
        Long questionIdLong = Long.parseLong(questionId);
        StackoverflowQuestionResponse stackoverflowQuestionResponse =
            stackoverflowClient.getQuestionById(questionIdLong);

        if (stackoverflowQuestionResponse.getLastActivityDate().isAfter(link.getLastUpdated())) {
            String newStackoverflowQuestionResponseString =
                objectMapper.writeValueAsString(stackoverflowQuestionResponse);
            link.setJsonProps(newStackoverflowQuestionResponseString);
            link.setLastUpdated(stackoverflowQuestionResponse.getLastActivityDate());

            log.info(LINK_WAS_UPDATED, link.getUrl());
            return oldStackoverflowQuestionResponse.getDifferenceMessageBetween(stackoverflowQuestionResponse);
        } else {
            log.info(LINK_WAS_NOT_UPDATED, link.getUrl());
            return null;
        }
    }

    private void sendLinkUpdateRequests(Map<Link, String> updatedLinksWithDescription) {
        for (Map.Entry<Link, String> entry : updatedLinksWithDescription.entrySet()) {
            Link link = entry.getKey();
            String description = entry.getValue();
            List<Chat> chats = linkService.findFollowers(link.getUrl());
            List<Long> tgChatIds = chats.stream().map(Chat::getChatId).toList();
            LinkUpdateRequest linkUpdateRequest =
                new LinkUpdateRequest(link.getLinkId(), link.getUrl(), description, tgChatIds);
            linkUpdateSender.send(linkUpdateRequest);
        }
    }

}
