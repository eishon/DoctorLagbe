package death_slash13.doctorlagbe.doctor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

@Service
public class DoctorService {
	@Autowired
	private DoctorRepository doctorRepository;
	
	public Iterable<Doctor> getDoctors(){
		return doctorRepository.findAll();
	}
	
	public Iterable<Doctor> getSearchedDoctors(String speciality, String wDistrict, String wHospital){
		return doctorRepository.findBySpecialityAndWDistrictAndWHospital(speciality, wDistrict, wHospital);
	}
	
	public Iterable<Doctor> getSearchedDoctorsBySpecialityAndDistrict(String speciality, String wDistrict){
		return doctorRepository.findBySpecialityAndWDistrict(speciality, wDistrict);
	}
	public Iterable<Doctor> getSearchedDoctorsByDistrictAndHospital(String wDistrict, String wHospital){
		return doctorRepository.findByWDistrictAndWHospital(wDistrict, wHospital);
	}
	public Iterable<Doctor> getSearchedDoctorsBySpecialityAndHospital(String speciality, String wHospital){
		return doctorRepository.findBySpecialityAndWHospital(speciality, wHospital);
	}
	public Iterable<Doctor> getSearchedDoctorsBySpeciality(String speciality){
		return doctorRepository.findBySpeciality(speciality);
	}
	public Iterable<Doctor> getSearchedDoctorsByDistrict(String wDistrict){
		return doctorRepository.findByWDistrict(wDistrict);
	}
	public Iterable<Doctor> getSearchedDoctorsByHospital(String wHospital){
		return doctorRepository.findByWHospital(wHospital);
	}
	
	public String findDoctorByToken(String token) {
		return doctorRepository.findDoctorIdByToken(token);
	}
	
	public Doctor getDoctor(String mobile) throws NotFoundException{
		Optional<Doctor> doctor = doctorRepository.findById(mobile);
		doctor.ifPresent(d ->{
			new Doctor(d);
		});
		
		doctor.orElseThrow(() -> new NotFoundException(mobile+" Not Found"));
		
		return doctor.map(d -> new Doctor(d)).get();
	}

	public void addDoctor(Doctor doctor) {
		doctorRepository.save(doctor);
	}
	
	public void updateDoctor(String id, Doctor doctor) {
		doctorRepository.save(doctor);
	}

	public void deleteDoctor(String mobile) {
		doctorRepository.deleteById(mobile);
	}

}
