package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RuleParameter {

    @JsonProperty("type")
    private String type;

    @JsonProperty("value")
    private String value;

    public RuleParameter() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
