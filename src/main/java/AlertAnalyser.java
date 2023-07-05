import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import configuration.ConfigurationControl;
import configuration.ConfigurationLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import socket.EventClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AlertAnalyser {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        // Hide the complaining of Apache libraries
        org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);

        ConfigurationControl control = new ConfigurationControl();
        control.startConfiguration();

        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        WebSocketClient socketClient = null;
        try {
            socketClient = new EventClient(
                    new URI(String.format("ws://%s:%d/", ConfigurationLoader.configuration.getHostname(),
                            ConfigurationLoader.configuration.getPort())));
        } catch (URISyntaxException e) {
            logger.fatal("Error occurred while forming the websocket url.", e);
            System.exit(1);
        }

        socketClient.connect();
    }
}
