package ru.tinkoff.edu.java.link_parser.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.tinkoff.edu.java.link_parser.utils.LinkParserHelper;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StackoverflowLinkParserTest {
    private static final LinkParser parser = new StackoverflowLinkParser();

    @Test
    public void parseValidLink() {
        String link = "https://stackoverflow.com/questions/4114095/";
        Map<String, String> result = parser.parse(link);
        Map<String, String> expected = Map.of("questionId", "4114095");
        assertEquals(expected, result);
    }

    @Test
    public void parseInvalidLinkWithInvalidFirstSegmentPath() {
        String link = "https://stackoverflow.com/lol/4114095/how-do-i-revert-a-git-repository-to-a-previous-commit";
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseValidLinkWithMultiplePathSegments() {
        String link = "https://stackoverflow.com/questions/4114095/how-do-i-revert-a-git-repository-to-a-previous-commit/";
        Map<String, String> result = parser.parse(link);
        Map<String, String> expected = Map.of("questionId", "4114095");
        assertEquals(expected, result);
    }

    @Test
    public void parseBrokenLink() {
        String link = "http:://stackoverflow.com/questions/4114095/s";
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseValidLinkWithEmptyPathSegments() {
        String link = "https://stackoverflow.com/";
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseValidLinkWithOnePathSegment() {
        String link = "http:://stackoverflow.com/questions/";
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseLinkWithSubdomain() {
        String link = "https://ru.stackoverflow.com/questions/4114095/";
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
    public void parseQuestionIdWithNegativeNumber() {
        String link = "https://stackoverflow.com/questions/-4114095/";
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }

    @Test
    public void parseQuestionIdWithNonNumericCharacters() {
        String link = "https://stackoverflow.com/questions/4114095s/";
        Map<String, String> result = parser.parse(link);
        assertNull(result);
    }
}