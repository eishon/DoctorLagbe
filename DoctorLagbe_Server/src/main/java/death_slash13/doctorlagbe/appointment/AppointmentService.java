package death_slash13.doctorlagbe.appointment;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

@Service
public class AppointmentService {
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	public Iterable<Appointment> getAppointments(){
		return appointmentRepository.findAll();
	}
	
	public Appointment getAppointment(String id) throws NotFoundException{
		Optional<Appointment> appointment = appointmentRepository.findById(id);
		appointment.ifPresent(a ->{
			new Appointment(a);
		});
		
		appointment.orElseThrow(() -> new NotFoundException(id+" Not Found"));
		
		return appointment.map(a -> new Appointment(a)).get();
	}

	public void addAppointment(Appointment appointment) {
		appointmentRepository.save(appointment);
	}
	
	public void updateAppointment(String id, Appointment appointment) {
		appointmentRepository.save(appointment);
	}

	public void deleteAppointment(String id) {
		appointmentRepository.deleteById(id);
	}
	
	public String findPatientIdByToken(String token) {
		return appointmentRepository.findPatientIdByToken(token);
	}
	
	public String findDoctorIdByToken(String token) {
		return appointmentRepository.findDoctorIdByToken(token);
	}
	
	public long getRowSize() {
		return appointmentRepository.count();
	}
	
	public Iterable<Appointment> getByDoctorId(String doctorId){
		return appointmentRepository.findByDoctorId(doctorId);
	}
	
	public Iterable<Appointment> getByPatientId(String patientId){
		return appointmentRepository.findByPatientId(patientId);
	}

}
