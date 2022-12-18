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

import projectSpringboot.algorithm.ValidForm;
import projectSpringboot.dto.UserDTO;
import projectSpringboot.entity.CusHistoryEntity;
import projectSpringboot.exceptionAndError.CanNotFindUserException;
import projectSpringboot.exceptionAndError.UserNotFoundException;
import projectSpringboot.service.ICusHistoryService;
import projectSpringboot.service.IUserService;

@Controller
public class CusHistoryController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ICusHistoryService ChService;
	
	
	
	/*-----------------------------------History draw Functions-------------------------*/

	/*
	 * Log out 
	 * */
	@GetMapping("/logOutCusHistory")
	public String logOutCusHistory(Model model, HttpServletRequest request) {
		
		UserDTO dtoQuery = null;
		request.getSession().setAttribute("dtoQuery", dtoQuery);
		
		UserDTO dtoUser = new UserDTO();
		model.addAttribute("dtoUser",dtoUser);	
		return "cusLottery";
	}
	
	
	
	/*
	 * Draw and save
	 * */
	@PostMapping("/cusHistory/drawAndSave")
	public String DrawAndSave(Model model, @ModelAttribute("dtoUser") UserDTO dtoUser, HttpServletRequest request) {

		ValidForm vf = new ValidForm();
		String validDrawCode = vf.validCusHistoryForm(dtoUser);
		
		// data invalidate from client, drawing code is empty
		String drawError= "";
		if(dtoUser.getCodeDrawCH().isEmpty()) {
			drawError= "Mã vé không được để trống";
			request.getSession().setAttribute("drawError", drawError);
			request.getSession().setAttribute("resultDraw", "");
			return "redirect:/cusHistory";
		}
		// data invalidate from client, drawing code is invalid
		else if (validDrawCode!=null) {
			drawError= validDrawCode;
			request.getSession().setAttribute("drawError", drawError);
			request.getSession().setAttribute("resultDraw", "");
			return "redirect:/cusHistory";
		} 
		else {
		System.out.println("run: /DrawAndSave");
		UserDTO dtoChDrawAndSave = (UserDTO)request.getSession().getAttribute("dtoQuery");
		UserDTO dtoLogin = (UserDTO)request.getSession().getAttribute("dtoUserName");
		
		/*if user log out, set dtoQuery is null*/
		if(dtoChDrawAndSave==null) {
			UserDTO dto = ChService.draw(dtoUser);
			String resultDraw = dto.getStatus();
			
			request.getSession().setAttribute("resultDraw", resultDraw);
//			drawError= "Kết quả: ";
			request.getSession().setAttribute("drawError", drawError);
			
			model.addAttribute("drawError",drawError);
			model.addAttribute("resultDraw",resultDraw);
			
			UserDTO dtoUserSer = new UserDTO();
			model.addAttribute("dtoUser",dtoUserSer);
			return "cusLottery";
		} else {
				
		// user have logged in
			if(dtoLogin!=null) {
				long id = dtoChDrawAndSave.getId();
				System.out.println("id: "+id);
				dtoUser.setId(id);
				UserDTO dto = ChService.save(dtoUser);
				String resultDraw = dto.getStatus();
				request.getSession().setAttribute("resultDraw", resultDraw);
				drawError= "Kết quả: ";
				request.getSession().setAttribute("drawError", drawError);
				return "redirect:/cusHistory";
			
				// user have not logged in	
			} else {
				UserDTO dto = ChService.draw(dtoUser);
				String resultDraw = dto.getStatus();
			
				request.getSession().setAttribute("resultDraw", resultDraw);
				drawError= "Kết quả: ";
				request.getSession().setAttribute("drawError", drawError);
			
				model.addAttribute("drawError",drawError);
				model.addAttribute("resultDraw",resultDraw);
			
				UserDTO dtoUserSer = new UserDTO();
				model.addAttribute("dtoUser",dtoUserSer);
				return "cusLottery";
			}
		}//check logout
		
		} // else check code not empty
	} //end function
	
	
	
	/*
	 * Delete one
	 * */
	@GetMapping("/cusHistory/deleteCusHistory/{id}")
	public String cusHistoryDeleteOne(@PathVariable(name="id") long id, HttpServletRequest request,
										Model model, @ModelAttribute("dtoUser") UserDTO dtoUser) {
		
		System.out.println("run: cusHistoryDeleteOne with id: "+ id);
		ChService.deleteOne(id);
		return "redirect:/cusHistory";
	}
	
	
	
	/*
	 * Delete many
	 * */
	@PostMapping("/cusHistory/deleteMany")
	public String cusHistoryDeleteMany(@ModelAttribute("deleteTotalUser") String deleteTotal, 
										Model model, HttpServletRequest request) {
		
		System.out.println("run: deleteMany");
		System.out.println("deleteTotal: "+deleteTotal);
		UserDTO dtoChDeleteMany = (UserDTO)request.getSession().getAttribute("dtoQuery");
		try {
			long user_id = dtoChDeleteMany.getId();
			System.out.println("user_id: "+user_id);
			
			ChService.deleteMany(deleteTotal, user_id);
			return "redirect:/cusHistory";
		} catch (UserNotFoundException e) {
			String s = ""+e;
			String[] output = s.split(":");
			String errorDelete = output[1];
			UserDTO dtoUser = new UserDTO();
			model.addAttribute("dtoUser",dtoUser);
//			model.addAttribute("errorDelete", errorDelete);
			request.getSession().setAttribute("errorDelete", errorDelete);
			
			return "redirect:/cusHistory";
		}

		
	}
	
	/*---------------------------------Login to start History draw Functions-----------------------*/

	/*
	 * login to view my draw History 
	 * */
	@PostMapping("/loginCusHistory")
	public String loginCusHistory(Model model, @ModelAttribute("dtoUser") UserDTO dtoUserCl, HttpServletRequest request) {
		UserDTO dtoUserSer = dtoUserCl;
		System.out.println("run: /loginCusHistory");
		System.out.println("dtoUserSer/test: "+dtoUserSer.toString());
		model.addAttribute("dtoUser",dtoUserSer);
		request.getSession().setAttribute("dtoUserName", dtoUserCl);
		
		try {
			UserDTO dtoQuery =  userService.getUserByMailUser(dtoUserCl);
			request.getSession().setAttribute("dtoQuery", dtoQuery);
			return "redirect:/cusHistory";
		} catch (CanNotFindUserException e) {
			System.out.println("Login fail");
//			String s = ""+e;
//			String[] output = s.split(":");
//			String errorMessage = output[1];
			UserDTO dtoUser = new UserDTO();
			
			String errorMessage = "Tài khoản hoặc mật khẩu không đúng";
			model.addAttribute("dtoUser",dtoUser);
			model.addAttribute("errorMessage",errorMessage);
			model.addAttribute("errorMessage",errorMessage);
			model.addAttribute("nameUser","Đăng nhập thất bại");
			return "cusLottery";
		}
	}
	
	
	
	
	/*
	 * display list of cusLottery
	 * */
	@GetMapping("/cusHistory")
	public String viewHomePage(Model model, HttpServletRequest request) {
//		UserDTO dtoUserSer = dtoUserCl;
		System.out.println("run: /cusHistory");
//		model.addAttribute("dtoUser",dtoUserSer);
//		request.getSession().setAttribute("dtoUserCl", dtoUserCl);
		

	 return "redirect:/cusHistory/page/1";
	}
	
	//go to pageNo
	@GetMapping("/cusHistory/page/{pageNo}")
