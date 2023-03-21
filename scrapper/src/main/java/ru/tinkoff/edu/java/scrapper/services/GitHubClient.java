package ru.tinkoff.edu.java.scrapper.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dtos.GithubRepoResponse;

@Service
public class GitHubClient {
    private WebClient webClient;

    public GithubRepoResponse getRepo(String owner, String repo) {
        return webClient.get()
                .uri("/repos/{owner}/{repo}", owner, repo)
                .retrieve()
                .bodyToMono(GithubRepoResponse.class)
                .block();
    }

    @Autowired
    public void setWebClient(@Qualifier("github_client") WebClient webClient) {
        this.webClient = webClient;
    }
}
