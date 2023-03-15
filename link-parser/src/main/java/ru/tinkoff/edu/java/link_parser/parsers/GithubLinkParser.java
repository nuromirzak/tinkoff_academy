package ru.tinkoff.edu.java.link_parser.parsers;

import ru.tinkoff.edu.java.link_parser.utils.LinkParserHelper;
import ru.tinkoff.edu.java.link_parser.validators.GithubLinkValidator;

import java.util.HashMap;
import java.util.Map;

public final class GithubLinkParser implements LinkParser {
    private static final GithubLinkValidator validator = new GithubLinkValidator();
    @Override
    public Map<String, String> parse(String link) {
        Map<String, String> result = new HashMap<>();

        result.put("owner", LinkParserHelper.getPathSegmentByIndex(link, 0));
        result.put("repo", LinkParserHelper.getPathSegmentByIndex(link, 1));

        return result;
    }

    @Override
    public boolean canParse(String link) {
        return validator.validate(link);
    }

    @Override
    public String toString() {
        return "GithubLinkParser{}";
    }
}
