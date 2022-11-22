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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import projectSpringboot.dto.LotteryDTO;
import projectSpringboot.dto.UserDTO;
import projectSpringboot.entity.LotteryBoardEntity;
import projectSpringboot.service.ILotteryBoardService;
import projectSpringboot.service.IRegionService;
import projectSpringboot.service.IUserService;

@Controller
public class LotteryController {

//	@Autowired
//	private IRegionService regionService;
	
	@Autowired
	private ILotteryBoardService lbService;
	
	@Autowired
	private IUserService userService;

	
	 
	@GetMapping("/admin")
	public String viewAdminIndex(Model model,HttpServletRequest request ) {
		
	 	 return "redirect:/admin/page/1"; 
    }
		
	@GetMapping("/admin/page/{pageNo}")
	public String showEmployeePage(@PathVariable(value = "pageNo") int pageNo, Model model, HttpServletRequest request) {
		
		int pageSize = 4;
		Page<LotteryBoardEntity> page = lbService.findPaginated(pageNo, pageSize);
		List<LotteryDTO> dtoList = lbService.findAllLotteryBoardPagination(pageNo, pageSize);
		
		UserDTO dtoUserSession = (UserDTO) request.getSession().getAttribute("dtoUserSession");
		model.addAttribute("nameUser", userService.getNameByMailUser(dtoUserSession));
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("dtoList", dtoList);		

		model.addAttribute("lotteryCode","Mã vé");
		model.addAttribute("provinceName","Tỉnh/Thành phố");
		model.addAttribute("date","ngày/tháng/năm");
		model.addAttribute("g0","Mã giải đặc biệt");
		model.addAttribute("g1","Mã giải nhất");
		model.addAttribute("g2","Mã giải nhì");
		model.addAttribute("g31","Mã giải ba");
		model.addAttribute("g41","Mã giải tư");
		model.addAttribute("g5","Mã giải năm");
		model.addAttribute("g61","Mã giải sáu");
		model.addAttribute("g7","Mã giải bảy");
		model.addAttribute("g8","Mã giải tám");
		
		LotteryDTO dto = new LotteryDTO();
		model.addAttribute("dto",dto);
		

		return "lotteryMng";
	}
	
	
	/*
	 * 
	 * */
	@RequestMapping(value = "/search/{pageNo}", method = RequestMethod.POST)
	public String findByProvinceDate(HttpServletRequest request, @PathVariable(value = "pageNo") int pageNo,
			@ModelAttribute("dto") LotteryDTO dtoReturn, Model model) {
		
		int pageSize = 4;
		Page<LotteryBoardEntity> page = lbService.searchPaginated(dtoReturn ,pageNo, pageSize);
		List<LotteryDTO> dtoList = lbService.searchList(dtoReturn, pageNo, pageSize);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("dtoList", dtoList);		
		
		for(LotteryDTO item : dtoList) {
			System.out.println(item.toString());
		}

		UserDTO dtoUserSession = (UserDTO) request.getSession().getAttribute("dtoUserSession");
		model.addAttribute("nameUser", userService.getNameByMailUser(dtoUserSession));
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("dtoList", dtoList);		

		model.addAttribute("lotteryCode","Mã vé");
		model.addAttribute("provinceName","Tỉnh/Thành phố");
		model.addAttribute("date","ngày/tháng/năm");
		model.addAttribute("g0","Mã giải đặc biệt");
		model.addAttribute("g1","Mã giải nhất");
		model.addAttribute("g2","Mã giải nhì");
		model.addAttribute("g31","Mã giải ba");
		model.addAttribute("g41","Mã giải tư");
		model.addAttribute("g5","Mã giải năm");
		model.addAttribute("g61","Mã giải sáu");
		model.addAttribute("g7","Mã giải bảy");
		model.addAttribute("g8","Mã giải tám");
		
		LotteryDTO dto = new LotteryDTO();
		model.addAttribute("dto",dto);
		
		return "lotteryMng";
	}
		
