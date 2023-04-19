package ru.tinkoff.edu.java.link_parser.validators;

import ru.tinkoff.edu.java.link_parser.parsers.LinkParser;
import ru.tinkoff.edu.java.link_parser.utils.LinkParserHelper;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Set;

public class GithubLinkValidator implements LinkValidator {
    private static final Set<String> invalidPaths = Set.of("about", "blog", "codespaces", "collections", "contact",
            "customer-stories", "events", "explore", "features", "issues", "join", "login", "logout", "marketplace",
            "new", "nonprofit", "notifications", "pricing", "pulls", "security", "settings", "sponsors", "stars",
            "topics", "trending", "wiki");

    @Override
    public boolean validate(URI link) {
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
        boolean hasValidPathSegment = !invalidPaths.contains(firstPathSegment);
        if (!hasValidPathSegment) {
            throw new IllegalArgumentException("Link contains invalid path segment: " + link);
        }

        return true;
    }
}
