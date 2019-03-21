package death_slash13.doctorlagbe.appointment;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import death_slash13.doctorlagbe.doctor.DoctorService;
import death_slash13.doctorlagbe.login.Login;
import death_slash13.doctorlagbe.login.LoginService;
import death_slash13.doctorlagbe.patient.PatientService;
import javassist.NotFoundException;

@RestController
public class AppointmentController {
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private PatientService patientService;
	@Autowired
	private DoctorService doctorService;

	//@RequestMapping("/appointments")
	public Iterable<Appointment> getAllAppointments() {
		return appointmentService.getAppointments();
	}
	
	@RequestMapping("/{token}/appointments")
	public HashMap<String, Object> getDoctorAppointments(@PathVariable String token) throws NotFoundException {
		Login temp = loginService.getLogin(loginService.getIdByAuthToken(token));
		HashMap<String, Object> map = new HashMap<>();
		if(temp.getType().equals("DOCTOR"))
			map.put("all", appointmentService.getByDoctorId(temp.getId()));
		else
			map.put("all", appointmentService.getByPatientId(temp.getId()));
		
		return map;
	}
	
	//@RequestMapping("/appointments/{id}")
	public Appointment getAppointment(@PathVariable String id) throws NotFoundException{
		return appointmentService.getAppointment(id);
	}
	
	//set appointment
	@RequestMapping("{patientToken}/appointment/{doctorId}/req")
	public String reqAppointment(@PathVariable String patientToken, @PathVariable String doctorId) throws NotFoundException{
		Appointment appointment = new Appointment();
		appointment.setId(Long.toString(appointmentService.getRowSize()+1));
		appointment.setPatientId(appointmentService.findPatientIdByToken(patientToken));
		appointment.setDoctorId(doctorId);
		appointment.setState("WAITING");
		appointment.setMessage("");
		appointment.setPatient_name(patientService.getPatient(appointment.getPatientId()).getName());
		appointment.setDoctor_name(doctorService.getDoctor(appointment.getDoctorId()).getName());
		
		appointmentService.addAppointment(appointment);
		return "Appointment Set";
	}
	
	//accept appointment
	@RequestMapping("{doctorToken}/appointment/{appointmentId}/accept/{msg}")
	public String acceptAppointment(@PathVariable String doctorToken, @PathVariable String appointmentId, @PathVariable String msg) throws NotFoundException{
		Appointment appointment = appointmentService.getAppointment(appointmentId);
		appointment.setState("ACCEPTED");
		appointment.setMessage(msg);
		
		appointmentService.updateAppointment(appointmentId, appointment);
		
		return "Appointment Accepted";
	}
	
	//reject appointment
	@RequestMapping("{doctorToken}/appointment/{appointmentId}/reject/{msg}")
	public String rejectAppointment(@PathVariable String doctorToken, @PathVariable String appointmentId, @PathVariable String msg) throws NotFoundException{
		Appointment appointment = appointmentService.getAppointment(appointmentId);
		appointment.setState("REJECTED");
		appointment.setMessage(msg);
		
		appointmentService.updateAppointment(appointmentId, appointment);
		
		return "Appointment Rejected";
	}
	
	//@RequestMapping(method=RequestMethod.POST, value="/appointments")
	public void addAppointment(@RequestBody Appointment appointment) {
		appointmentService.addAppointment(appointment);
	}
	
	//@RequestMapping(method=RequestMethod.PUT, value="/appointments/{id}")
	public void updateAppointment(@RequestBody Appointment appointment, @PathVariable String id) {
		appointmentService.updateAppointment(id, appointment);	
	}
	
	//@RequestMapping(method=RequestMethod.DELETE, value="/appointments/{id}")
	public void deleteAppointment(@PathVariable String id) {
		appointmentService.deleteAppointment(id);	
	}
	
}
