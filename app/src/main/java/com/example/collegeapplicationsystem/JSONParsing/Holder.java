package com.example.collegeapplicationsystem.JSONParsing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "metadata",
        "results"
})
public class Holder {

    @JsonProperty("metadata")
    private Metadata metadata;
    @JsonProperty("results")
    private List<College> colleges = null;

    @JsonProperty("metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("results")
    public List<College> getColleges() {
        return colleges;
    }

    @JsonProperty("results")
    public void setColleges(List<College> colleges) {
        this.colleges = colleges;
    }

    @Override
    public String toString() {
        return "Holder{" +
                "metadata=" + metadata +
                ", results=" + colleges +
                '}';
    }
}
