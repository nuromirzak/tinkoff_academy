package ru.tinkoff.edu.java.scrapper.clients.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class GithubRepoResponse {
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
    private String license;
    private List<String> topics;
}
