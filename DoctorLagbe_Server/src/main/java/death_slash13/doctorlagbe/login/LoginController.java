package death_slash13.doctorlagbe.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;

@RestController
public class LoginController {
	@Autowired
	private LoginService loginService;

	@RequestMapping("/logins")
	public Iterable<Login> getAllLogins() {
		return loginService.getLogins();
	}
	
	//Login
	@RequestMapping(method=RequestMethod.POST, value="/login/d")
	public String loginDoctor(@RequestBody LoginReq loginReq) throws NotFoundException {
		Login temp = loginService.getLogin(loginReq.getId());
		if(temp.getPassword().equals(loginReq.getPassword()) && temp.getType().equals("DOCTOR")) {
			return temp.getToken();
		}
		return "";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/login/p")
	public String loginPatient(@RequestBody LoginReq loginReq) throws NotFoundException {
		Login temp = loginService.getLogin(loginReq.getId());
		if(temp.getPassword().equals(loginReq.getPassword()) && temp.getType().equals("PATIENT")) {
			return temp.getToken();
		}
		return "";
	}
	
	//@RequestMapping("/logins/{id}")
	public Login getLogin(@PathVariable String id) throws NotFoundException{
		return loginService.getLogin(id);
	}
	
	//@RequestMapping(method=RequestMethod.POST, value="/logins")
	public void addLogin(@RequestBody Login login) {
		loginService.addLogin(login);
	}
	
	//@RequestMapping(method=RequestMethod.PUT, value="/logins/{id}")
	public void updateLogin(@RequestBody Login login, @PathVariable String id) {
		loginService.updateLogin(id, login);	
	}
	
	//@RequestMapping(method=RequestMethod.DELETE, value="/logins/{id}")
	public void deleteLogin(@PathVariable String id) {
		loginService.deleteLogin(id);	
	}
	
}
