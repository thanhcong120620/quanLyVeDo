package projectSpringboot.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import projectSpringboot.dto.UserDTO;
import projectSpringboot.service.ICusHistoryService;
import projectSpringboot.service.IUserService;
import projectSpringboot.service.implement.UserCanNotDeleteException;

@RestController
public class UserAPI {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ICusHistoryService chService;
	
	
	
	@PostMapping(value = "/api/user")
	public UserDTO createUser(@RequestBody UserDTO model) {
		System.out.println(model.toString());
		
		return userService.save(model);
	}
	
	
	@GetMapping(value = "/api/chUser/{id}")
	public List<UserDTO> getAllCusHistoryUserById(@PathVariable("id") long id) {
		return chService.findAllByUser_id(id);
	}
	
	
	@GetMapping(value = "/api/user/{id}")
	public UserDTO getUserById(@PathVariable("id") long id) {
		return userService.getUserById(id);
	}
	
	
	@GetMapping(value = "/api/userAll")
	public List<UserDTO> displayData() {
		return userService.findAllUser();
	}
	
	
	@GetMapping(value = "/api/userSearch")
	public List<UserDTO> searchData(@RequestBody UserDTO model) {
		
		
		return userService.searchList(model);
	}
	
	
	
	@PutMapping(value = "/api/user/{id}")
	public UserDTO updateUser(@RequestBody UserDTO model, @PathVariable("id") long id) {
		model.setId(id);
		
		return userService.save(model);

	}
	
	
	@DeleteMapping(value = "/api/user/{id}")
	public void deleteOne(@PathVariable("id") long id) {
		
		try {
			userService.deleteOne(id);
		} catch (UserCanNotDeleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	@DeleteMapping(value = "/api/user")
	public void deleteMany(@RequestBody String ids) {
		userService.deleteMany(ids);
	}
	
	
	

}
