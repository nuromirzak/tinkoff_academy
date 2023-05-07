package ru.tinkoff.edu.java.scrapper.dtos.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class GithubRepoResponse extends LinkProperties {
    public static final String DISCRIMINATOR = "link_properties.github_repository";

    private static final String TO = "\" to \"";
    private static final String DOT_NEWLINE = "\".\n";

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
        updateDescription.append("Repository ").append(newGithubRepoResponse.getFullName())
            .append(" has a new update!\n");
        if (!newGithubRepoResponse.getDescription().equals(description)) {
            updateDescription.append("Description has been changed from \"").append(description).append(TO)
                .append(newGithubRepoResponse.getDescription()).append(DOT_NEWLINE);
        }
        if (!Objects.equals(newGithubRepoResponse.getHomepage(), homepage)) {
            updateDescription.append("Homepage has been changed from \"").append(homepage).append(TO)
                .append(newGithubRepoResponse.getHomepage()).append(DOT_NEWLINE);
        }
        if (newGithubRepoResponse.getStargazersCount() != stargazersCount) {
            updateDescription.append("Stargazers count has been changed from \"").append(stargazersCount).append(TO)
                .append(newGithubRepoResponse.getStargazersCount()).append(DOT_NEWLINE);
        }
        if (newGithubRepoResponse.getWatchersCount() != watchersCount) {
            updateDescription.append("Watchers count has been changed from \"").append(watchersCount).append(TO)
                .append(newGithubRepoResponse.getWatchersCount()).append(DOT_NEWLINE);
        }
        if (newGithubRepoResponse.getForksCount() != forksCount) {
            updateDescription.append("Forks count has been changed from \"").append(forksCount).append(TO)
                .append(newGithubRepoResponse.getForksCount()).append(DOT_NEWLINE);
        }
        if (!Boolean.valueOf(newGithubRepoResponse.isArchived()).equals(archived)) {
            updateDescription.append("Archived has been changed from \"").append(archived).append(TO)
                .append(newGithubRepoResponse.isArchived()).append(DOT_NEWLINE);
        }
        if (!Boolean.valueOf(newGithubRepoResponse.isDisabled()).equals(disabled)) {
            updateDescription.append("Disabled has been changed from \"").append(disabled).append(TO)
                .append(newGithubRepoResponse.isDisabled()).append(DOT_NEWLINE);
        }
        if (newGithubRepoResponse.getOpenIssuesCount() != openIssuesCount) {
            updateDescription.append("Open issues count has been changed from \"").append(openIssuesCount).append(TO)
                .append(newGithubRepoResponse.getOpenIssuesCount()).append(DOT_NEWLINE);
        }
        if (!Objects.equals(newGithubRepoResponse.getTopics(), topics)) {
            updateDescription.append("Topics has been changed from \"").append(topics).append(TO)
                .append(newGithubRepoResponse.getTopics()).append(DOT_NEWLINE);
        }
        return updateDescription.toString();
    }

    @Override
    public String getType() {
        return DISCRIMINATOR;
    }
}
