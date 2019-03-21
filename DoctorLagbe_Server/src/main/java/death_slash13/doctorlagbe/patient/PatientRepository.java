package death_slash13.doctorlagbe.patient;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import death_slash13.doctorlagbe.login.Login;

public interface PatientRepository extends  CrudRepository<Patient, String> {
	@Query(value = "SELECT l.id FROM login l where l.token = :token and l.type = 'PATIENT'", nativeQuery = true) 
    public String findPatientIdByToken(@Param("token") String token);
	@Query(value = "SELECT l.id FROM login l where l.token = :token and l.type = 'DOCTOR'", nativeQuery = true) 
    public String findDoctorIdByToken(@Param("token") String token);
}
