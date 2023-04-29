package ru.tinkoff.edu.java.scrapper.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dtos.responses.GithubRepoResponse;

@Service
@RequiredArgsConstructor
public class GitHubClient {
    private final WebClient githubWebClient;

    public GithubRepoResponse getRepo(String owner, String repo) {
        return githubWebClient.get()
                .uri("/repos/{owner}/{repo}", owner, repo)
                .retrieve()
                .bodyToMono(GithubRepoResponse.class)
                .block();
    }
}
