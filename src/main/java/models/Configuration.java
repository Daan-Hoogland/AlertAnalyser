package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;

/**
 * The configuration object. Used to serialize the configuration JSON to an actual Java object.
 */
public class Configuration {

    @JsonProperty("class_location")
    private String classLocation;

    @JsonProperty("template_location")
    private String templateLocation;

    @JsonProperty("reload_interval")
    private int reloadInterval;

    @JsonProperty("socket_hostname")
    private String hostname;

    @JsonProperty("socket_port")
    private int port;

    @JsonProperty("signature_configurations")
    private HashMap<String, List<String>> signatureConfigurations;

    public Configuration() {
    }

    public String getClassLocation() {
        return classLocation;
    }

    public void setClassLocation(String classLocation) {
        this.classLocation = classLocation;
    }

    public String getTemplateLocation() {
        return templateLocation;
    }

    public void setTemplateLocation(String templateLocation) {
        this.templateLocation = templateLocation;
    }

    public int getReloadInterval() {
        return reloadInterval;
    }

    public void setReloadInterval(int reloadInterval) {
        this.reloadInterval = reloadInterval;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public HashMap<String, List<String>> getSignatureConfigurations() {
        return signatureConfigurations;
    }

    public void setSignatureConfigurations(HashMap<String, List<String>> signatureConfigurations) {
        this.signatureConfigurations = signatureConfigurations;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "classLocation='" + classLocation + '\'' +
                ", templateLocation='" + templateLocation + '\'' +
                ", signatureConfigurations=" + signatureConfigurations +
                '}';
    }
}
