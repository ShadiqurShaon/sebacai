package com.example.medicine;

public class PastAppointmentDatatype {
    private String id;
    private String patient_name;
    private String doctor_name;
    private String doctor_location;
    private String doctor_designation;
    private String appointment_date;

    public PastAppointmentDatatype(String id, String patient_name, String doctor_name, String doctor_location, String doctor_designation, String appointment_date) {
        this.id = id;
        this.patient_name = patient_name;
        this.doctor_name = doctor_name;
        this.doctor_location = doctor_location;
        this.doctor_designation = doctor_designation;
        this.appointment_date = appointment_date;
    }

    public String getId() {
        return id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public String getDoctor_location() {
        return doctor_location;
    }

    public String getDoctor_designation() {
        return doctor_designation;
    }

    public String getAppointment_date() {
        return appointment_date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public void setDoctor_location(String doctor_location) {
        this.doctor_location = doctor_location;
    }

    public void setDoctor_designation(String doctor_designation) {
        this.doctor_designation = doctor_designation;
    }

    public void setAppointment_date(String appointment_date) {
        this.appointment_date = appointment_date;
    }
}
