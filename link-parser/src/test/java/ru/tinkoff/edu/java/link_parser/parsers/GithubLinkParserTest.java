package ru.tinkoff.edu.java.link_parser.parsers;

import java.net.URI;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GithubLinkParserTest {
    private static final LinkParser parser = new GithubLinkParser();

    @Test
    public void parseValidLink() {
        URI uri = URI.create("https://github.com/Facebook/react/");
        Map<String, String> result = parser.parse(uri);
        Map<String, String> expected = Map.of("owner", "Facebook", "repo", "react");
        assertEquals(expected, result);
    }

    @Test
    public void parseInvalidLinkWithInvalidFirstSegmentPathFeatures() {
        URI uri = URI.create("https://github.com/features/copilot/");
        Map<String, String> result = parser.parse(uri);
        assertNull(result);
    }

    @Test
    public void parseInvalidLinkWithInvalidFirstSegmentPathCodespaces() {
        URI uri = URI.create("https://github.com/codespaces/new");
        Map<String, String> result = parser.parse(uri);
        assertNull(result);
    }

    @Test
    public void parseValidLinkWithMultiplePathSegments() {
        URI uri = URI.create("https://github.com/nuromirzak/nuromirzak/blob/main/README.md");
        Map<String, String> result = parser.parse(uri);
        Map<String, String> expected = Map.of("owner", "nuromirzak", "repo", "nuromirzak");
        assertEquals(expected, result);
    }

    @Test
    public void parseBrokenLink() {
        URI uri = URI.create("http:://github.com/nuromirzak/nuromirzak/blob/main/README.md");
        Map<String, String> result = parser.parse(uri);
        assertNull(result);
    }

    @Test
    public void parseValidLinkWithEmptyPathSegments() {
        URI uri = URI.create("https://github.com/");
        Map<String, String> result = parser.parse(uri);
        assertNull(result);
    }

    @Test
    public void parseValidLinkWithOnePathSegment() {
        URI uri = URI.create("http:://github.com/nuromirzak/");
        Map<String, String> result = parser.parse(uri);
        assertNull(result);
    }

    @Test
    public void parseLinkWithSubdomain() {
        URI uri = URI.create("https://nuromirzak.github.com/nuromirzak/nuromirzak/blob/main/README.md");
        Map<String, String> result = parser.parse(uri);
        assertNull(result);
    }

    @Test
    public void parseAnotherDomainLink() {
        URI uri = URI.create("https://github.io/nuromirzak/nuromirzak/blob/main/README.md");
        Map<String, String> result = parser.parse(uri);
        assertNull(result);
    }

    @Test
    public void parseLinkWithUsernameInPath() {
        URI uri = URI.create("https://github.com/nuromirzak");
        Map<String, String> result = parser.parse(uri);
        assertNull(result);
    }

    @Test
    public void parseLinkWithUsernameAndRepoInPath() {
        URI uri = URI.create("https://github.com/nuromirzak/awesome-project");
        Map<String, String> result = parser.parse(uri);
        Map<String, String> expected = Map.of("owner", "nuromirzak", "repo", "awesome-project");
        assertEquals(expected, result);
    }

    @Test
    public void parseLinkWithUsernameAndRepoInPathAndQueryParams() {
        URI uri = URI.create(
            "https://github.com/nuromirzak/awesome-project?utm_source=github&utm_medium=repository&utm_campaign=978711");
        Map<String, String> result = parser.parse(uri);
        Map<String, String> expected = Map.of("owner", "nuromirzak", "repo", "awesome-project");
        assertEquals(expected, result);
    }

    @Test
    public void parseLinkWithUsernameAndRepoInPathAndFragment() {
        URI uri = URI.create("https://github.com/nuromirzak/awesome-project#readme");
        Map<String, String> result = parser.parse(uri);
        Map<String, String> expected = Map.of("owner", "nuromirzak", "repo", "awesome-project");
        assertEquals(expected, result);
    }

    @Test
    public void parseLinkWithOrganizationAndRepoInPath() {
        URI uri = URI.create("https://github.com/openai/gpt-3");
        Map<String, String> result = parser.parse(uri);
        Map<String, String> expected = Map.of("owner", "openai", "repo", "gpt-3");
        assertEquals(expected, result);
    }

    @Test
    public void parseLinkWithOrganizationAndRepoInPathAndQueryParams() {
        URI uri =
            URI.create("https://github.com/openai/gpt-3?utm_source=github&utm_medium=repository&utm_campaign=978711");
        Map<String, String> result = parser.parse(uri);
        Map<String, String> expected = Map.of("owner", "openai", "repo", "gpt-3");
        assertEquals(expected, result);
    }

    @Test
    public void parseLinkWithOrganizationAndRepoInPathAndFragment() {
        URI uri = URI.create("https://github.com/openai/gpt-3#readme");
        Map<String, String> result = parser.parse(uri);
        Map<String, String> expected = Map.of("owner", "openai", "repo", "gpt-3");
        assertEquals(expected, result);
    }
}
