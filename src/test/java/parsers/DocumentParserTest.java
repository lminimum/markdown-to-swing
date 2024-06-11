package parsers;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DocumentParserTest {
    private DocumentParser parser;

    @Before
    public void setUp() {
        parser = new DocumentParser();
    }

    @Test
    public void testValidate() {
        assertTrue("Validation should return true", parser.validate());
    }

    @Test
    public void testParseParagraphsAndHeadings() {
        DocumentParser multipleParagraphs = new DocumentParser(
                "# Paragraph 1\n\nParagraph 2\n\nParagraph 3\n\n\n\n## Paragraph 4\nParagraph 5\n\n\n\n");
        List<String> paragraphsAndHeadings = multipleParagraphs.parseParagraphsAndHeadings();

        assertEquals("There should be 5 paragraphs", 5, paragraphsAndHeadings.size());
        assertEquals("First paragraph should be '# Paragraph 1'", "# Paragraph 1", paragraphsAndHeadings.get(0));
        assertEquals("Second paragraph should be 'Paragraph 2'", "Paragraph 2", paragraphsAndHeadings.get(1));
        assertEquals("Third paragraph should be 'Paragraph 3'", "Paragraph 3", paragraphsAndHeadings.get(2));
        assertEquals("Fourth paragraph should be '## Paragraph 4'", "## Paragraph 4", paragraphsAndHeadings.get(3));
        assertEquals("Fourth paragraph should be 'Paragraph 5'", "Paragraph 5", paragraphsAndHeadings.get(4));
    }
}
