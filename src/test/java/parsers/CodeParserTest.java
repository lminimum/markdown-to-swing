package parsers;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CodeParserTest {
    private CodeParser parser;

    @Before
    public void setUp() {
        parser = new CodeParser(
                "```java\npublic class Main {\n    public static void main(String[] args) {\n        System.out.println(\"Hello, World!\");\n    }\n}\n```",
                false);
    }

    @Test
    public void testValidate() {
        assertTrue("Should be valid", parser.validate());
    }

    @Test
    public void testValidateFailedInline() {
        CodeParser invalidParser = new CodeParser("`java", false);
        assertFalse("Should be invalid", invalidParser.validate());
    }

    @Test
    public void testValidateFailed() {
        CodeParser inlineParser = new CodeParser("```java", true);
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

    @Test
    public void testParseInline() {
        CodeParser inlineParser = new CodeParser("`System.out.println(\"Hello, World!\");`", true);
        inlineParser.parse();
        assertEquals("System.out.println(\"Hello, World!\");", inlineParser.code);
    }
}
