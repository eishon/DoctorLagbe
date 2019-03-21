package death_slash13.doctorlagbe.patient;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import death_slash13.doctorlagbe.login.Login;
import death_slash13.doctorlagbe.login.LoginService;
import javassist.NotFoundException;

@RestController
public class PatientController {
	@Autowired
	private PatientService patientService;
	@Autowired
	private LoginService loginService;

	//@RequestMapping("/patients")
	public Iterable<Patient> getAllPatients() {
		return patientService.getPatients();
	}
	
	//Get Patient Info
	@RequestMapping("/{token}/patient")
	public Patient getPatient(@PathVariable String token) throws NotFoundException{
		return patientService.getPatient(getPatientIdByToken(token));
	}
	
	//@RequestMapping(value="/patient/{token}")
	public String getPatientIdByToken(@PathVariable String token) {
		return patientService.findPatientByToken(token);
	}
	
	//Register
	@RequestMapping(method=RequestMethod.POST, value="/{pass}/patient/r")
	public String addPatient(@RequestBody Patient patient, @PathVariable String pass) {
		int min = 100000;
		int max = 999999;
		int val = 0;
		
		Random random = new Random();
		val = random.nextInt((max - min) + 1) + min;
		
		while(loginService.getTokenValidity(Integer.toString(val))) {
			val = random.nextInt((max - min) + 1) + min;
		}
		
		Login l = new Login();
		l.setId(patient.getMobile());
		l.setPassword(pass);
		l.setToken(Integer.toString(val));
		l.setType("PATIENT");
		
		patientService.addPatient(patient);
		loginService.addLogin(l);
		
		return "Registered";
	}
	
	//Edit Info
	@RequestMapping(method=RequestMethod.POST, value="{token}/patients/")
	public String updatePatient(@RequestBody Patient patient, @PathVariable String token) {
		patientService.updatePatient(getPatientIdByToken(token), patient);
		return "Updated";
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/patients/{mobile}")
	public void deletePatient(@PathVariable String mobile) {
		patientService.deletePatient(mobile);	
	}
	
}
