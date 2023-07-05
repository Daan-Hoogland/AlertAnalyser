package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RuleTemplate {

    @JsonProperty("rule")
    private String rule;

    @JsonProperty("parameters")
    private List<RuleParameter> parameters;

    public RuleTemplate() {
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public List<RuleParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<RuleParameter> parameters) {
        this.parameters = parameters;
    }
}
