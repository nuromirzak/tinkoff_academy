package ru.tinkoff.edu.java.scrapper.dtos.responses;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StackoverflowQuestionResponse {
    private Long ownerId;
    private String ownerName;
    private String title;
    private Integer answerCount;
    private Integer score;
    private List<String> tags;
    private OffsetDateTime creationDate;
    private OffsetDateTime lastActivityDate;

    public String getDifferenceMessageBetween(StackoverflowQuestionResponse newStackoverflowQuestionResponse) {
        StringBuilder updateDescription = new StringBuilder();
        updateDescription.append("Вопрос \"")
            .append(newStackoverflowQuestionResponse.getTitle())
            .append("\" обновлен!\n")
            .append(getTitleDifference(newStackoverflowQuestionResponse))
            .append(getAnswerCountDifference(newStackoverflowQuestionResponse))
            .append(getScoreDifference(newStackoverflowQuestionResponse))
            .append(getTagsDifference(newStackoverflowQuestionResponse));

        return updateDescription.toString();
    }

    private String getTitleDifference(StackoverflowQuestionResponse newStackoverflowQuestionResponse) {
        if (!Objects.equals(newStackoverflowQuestionResponse.getTitle(), title)) {
            return new StringBuilder().append("Заголовок изменен с \"")
                .append(title)
                .append("\" на \"")
                .append(newStackoverflowQuestionResponse.getTitle())
                .append("\"\n")
                .toString();
        }
        return "";
    }

    private String getAnswerCountDifference(StackoverflowQuestionResponse newStackoverflowQuestionResponse) {
        if (!Objects.equals(newStackoverflowQuestionResponse.getAnswerCount(), answerCount)) {
            return getNumericDifferenceMessage("Количество ответов",
                newStackoverflowQuestionResponse.getAnswerCount(), answerCount);
        }
        return "";
    }

    private String getScoreDifference(StackoverflowQuestionResponse newStackoverflowQuestionResponse) {
        if (!Objects.equals(newStackoverflowQuestionResponse.getScore(), score)) {
            return getNumericDifferenceMessage("Оценка", newStackoverflowQuestionResponse.getScore(), score);
        }
        return "";
    }

    private String getTagsDifference(StackoverflowQuestionResponse newStackoverflowQuestionResponse) {
        if (!Objects.equals(newStackoverflowQuestionResponse.getTags(), tags)) {
            List<String> addedTags = new ArrayList<>(newStackoverflowQuestionResponse.getTags());
            addedTags.removeAll(tags);
            List<String> removedTags = new ArrayList<>(tags);
            removedTags.removeAll(newStackoverflowQuestionResponse.getTags());

            StringBuilder tagDifference = new StringBuilder();

            if (!addedTags.isEmpty()) {
                tagDifference.append("Добавлены теги: ").append(addedTags).append("\n");
            }
            if (!removedTags.isEmpty()) {
                tagDifference.append("Удалены теги: ").append(removedTags).append("\n");
            }

            return tagDifference.toString();
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
