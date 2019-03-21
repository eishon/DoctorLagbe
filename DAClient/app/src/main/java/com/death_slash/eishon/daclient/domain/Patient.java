package com.death_slash.eishon.daclient.domain;

import java.io.Serializable;

public class Patient implements Serializable {
    private String mobile;
    private String name;
    private String sex;
    private String age;
    private String bloodGroup;
    private String pDistrict;

    public Patient() {

    }

    public Patient(String mobile) {
        this.mobile = mobile;
        this.name = "";
        this.sex = "";
        this.age = "";
        this.bloodGroup = "";
        this.pDistrict = "";
    }

    public Patient(String mobile, String name, String sex, String age, String bloodGroup, String pDistrict) {
        this.mobile = mobile;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.pDistrict = pDistrict;
    }

    public Patient(Patient p) {
        this.mobile = p.mobile;
        this.name = p.name;
        this.sex = p.sex;
        this.age = p.age;
        this.bloodGroup = p.bloodGroup;
        this.pDistrict = p.pDistrict;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getpDistrict() {
        return pDistrict;
    }

    public void setpDistrict(String pDistrict) {
        this.pDistrict = pDistrict;
    }
}
