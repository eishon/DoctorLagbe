package com.death_slash.eishon.daclient.domain;

import java.io.Serializable;

public class Appointment implements Serializable {
    private String id;
    private String patientId;
    private String doctorId;
    private String state;
    private String message;
    private String patient_name;
    private String doctor_name;

    public Appointment() {

    }

    public Appointment(String id, String patientId, String doctorId, String state, String message, String patient_name, String doctor_name) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.state = state;
        this.message = message;
        this.patient_name = patient_name;
        this.doctor_name = doctor_name;
    }

    public Appointment(Appointment a) {
        this.id = a.id;
        this.patientId = a.patientId;
        this.doctorId = a.doctorId;
        this.state = a.state;
        this.message = a.message;
        this.patient_name = a.patient_name;
        this.doctor_name = a.doctor_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }
}
