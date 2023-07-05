package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The HTTP part of an event. Used in serializing the event.
 */
public class Alert {

    @JsonProperty("action")
    private String action;

    @JsonProperty("gid")
    private String gId;

    @JsonProperty("signature_id")
    private String signatureId;

    @JsonProperty("signature")
    private String signature;

    @JsonProperty("category")
    private String category;

    @JsonProperty("severity")
    private int severity;

    public Alert() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getgId() {
        return gId;
    }

    public void setgId(String gId) {
        this.gId = gId;
    }

    public String getSignatureId() {
        return signatureId;
    }

    public void setSignatureId(String signatureId) {
        this.signatureId = signatureId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "action='" + action + '\'' +
                ", signatureId='" + signatureId + '\'' +
                ", signature='" + signature + '\'' +
                ", category='" + category + '\'' +
                ", severity=" + severity +
                '}';
    }
}
