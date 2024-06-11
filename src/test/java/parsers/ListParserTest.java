package parsers;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import enums.ListType;

public class ListParserTest {
    private ListParser parser;

    @Before
    public void setUp() {
        parser = new ListParser("- Example 1\n- Example 2\n- Example 3");
    }

    @Test
    public void testValidate() {
        assertTrue("Should be valid", parser.validate());
    }

    @Test
    public void testValidateFailed() {
        ListParser brokenParser = new ListParser("Example 1\n2. Example 2\n3. Example 3");
        assertFalse("Should be invalid", brokenParser.validate());
    }

    @Test
    public void testParse() {
        parser.parse();
        assertEquals("Type should be UNORDERED", ListType.UNORDERED, parser.type);
        assertArrayEquals("Items should be ['Example 1', 'Example 2', 'Example 3']",
                new String[] { "Example 1", "Example 2", "Example 3" }, parser.items);
    }

    @Test
    public void testParseOrdered() {
        ListParser orderedParser = new ListParser("1. Example 1\n2. Example 2\n3. Example 3");
        orderedParser.parse();
        assertEquals("Type should be ORDERED", ListType.ORDERED, orderedParser.type);
        assertArrayEquals("Items should be ['Example 1', 'Example 2', 'Example 3']",
                new String[] { "Example 1", "Example 2", "Example 3" }, orderedParser.items);
    }

    @Test
    public void testParseMixedUnordered() {
        ListParser mixedParser = new ListParser("* Example 1\n+ Example 2\n- Example 3");
        mixedParser.parse();
        assertEquals("Type should be UNORDERED", ListType.UNORDERED, mixedParser.type);
        assertArrayEquals("Items should be ['Example 1', 'Example 2', 'Example 3']",
                new String[] { "Example 1", "Example 2", "Example 3" }, mixedParser.items);
    }

    @Test
    public void testParseMixedOrdered() {
        ListParser mixedParser = new ListParser("1. Example 1\n3. Example 2\n2. Example 3");
        mixedParser.parse();
        assertEquals("Type should be ORDERED", ListType.ORDERED, mixedParser.type);
        assertArrayEquals("Items should be ['Example 1', 'Example 2', 'Example 3']",
                new String[] { "Example 1", "Example 2", "Example 3" }, mixedParser.items);
    }

    @Test
    public void testParseMixedBoth() {
        ListParser mixedParser = new ListParser("1. Example 1\n- Example 2\n3. Example 3");
        mixedParser.parse();
        assertEquals("Type should be ORDERED", ListType.ORDERED, mixedParser.type);
        assertArrayEquals("Items should be ['Example 1', 'Example 2', 'Example 3']",
                new String[] { "Example 1", "Example 2", "Example 3" }, mixedParser.items);
    }
}
