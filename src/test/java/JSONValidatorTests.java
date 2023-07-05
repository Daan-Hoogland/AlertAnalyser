import org.junit.Assume;
import org.junit.Test;
import utils.JSONUtils;

/**
 * Tests the JSON validator method found in {@link JSONUtils}
 */
public class JSONValidatorTests {

    public String validJSON = "{\n" +
            "  \"class_location\": \"/home/dan/.AlertAnalyser/classes/\",\n" +
            "  \"template_location\": \"/home/dan/.AlertAnalyser/templates/\",\n" +
            "  \"signature_configurations\": {\n" +
            "    \"2000419\": [\"test\", \"whois\", \"\"],\n" +
            "    \"2000420\": [\"\", \"\"],\n" +
            "    \"2000421\": [\"\"]\n" +
            "  }\n" +
            "}\n";

    public String invalidJSON = "{\n" +
            "  \"class_location\": \"/home/dan/.AlertAnalyser/classes/\"\n" +
            "  \"template_location\": \"/home/dan/.AlertAnalyser/templates/\",\n" +
            "  \"signature_configurations\": {\n" +
            "    \"2000419\": [\"test\", \"whois\", \"\"],\n" +
            "    \"2000420\": [\"\", \"\"],\n" +
            "    \"2000421\": [\"\"]\n" +
            "  }\n" +
            "}\n";

    /**
     * Tests a valid JSON String.
     */
    @Test
    public void testValidJSON() {
        Assume.assumeTrue(JSONUtils.isJSONValid(validJSON));
    }

    /**
     * Tests an invalid JSON String.
     */
    @Test
    public void testInvalidJSON() {
        Assume.assumeFalse(JSONUtils.isJSONValid(invalidJSON));
    }
}
