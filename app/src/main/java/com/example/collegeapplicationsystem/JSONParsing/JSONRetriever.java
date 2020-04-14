package com.example.collegeapplicationsystem.JSONParsing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class JSONRetriever {

    private static final String BASE_LINK = "https://api.data.gov/ed/collegescorecard/v1/schools?" +
            "&_fields=id,school.city,school.zip,school.state,school.school_url,school.name,latest.cost.tuition.in_state,latest.cost.tuition.out_of_state,latest.admissions.sat_scores.25th_percentile.critical_reading," +
            "latest.admissions.sat_scores.75th_percentile.critical_reading," +
            "latest.admissions.sat_scores.75th_percentile.math," +
            "latest.admissions.sat_scores.25th_percentile.math&api_key=dgUsHXeGByGDW8f0tZNaettzE1VmI7329ru1vLKl&_per_page=100" +
            "&page=";
    private static final int START_PAGE = 0;
    private static final int TIMEOUT_TIME = 30000;

    private int totalPages;

    private static int connectionCount = 0;

    public JSONRetriever() {
    }

    private String getDataFromApi(String link) {
        StringBuilder data = new StringBuilder();
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            httpURLConnection.setReadTimeout(TIMEOUT_TIME);
            httpURLConnection.connect();
            System.out.println("API Call #" + ++connectionCount);

            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    data.append(scanner.nextLine());
                }
                scanner.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    public Holder mapPageToObject(int page) {
        String data = getDataFromApi(BASE_LINK + page);
        Holder holder = parseJSON(data);
        addSearchNameToCollege(holder);

        return holder;
    }

    public Holder mapAllPagesToObjects() {
        Holder allColleges = mapPageToObject(START_PAGE);
        int totalColleges = allColleges.getMetadata().getTotal();
        if (totalColleges % 100 == 0) {
            totalPages = totalColleges / 100;
        } else {
            totalPages = totalColleges / 100 + 1;
        }

        for (int i = START_PAGE + 1; i < totalPages; i++) {
            allColleges.getColleges().addAll(mapPageToObject(i).getColleges());
        }
        return allColleges;
    }

    private void addSearchNameToCollege(Holder holder) {
        for (College college : holder.getColleges()) {
            college.setSearchName(college.getSchoolName().trim().toLowerCase());
        }
    }

    private Holder parseJSON(String data) {
        ObjectMapper objectMapper = new ObjectMapper();
        Holder holder = null;
        try {
            holder = objectMapper.readValue(data, Holder.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return holder;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
