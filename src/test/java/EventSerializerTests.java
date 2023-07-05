import models.Event;
import org.junit.Test;
import utils.EventSerializer;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Contains tests for the serialization of {@link Event}. This code is found in {@link EventSerializer}.
 */
public class EventSerializerTests {

    private String correctJson = "{\n" +
            "\t\"timestamp\": \"2017-09-11T11:56:25.654977+0000\",\n" +
            "\t\"flow_id\": 1413168692309904,\n" +
            "\t\"in_iface\": \"em2\",\n" +
            "\t\"event_type\": \"alert\",\n" +
            "\t\"vlan\": 12,\n" +
            "\t\"src_ip\": \"192.168.12.25\",\n" +
            "\t\"src_port\": 45518,\n" +
            "\t\"dest_ip\": \"91.189.95.15\",\n" +
            "\t\"dest_port\": 80,\n" +
            "\t\"proto\": \"TCP\",\n" +
            "\t\"tx_id\": 0,\n" +
            "\t\"alert\": {\n" +
            "\t\t\"action\": \"allowed\",\n" +
            "\t\t\"gid\": 1,\n" +
            "\t\t\"signature_id\": 2013031,\n" +
            "\t\t\"rev\": 4,\n" +
            "\t\t\"signature\": \"ET POLICY Python-urllib\\/ Suspicious User Agent\",\n" +
            "\t\t\"category\": \"Attempted Information Leak\",\n" +
            "\t\t\"severity\": 2\n" +
            "\t},\n" +
            "\t\"http\": {\n" +
            "\t\t\"hostname\": \"changelogs.ubuntu.com\",\n" +
            "\t\t\"url\": \"\\/meta-release-lts\",\n" +
            "\t\t\"http_user_agent\": \"Python-urllib\\/3.5\",\n" +
            "\t\t\"http_method\": \"GET\",\n" +
            "\t\t\"protocol\": \"HTTP\\/1.1\",\n" +
            "\t\t\"status\": 304,\n" +
            "\t\t\"length\": 0\n" +
            "\t},\n" +
            "\t\"app_proto\": \"http\",\n" +
            "\t\"payload_printable\": \"GET \\/meta-release-lts HTTP\\/1.1\\r\\nAccept-Encoding: identity\\r\\nConnection: close\\r\\nPragma: no-cache\\r\\nCache-Control: No-Cache\\r\\nHost: changelogs.ubuntu.com\\r\\nIf-Modified-Since: Sun Aug  6 04:48:01 2017\\r\\nUser-Agent: Python-urllib\\/3.5\\r\\n\\r\\n\",\n" +
            "\t\"stream\": 1\n" +
            "}";

    /**
     * Tests if the serialization of an event in the form of a JSON String works.
     *
     * @throws IOException If something goes wrong (like invalid JSON).
     */
    @Test
    public void serializeObject() throws IOException {
        Event event = EventSerializer.serializeEvent(correctJson);

        assertAll("event",
                () -> assertEquals("1413168692309904", event.getFlowId()),
                () -> assertEquals("ET POLICY Python-urllib/ Suspicious User Agent", event.getAlert().getSignature())
        );
    }
}
