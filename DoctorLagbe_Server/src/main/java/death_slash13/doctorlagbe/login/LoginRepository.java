package death_slash13.doctorlagbe.login;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LoginRepository extends  CrudRepository<Login, String> {
	@Query(value = "SELECT l.id FROM login l where l.token = :token", nativeQuery = true) 
    public String findLoginIdByToken(@Param("token") String token);
}
