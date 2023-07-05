package socket;

import analysis.EventAnalyser;
import analysis.ThreadManager;
import configuration.ConfigurationLoader;
import exceptions.InvalidJSONException;
import models.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import utils.EventSerializer;
import utils.JSONUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;

/**
 * The websocket client that connects to Logstash when the application is successfully configured.
 */
public class EventClient extends WebSocketClient {

    private static final Logger logger = LogManager.getLogger();

    public EventClient(URI serverURI) {
        super(serverURI);
    }

    public EventClient(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    /**
     * Gets called when a new connection opens. Currently prints a simple message.
     * Useful to keep track of opening and closing connections.
     *
     * @param serverHandshake The {@link ServerHandshake} object containing information about the handshake
     *                        that occurred between the server and client.
     */
    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        logger.info(String.format("Connection opened with %s:%d", ConfigurationLoader.configuration.getHostname(), ConfigurationLoader.configuration.getPort()));
    }

    /**
     * The method that is called when a message is received through the websocket. This is where the incoming events will come in.
     * Serializes the JSON to a {@link Event} object and starts the {@link EventAnalyser}
     * on the {@link java.util.concurrent.ExecutorService} found in {@link ThreadManager}.
     *
     * @param message The received message, as a String.
     */
    @Override
    public void onMessage(String message) {
        try {
            if (JSONUtils.isJSONValid(message)) {

                Event event = EventSerializer.serializeEvent(message);

                ThreadManager.THREAD_MANAGER.execute(new EventAnalyser(event));

            } else {
                throw new InvalidJSONException();
            }
        } catch (IOException e) {
            logger.error("An error has occurred while serializing the JSON.");
            e.printStackTrace();
        } catch (InvalidJSONException e) {
            logger.error("The received JSON is invalid.");
        }
    }

    /**
     * Gets called when a connection closes.
     *
     * @param code   The exit code for the closing of the connection.
     * @param reason The reason for the closing of the connection.
     * @param remote If the connection was closed by the remote source (true) or the server (false).
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.warn(String.format("Connection closed with code %d for reason: %s", code, reason));
    }

    /**
     * Gets called when any error occurs. Should possibly exit the application?
     *
     * @param e The exception thrown
     */
    @Override
    public void onError(Exception e) {
        if (e.getClass() == ConnectException.class) {
            logger.fatal("Unable to connect to the websocket server.");
            logger.fatal("Exiting.");
            System.exit(1);
        } else {
            logger.error("An error has occurred during websocket communication.", e);
        }
    }
}
