package death_slash13.doctorlagbe.doctor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import death_slash13.doctorlagbe.login.Login;
import death_slash13.doctorlagbe.login.LoginService;
import death_slash13.doctorlagbe.patient.Patient;
import death_slash13.doctorlagbe.patient.PatientService;
import javassist.NotFoundException;

@RestController
public class DoctorController {
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private PatientService patientService;
	@Autowired
	private LoginService loginService;
	
	@RequestMapping("/bloodgroup/{blood}")
	public HashMap<String, Object> getBloodDonor(@PathVariable String blood) throws NotFoundException {
		HashMap<String, Object> map = new HashMap<>();
		Iterable<Doctor> doctors = doctorService.getDoctors();
		Iterable<Patient> patients = patientService.getPatients();
		ArrayList<BloodSearch> bloodSearches = new ArrayList<>();
		
		for (Doctor d : doctors) {
			if(d.getBloodGroup().equals(blood)) {
				BloodSearch b = new BloodSearch(d.getName(), d.getMobile(), d.getBloodGroup(), d.getwDistrict());
				bloodSearches.add(b);
			}
		}
		
		for (Patient p : patients) {
			if(p.getBloodGroup().equals(blood)) {
				BloodSearch b = new BloodSearch(p.getName(), p.getMobile(), p.getBloodGroup(), p.getpDistrict());
				bloodSearches.add(b);
			}
		}
		
		map.put("all", bloodSearches);
		
		return map;
	}

	@RequestMapping("/{token}/alldoctors")
	public HashMap<String, Object> getAllDoctors(@PathVariable String token) throws NotFoundException {
		Login temp = new Login();
		temp = loginService.getLogin(loginService.getIdByAuthToken(token));
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("all", doctorService.getDoctors());
		
		if(temp.getType().equals("PATIENT"))
			return map;
		return null;
	}
	
	@RequestMapping("/{token}/doctors/search/{speciality}/{wDistrict}/{wHospital}")
	public HashMap<String, Object> getAllSearchedDoctors(@PathVariable String token, @PathVariable String speciality, @PathVariable String wDistrict, @PathVariable String wHospital) throws NotFoundException {
		Login temp = new Login();
		temp = loginService.getLogin(loginService.getIdByAuthToken(token));
		
		HashMap<String, Object> map = new HashMap<>();
		if(speciality.equals("ALL") || wDistrict.equals("ALL") || wHospital.equals("ALL")) {
			if(speciality.equals("ALL") &&  wDistrict.equals("ALL") && wHospital.equals("ALL"))
				map.put("all", doctorService.getDoctors());
			else if(speciality.equals("ALL") &&  wDistrict.equals("ALL"))
				map.put("all", doctorService.getSearchedDoctorsByHospital(wHospital));
			else if(speciality.equals("ALL") && wHospital.equals("ALL"))
				map.put("all", doctorService.getSearchedDoctorsByDistrict(wDistrict));
			else if(wDistrict.equals("ALL") && wHospital.equals("ALL"))
				map.put("all", doctorService.getSearchedDoctorsBySpeciality(speciality));
			else if(speciality.equals("ALL"))
				map.put("all", doctorService.getSearchedDoctorsByDistrictAndHospital(wDistrict, wHospital));
			else if(wDistrict.equals("ALL"))
				map.put("all", doctorService.getSearchedDoctorsBySpecialityAndHospital(speciality, wHospital));
			else if(wHospital.equals("ALL"))
				map.put("all", doctorService.getSearchedDoctorsBySpecialityAndDistrict(speciality, wDistrict));
		} else
			map.put("all", doctorService.getSearchedDoctors(speciality, wDistrict, wHospital));
		
		if(temp.getType().equals("PATIENT"))
			return map;
		return null;
	}
	
	//Get Doctor Info
	@RequestMapping("/{token}/doctors")
	public Doctor getDoctor(@PathVariable String token) throws NotFoundException{
		return doctorService.getDoctor(getDoctorIdByToken(token));
	}
	
	//Get Doctor Info
		@RequestMapping("/{token}/doctors/{doctorId}")
		public Doctor getDoctorInfo(@PathVariable String token, @PathVariable String doctorId) throws NotFoundException{
			return doctorService.getDoctor(doctorId);
		}
	
	//@RequestMapping(value="/{token}/doctor")
	public String getDoctorIdByToken(@PathVariable String token) {
		return doctorService.findDoctorByToken(token);
	}
	
	//Register
	@RequestMapping(method=RequestMethod.POST, value="/{pass}/doctors/r")
	public String addDoctor(@RequestBody Doctor doctor, @PathVariable String pass) {
		int min = 100000;
		int max = 999999;
		int val = 0;
		
		Random random = new Random();
		val = random.nextInt((max - min) + 1) + min;
		
		while(loginService.getTokenValidity(Integer.toString(val))) {
			val = random.nextInt((max - min) + 1) + min;
		}
		
		Login l = new Login();
		l.setId(doctor.getMobile());
		l.setPassword(pass);
		l.setToken(Integer.toString(val));
		l.setType("DOCTOR");
		
		doctorService.addDoctor(doctor);
		loginService.addLogin(l);
		
		return "Registered";
	}
	
	//Edit info
	@RequestMapping(method=RequestMethod.POST, value="/{token}/doctors")
	public String updateDoctor(@RequestBody Doctor doctor, @PathVariable String token) {
		doctorService.updateDoctor(getDoctorIdByToken(token), doctor);
		return "Updated";
	}
	
	//@RequestMapping(method=RequestMethod.DELETE, value="/doctors/{mobile}")
	public void deleteDoctor(@PathVariable String mobile) {
		doctorService.deleteDoctor(mobile);	
	}
	
}
