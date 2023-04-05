package ru.tinkoff.edu.java.link_parser.parsers;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GithubLinkParserTest {
    private static final LinkParser parser = new GithubLinkParser();

    @Test
    public void parseValidLink() {
        String link = "https://github.com/Facebook/react/";
        Map<String, String> result = parser.parse(link);
        Map<String, String> expected = Map.of("owner", "Facebook", "repo", "react");
        assertEquals(expected, result);
    }

    @Test
    public void parseInvalidLinkWithInvalidFirstSegmentPathFeatures() {
        String link = "https://github.com/features/copilot/";
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseInvalidLinkWithInvalidFirstSegmentPathCodespaces() {
        String link = "https://github.com/codespaces/new";
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseValidLinkWithMultiplePathSegments() {
        String link = "https://github.com/nuromirzak/nuromirzak/blob/main/README.md";
        Map<String, String> result = parser.parse(link);
        Map<String, String> expected = Map.of("owner", "nuromirzak", "repo", "nuromirzak");
        assertEquals(expected, result);
    }

    @Test
    public void parseBrokenLink() {
        String link = "http:://github.com/nuromirzak/nuromirzak/blob/main/README.md";
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseValidLinkWithEmptyPathSegments() {
        String link = "https://github.com/";
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseValidLinkWithOnePathSegment() {
        String link = "http:://github.com/nuromirzak/";
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseLinkWithSubdomain() {
        String link = "https://nuromirzak.github.com/nuromirzak/nuromirzak/blob/main/README.md";
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseAnotherDomainLink() {
        String link = "https://github.io/nuromirzak/nuromirzak/blob/main/README.md";
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseLinkWithUsernameInPath() {
        String link = "https://github.com/nuromirzak";
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseLinkWithUsernameAndRepoInPath() {
        String link = "https://github.com/nuromirzak/awesome-project";
        Map<String, String> result = parser.parse(link);
        Map<String, String> expected = Map.of("owner", "nuromirzak", "repo", "awesome-project");
        assertEquals(expected, result);
    }

    @Test
    public void parseLinkWithUsernameAndRepoInPathAndQueryParams() {
        String link = "https://github.com/nuromirzak/awesome-project?utm_source=github&utm_medium=repository&utm_campaign=978711";
        Map<String, String> result = parser.parse(link);
        Map<String, String> expected = Map.of("owner", "nuromirzak", "repo", "awesome-project");
        assertEquals(expected, result);
    }

    @Test
    public void parseLinkWithUsernameAndRepoInPathAndFragment() {
        String link = "https://github.com/nuromirzak/awesome-project#readme";
        Map<String, String> result = parser.parse(link);
        Map<String, String> expected = Map.of("owner", "nuromirzak", "repo", "awesome-project");
        assertEquals(expected, result);
    }

    @Test
    public void parseLinkWithOrganizationAndRepoInPath() {
        String link = "https://github.com/openai/gpt-3";
        Map<String, String> result = parser.parse(link);
        Map<String, String> expected = Map.of("owner", "openai", "repo", "gpt-3");
        assertEquals(expected, result);
    }

    @Test
    public void parseLinkWithOrganizationAndRepoInPathAndQueryParams() {
        String link = "https://github.com/openai/gpt-3?utm_source=github&utm_medium=repository&utm_campaign=978711";
        Map<String, String> result = parser.parse(link);
        Map<String, String> expected = Map.of("owner", "openai", "repo", "gpt-3");
        assertEquals(expected, result);
    }

    @Test
    public void parseLinkWithOrganizationAndRepoInPathAndFragment() {
        String link = "https://github.com/openai/gpt-3#readme";
        Map<String, String> result = parser.parse(link);
        Map<String, String> expected = Map.of("owner", "openai", "repo", "gpt-3");
        assertEquals(expected, result);
    }
}