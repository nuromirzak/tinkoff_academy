package ru.tinkoff.edu.java.scrapper.clients;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.clients.responses.StackoverflowQuestionResponse;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StackoverflowClient {
    private final WebClient webClient;

    @Autowired
    public StackoverflowClient(@Qualifier("stackoverflowWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public StackoverflowQuestionResponse getQuestionById(Long id) {
        return webClient.get()
                .uri(uri -> uri.path("/questions/{id}")
                        .queryParam("site", "stackoverflow")
                        .build(id))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(this::convertJsonToStackoverflowQuestion)
                .block();
    }

    private StackoverflowQuestionResponse convertJsonToStackoverflowQuestion(JsonNode jsonNode) {
        StackoverflowQuestionResponse question = new StackoverflowQuestionResponse();
        JsonNode itemsArray = jsonNode.path("items").get(0);
        JsonNode owner = itemsArray.path("owner");
        JsonNode tags = itemsArray.path("tags");

        question.setOwnerId(owner.path("user_id").asLong());
        question.setOwnerName(owner.path("display_name").asText());
        question.setTitle(itemsArray.path("title").asText());
        question.setAnswerCount(itemsArray.path("answer_count").asInt());
        question.setScore(itemsArray.path("score").asInt());

        List<String> tagsList = new ArrayList<>();
        tags.forEach(tag -> tagsList.add(tag.asText()));
        question.setTags(tagsList);

        Date creationDate = new Date(itemsArray.path("creation_date").asLong() * 1000);
        question.setCreationDate(creationDate.toInstant().atOffset(ZoneOffset.UTC));

        Date lastActivityDate = new Date(itemsArray.path("last_activity_date").asLong() * 1000);
        question.setLastActivityDate(lastActivityDate.toInstant().atOffset(ZoneOffset.UTC));

        return question;
    }
}

