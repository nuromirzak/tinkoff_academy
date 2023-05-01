package ru.tinkoff.edu.java.scrapper.dtos.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class GithubRepoResponse extends LinkProperties {
    public static final String DISCRIMINATOR = "link_properties.github_repository";

    private String fullName;
    private String description;
    private String htmlUrl;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private OffsetDateTime pushedAt;
    private String homepage;
    private int stargazersCount;
    private int watchersCount;
    private int forksCount;
    private boolean archived;
    private boolean disabled;
    private int openIssuesCount;
    private List<String> topics;

    public String getDifferenceMessageBetween(GithubRepoResponse newGithubRepoResponse) {
        StringBuilder updateDescription = new StringBuilder();
        updateDescription.append("Repository ").append(newGithubRepoResponse.getFullName()).append(" has a new update!\n");
        if (!newGithubRepoResponse.getDescription().equals(description)) {
            updateDescription.append("Description has been changed from \"").append(description).append("\" to \"").append(newGithubRepoResponse.getDescription()).append("\".\n");
        }
        if (!Objects.equals(newGithubRepoResponse.getHomepage(), homepage)) {
            updateDescription.append("Homepage has been changed from \"").append(homepage).append("\" to \"").append(newGithubRepoResponse.getHomepage()).append("\".\n");
        }
        if (newGithubRepoResponse.getStargazersCount() != stargazersCount) {
            updateDescription.append("Stargazers count has been changed from \"").append(stargazersCount).append("\" to \"").append(newGithubRepoResponse.getStargazersCount()).append("\".\n");
        }
        if (newGithubRepoResponse.getWatchersCount() != watchersCount) {
            updateDescription.append("Watchers count has been changed from \"").append(watchersCount).append("\" to \"").append(newGithubRepoResponse.getWatchersCount()).append("\".\n");
        }
        if (newGithubRepoResponse.getForksCount() != forksCount) {
            updateDescription.append("Forks count has been changed from \"").append(forksCount).append("\" to \"").append(newGithubRepoResponse.getForksCount()).append("\".\n");
        }
        if (!Boolean.valueOf(newGithubRepoResponse.isArchived()).equals(archived)) {
            updateDescription.append("Archived has been changed from \"").append(archived).append("\" to \"").append(newGithubRepoResponse.isArchived()).append("\".\n");
        }
        if (!Boolean.valueOf(newGithubRepoResponse.isDisabled()).equals(disabled)) {
            updateDescription.append("Disabled has been changed from \"").append(disabled).append("\" to \"").append(newGithubRepoResponse.isDisabled()).append("\".\n");
        }
        if (newGithubRepoResponse.getOpenIssuesCount() != openIssuesCount) {
            updateDescription.append("Open issues count has been changed from \"").append(openIssuesCount).append("\" to \"").append(newGithubRepoResponse.getOpenIssuesCount()).append("\".\n");
        }
        if (!Objects.equals(newGithubRepoResponse.getTopics(), topics)) {
            updateDescription.append("Topics has been changed from \"").append(topics).append("\" to \"").append(newGithubRepoResponse.getTopics()).append("\".\n");
        }
        return updateDescription.toString();
    }

    @Override
    public String getType() {
        return DISCRIMINATOR;
    }
}
