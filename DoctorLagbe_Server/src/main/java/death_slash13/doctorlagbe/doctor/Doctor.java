package death_slash13.doctorlagbe.doctor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "doctor")
public class Doctor {
	@Id
	@Column( name = "mobile")
	private String mobile;
	@Column( name = "name")
	private String name;
	@Column( name = "sex")
	private String sex;
	@Column( name = "bloodGroup")
	private String bloodGroup;
	@Column( name = "speciality")
	private String speciality;
	@Column( name = "BDMCId")
	private String BDMCId;
	@Column( name = "wDistrict")
	private String wDistrict;
	@Column( name = "wHospital")
	private String wHospital;
	@Column( name = "mobile4A")
	private String mobile4A;
	
	public Doctor() {
		
	}
	
	public Doctor(String mobile) {
		this.mobile = mobile;
		this.name = "";
		this.sex = "";
		this.bloodGroup = "";
		this.speciality = "";
		this.BDMCId = "";
		this.wDistrict = "";
		this.wHospital = "";
		this.mobile4A = "";
	}
	
	public Doctor(String mobile, String name, String sex, String bloodGroup, String speciality, String bDMCId,
			String wDistrict, String wHospital, String mobile4a) {
		this.mobile = mobile;
		this.name = name;
		this.sex = sex;
		this.bloodGroup = bloodGroup;
		this.speciality = speciality;
		this.BDMCId = bDMCId;
		this.wDistrict = wDistrict;
		this.wHospital = wHospital;
		this.mobile4A = mobile4a;
	}
	
	public Doctor(Doctor d) {
		this.mobile = d.mobile;
		this.name = d.name;
		this.sex = d.sex;
		this.bloodGroup = d.bloodGroup;
		this.speciality = d.speciality;
		this.BDMCId = d.BDMCId;
		this.wDistrict = d.wDistrict;
		this.wHospital = d.wHospital;
		this.mobile4A = d.mobile4A;
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
	public String getBloodGroup() {
		return bloodGroup;
	}
	
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getSpeciality() {
		return speciality;
	}
	
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getwDistrict() {
		return wDistrict;
	}
	
	public void setwDistrict(String wDistrict) {
		this.wDistrict = wDistrict;
	}
	public String getwHospital() {
		return wHospital;
	}
	
	public void setwHospital(String wHospital) {
		this.wHospital = wHospital;
	}
	
	public String getMobile4A() {
		return mobile4A;
	}
	public void setMobile4A(String mobile4a) {
		mobile4A = mobile4a;
	}

	public String getBDMCId() {
		return BDMCId;
	}

	public void setBDMCId(String bDMCId) {
		BDMCId = bDMCId;
	}
}