	@GetMapping("/find/{pageNo}/{id}")
	public String displayLotteryBoardById(HttpServletRequest request, @PathVariable(name="id") long id,
											@PathVariable(value= "pageNo") int pageNo, Model model) {
		
		//Get back table data
		int pageSize = 4;
		Page<LotteryBoardEntity> page = lbService.findPaginated(pageNo, pageSize);
		List<LotteryDTO> dtoList = lbService.findAllLotteryBoardPagination(pageNo, pageSize);
		
		UserDTO dtoUserSession = (UserDTO) request.getSession().getAttribute("dtoUserSession");
		model.addAttribute("nameUser", userService.getNameByMailUser(dtoUserSession));
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("dtoList", dtoList);		

		LotteryDTO dto = new LotteryDTO();
		model.addAttribute("dto",dto);
		
		//Display data in lottery board
		LotteryDTO lotteryBoard = lbService.getLotteryBoardById(id);
		String lotteryCode = ""+ lotteryBoard.getRegionCode() +""+ lotteryBoard.getId();
		model.addAttribute("lotteryCode",lotteryCode);

		String provinceName = lotteryBoard.getNameProvince();
		model.addAttribute("provinceName",provinceName);
		String date = ""+ lotteryBoard.getDay() +"/"+ lotteryBoard.getMonth()+"/"+ lotteryBoard.getYear();
		model.addAttribute("date",date);
		
		String g0 = lotteryBoard.getG0();
		model.addAttribute("g0",g0);
		String g1 = lotteryBoard.getG1();
		model.addAttribute("g1",g1);
		String g2 = lotteryBoard.getG2();
		model.addAttribute("g2",g2);
		String g31 = lotteryBoard.getG31() + ", ";
		model.addAttribute("g31",g31);
		String g32 = lotteryBoard.getG32();
		model.addAttribute("g32",g32);
		String g41 = lotteryBoard.getG41() + ", ";
		model.addAttribute("g41",g41);
		String g42 = lotteryBoard.getG42() + ", ";
		model.addAttribute("g42",g42);
		String g43 = lotteryBoard.getG43() + ", ";
		model.addAttribute("g43",g43);
		String g44 = lotteryBoard.getG44() + ", ";
		model.addAttribute("g44",g44);
		String g45 = lotteryBoard.getG45() + ", ";
		model.addAttribute("g45",g45);
		String g46 = lotteryBoard.getG46() + ", ";
		model.addAttribute("g46",g46);
		String g47 = lotteryBoard.getG47();
		model.addAttribute("g47",g47);
		String g5 = lotteryBoard.getG5();
		model.addAttribute("g5",g5);
		String g61 = lotteryBoard.getG61() + ", ";
		model.addAttribute("g61",g61);
		String g62 = lotteryBoard.getG62() + ", ";
		model.addAttribute("g62",g62);
		String g63 = lotteryBoard.getG63();
		model.addAttribute("g63",g63);
		String g7 = lotteryBoard.getG7();
		model.addAttribute("g7",g7);
		String g8 = lotteryBoard.getG8();
		model.addAttribute("g8",g8);
		

		return "lotteryMng";
	}
		

	
/* ------------------------------------Management function controller------------------------------------*/
	

	/*
	 * Save Lottery into database
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveLottery(@ModelAttribute("dto") LotteryDTO dto) {
		
		//Save Region first
//		regionService.save(dto);
		System.out.println("oke");
		//Save Lottery Board
		lbService.save(dto);
		
		return "redirect:/admin";
	}
	
	
	/* 
	 * UpdateSave Lottery into database
	 * */
	@GetMapping("/edit/{id}")
	public String showLotteryById(@PathVariable(name="id") long id, Model model) {
		LotteryDTO dto = lbService.getLotteryBoardById(id);
		String regionCode = dto.getRegionCode();
		String idString = String.valueOf(dto.getId());
		model.addAttribute("dto",dto);
		model.addAttribute("regionCode",regionCode);
		model.addAttribute("idString",idString);
		return "LotteryUpdate";
	}
	
	
	/* 
	 * delete one Lottery into database
	 * */
	@GetMapping("/delete/{id}")
	public String deleteOne(@PathVariable(name="id") long id) {
		lbService.deleteOne(id);
		return "redirect:/admin";
	}
	
	
	/* 
	 * delete many Lottery into database
	 * */
	@RequestMapping(value = "/deleteMany", method = RequestMethod.POST)
	public String deleteMany(@ModelAttribute("deleteTotal") String deleteTotal) {
		lbService.deleteMany(deleteTotal);
		return "redirect:/admin";
	}
	
	
	/*
	 * Create a new Lottery
	 * */
	@GetMapping("/new")
	public String createNewLottery(Model model) {
		LotteryDTO dto = new LotteryDTO();
		model.addAttribute("dto",dto);
		return "lotteryCreate2";
	}
	
	
}
