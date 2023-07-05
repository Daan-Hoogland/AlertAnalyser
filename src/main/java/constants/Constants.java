package constants;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final String TEST_ALERT = "{\n" +
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
            "\t\t\"signature_id\": 2000419,\n" +
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

    public static final String CONFIG_FOLDER = System.getProperty("user.home") + "/.EventAnalyser/";
    public static final String CONFIG_FILE = "config.json";

    public static final String DEFAULT_TEMPLATE_FOLDER = "templates/";
    public static final String DEFAULT_CLASS_FOLDER = "classes/";

    public static final String CONFIG_TEMPLATE = "{\n" +
            "  \"class_location\": \"" + CONFIG_FOLDER + DEFAULT_CLASS_FOLDER + "\",\n" +
            "  \"template_location\": \"" + CONFIG_FOLDER + DEFAULT_TEMPLATE_FOLDER + "\",\n" +
            "  \"reload_interval\": 1,\n" +
            "  \"socket_hostname\": \"localhost\"," +
            "  \"socket_port\": 3232," +
            "  \"signature_configurations\": {\n" +
            "    \"2000419\": [\"TestClass\"]\n" +
            "  }\n" +
            "}";

    public static final Map<String, String> JSON_JAVA_VARIABLE_NAMES;

    static {
        // Event object
        JSON_JAVA_VARIABLE_NAMES = new HashMap<>();
        JSON_JAVA_VARIABLE_NAMES.put("timestamp", "Timestamp");
        JSON_JAVA_VARIABLE_NAMES.put("flow_id", "FlowId");
        JSON_JAVA_VARIABLE_NAMES.put("src_ip", "SrcIp");
        JSON_JAVA_VARIABLE_NAMES.put("src_port", "SrcPort");
        JSON_JAVA_VARIABLE_NAMES.put("dest_ip", "SrcPort");
        JSON_JAVA_VARIABLE_NAMES.put("dest_port", "DestPort");
        JSON_JAVA_VARIABLE_NAMES.put("proto", "Protocol");
        JSON_JAVA_VARIABLE_NAMES.put("app_proto", "AppProtocol");
        JSON_JAVA_VARIABLE_NAMES.put("alert", "Alert");
        JSON_JAVA_VARIABLE_NAMES.put("http", "Http");

        // HTTP object
        JSON_JAVA_VARIABLE_NAMES.put("hostname", "Hostname");
        JSON_JAVA_VARIABLE_NAMES.put("url", "Url");
        JSON_JAVA_VARIABLE_NAMES.put("http_user_agent", "UserAgent");
        JSON_JAVA_VARIABLE_NAMES.put("http_method", "Method");
        JSON_JAVA_VARIABLE_NAMES.put("protocol", "Protocol");
        JSON_JAVA_VARIABLE_NAMES.put("status", "Status");
        JSON_JAVA_VARIABLE_NAMES.put("length", "Length");

        // Alert object
        JSON_JAVA_VARIABLE_NAMES.put("action", "Action");
        JSON_JAVA_VARIABLE_NAMES.put("gid", "gId");
        JSON_JAVA_VARIABLE_NAMES.put("signature_id", "SignatureId");
        JSON_JAVA_VARIABLE_NAMES.put("signature", "Signature");
        JSON_JAVA_VARIABLE_NAMES.put("category", "Category");
        JSON_JAVA_VARIABLE_NAMES.put("severity", "Severity");
    }

}
