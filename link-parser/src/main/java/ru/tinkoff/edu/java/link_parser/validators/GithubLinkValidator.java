package ru.tinkoff.edu.java.link_parser.validators;

import ru.tinkoff.edu.java.link_parser.utils.LinkParserHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

public class GithubLinkValidator implements LinkValidator {
    private static final Set<String> invalidPaths = Set.of("nonprofit", "issues", "sponsors", "codespaces", "new", "features", "trending",
            "settings", "topics", "pricing", "security", "logout", "pulls", "collections", "explore", "events",
            "marketplace", "login", "wiki", "notifications", "join", "customer-stories");

    @Override
    public boolean validate(String link) {
        try {
            URL url = new URL(link);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        if (containsSubdomain(link)) {
            return false;
        }

        boolean hasAtLeastTwoPathSegments = LinkParserHelper.getAllPathSegments(link).size() >= 2;
        boolean hasValidPathSegment = hasAtLeastTwoPathSegments && !invalidPaths.contains(LinkParserHelper.getPathSegmentByIndex(link, 0));

        return hasAtLeastTwoPathSegments && hasValidPathSegment;
    }

    private boolean containsSubdomain(String link) {
        List<String> subdomains = LinkParserHelper.getSubdomains(link);
        return subdomains.size() > 2;
    }
}
