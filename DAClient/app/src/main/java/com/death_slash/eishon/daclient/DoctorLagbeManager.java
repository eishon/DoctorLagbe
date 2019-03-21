package com.death_slash.eishon.daclient;

import android.content.Context;

import com.death_slash.eishon.daclient.domain.Doctor;
import com.death_slash.eishon.daclient.domain.Patient;

public class DoctorLagbeManager {
    private static Context context;

    private static String BASE_URL = "";
    private static String API_TOKEN = "";

    private static Doctor doctor;
    private static Patient patient;

    public static boolean userIsDoctor = true;
    public static boolean newUserIsDoctor = true;

    public static void setContext(Context context) {
        DoctorLagbeManager.context = context;
    }

    public static void setBaseUrl(String baseUrl){
        BASE_URL = baseUrl;
    }

    public static void setAPIToken(String api_token){
        API_TOKEN = api_token;
    }

    public static Doctor getDoctor() {
        return doctor;
    }

    public static Patient getPatient() {
        return patient;
    }

    public static void setDoctor(Doctor doctor) {
        DoctorLagbeManager.doctor = doctor;
    }

    public static void setPatient(Patient patient) {
        DoctorLagbeManager.patient = patient;
    }

    public static String getAPIToken(){
        return API_TOKEN;
    }

    public static String loginDoctorURL(){
        String url = "";
        url += BASE_URL + "/login/d";
        return url;
    }

    public static String loginPatientURL(){
        String url = "";
        url += BASE_URL + "/login/p";
        return url;
    }

    public static String registerPatientURL(String pass){
        String url = "";
        url += BASE_URL + "/" + pass + "/patients/r";
        return url;
    }

    public static String getPatientInfoURL(String token){
        String url = "";
        url += BASE_URL + "/" + token + "/patient";
        return url;
    }

    public static String editPatientInfoURL(String token){
        String url = "";
        url += BASE_URL + "/" + token + "/patients";
        return url;
    }

    public static String registerDoctorURL(String pass){
        String url = "";
        url += BASE_URL + "/" + pass + "/doctors/r";
        return url;
    }

    public static String getDoctorInfoURL(String token){
        String url = "";
        url += BASE_URL + "/" + token + "/doctors";
        return url;
    }

    public static String getDoctorInfoURL_2(String token, String id){
        String url = "";
        url += BASE_URL + "/" + token + "/doctors/" + id;
        return url;
    }

    public static String getAllDoctorInfoURL(String token){
        String url = "";
        url += BASE_URL + "/" + token + "/alldoctors";
        return url;
    }

    public static String getAllDoctorInfoSearchURL(String token, String district, String hospital, String speciality){
        String url = "";
        url += BASE_URL + "/" + token + "/doctors/search/" + speciality + "/" + district + "/" + hospital;
        return url;
    }

    public static String editDoctorInfoURL(String token){
        String url = "";
        url += BASE_URL + "/" + token + "/doctors";
        return url;
    }

    public static String getAppointmentsURL(String token){
        String url = "";
        url += BASE_URL + "/" + token + "/appointments";
        return url;
    }

    public static String setAppointmentsURL(String patientToken, String doctorId){
        String url = "";
        url += BASE_URL + "/" + patientToken + "/appointment/" + doctorId + "/req";
        return url;
    }

    public static String acceptAppointmentsURL(String doctorToken, String appointmentId, String msg){
        String url = "";
        url += BASE_URL + "/" + doctorToken + "/appointment/" + appointmentId + "/accept/" + msg;
        return url;
    }

    public static String rejectAppointmentsURL(String doctorToken, String appointmentId, String msg){
        String url = "";
        url += BASE_URL + "/" + doctorToken + "/appointment/" + appointmentId + "/reject/" + msg;
        return url;
    }

    public static String getBloodGroup(String bg){
        String url = "";
        url += BASE_URL + "/bloodgroup/" + bg;
        return url;
    }
}
