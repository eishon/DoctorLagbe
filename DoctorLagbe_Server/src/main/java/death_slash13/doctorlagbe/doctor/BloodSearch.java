package death_slash13.doctorlagbe.doctor;

public class BloodSearch {
	private String name;
    private String mobile;
    private String blood;
    private String district;

    public BloodSearch(){

    }

    public BloodSearch(String name, String mobile, String blood, String district){
        this.name = name;
        this.mobile = mobile;
        this.blood = blood;
        this.district = district;
    }

    public BloodSearch(BloodSearch b){
        this.name = b.name;
        this.mobile = b.mobile;
        this.blood = b.blood;
        this.district = b.district;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getBlood() {
        return blood;
    }

    public String getDistrict() {
        return district;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
