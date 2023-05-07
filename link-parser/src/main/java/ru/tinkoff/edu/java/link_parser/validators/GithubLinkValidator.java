package ru.tinkoff.edu.java.link_parser.validators;

import java.net.URI;
import java.util.Set;
import ru.tinkoff.edu.java.link_parser.utils.LinkParserHelper;

public class GithubLinkValidator implements LinkValidator {
    private static final Set<String> INVALID_PATHS = Set.of("about", "blog", "codespaces", "collections", "contact",
        "customer-stories", "events", "explore", "features", "issues", "join", "login", "logout", "marketplace",
        "new", "nonprofit", "notifications", "pricing", "pulls", "security", "settings", "sponsors", "stars",
        "topics", "trending", "wiki"
    );

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
        boolean hasValidPathSegment = !INVALID_PATHS.contains(firstPathSegment);
        if (!hasValidPathSegment) {
            throw new IllegalArgumentException("Link contains invalid path segment: " + link);
        }

        return true;
    }
}
