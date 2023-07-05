package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The event, this is what comes in from LogStash and is used in the analysis. This object exists for serialization purposes.
 */
public class Event {

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("flow_id")
    private String flowId;

    @JsonProperty("src_ip")
    private String srcIp;

    @JsonProperty("src_port")
    private String srcPort;

    @JsonProperty("dest_ip")
    private String destIp;

    @JsonProperty("dest_port")
    private String destPort;

    @JsonProperty("proto")
    private String protocol;

    @JsonProperty("app_proto")
    private String appProtocol;

    @JsonProperty("alert")
    private Alert alert;

    @JsonProperty("http")
    private Http http;

    public Event() {
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp;
    }

    public String getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(String srcPort) {
        this.srcPort = srcPort;
    }

    public String getDestIp() {
        return destIp;
    }

    public void setDestIp(String destIp) {
        this.destIp = destIp;
    }

    public String getDestPort() {
        return destPort;
    }

    public void setDestPort(String destPort) {
        this.destPort = destPort;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAppProtocol() {
        return appProtocol;
    }

    public void setAppProtocol(String appProtocol) {
        this.appProtocol = appProtocol;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public Http getHttp() {
        return http;
    }

    public void setHttp(Http http) {
        this.http = http;
    }

    @Override
    public String toString() {
        return "Event{" +
                "timestamp='" + timestamp + '\'' +
                ", flowId=" + flowId +
                ", srcIp='" + srcIp + '\'' +
                ", srcPort='" + srcPort + '\'' +
                ", destIp='" + destIp + '\'' +
                ", destPort='" + destPort + '\'' +
                ", protocol='" + protocol + '\'' +
                ", appProtocol='" + appProtocol + '\'' +
                '}';
    }
}
