package projectSpringboot.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import projectSpringboot.dto.LotteryDTO;
import projectSpringboot.dto.UserDTO;
import projectSpringboot.entity.UserEntity;
import projectSpringboot.exceptionAndError.CanNotCreateUserException;
import projectSpringboot.exceptionAndError.CanNotFindUserException;
import projectSpringboot.exceptionAndError.UserCanNotDeleteException;
import projectSpringboot.exceptionAndError.UserCanNotUpdateAdminException;
import projectSpringboot.service.IUserService;

@Controller
public class UserController {

	@Autowired
	private IUserService userService;
	

/*-----------------------------login + register + password support---------------------------------------*/	
	
	
	
	/*
	 * send new password by mail
	 * */
	@RequestMapping(value = "/passwordMail", method = RequestMethod.POST)
	public String forgetPassword(@ModelAttribute("dtoUser") UserDTO dtoUser, Model model) {
		System.out.println("Model: "+model.toString());		
		try {
			userService.changeAndSendPwdByMail(dtoUser);
			System.out.println("Send success");
			return "redirect:/login";
		} catch (CanNotFindUserException e) {
			System.out.println("Send fail");
			String s = ""+e;
			String[] output = s.split(":");
			String errorMessage = output[1];
			model.addAttribute("errorMessage",errorMessage);
			System.out.println(errorMessage);
			return "forgetPassword";
		}
	}
	
	
	
	/*
	 * create new User
	 * */
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public String newUser(@ModelAttribute("dtoUser") UserDTO dtoUser, Model model) {
		System.out.println("Model: "+model.toString());		
		try {
			userService.createNewUserByRegister(dtoUser);
			System.out.println("create success");
			return "redirect:/login";
		} catch (CanNotCreateUserException e) {
			System.out.println("create fail");
			String s = ""+e;
			String[] output = s.split(":");
			String errorMessage = output[1];
			model.addAttribute("errorMessage",errorMessage);
			System.out.println(errorMessage);
			
			UserDTO dtoUser2 = new UserDTO();
			model.addAttribute("dtoUser",dtoUser2);	
			return "register";
		}
	}
	
	
	/*
	 * change password
	 * */
	@RequestMapping(value = "/passwordSupport", method = RequestMethod.POST)
	public String passwordSupport(@ModelAttribute("dtoUser") UserDTO dtoUser, Model model) {
		System.out.println("Model: "+model.toString());		
		try {
			userService.updateUserPasswordByMailUser(dtoUser);
			System.out.println("Change success");
			return "redirect:/login";
		} catch (CanNotFindUserException e) {
			System.out.println("Change fail");
			String s = ""+e;
			String[] output = s.split(":");
			String errorMessage = output[1];
			model.addAttribute("errorMessage",errorMessage);
			System.out.println(errorMessage);
			return "passwordSupport";
		}
	}
	
	
	/*
	 * login to data (tạm thời chưa sử dụng, đang sử dụng với MD5)
	 * */
	@RequestMapping(value = "/loginUser", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("dtoUser") UserDTO dtoUser, Model model, HttpServletRequest request) {
		
		System.out.println(dtoUser.toString());
		System.out.println("Url: /loginUser");
		//Save Lottery Board
		try {
			UserDTO dto =  userService.getUserByMailUser(dtoUser);
//			model.addAttribute("dtoUser",dto);
			System.out.println("Login success");
			request.getSession().setAttribute("dtoUserSession", dtoUser);
			//if admin to move admin page
			if(dto.getRoleUser().equals("admin")) {
				return "redirect:/UserManager";
			}		
			// if customer to move landing page
			return "landingPage";
			
		} catch (CanNotFindUserException e) {
			System.out.println("Login fail");
			String s = ""+e;
			String[] output = s.split(":");
			String errorMessage = output[1];
			model.addAttribute("errorMessage",errorMessage);
			System.out.println(errorMessage);
//			e.printStackTrace();
			return "login";
			
		}
		
		
	}
	
	
/*Move to page*/	
	
