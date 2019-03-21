package death_slash13.doctorlagbe.login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

@Service
public class LoginService {
	@Autowired
	private LoginRepository loginRepository;
	
	public Iterable<Login> getLogins(){
		return loginRepository.findAll();
	}
	
	public Login getLogin(String id) throws NotFoundException{
		Optional<Login> login = loginRepository.findById(id);
		login.ifPresent(l ->{
			new Login(l);
		});
		
		login.orElseThrow(() -> new NotFoundException(id+" Not Found"));
		
		return login.map(l -> new Login(l)).get();
	}
	
	public String getIdByAuthToken(String token) throws NotFoundException{
		return loginRepository.findLoginIdByToken(token);
	}

	public void addLogin(Login login) {
		loginRepository.save(login);
	}
	
	public void updateLogin(String id, Login login) {
		loginRepository.save(login);
	}

	public void deleteLogin(String id) {
		loginRepository.deleteById(id);
	}
	
	public boolean getTokenValidity(String token) {
		try {
			if(getIdByAuthToken(token)!=null)
				return true;
		}catch (NotFoundException e) {
			
		}
		return false;	
	}

}
