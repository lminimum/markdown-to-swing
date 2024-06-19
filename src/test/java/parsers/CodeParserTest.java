// Markdown to Java Swing Converter
// Matthew Vine
// CSIS 505-B01 (Liberty University)
// July 5, 2024
package parsers;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CodeParserTest {
    private CodeParser parser;

    @Before
    public void setUp() {
        parser = new CodeParser(
                "```java\npublic class Main {\n    public static void main(String[] args) {\n        System.out.println(\"Hello, World!\");\n    }\n}\n```");
    }

    @Test
    public void testValidate() {
        assertTrue("Should be valid", parser.validate());
    }

    @Test
    public void testValidateFailed() {
        CodeParser inlineParser = new CodeParser("```java");
        assertFalse("Should be invalid", inlineParser.validate());
    }

    @Test
    public void testParse() {
        parser.parse();
        assertEquals("java", parser.language);
        System.out.println("\"" + parser.code + "\"");
        assertEquals(
                "public class Main {\n    public static void main(String[] args) {\n        System.out.println(\"Hello, World!\");\n    }\n}",
                parser.code);
    }
}
