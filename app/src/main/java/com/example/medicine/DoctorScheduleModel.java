package com.example.medicine;

public class DoctorScheduleModel {
    String day;
    String time;

    public DoctorScheduleModel(String day, String time) {
        this.day = day;
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
