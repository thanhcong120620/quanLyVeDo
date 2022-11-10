package projectSpringboot.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import projectSpringboot.dto.LotteryDTO;
import projectSpringboot.dto.UserDTO;
import projectSpringboot.service.ICusHistoryService;
import projectSpringboot.service.IUserService;
import projectSpringboot.service.implement.UserCanNotDeleteException;

@Controller
public class UserController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private ICusHistoryService chService;
	
	
	@GetMapping("/test")
	public String test(Model model) {
		UserDTO dto = new UserDTO();
		model.addAttribute("dto",dto);		
		return "redirect:/UserManager";
	}
	
	
	
	/*
	 * Save User into database
	 * */
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public String saveLottery(@ModelAttribute("dto") UserDTO dto) {
		System.out.println("Id in save: "+dto.getId());
		//Save Lottery Board
		userService.save(dto);
		
		return "redirect:/UserManager";
	}
	
	
	
	
	/* 
	 * UpdateSave Lottery into database
	 * */
	@GetMapping("/editUser/{id}")
	public String showLotteryById(@PathVariable(name="id") long id, Model model) {
		UserDTO dto = userService.getUserById(id);
		System.out.println("Id in edit: "+dto.getId());
		model.addAttribute("dto",dto);
		return "userUpdate";
	}
	
	
	
	/*
	 * Search User
	 * */
	@RequestMapping(value = "/searchUser/{pageNumber}", method = RequestMethod.POST)
	public String findByProvinceDate(HttpServletRequest request, 
			@PathVariable int pageNumber,@ModelAttribute("dto") UserDTO dtoReturn, Model model) {
//		List<LotteryDTO> dtoList = new ArrayList<>();
		List<UserDTO> list = userService.searchList(dtoReturn);
//		String errorMessage = "Kết quả được hiển thị";

		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("userlist");	
		int pagesize = 3;	
		pages = new PagedListHolder<>(list);	
		pages.setPageSize(pagesize);	
			
		final int goToPage = pageNumber - 1;	
		if (goToPage <= pages.getPageCount() && goToPage >= 0) {
			pages.setPage(goToPage);	
		}	
		request.getSession().setAttribute("userlist", pages);	
		int current = pages.getPage() + 1;	
		int begin = Math.max(1, current - list.size());	
		int end = Math.min(begin + 5, pages.getPageCount());	
		int totalPageCount = pages.getPageCount();	
		String baseUrl = "/UserManager/page/";	
		model.addAttribute("beginIndex", begin);	
		model.addAttribute("endIndex", end);	
		model.addAttribute("currentIndex", current);	
		model.addAttribute("totalPageCount", totalPageCount);	
		model.addAttribute("baseUrl", baseUrl);	
		model.addAttribute("dtoUserList", pages);


//		model.addAttribute("dtoList",dtoList);
//		model.addAttribute("errorMessage",errorMessage);


		LotteryDTO dto = new LotteryDTO();
		model.addAttribute("dto",dto);
		
		return "userMng";
	}
	
	
	
	
	
	/* 
	 * delete one User to database
	 * */
	@GetMapping("/deleteUser/{pageNumber}/{id}")
	public String deleteOne(@PathVariable(name="id") long id, Model model, 
			HttpServletRequest request, @PathVariable int pageNumber) {
		
		
		try {
			userService.deleteOne(id);
			System.out.println("run try !");
			//get back data
			
			
			return "redirect:/UserManager";
			
		} catch (UserCanNotDeleteException e) {
			System.out.println("run catch !");
			//get back data
			PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("userlist");
			int pagesize = 3;
			List<UserDTO> list =(List<UserDTO>) userService.findAllUser();
			
			System.out.println(list.size());
			if (pages == null) {
				pages = new PagedListHolder<>(list);
				pages.setPageSize(pagesize);
			} else {
				final int goToPage = pageNumber - 1;
				if (goToPage <= pages.getPageCount() && goToPage >= 0) {
					pages.setPage(goToPage);
				}
			}
			request.getSession().setAttribute("userlist", pages);
			int current = pages.getPage() + 1;
			int begin = Math.max(1, current - list.size());
			int end = Math.min(begin + 5, pages.getPageCount());
			int totalPageCount = pages.getPageCount();
			String baseUrl = "/UserManager/page/";

			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("totalPageCount", totalPageCount);
			model.addAttribute("baseUrl", baseUrl);
			model.addAttribute("dtoUserList", pages);

			
			LotteryDTO dto = new LotteryDTO();
			model.addAttribute("dto",dto);
			
			
			model.addAttribute("errorMessage", "Không thể xóa admin: "+id);
//			return "redirect:/UserManager";
			return "userMng";
		}
		
	}
	
	
	/* 
	 * delete many User into database
	 * */
	@RequestMapping(value = "/deleteManyUser", method = RequestMethod.POST)
	public String deleteMany(@ModelAttribute("deleteTotalUser") String deleteTotalUser) {

		userService.deleteMany(deleteTotalUser);
		return "redirect:/UserManager";
	}
	
	
	
	@GetMapping("/UserManager")
	public String viewAdminIndex(Model model,HttpServletRequest request
			,RedirectAttributes redirect) {
		request.getSession().setAttribute("userlist", null);
		
		if(model.asMap().get("success") != null)
			redirect.addFlashAttribute("success",model.asMap().get("success").toString());

		
		
		return "redirect:/UserManager/page/1";
    }
	
	@GetMapping("/UserManager/page/{pageNumber}")
	public String showEmployeePage(HttpServletRequest request, 
			@PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("userlist");
		int pagesize = 3;
		List<UserDTO> list =(List<UserDTO>) userService.findAllUser();
		
		System.out.println(list.size());
		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("userlist", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/UserManager/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("dtoUserList", pages);

		
		LotteryDTO dto = new LotteryDTO();
		model.addAttribute("dto",dto);
		


		return "userMng";
	}
	
	
	
	
	
	
}
