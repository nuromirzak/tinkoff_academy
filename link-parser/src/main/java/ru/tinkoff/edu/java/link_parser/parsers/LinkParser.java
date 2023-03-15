package ru.tinkoff.edu.java.link_parser.parsers;

import java.util.Map;

public sealed interface LinkParser permits GlobalLinkParser, GithubLinkParser, StackoverflowLinkParser {
    public Map<String, String> parse(String link);

    public boolean canParse(String link);
}
