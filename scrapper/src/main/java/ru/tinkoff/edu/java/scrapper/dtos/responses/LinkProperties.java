package ru.tinkoff.edu.java.scrapper.dtos.responses;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(
                name = GithubRepoResponse.DISCRIMINATOR,
                value = GithubRepoResponse.class
        ),
        @JsonSubTypes.Type(
                name = StackoverflowQuestionResponse.DISCRIMINATOR,
                value = StackoverflowQuestionResponse.class
        )
})
public abstract class LinkProperties {
    private String name;

    public LinkProperties() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
    public abstract String getType();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkProperties that)) return false;
        return Objects.equals(this, that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType());
    }
}
