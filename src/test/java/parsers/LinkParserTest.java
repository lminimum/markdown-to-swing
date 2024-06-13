package parsers;

import static org.junit.Assert.*;
import javax.swing.JLabel;
import org.junit.Before;
import org.junit.Test;

public class LinkParserTest {
    private LinkParser parser;

    @Before
    public void setUp() {
        parser = new LinkParser("[example](http://example.com)");
    }

    @Test
    public void testValidate_ValidFormat() {
        assertTrue("Validation should return true for correct markdown link format", parser.validate());
    }

    @Test
    public void testValidate_InvalidFormat() {
        LinkParser invalidParser = new LinkParser("[example http://example.com)");
        assertFalse("Validation should return false for incorrect markdown link format", invalidParser.validate());
    }

    @Test
    public void testParse() {
        parser.parse();
        assertEquals("The title should be parsed correctly", "example", parser.title);
        assertEquals("The link should be parsed correctly", "http://example.com", parser.link);
    }

    @Test
    public void testToJavaSwingCode() {
        parser.parse();
        String expectedCode = "new JLabel(\"example\")";
        assertEquals("The Swing code generation should match the expected format", expectedCode,
                parser.toJavaSwingCode(2));
    }

    @Test
    public void testtoJavaSwingComponent() {
        parser.parse();
        JLabel label = parser.toJavaSwingComponent();
        assertEquals("The JLabel text should match the parsed title", "example", label.getText());
    }
}
