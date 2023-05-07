package ru.tinkoff.edu.java.link_parser.parsers;

import java.net.URI;
import java.util.Map;

public sealed interface LinkParser permits GithubLinkParser, StackoverflowLinkParser {
    Map<String, String> parse(URI link);

    boolean canParse(URI link);
}
