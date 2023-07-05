package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The HTTP part of an event. Used in serializing the event.
 */
public class Http {

    @JsonProperty("hostname")
    private String hostname;

    @JsonProperty("url")
    private String url;

    @JsonProperty("http_user_agent")
    private String userAgent;

    @JsonProperty("http_method")
    private String method;

    @JsonProperty("protocol")
    private String protocol;

    @JsonProperty("status")
    private int status;

    @JsonProperty("length")
    private int length;

    public Http() {
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Http{" +
                "hostname='" + hostname + '\'' +
                ", url='" + url + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", method='" + method + '\'' +
                ", protocol='" + protocol + '\'' +
                ", status=" + status +
                ", length=" + length +
                '}';
    }
}
