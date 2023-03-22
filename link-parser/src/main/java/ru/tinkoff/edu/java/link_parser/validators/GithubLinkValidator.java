package ru.tinkoff.edu.java.link_parser.validators;

import ru.tinkoff.edu.java.link_parser.parsers.LinkParser;
import ru.tinkoff.edu.java.link_parser.utils.LinkParserHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

public class GithubLinkValidator implements LinkValidator {
    private static final Set<String> invalidPaths = Set.of("ABOUT", "BLOG", "CODESPACES", "COLLECTIONS", "CONTACT",
            "CUSTOMER-STORIES", "EVENTS", "EXPLORE", "FEATURES", "ISSUES", "JOIN", "LOGIN", "LOGOUT", "MARKETPLACE",
            "NEW", "NONPROFIT", "NOTIFICATIONS", "PRICING", "PULLS", "SECURITY", "SETTINGS", "SPONSORS", "STARS",
            "TOPICS", "TRENDING", "WIKI");

    @Override
    public boolean validate(String link) {
        try {
            URL url = new URL(link);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        if (LinkParserHelper.containsSubdomain(link)) {
            throw new IllegalArgumentException("Link contains subdomain: " + link);
        }

        if (!LinkParserHelper.getTopLevelDomain(link).equals("github.com")) {
            throw new IllegalArgumentException("Link does not contain github.com as top level domain: " + link);
        }

        boolean hasAtLeastTwoPathSegments = LinkParserHelper.getAllPathSegments(link).size() >= 2;
        if (!hasAtLeastTwoPathSegments) {
            throw new IllegalArgumentException("Link does not contain at least two path segments: " + link);
        }

        String firstPathSegment = LinkParserHelper.getPathSegmentByIndex(link, 0);
        boolean hasValidPathSegment = !invalidPaths.contains(firstPathSegment.toUpperCase());
        if (!hasValidPathSegment) {
            throw new IllegalArgumentException("Link contains invalid path segment: " + link);
        }

        return true;
    }
}
