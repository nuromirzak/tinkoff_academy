package ru.tinkoff.edu.java.link_parser.parsers;

import java.net.URI;
import java.util.Map;

public sealed interface LinkParser permits GithubLinkParser, StackoverflowLinkParser {
    /**
     * @param link ссылка, которую нужно распарсить
     * @return Map если ссылка соответствует формату, иначе null
     */
    public Map<String, String> parse(URI link);

    /**
     * Этот метод не должен кидать исключения
     *
     * @param link ссылка, которую нужно проверить
     * @return true если ссылка соответствует формату, иначе false
     */
    public boolean canParse(URI link);
}
