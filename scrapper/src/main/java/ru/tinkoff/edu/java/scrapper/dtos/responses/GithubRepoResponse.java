package ru.tinkoff.edu.java.scrapper.dtos.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class GithubRepoResponse {
    private static final String TO = "\" на \"";
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
        updateDescription.append("Репозиторий \"")
            .append(newGithubRepoResponse.getFullName())
            .append("\" обновлен!\n")
            .append(getDescriptionDifference(newGithubRepoResponse))
            .append(getHomepageDifference(newGithubRepoResponse))
            .append(getStargazersCountDifference(newGithubRepoResponse))
            .append(getWatchersCountDifference(newGithubRepoResponse))
            .append(getForksCountDifference(newGithubRepoResponse))
            .append(getArchivedDifference(newGithubRepoResponse))
            .append(getDisabledDifference(newGithubRepoResponse))
            .append(getOpenIssuesCountDifference(newGithubRepoResponse))
            .append(getTopicsDifference(newGithubRepoResponse));

        return updateDescription.toString();
    }

    private String getDescriptionDifference(GithubRepoResponse newGithubRepoResponse) {
        if (!newGithubRepoResponse.getDescription().equals(description)) {
            StringBuilder descriptionDifference = new StringBuilder();
            descriptionDifference.append("Описание изменено с \"")
                .append(description)
                .append(TO)
                .append(newGithubRepoResponse.getDescription())
                .append(DOT_NEWLINE);
            return descriptionDifference.toString();
        }
        return "";
    }

    private String getHomepageDifference(GithubRepoResponse newGithubRepoResponse) {
        if (!Objects.equals(newGithubRepoResponse.getHomepage(), homepage)) {
            return new StringBuilder()
                .append("Домашняя страница изменена с \"")
                .append(homepage)
                .append(TO)
                .append(newGithubRepoResponse.getHomepage())
                .append(DOT_NEWLINE)
                .toString();
        }
        return "";
    }

    private String getStargazersCountDifference(GithubRepoResponse newGithubRepoResponse) {
        if (newGithubRepoResponse.getStargazersCount() != stargazersCount) {
            return getNumericDifferenceMessage("Количество звезд", stargazersCount,
                newGithubRepoResponse.getStargazersCount());
        }
        return "";
    }

    private String getWatchersCountDifference(GithubRepoResponse newGithubRepoResponse) {
        if (newGithubRepoResponse.getWatchersCount() != watchersCount) {
            return getNumericDifferenceMessage("Количество наблюдателей", watchersCount,
                newGithubRepoResponse.getWatchersCount());
        }
        return "";
    }

    private String getForksCountDifference(GithubRepoResponse newGithubRepoResponse) {
        if (newGithubRepoResponse.getForksCount() != forksCount) {
            return getNumericDifferenceMessage("Количество форков", forksCount,
                newGithubRepoResponse.getForksCount());
        }
        return "";
    }

    private String getArchivedDifference(GithubRepoResponse newGithubRepoResponse) {
        if (!Boolean.valueOf(newGithubRepoResponse.isArchived()).equals(archived)) {
            if (newGithubRepoResponse.isArchived()) {
                return "Репозиторий был архивирован\n";
            } else {
                return "Репозиторий был разархивирован\n";
            }
        }
        return "";
    }

    private String getDisabledDifference(GithubRepoResponse newGithubRepoResponse) {
        if (!Boolean.valueOf(newGithubRepoResponse.isDisabled()).equals(disabled)) {
            if (newGithubRepoResponse.isDisabled()) {
                return "Репозиторий был деактивирован\n";
            } else {
                return "Репозиторий был активирован\n";
            }
        }
        return "";
    }

    private String getOpenIssuesCountDifference(GithubRepoResponse newGithubRepoResponse) {
        if (newGithubRepoResponse.getOpenIssuesCount() != openIssuesCount) {
            return getNumericDifferenceMessage("Количество тикетов", openIssuesCount,
                newGithubRepoResponse.getOpenIssuesCount());
        }
        return "";
    }

    private String getTopicsDifference(GithubRepoResponse newGithubRepoResponse) {
        if (!Objects.equals(newGithubRepoResponse.getTopics(), topics)) {
            List<String> addedTopics = new ArrayList<>(newGithubRepoResponse.getTopics());
            addedTopics.removeAll(topics);
            List<String> removedTopics = new ArrayList<>(topics);
            removedTopics.removeAll(newGithubRepoResponse.getTopics());
            StringBuilder topicsUpdate = new StringBuilder();
            if (!addedTopics.isEmpty()) {
                topicsUpdate.append("Добавлены темы: ").append(addedTopics).append("\n");
            }
            if (!removedTopics.isEmpty()) {
                topicsUpdate.append("Удалены темы: ").append(removedTopics).append("\n");
            }
            return topicsUpdate.toString();
        }
        return "";
    }

    private String getNumericDifferenceMessage(String prefix, int newValue, int oldValue) {
        String[] words = prefix.split(" ");
        String firstWord = words[0];
        boolean isFeminine = firstWord.endsWith("а") || firstWord.endsWith("я") || firstWord.endsWith("ь");

        String increased = isFeminine ? "увеличилась с " : "увеличилось с ";
        String decreased = isFeminine ? "уменьшилась с " : "уменьшилось с ";
        String difference = newValue > oldValue ? increased : decreased;

        return new StringBuilder(prefix)
            .append(" ")
            .append(difference)
            .append(oldValue)
            .append(" до ")
            .append(newValue)
            .append("\n")
            .toString();
    }
}
