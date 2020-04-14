package com.example.collegeapplicationsystem.JSONParsing;

// Test class

public class Demo {
    public static void main(String[] args) {
        JSONRetriever jsonRetriever = new JSONRetriever();
        Holder holder = jsonRetriever.mapAllPagesToObjects();
        System.out.println(holder.getColleges().size());
    }
}
