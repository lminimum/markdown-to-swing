// Markdown to Java Swing Converter
// Matthew Vine
// CSIS 505-B01 (Liberty University)
// July 5, 2024
package parsers;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import enums.ParagraphType;

public class ParagraphParserTest {
    private ParagraphParser parser;

    @Before
    public void setUp() {
        parser = new ParagraphParser("Example");
    }

    @Test
    public void testValidate() {
        assertTrue("Validation should return true", parser.validate());
        assertFalse("Validation should return false", new ParagraphParser(null).validate());
        assertFalse("Validation should return false", new ParagraphParser("").validate());
    }

    @Test
    public void testParse() {
        parser.parse();
        assertEquals("Type should be NORMAL", ParagraphType.NORMAL, parser.type);
        assertEquals("Content should be 'Example'", "Example", parser.content);
    }
}
