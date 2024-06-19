// Markdown to Java Swing Converter
// Matthew Vine
// CSIS 505-B01 (Liberty University)
// July 5, 2024
package parsers;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class HeadingParserTest {
    private HeadingParser parser;

    @Before
    public void setUp() {
        parser = new HeadingParser("# Example");
    }

    @Test
    public void testValidate() {
        assertTrue("Validation should return true", parser.validate());
        assertFalse("Validation should return false", new HeadingParser(null).validate());
        assertFalse("Validation should return false", new HeadingParser("").validate());
    }

    @Test
    public void testParse() {
        parser.parse();
        assertEquals("Level should be 1", 1, parser.level);
        assertEquals("Title should be 'Example'", "Example", parser.title);
    }

    @Test
    public void testParseHeadingWithSpaces() {
        HeadingParser heading = new HeadingParser("##  Heading with spaces ");
        heading.parse();
        assertEquals("Level should be 2", 2, heading.level);
        assertEquals("Title should be 'Heading with spaces'", "Heading with spaces", heading.title);
    }

    @Test
    public void testParseLevel3() {
        HeadingParser heading = new HeadingParser("### Level 3 heading");
        heading.parse();
        assertEquals("Level should be 3", 3, heading.level);
        assertEquals("Title should be 'Level 3 heading'", "Level 3 heading", heading.title);
    }
}
