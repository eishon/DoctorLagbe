package death_slash13.doctorlagbe.patient;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import death_slash13.doctorlagbe.appointment.Appointment;
import javassist.NotFoundException;

@Service
public class PatientService {
	@Autowired
	private PatientRepository patientRepository;
	
	public Iterable<Patient> getPatients(){
		return patientRepository.findAll();
	}
	
	public String findPatientByToken(String token) {
		return patientRepository.findPatientIdByToken(token);
		//return patientRepository.findAllByToken(token).getId();
	}
	
	public Patient getPatient(String mobile) throws NotFoundException{
		Optional<Patient> patient = patientRepository.findById(mobile);
		patient.ifPresent(p ->{
			new Patient(p);
		});
		
		patient.orElseThrow(() -> new NotFoundException(mobile+" Not Found"));
		
		return patient.map(p -> new Patient(p)).get();
	}

	public void addPatient(Patient patient) {
		patientRepository.save(patient);
	}
	
	public void updatePatient(String id, Patient patient) {
		patientRepository.save(patient);
	}

	public void deletePatient(String mobile) {
		patientRepository.deleteById(mobile);
	}

}
