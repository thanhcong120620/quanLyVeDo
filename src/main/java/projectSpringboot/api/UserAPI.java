package projectSpringboot.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import projectSpringboot.dto.UserDTO;
import projectSpringboot.entity.CusHistoryEntity;
import projectSpringboot.entity.UserEntity;
import projectSpringboot.exceptionAndError.CanNotFindUserException;
import projectSpringboot.exceptionAndError.UserCanNotDeleteException;
import projectSpringboot.exceptionAndError.UserCanNotUpdateAdminException;
import projectSpringboot.exceptionAndError.UserNotFoundException;
import projectSpringboot.service.ICusHistoryService;
import projectSpringboot.service.IUserService;
import projectSpringboot.service.implement.MailService;

@RestController
public class UserAPI {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ICusHistoryService chService;
	
    @Autowired
    MailService mailService;
	
	
    
    /*
     * cusLottery with Pagination--------------------------------------------------------------
     * */
 	@GetMapping(value = "/api/userTest")
 	public List<UserDTO> viewHomeUserPage(Model model, @RequestBody UserDTO dtoUserCl) {
 		System.out.println("run test (controller) ");
 		System.out.println(dtoUserCl.toString());
 	 return findPaginatedCusHisPage(1, model);  
 	}
 	
 	@GetMapping(value = "/api/userTest/page/{pageNo}")
 	public List<UserDTO> findPaginatedCusHisPage(@PathVariable(value = "pageNo") int pageNo, Model model) {
 	    int pageSize = 2;
		Page<UserEntity> page = userService.findPaginated(pageNo, pageSize);
		List<UserDTO> listUser = userService.findAllUserPagination(pageNo, pageSize);
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listUser", listUser);
		return listUser;
 		
 	}
    
    
    
    
    /*
     * cusLottery with Pagination--------------------------------------------------------------
     * */
    // display list of cusHistory
// 	@GetMapping(value = "/api/test")
// 	public List<UserDTO> viewHomeCusHisPage(Model model, @RequestBody UserDTO dtoUserCl) {
// 		System.out.println("run test (controller) ");
// 		System.out.println(dtoUserCl.toString());
// 	 return findPaginated(1, model, dtoUserCl);  
// 	}
// 	
// 	@GetMapping(value = "/page/{pageNo}")
// 	public List<UserDTO> findPaginatedCusHisPage(@PathVariable(value = "pageNo") int pageNo, Model model, @RequestBody UserDTO dtoUserCl) {
// 	    int pageSize = 2;
// 	   
// 	   
// 		try {
//// 			System.out.println("dtoUserCl 2: "+dtoUserCl.toString());
//// 			System.out.println("Mail: "+dtoUserCl.getMailUser());
// 			 UserDTO dto = userService.getUserByMailUser(dtoUserCl);
//// 			System.out.println("dto: "+dto.toString());
// 		    Page<CusHistoryEntity> page = chService.findPaginated(dto.getId(),pageNo, pageSize);
// 		    List<UserDTO> listCusLottery = chService.findAllByUser_id2(dto.getId(), pageNo, pageSize);
// 		    
// 		    System.out.println("pageNo: "+pageNo+", page.getTotalPages(): "+page.getTotalPages()+", page.getTotalElements(): "+page.getTotalElements());
// 		    
// 		    model.addAttribute("currentPage", pageNo);
// 		    model.addAttribute("totalPages", page.getTotalPages());
// 		    model.addAttribute("totalItems", page.getTotalElements());
// 		    model.addAttribute("listCusLottery", listCusLottery);
//// 		    System.out.println("run findPaginated (controller) ");
// 		    return listCusLottery;
// 		} catch (CanNotFindUserException e) {
// 			System.out.println("run findPaginated catch  (controller) ");
// 			System.out.println("Login fail");
// 			String s = ""+e;
// 			String[] output = s.split(":");
//// 			String errorMessage = output[1];
// 			String errorMessage = "Tài khoản hoặc mật khẩu không đúng";
// 			model.addAttribute("errorMessage",errorMessage);
// 			model.addAttribute("nameUser","Đăng nhập thất bại");
// 			System.out.println(errorMessage);
// 			
//// 			UserDTO dtoUserSer = new UserDTO();
//// 			model.addAttribute("dtoUser",dtoUserSer);	
// 			return null;
// 		}
//    
//    
//	
// 	}
    
    /*-------------------------------------------------------------*/
    
	@PostMapping(value = "/api/cusHistory/drawAndSave")
	public UserDTO chDrawAndSave(@RequestBody UserDTO model)  {
		return chService.save(model);
	}
 	
 	
	@DeleteMapping(value = "/api/chUser/{id}")
	public void deleteOneCH(@PathVariable("id") long id)  {
		chService.deleteOne(id);
	}
    
	@DeleteMapping(value = "/api/chUser")
	public void deleteManyCH(@RequestBody String ids) {
			try {
				chService.deleteMany(ids,2);
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
    
	@PostMapping(value = "/api/chUser")
	public UserDTO saveCusHistory(@RequestBody UserDTO model) {
		System.out.println("Input: "+ model.toString());
		//Dto phải có ID của User trước, yêu cầu user phải đăng nhập trước khi do
		UserDTO dto = chService.save(model);
		System.out.println("Out put: "+ dto.toString());
		return dto;
	}
	
	
	@GetMapping(value = "/api/chUser/{id}")
	public List<UserDTO> getAllCusHistoryUserById(@PathVariable("id") long id) {
		return chService.findAllByUserid(id);
	}
	
	
	
	
    
	/*-------------------------------------test User--------------------------------*/
    
	@PostMapping(value = "/api/passwordSupport")
	public void passwordSupport(@RequestBody UserDTO model) throws CanNotFindUserException {
		System.out.println("Model: "+model.toString());		
		userService.updateUserPasswordByMailUser(model);
	}
	
	
	@PostMapping(value = "/api/userLogin")
	public void loginUser(@RequestBody UserDTO model) throws CanNotFindUserException {
		System.out.println("Model: "+model.toString());		
		userService.getUserByMailUser(model);
	}
	
	
	
	@PostMapping(value = "/api/user")
	public void createUser(@RequestBody UserDTO model) throws UserCanNotUpdateAdminException {
		System.out.println(model.toString());
		
		userService.save(model);
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
		
		
		return userService.searchList(model,1,2);//being error
	}
	
	
	
	@PutMapping(value = "/api/user/{id}")
	public void updateUser(@RequestBody UserDTO model, @PathVariable("id") long id) throws UserCanNotUpdateAdminException {
		model.setId(id);
		
		userService.save(model);

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
	public void deleteMany(@RequestBody String ids) throws UserNotFoundException {
		try {
			userService.deleteMany(ids);
		} catch (UserCanNotDeleteException e) {
			String s = ""+e;
			System.out.println(s);
		}
	}
	
/*Send mail API*/	
    @PostMapping(value = "/api/sendMail")
    public void sendMail(@RequestBody UserDTO model) throws CanNotFindUserException {
//        mailService.sendMail("Thành Công","Cong!120620","nguyenthanhcong.dn.cv@gmail.com");
    	userService.changeAndSendPwdByMail(model);
    }

}
