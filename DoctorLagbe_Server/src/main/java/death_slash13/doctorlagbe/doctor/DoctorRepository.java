package death_slash13.doctorlagbe.doctor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DoctorRepository extends  CrudRepository<Doctor, String> {
	//JPA Query
	public Iterable<Doctor> findBySpecialityContainingOrWDistrictContainingOrWHospitalContaining(String speciality, String wDistrict, String wHospital);
	public Iterable<Doctor> findBySpecialityAndWDistrictAndWHospital(String speciality, String wDistrict, String wHospital);
	public Iterable<Doctor> findBySpecialityAndWDistrict(String speciality, String wDistrict);
	public Iterable<Doctor> findBySpecialityAndWHospital(String speciality, String wHospital);
	public Iterable<Doctor> findByWDistrictAndWHospital(String wDistrict, String wHospital);
	public Iterable<Doctor> findBySpeciality(String speciality);
	public Iterable<Doctor> findByWDistrict(String wDistrict);
	public Iterable<Doctor> findByWHospital(String wHospital);
	@Query(value = "SELECT l.id FROM login l where l.token = :token and l.type = 'PATIENT'", nativeQuery = true) 
    public String findPatientIdByToken(@Param("token") String token);
	@Query(value = "SELECT l.id FROM login l where l.token = :token and l.type = 'DOCTOR'", nativeQuery = true) 
    public String findDoctorIdByToken(@Param("token") String token);
}