	/*
	 * (tạm thời chưa sử dụng)
	 * */
	@GetMapping("/loginLock")
	public String viewLogin(Model model) {
		UserDTO dtoUser = new UserDTO();
		model.addAttribute("dtoUser",dtoUser);		

		return "login";
	}
	
	@GetMapping("/register")
	public String viewRegister(Model model) {
		UserDTO dtoUser = new UserDTO();
		model.addAttribute("dtoUser",dtoUser);		

		return "register";
	}
	
	@GetMapping("/passwordSupport")
	public String viewPasswordSupport(Model model) {
		UserDTO dtoUser = new UserDTO();
		model.addAttribute("dtoUser",dtoUser);		

		return "passwordSupport";
	}
	
	@GetMapping("/forgetPassword")
	public String viewForgetPassword(Model model) {
		UserDTO dtoUser = new UserDTO();
		model.addAttribute("dtoUser",dtoUser);		

		return "forgetPassword";
	}
	
//	@GetMapping("/cusLottery")
//	public String viewCusHistory(Model model) {
//		UserDTO dtoUser = new UserDTO();
//		model.addAttribute("dtoUser",dtoUser);		
//
//		return "cusLottery";
//	}
	
	
	
	
/*-----------------------------------------User data----------------------------------------------------*/	
	
	/*
	 * Reset password
	 * */
	@RequestMapping(value = "/resetPassword/{id}", method = RequestMethod.GET)
	public String resetPassword(@PathVariable(name="id") long id, Model model) {
		UserDTO dto = userService.resetPassword(id);
		model.addAttribute("dtoUser",dto);
		System.out.println("oke id: "+id+"  & "+dto.getPasswordUser());
		
		return "userUpdate";
	}
	
	
	
	/*
	 * Save User into database
	 * */
	@RequestMapping(value = "/updateAndSave", method = RequestMethod.POST)
	public String updateAndSave(@ModelAttribute("dtoUser") UserDTO dto, Model model) {
		
		System.out.println("Id in save: "+dto.getId());
		
//		userService.save(dto);
//		return "redirect:/UserManager";
		try {
			System.out.println("run try !");
			userService.save(dto);
			return "redirect:/admin/UserManager";
		} catch (UserCanNotUpdateAdminException e) {
			System.out.println("run catch !");
			UserDTO dto2 = dto;
			dto2.setRoleUser("customer");
			model.addAttribute("dtoUser",dto2);
			
			//Convert error information
			String s = ""+e;
			String[] output = s.split(":");
			String errorMessage = output[1];
			model.addAttribute("errorMessage",errorMessage);
			System.out.println(errorMessage);
			
			return "userUpdate";
		}
		
	}
	
	
	
	/* 
	 * UpdateSave Lottery into database
	 * */
	@GetMapping("/editUser/{id}")
	public String showLotteryById(@PathVariable(name="id") long id, Model model,HttpServletRequest request) {
		
		//Get name
		UserDTO dtoUserSession = (UserDTO)request.getSession().getAttribute("dtoUserSession");
		model.addAttribute("nameUser",userService.getNameByMailUser(dtoUserSession));
		UserDTO dto = userService.getUserById(id);
		System.out.println("Id in edit: "+dto.getId());
		model.addAttribute("dtoUser",dto);
		return "userUpdate";
	}
	
	
	
