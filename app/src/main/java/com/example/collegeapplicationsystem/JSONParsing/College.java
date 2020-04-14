package com.example.collegeapplicationsystem.JSONParsing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "latest.cost.tuition.out_of_state",
        "latest.admissions.sat_scores.75th_percentile.math",
        "school.zip",
        "latest.admissions.sat_scores.25th_percentile.critical_reading",
        "latest.admissions.sat_scores.25th_percentile.math",
        "school.school_url",
        "latest.cost.tuition.in_state",
        "school.name",
        "latest.admissions.sat_scores.75th_percentile.critical_reading",
        "school.state",
        "id",
        "school.city"
})
public class College implements Serializable {
    @JsonProperty("latest.cost.tuition.out_of_state")
    private Integer latestCostTuitionOutOfState;

    @JsonProperty("latest.admissions.sat_scores.75th_percentile.math")
    private Float latestAdmissionsSatScores75thPercentileMath;

    @JsonProperty("school.zip")
    private String schoolZip;

    @JsonProperty("latest.admissions.sat_scores.25th_percentile.critical_reading")
    private Float latestAdmissionsSatScores25thPercentileCriticalReading;

    @JsonProperty("latest.admissions.sat_scores.25th_percentile.math")
    private Float latestAdmissionsSatScores25thPercentileMath;

    @JsonProperty("school.school_url")
    private String schoolSchoolUrl;

    @JsonProperty("latest.cost.tuition.in_state")
    private Integer latestCostTuitionInState;

    @JsonProperty("school.name")
    private String schoolName;

    @JsonProperty("latest.admissions.sat_scores.75th_percentile.critical_reading")
    private Float latestAdmissionsSatScores75thPercentileCriticalReading;

    @JsonProperty("school.state")
    private String schoolState;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("school.city")
    private String schoolCity;

    private String searchName;

    public College() {
    }

    @JsonProperty("latest.cost.tuition.out_of_state")
    public Integer getLatestCostTuitionOutOfState() {
        return latestCostTuitionOutOfState;
    }

    @JsonProperty("latest.cost.tuition.out_of_state")
    public void setLatestCostTuitionOutOfState(Integer latestCostTuitionOutOfState) {
        this.latestCostTuitionOutOfState = latestCostTuitionOutOfState;
    }

    @JsonProperty("latest.admissions.sat_scores.75th_percentile.math")
    public Float getLatestAdmissionsSatScores75thPercentileMath() {
        return latestAdmissionsSatScores75thPercentileMath;
    }

    @JsonProperty("latest.admissions.sat_scores.75th_percentile.math")
    public void setLatestAdmissionsSatScores75thPercentileMath(Float latestAdmissionsSatScores75thPercentileMath) {
        this.latestAdmissionsSatScores75thPercentileMath = latestAdmissionsSatScores75thPercentileMath;
    }

    @JsonProperty("school.zip")
    public String getSchoolZip() {
        return schoolZip;
    }

    @JsonProperty("school.zip")
    public void setSchoolZip(String schoolZip) {
        this.schoolZip = schoolZip;
    }

    @JsonProperty("latest.admissions.sat_scores.25th_percentile.critical_reading")
    public Float getLatestAdmissionsSatScores25thPercentileCriticalReading() {
        return latestAdmissionsSatScores25thPercentileCriticalReading;
    }

    @JsonProperty("latest.admissions.sat_scores.25th_percentile.critical_reading")
    public void setLatestAdmissionsSatScores25thPercentileCriticalReading(Float latestAdmissionsSatScores25thPercentileCriticalReading) {
        this.latestAdmissionsSatScores25thPercentileCriticalReading = latestAdmissionsSatScores25thPercentileCriticalReading;
    }

    @JsonProperty("latest.admissions.sat_scores.25th_percentile.math")
    public Float getLatestAdmissionsSatScores25thPercentileMath() {
        return latestAdmissionsSatScores25thPercentileMath;
    }

    @JsonProperty("latest.admissions.sat_scores.25th_percentile.math")
    public void setLatestAdmissionsSatScores25thPercentileMath(Float latestAdmissionsSatScores25thPercentileMath) {
        this.latestAdmissionsSatScores25thPercentileMath = latestAdmissionsSatScores25thPercentileMath;
    }

    @JsonProperty("school.school_url")
    public String getSchoolSchoolUrl() {
        return schoolSchoolUrl;
    }

    @JsonProperty("school.school_url")
    public void setSchoolSchoolUrl(String schoolSchoolUrl) {
        this.schoolSchoolUrl = schoolSchoolUrl;
    }

    @JsonProperty("latest.cost.tuition.in_state")
    public Integer getLatestCostTuitionInState() {
        return latestCostTuitionInState;
    }

    @JsonProperty("latest.cost.tuition.in_state")
    public void setLatestCostTuitionInState(Integer latestCostTuitionInState) {
        this.latestCostTuitionInState = latestCostTuitionInState;
    }

    @JsonProperty("school.name")
    public String getSchoolName() {
        return schoolName;
    }

    @JsonProperty("school.name")
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @JsonProperty("latest.admissions.sat_scores.75th_percentile.critical_reading")
    public Float getLatestAdmissionsSatScores75thPercentileCriticalReading() {
        return latestAdmissionsSatScores75thPercentileCriticalReading;
    }

    @JsonProperty("latest.admissions.sat_scores.75th_percentile.critical_reading")
    public void setLatestAdmissionsSatScores75thPercentileCriticalReading(Float latestAdmissionsSatScores75thPercentileCriticalReading) {
        this.latestAdmissionsSatScores75thPercentileCriticalReading = latestAdmissionsSatScores75thPercentileCriticalReading;
    }

    @JsonProperty("school.state")
    public String getSchoolState() {
        return schoolState;
    }

    @JsonProperty("school.state")
    public void setSchoolState(String schoolState) {
        this.schoolState = schoolState;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("school.city")
    public String getSchoolCity() {
        return schoolCity;
    }

    @JsonProperty("school.city")
    public void setSchoolCity(String schoolCity) {
        this.schoolCity = schoolCity;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    @Override
    public String toString() {
        return "College{" +
                "latestCostTuitionOutOfState=" + latestCostTuitionOutOfState +
                ", latestAdmissionsSatScores75thPercentileMath=" + latestAdmissionsSatScores75thPercentileMath +
                ", schoolZip='" + schoolZip + '\'' +
                ", latestAdmissionsSatScores25thPercentileCriticalReading=" + latestAdmissionsSatScores25thPercentileCriticalReading +
                ", latestAdmissionsSatScores25thPercentileMath=" + latestAdmissionsSatScores25thPercentileMath +
                ", schoolSchoolUrl='" + schoolSchoolUrl + '\'' +
                ", latestCostTuitionInState=" + latestCostTuitionInState +
                ", schoolName='" + schoolName + '\'' +
                ", latestAdmissionsSatScores75thPercentileCriticalReading=" + latestAdmissionsSatScores75thPercentileCriticalReading +
                ", schoolState='" + schoolState + '\'' +
                ", id=" + id +
                ", schoolCity='" + schoolCity + '\'' +
                ", searchName='" + searchName + '\'' +
                '}';
    }
}