//	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model, @ModelAttribute("dtoUser") UserDTO dtoUserCl) {
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model, HttpServletRequest request) {
	    int pageSize = 4;
//	    UserDTO dtoUserCl = (UserDTO) request.getSession().getAttribute("dtoUserCl");

		UserDTO dtoUserName = (UserDTO)request.getSession().getAttribute("dtoUserName");
	    /*Check whether user have not login*/
	    if(dtoUserName==null) {
	    	return "redirect:/cusLottery";
	    }
	    
	    /*Get data by session*/
		model.addAttribute("nameUser",userService.getNameByMailUser(dtoUserName));
		
		//Get resultDraw
		String resultDraw = (String) request.getSession().getAttribute("resultDraw");
		
		//Get error
		String errorDelete = (String) request.getSession().getAttribute("errorDelete");
		String drawError = (String) request.getSession().getAttribute("drawError");
		
		//Get entity
		UserDTO dtoUserQuery = (UserDTO)request.getSession().getAttribute("dtoUserName");
	    
		try {
			UserDTO dto = userService.getUserByMailUser(dtoUserQuery);
		    Page<CusHistoryEntity> page = ChService.findPaginated(dto.getId(),pageNo, pageSize);
		    List<UserDTO> listCusLottery = ChService.findAllByUserid2(dto.getId(), pageNo, pageSize);

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
			
			UserDTO dtoUserSer = dtoUserQuery;
			System.out.println("dtoUserSer: "+dtoUserSer.toString());
			model.addAttribute("dtoUser",dtoUserSer);	
			model.addAttribute("errorDelete",errorDelete);
			model.addAttribute("drawError",drawError);
//			System.out.println(drawError);
			model.addAttribute("resultDraw",resultDraw);
			
			
		    return "cusLottery";
		} catch (CanNotFindUserException e) {
			UserDTO dtoUserSer = dtoUserQuery;
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