	/*
	 * Search User
	 * */
	@RequestMapping(value = "/searchUser/{pageNo}", method = RequestMethod.POST)
	public String searchUser(HttpServletRequest request, @PathVariable(value = "pageNo") int pageNo,
								@ModelAttribute("dtoUser") UserDTO dtoReturn, Model model) {
		
		int pageSize =2;
		Page<UserEntity> page = userService.searchPaginated(dtoReturn.getRoleUser(),pageNo, pageSize);
		List<UserDTO> listUserFounding = userService.searchList(dtoReturn, pageNo, pageSize);
		
		//Get name
		UserDTO dtoUserSession = (UserDTO)request.getSession().getAttribute("dtoUserSession");
		model.addAttribute("nameUser",userService.getNameByMailUser(dtoUserSession));

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listUser", listUserFounding);

		LotteryDTO dto = new LotteryDTO();
		model.addAttribute("dtoUser",dto);
		
		return "userMng";
	}
	

	
	/* 
	 * delete one User to database
	 * */
	@GetMapping("/deleteUser/{pageNo}/{id}")
	public String deleteOne(@PathVariable(name="id") long id, Model model, 
			HttpServletRequest request, @PathVariable(value = "pageNo") int pageNo) {
		
		try {
			userService.deleteOne(id);
			System.out.println("run try !");
			return "redirect:/admin/UserManager";
			
		} catch (UserCanNotDeleteException e) {
			System.out.println("run catch !");
			
			//get back data
			int pageSize = 2;
			Page<UserEntity> page = userService.findPaginated(pageNo, pageSize);
			List<UserDTO> listUser = userService.findAllUserPagination(pageNo, pageSize);
			
			//Get name
			UserDTO dtoUserSession = (UserDTO)request.getSession().getAttribute("dtoUserSession");
			model.addAttribute("nameUser",userService.getNameByMailUser(dtoUserSession));
			
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());
			model.addAttribute("listUser", listUser);
			
			LotteryDTO dto = new LotteryDTO();
			model.addAttribute("dtoUser",dto);
			
			model.addAttribute("errorMessage", "Không thể xóa admin: "+id);
			return "userMng";
		}
		
	}
	
	
	/* 
	 * delete many User into database
	 * */
	@RequestMapping(value = "/deleteManyUser/{pageNo}", method = RequestMethod.POST)
	public String deleteMany(@ModelAttribute("deleteTotalUser") String deleteTotalUser, Model model, 
			HttpServletRequest request, @PathVariable(value = "pageNo") int pageNo) {
		
		try {
			userService.deleteMany(deleteTotalUser);
			System.out.println("run try !");
			return "redirect:/admin/UserManager";
			
		} catch (UserCanNotDeleteException e) {
			System.out.println("run catch !");
			//Convert error information
			String s = ""+e;
			String[] output = s.split(":");
			String errorMessage = output[1];
			model.addAttribute("errorMessage", errorMessage);
	
			//get back data
			int pageSize = 2;
			Page<UserEntity> page = userService.findPaginated(pageNo, pageSize);
			List<UserDTO> listUser = userService.findAllUserPagination(pageNo, pageSize);
			
			//Get name
			UserDTO dtoUserSession = (UserDTO)request.getSession().getAttribute("dtoUserSession");
			model.addAttribute("nameUser",userService.getNameByMailUser(dtoUserSession));
			
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());
			model.addAttribute("listUser", listUser);

			UserDTO dto = new UserDTO();
			model.addAttribute("dtoUser",dto);
			return "userMng";
		}
	}
	
	
	/*
	 * View page 1
	 * */
	@GetMapping("/admin/UserManager")
 	public String viewHomeUserPage(Model model, HttpServletRequest request) {
// 		System.out.println("run test (controller) ");
// 		System.out.println(dtoUserCl.toString());
 	 return "redirect:/admin/UserManager/page/1"; 
 	}
	
	@GetMapping(value = "/admin/UserManager/page/{pageNo}")
 	public String findPaginatedCusHisPage(@PathVariable(value = "pageNo") int pageNo, Model model, HttpServletRequest request) {
 	    int pageSize = 2;
		Page<UserEntity> page = userService.findPaginated(pageNo, pageSize);
		List<UserDTO> listUser = userService.findAllUserPagination(pageNo, pageSize);
		
		//Get name
		UserDTO dtoUserSession = (UserDTO)request.getSession().getAttribute("dtoUserSession");
		model.addAttribute("nameUser",userService.getNameByMailUser(dtoUserSession));
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listUser", listUser);
		
		
		

		return "userMng";
	}
	
	
	
	
	
	
}
