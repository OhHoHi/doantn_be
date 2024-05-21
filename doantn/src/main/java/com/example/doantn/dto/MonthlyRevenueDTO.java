package com.example.doantn.dto;

public class MonthlyRevenueDTO {
    private int month;
    private int year;
    private double revenue;

    public MonthlyRevenueDTO(int month, int year, double revenue) {
        this.month = month;
        this.year = year;
        this.revenue = revenue;
    }

    // Getters and setters
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}