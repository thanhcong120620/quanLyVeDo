package projectSpringboot.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import projectSpringboot.dto.UserDTO;
import projectSpringboot.entity.CusHistoryEntity;
import projectSpringboot.exception.CanNotFindUserException;
import projectSpringboot.service.ICusHistoryService;
import projectSpringboot.service.IUserService;

@Controller
public class CusHistoryController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ICusHistoryService ChService;
	
	
	/*
	 * Url from landing page to cusLottery
	 * */
	@GetMapping("/cusLottery")
	public String viewCusHistoryIndex(Model model) {
		UserDTO dtoUser = new UserDTO();
		model.addAttribute("dtoUser",dtoUser);		

		return "cusLottery";
    }
	
	
	/*
	 * display list of cusLottery
	 * */
	@PostMapping("/cusHistory")
	public String viewHomePage(Model model, @ModelAttribute("dtoUser") UserDTO dtoUserCl, HttpServletRequest request) {
		UserDTO dtoUserSer = dtoUserCl;
		System.out.println("dtoUserSer/test: "+dtoUserSer.toString());
		model.addAttribute("dtoUser",dtoUserSer);
		request.getSession().setAttribute("dtoUserCl", dtoUserCl);
//	 return findPaginated(1, model, dtoUserCl);
	 return "redirect:/cusHistory/page/1";
	}
	
	//go to pageNo
	@GetMapping("/cusHistory/page/{pageNo}")
//	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model, @ModelAttribute("dtoUser") UserDTO dtoUserCl) {
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model, HttpServletRequest request) {
	    int pageSize = 2;
	    UserDTO dtoUserCl = (UserDTO) request.getSession().getAttribute("dtoUserCl");
	    System.out.println("pageNo: "+pageNo);
	    System.out.println("dtoUserCl in page no: "+pageNo+": "+dtoUserCl.toString());
	    
//	    UserDTO dto;
		try {
			UserDTO dto = userService.getUserByMailUser(dtoUserCl);
		    Page<CusHistoryEntity> page = ChService.findPaginated(dto.getId(),pageNo, pageSize);
		    List<UserDTO> listCusLottery = ChService.findAllByUser_id2(dto.getId(), pageNo, pageSize);

		    model.addAttribute("currentPage", pageNo);
		    model.addAttribute("totalPages", page.getTotalPages());
		    model.addAttribute("totalItems", page.getTotalElements());
		    model.addAttribute("listCusLottery", listCusLottery);
//		    System.out.println("run findPaginated (controller) ");
		    
		  //push data to client
			String nameUser = dto.getNameUser();
			String mailUser = dto.getMailUser();
			String phone = dto.getPhone();
			String addressUser = dto.getAddressUser();
			model.addAttribute("nameUser",nameUser);
			model.addAttribute("mailUser",mailUser);
			model.addAttribute("phone",phone);
			model.addAttribute("addressUser",addressUser);
			
			UserDTO dtoUserSer = dtoUserCl;
			System.out.println("dtoUserSer: "+dtoUserSer.toString());
			model.addAttribute("dtoUser",dtoUserSer);	
			
		    return "cusLottery";
		} catch (CanNotFindUserException e) {
			UserDTO dtoUserSer = dtoUserCl;
			System.out.println("dtoUserSer: "+dtoUserSer.toString());
			model.addAttribute("dtoUser",dtoUserSer);
//			System.out.println("run findPaginated catch  (controller) ");
			System.out.println("Login fail");
			String s = ""+e;
			String[] output = s.split(":");
//			String errorMessage = output[1];
			String errorMessage = "Tài khoản hoặc mật khẩu không đúng";
			model.addAttribute("errorMessage",errorMessage);
			model.addAttribute("nameUser","Đăng nhập thất bại");
			System.out.println(errorMessage);
			
			UserDTO dtoUser = new UserDTO();
			model.addAttribute("dtoUser",dtoUser);	
			return "cusLottery";
		}
	    
	}
	
	
	
	

	
}
