package projectSpringboot.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import projectSpringboot.algorithm.SetUpDate;
import projectSpringboot.dto.IndexDTO;
import projectSpringboot.dto.LotteryDTO;
import projectSpringboot.dto.StaticDTO;
import projectSpringboot.dto.UserDTO;
import projectSpringboot.entity.LotteryBoardEntity;
import projectSpringboot.entity.UserEntity;
import projectSpringboot.service.ILotteryBoardService;
import projectSpringboot.service.IStaticService;

@Controller
public class WebController {
	
	@Autowired
	private IStaticService staticService;
	
	

	
	@GetMapping("/")
	public String test(Model model) {
		IndexDTO indexDTO = new IndexDTO();
		model.addAttribute("indexDTO", indexDTO);
		
		return "redirect:/home";
	}
	
	/*------------------------------------------WEB CONTROLLER------------------------------------*/
	

	
	@GetMapping("/user")
	public String user() {
		return "redirect:/user/cusHistory";
	}
	
	/*
	 * Url from landing page to cusLottery
	 * */
	@GetMapping("/cusLottery")
	public String viewCusHistoryIndex(Model model) {
		UserDTO dtoUser = new UserDTO();
		model.addAttribute("dtoUser",dtoUser);		

		return "cusLottery";
    }
	
	
	@GetMapping("/admin/userMngDirect")
	public String moveUserMng(Model model) {
		UserDTO dtoUser = new UserDTO();
		model.addAttribute("dtoUser",dtoUser);		
		return "redirect:/admin/UserManager";
	}
	
	@GetMapping("/login")
//	public String login(HttpServletRequest request, @ModelAttribute("dtoUser") UserEntity dtoUser,
	public String login(
			Model model, @RequestParam(value = "error", required = false) boolean error) {    
	
		
    if(error) {
    	UserDTO dtoUser = new UserDTO();
    	model.addAttribute("dtoUser",dtoUser);
    	model.addAttribute("errorMessage","Check your mail or password again !");
    	System.out.println("Tài khoản không tồn tại");
    	return "login";
    } 
		
//    	LotteryDTO lbDTO = new LotteryDTO();
//		model.addAttribute("dto",lbDTO);
    	UserDTO dtoUser = new UserDTO();
    	model.addAttribute("dtoUser",dtoUser);
		return "login";
	}
	
	@GetMapping("/403")
	public String page403() {
		return "403";
	}
	
	@GetMapping("/landingPage")
	public String landingPage() {
		return "landingPage";
	}
	
	
/* ----------------------------------------Index controller-----------------------------------------------*/
	
	
	/*
	 * view with Hà Nội
	 * */
	@GetMapping("/index/viewMBHN")
	public String viewLbHN(Model model, HttpServletRequest request) {
		request.getSession().setAttribute("region", "MB-HN");
		request.getSession().setAttribute("province", "thành phố Hà Nội");
		return "redirect:/index/viewRegion";
	}
	/*
	 * view with Hải Phòng
	 * */
	@GetMapping("/index/viewMBHP")
	public String viewLbHP(Model model, HttpServletRequest request) {
		request.getSession().setAttribute("region", "MB-HP");
		request.getSession().setAttribute("province", "thành phố Hải Phòng");
		return "redirect:/index/viewRegion";
	}
	/*
	 * view with Đà Nẵng
	 * */
	@GetMapping("/index/viewMTDN")
	public String viewLbDN(Model model, HttpServletRequest request) {
		request.getSession().setAttribute("region", "MT-DN");
		request.getSession().setAttribute("province", "thành phố Đà Nẵng");
		return "redirect:/index/viewRegion";
	}
	/*
	 * view with Đăk Lăk
	 * */
	@GetMapping("/index/viewMTDL")
	public String viewLbDL(Model model, HttpServletRequest request) {
		request.getSession().setAttribute("region", "MT-DL");
		request.getSession().setAttribute("province", "tỉnh Đăk Lăk");
		return "redirect:/index/viewRegion";
	}
	/*
	 * view with Sài Gòn
	 * */
	@GetMapping("/index/viewMNSG")
	public String viewLbSG(Model model, HttpServletRequest request) {
		request.getSession().setAttribute("region", "MN-SG");
		request.getSession().setAttribute("province", "thành phố Hồ Chí Minh");
		return "redirect:/index/viewRegion";
	}
	/*
	 * view with Hà Nội
	 * */
	@GetMapping("/index/viewMNCT")
	public String viewLbCT(Model model, HttpServletRequest request) {
		request.getSession().setAttribute("region", "MN-CT");
		request.getSession().setAttribute("province", "tỉnh Cần Thơ");
		return "redirect:/index/viewRegion";
	}
	
	
	
	/*
	 * set up view with region 
	 * */
	@GetMapping("/index/viewRegion")
	public String viewLbRegion(Model model, HttpServletRequest request) {
		System.out.println("run: /index/viewRegion");
		String region = (String) request.getSession().getAttribute("region");
		String province = (String) request.getSession().getAttribute("province");
		
		IndexDTO dto = staticService.displayRegion(region);
		List<LotteryDTO> lotteryBoardList = dto.getLbList();
		model.addAttribute("lotteryBoardList", lotteryBoardList);
		model.addAttribute("province", province);
		
		/*----------------------set up top 5 ----------------------*/
		StaticDTO staticDto = staticService.staticLottery();
		model.addAttribute("numTop1", ""+ staticDto.getTop1());
		model.addAttribute("frequencyTop1", ""+ staticDto.getFrequencyTop1());
		model.addAttribute("percent1", ""+ staticDto.getPercentTop1());
		model.addAttribute("numTop2", ""+ staticDto.getTop2());
		model.addAttribute("frequencyTop2", ""+ staticDto.getFrequencyTop2());
		model.addAttribute("percent2", ""+ staticDto.getPercentTop2());
		model.addAttribute("numTop3", ""+ staticDto.getTop3());
		model.addAttribute("frequencyTop3", ""+ staticDto.getFrequencyTop3());
		model.addAttribute("percent3", ""+ staticDto.getPercentTop3());
		model.addAttribute("numTop4", ""+ staticDto.getTop4());
		model.addAttribute("frequencyTop4", ""+ staticDto.getFrequencyTop4());
		model.addAttribute("percent4", ""+ staticDto.getPercentTop4());
		model.addAttribute("numTop5", ""+ staticDto.getTop5());
		model.addAttribute("frequencyTop5", ""+ staticDto.getFrequencyTop5());
		model.addAttribute("percent5", ""+ staticDto.getPercentTop5());
		IndexDTO indexDTO = new IndexDTO();
		model.addAttribute("indexDTO", indexDTO);		
		StaticDTO staticDTO = new StaticDTO();
		model.addAttribute("staticDTO", staticDTO);
		
		return "homeRegion";
	}
	
	
	/*
	 * view with day
	 * */
	@PostMapping("/index/viewByDate")
	public String viewByDay(Model model, @ModelAttribute("indexDTO") IndexDTO indexDtolient, HttpServletRequest request) {
		
		String dayCalendar = indexDtolient.getDayDraw();
		System.out.println("dayCalendar: "+dayCalendar);
		SetUpDate setUpDate = new SetUpDate();
		String setDay = setUpDate.setCalendarIndex(dayCalendar);
		System.out.println("1");
		IndexDTO indexDto = new IndexDTO();
		indexDto = staticService.displayYesterday(setDay);
		System.out.println("2");
		List<LotteryDTO> lbNorth = indexDto.getNorthLbList();
		List<LotteryDTO> lbCentral = indexDto.getCentralLbList();
		List<LotteryDTO> lbSouth = indexDto.getSouthLbList();
		System.out.println("3");
		model.addAttribute("lbNorth",lbNorth);	
		model.addAttribute("lbCentral",lbCentral);	
		model.addAttribute("lbSouth",lbSouth);
		model.addAttribute("yesterday",setDay);
		
		System.out.println("4");
		/*----------------------set up top 5 ----------------------*/
		StaticDTO staticDto = staticService.staticLottery();
		model.addAttribute("numTop1", ""+ staticDto.getTop1());
		model.addAttribute("frequencyTop1", ""+ staticDto.getFrequencyTop1());
		model.addAttribute("percent1", ""+ staticDto.getPercentTop1());
		
		model.addAttribute("numTop2", ""+ staticDto.getTop2());
		model.addAttribute("frequencyTop2", ""+ staticDto.getFrequencyTop2());
		model.addAttribute("percent2", ""+ staticDto.getPercentTop2());
		
		model.addAttribute("numTop3", ""+ staticDto.getTop3());
		model.addAttribute("frequencyTop3", ""+ staticDto.getFrequencyTop3());
		model.addAttribute("percent3", ""+ staticDto.getPercentTop3());
		
		model.addAttribute("numTop4", ""+ staticDto.getTop4());
		model.addAttribute("frequencyTop4", ""+ staticDto.getFrequencyTop4());
		model.addAttribute("percent4", ""+ staticDto.getPercentTop4());
		
		model.addAttribute("numTop5", ""+ staticDto.getTop5());
		model.addAttribute("frequencyTop5", ""+ staticDto.getFrequencyTop5());
		model.addAttribute("percent5", ""+ staticDto.getPercentTop5());
		
		IndexDTO indexDTO = new IndexDTO();
		model.addAttribute("indexDTO", indexDTO);		
		StaticDTO staticDTO = new StaticDTO();
		model.addAttribute("staticDTO", staticDTO);
		System.out.println("5");
		//Get error
		String resultDraw = (String) request.getSession().getAttribute("resultDraw");
		String drawError = (String) request.getSession().getAttribute("drawError");
		model.addAttribute("resultDraw", resultDraw);
		model.addAttribute("drawError", drawError);
		
		return "home";
	}
	
	
	/*
	 * Draw with day and province
	 * */
	@GetMapping("/index/draw")
	public String DrawAndSave(Model model, @ModelAttribute("indexDTO") IndexDTO indexDtolient, HttpServletRequest request) {
		
		IndexDTO dto = staticService.draw(indexDtolient);
		String resultDraw = dto.getStatus();
		
		request.getSession().setAttribute("resultDraw", resultDraw);
		String drawError= "Kết quả: ";
		request.getSession().setAttribute("drawError", drawError);
		
		model.addAttribute("drawError",drawError);
		model.addAttribute("resultDraw",resultDraw);
		
		IndexDTO indexDTO = new IndexDTO();
		model.addAttribute("indexDTO", indexDTO);	
		
		
		
		return "redirect:/home";
	}	
	
	@GetMapping("/home")
	public String trangChu(Model model, HttpServletRequest request) {
		System.out.println("run: /home");
		
		SetUpDate setUpDate = new SetUpDate();
		setUpDate.setCurrentDay();
		String setDay = setUpDate.setYesterday();
		
		IndexDTO indexDto = new IndexDTO();
		indexDto = staticService.displayYesterday(setDay);
		
		List<LotteryDTO> lbNorth = indexDto.getNorthLbList();
		List<LotteryDTO> lbCentral = indexDto.getCentralLbList();
		List<LotteryDTO> lbSouth = indexDto.getSouthLbList();
		
		model.addAttribute("lbNorth",lbNorth);	
		model.addAttribute("lbCentral",lbCentral);	
		model.addAttribute("lbSouth",lbSouth);
		model.addAttribute("yesterday",setDay);
		

		/*----------------------set up top 5 ----------------------*/
		StaticDTO staticDto = staticService.staticLottery();
		model.addAttribute("numTop1", ""+ staticDto.getTop1());
		model.addAttribute("frequencyTop1", ""+ staticDto.getFrequencyTop1());
		model.addAttribute("percent1", ""+ staticDto.getPercentTop1());
		
		model.addAttribute("numTop2", ""+ staticDto.getTop2());
		model.addAttribute("frequencyTop2", ""+ staticDto.getFrequencyTop2());
		model.addAttribute("percent2", ""+ staticDto.getPercentTop2());
		
		model.addAttribute("numTop3", ""+ staticDto.getTop3());
		model.addAttribute("frequencyTop3", ""+ staticDto.getFrequencyTop3());
		model.addAttribute("percent3", ""+ staticDto.getPercentTop3());
		
		model.addAttribute("numTop4", ""+ staticDto.getTop4());
		model.addAttribute("frequencyTop4", ""+ staticDto.getFrequencyTop4());
		model.addAttribute("percent4", ""+ staticDto.getPercentTop4());
		
		model.addAttribute("numTop5", ""+ staticDto.getTop5());
		model.addAttribute("frequencyTop5", ""+ staticDto.getFrequencyTop5());
		model.addAttribute("percent5", ""+ staticDto.getPercentTop5());
		
		IndexDTO indexDTO = new IndexDTO();
		model.addAttribute("indexDTO", indexDTO);		
		StaticDTO staticDTO = new StaticDTO();
		model.addAttribute("staticDTO", staticDTO);
		
		//Get error
		String resultDraw = (String) request.getSession().getAttribute("resultDraw");
		String drawError = (String) request.getSession().getAttribute("drawError");
		model.addAttribute("resultDraw", resultDraw);
		model.addAttribute("drawError", drawError);
		
		return "home";
	}
	
	
	
/* -------------------------------------Static controller--------------------------------------------*/

	@GetMapping("/static")
	public String staticPage(Model model) {
		
		StaticDTO staticDTO  = new StaticDTO();
		model.addAttribute("staticDTO", staticDTO);
		
		return "redirect:/staticView";
	}
	
	@GetMapping("/staticView")
	public String staticView(Model model) {
		
		StaticDTO dto = staticService.staticLottery();

		/*----------------------set up top 5 ----------------------*/
		model.addAttribute("numTop1", ""+ dto.getTop1());
		model.addAttribute("frequencyTop1", ""+ dto.getFrequencyTop1());
		model.addAttribute("percent1", ""+ dto.getPercentTop1());
		
		model.addAttribute("numTop2", ""+ dto.getTop2());
		model.addAttribute("frequencyTop2", ""+ dto.getFrequencyTop2());
		model.addAttribute("percent2", ""+ dto.getPercentTop2());
		
		model.addAttribute("numTop3", ""+ dto.getTop3());
		model.addAttribute("frequencyTop3", ""+ dto.getFrequencyTop3());
		model.addAttribute("percent3", ""+ dto.getPercentTop3());
		
		model.addAttribute("numTop4", ""+ dto.getTop4());
		model.addAttribute("frequencyTop4", ""+ dto.getFrequencyTop4());
		model.addAttribute("percent4", ""+ dto.getPercentTop4());
		
		model.addAttribute("numTop5", ""+ dto.getTop5());
		model.addAttribute("frequencyTop5", ""+ dto.getFrequencyTop5());
		model.addAttribute("percent5", ""+ dto.getPercentTop5());
		
		/*----------------------set up 100 num ----------------------*/
		model.addAttribute("frequency00", ""+ dto.getFrequency00());
		model.addAttribute("percent00", ""+ dto.getPercent00());

		model.addAttribute("frequency01", ""+ dto.getFrequency01());
		model.addAttribute("percent01", ""+ dto.getPercent01());

		model.addAttribute("frequency02", ""+ dto.getFrequency02());
		model.addAttribute("percent02", ""+ dto.getPercent02());

		model.addAttribute("frequency03", ""+ dto.getFrequency03());
		model.addAttribute("percent03", ""+ dto.getPercent03());

		model.addAttribute("frequency04", ""+ dto.getFrequency04());
		model.addAttribute("percent04", ""+ dto.getPercent04());

		model.addAttribute("frequency05", ""+ dto.getFrequency05());
		model.addAttribute("percent05", ""+ dto.getPercent05());

		model.addAttribute("frequency06", ""+ dto.getFrequency06());
		model.addAttribute("percent06", ""+ dto.getPercent06());

		model.addAttribute("frequency07", ""+ dto.getFrequency07());
		model.addAttribute("percent07", ""+ dto.getPercent07());

		model.addAttribute("frequency08", ""+ dto.getFrequency08());
		model.addAttribute("percent08", ""+ dto.getPercent08());

		model.addAttribute("frequency09", ""+ dto.getFrequency09());
		model.addAttribute("percent09", ""+ dto.getPercent09());

		model.addAttribute("frequency10", ""+ dto.getFrequency10());
		model.addAttribute("percent10", ""+ dto.getPercent10());

		model.addAttribute("frequency11", ""+ dto.getFrequency11());
		model.addAttribute("percent11", ""+ dto.getPercent11());

		model.addAttribute("frequency12", ""+ dto.getFrequency12());
		model.addAttribute("percent12", ""+ dto.getPercent12());

		model.addAttribute("frequency13", ""+ dto.getFrequency13());
		model.addAttribute("percent13", ""+ dto.getPercent13());

		model.addAttribute("frequency14", ""+ dto.getFrequency14());
		model.addAttribute("percent14", ""+ dto.getPercent14());

		model.addAttribute("frequency15", ""+ dto.getFrequency15());
		model.addAttribute("percent15", ""+ dto.getPercent15());

		model.addAttribute("frequency16", ""+ dto.getFrequency16());
		model.addAttribute("percent16", ""+ dto.getPercent16());

		model.addAttribute("frequency17", ""+ dto.getFrequency17());
		model.addAttribute("percent17", ""+ dto.getPercent17());

		model.addAttribute("frequency18", ""+ dto.getFrequency18());
		model.addAttribute("percent18", ""+ dto.getPercent18());

		model.addAttribute("frequency19", ""+ dto.getFrequency19());
		model.addAttribute("percent19", ""+ dto.getPercent19());

		model.addAttribute("frequency20", ""+ dto.getFrequency20());
		model.addAttribute("percent20", ""+ dto.getPercent20());

		model.addAttribute("frequency21", ""+ dto.getFrequency21());
		model.addAttribute("percent21", ""+ dto.getPercent21());

		model.addAttribute("frequency22", ""+ dto.getFrequency22());
		model.addAttribute("percent22", ""+ dto.getPercent22());

		model.addAttribute("frequency23", ""+ dto.getFrequency23());
		model.addAttribute("percent23", ""+ dto.getPercent23());

		model.addAttribute("frequency24", ""+ dto.getFrequency24());
		model.addAttribute("percent24", ""+ dto.getPercent24());

		model.addAttribute("frequency25", ""+ dto.getFrequency25());
		model.addAttribute("percent25", ""+ dto.getPercent25());

		model.addAttribute("frequency26", ""+ dto.getFrequency26());
		model.addAttribute("percent26", ""+ dto.getPercent26());

		model.addAttribute("frequency27", ""+ dto.getFrequency27());
		model.addAttribute("percent27", ""+ dto.getPercent27());

		model.addAttribute("frequency28", ""+ dto.getFrequency28());
		model.addAttribute("percent28", ""+ dto.getPercent28());

		model.addAttribute("frequency29", ""+ dto.getFrequency29());
		model.addAttribute("percent29", ""+ dto.getPercent29());

		model.addAttribute("frequency30", ""+ dto.getFrequency30());
		model.addAttribute("percent30", ""+ dto.getPercent30());

		model.addAttribute("frequency31", ""+ dto.getFrequency31());
		model.addAttribute("percent31", ""+ dto.getPercent31());

		model.addAttribute("frequency32", ""+ dto.getFrequency32());
		model.addAttribute("percent32", ""+ dto.getPercent32());

		model.addAttribute("frequency33", ""+ dto.getFrequency33());
		model.addAttribute("percent33", ""+ dto.getPercent33());

		model.addAttribute("frequency34", ""+ dto.getFrequency34());
		model.addAttribute("percent34", ""+ dto.getPercent34());

		model.addAttribute("frequency35", ""+ dto.getFrequency35());
		model.addAttribute("percent35", ""+ dto.getPercent35());

		model.addAttribute("frequency36", ""+ dto.getFrequency36());
		model.addAttribute("percent36", ""+ dto.getPercent36());

		model.addAttribute("frequency37", ""+ dto.getFrequency37());
		model.addAttribute("percent37", ""+ dto.getPercent37());

		model.addAttribute("frequency38", ""+ dto.getFrequency38());
		model.addAttribute("percent38", ""+ dto.getPercent38());

		model.addAttribute("frequency39", ""+ dto.getFrequency39());
		model.addAttribute("percent39", ""+ dto.getPercent39());

		model.addAttribute("frequency40", ""+ dto.getFrequency40());
		model.addAttribute("percent40", ""+ dto.getPercent40());

		model.addAttribute("frequency41", ""+ dto.getFrequency41());
		model.addAttribute("percent41", ""+ dto.getPercent41());

		model.addAttribute("frequency42", ""+ dto.getFrequency42());
		model.addAttribute("percent42", ""+ dto.getPercent42());

		model.addAttribute("frequency43", ""+ dto.getFrequency43());
		model.addAttribute("percent43", ""+ dto.getPercent43());

		model.addAttribute("frequency44", ""+ dto.getFrequency44());
		model.addAttribute("percent44", ""+ dto.getPercent44());
		
		model.addAttribute("frequency45", ""+ dto.getFrequency45());
		model.addAttribute("percent45", ""+ dto.getPercent45());

		model.addAttribute("frequency46", ""+ dto.getFrequency46());
		model.addAttribute("percent46", ""+ dto.getPercent46());

		model.addAttribute("frequency47", ""+ dto.getFrequency47());
		model.addAttribute("percent47", ""+ dto.getPercent47());

		model.addAttribute("frequency48", ""+ dto.getFrequency48());
		model.addAttribute("percent48", ""+ dto.getPercent48());

		model.addAttribute("frequency49", ""+ dto.getFrequency49());
		model.addAttribute("percent49", ""+ dto.getPercent49());

		model.addAttribute("frequency50", ""+ dto.getFrequency50());
		model.addAttribute("percent50", ""+ dto.getPercent50());

		model.addAttribute("frequency51", ""+ dto.getFrequency51());
		model.addAttribute("percent51", ""+ dto.getPercent51());

		model.addAttribute("frequency52", ""+ dto.getFrequency52());
		model.addAttribute("percent52", ""+ dto.getPercent52());

		model.addAttribute("frequency53", ""+ dto.getFrequency53());
		model.addAttribute("percent53", ""+ dto.getPercent53());

		model.addAttribute("frequency54", ""+ dto.getFrequency54());
		model.addAttribute("percent54", ""+ dto.getPercent54());

		model.addAttribute("frequency55", ""+ dto.getFrequency55());
		model.addAttribute("percent55", ""+ dto.getPercent55());

		model.addAttribute("frequency56", ""+ dto.getFrequency56());
		model.addAttribute("percent56", ""+ dto.getPercent56());

		model.addAttribute("frequency57", ""+ dto.getFrequency57());
		model.addAttribute("percent57", ""+ dto.getPercent57());

		model.addAttribute("frequency58", ""+ dto.getFrequency58());
		model.addAttribute("percent58", ""+ dto.getPercent58());

		model.addAttribute("frequency59", ""+ dto.getFrequency59());
		model.addAttribute("percent59", ""+ dto.getPercent59());

		model.addAttribute("frequency60", ""+ dto.getFrequency60());
		model.addAttribute("percent60", ""+ dto.getPercent60());

		model.addAttribute("frequency61", ""+ dto.getFrequency61());
		model.addAttribute("percent61", ""+ dto.getPercent61());

		model.addAttribute("frequency62", ""+ dto.getFrequency62());
		model.addAttribute("percent62", ""+ dto.getPercent62());

		model.addAttribute("frequency63", ""+ dto.getFrequency63());
		model.addAttribute("percent63", ""+ dto.getPercent63());

		model.addAttribute("frequency64", ""+ dto.getFrequency64());
		model.addAttribute("percent64", ""+ dto.getPercent64());

		model.addAttribute("frequency65", ""+ dto.getFrequency65());
		model.addAttribute("percent65", ""+ dto.getPercent65());

		model.addAttribute("frequency66", ""+ dto.getFrequency66());
		model.addAttribute("percent66", ""+ dto.getPercent66());

		model.addAttribute("frequency67", ""+ dto.getFrequency67());
		model.addAttribute("percent67", ""+ dto.getPercent67());

		model.addAttribute("frequency68", ""+ dto.getFrequency68());
		model.addAttribute("percent68", ""+ dto.getPercent68());

		model.addAttribute("frequency69", ""+ dto.getFrequency69());
		model.addAttribute("percent69", ""+ dto.getPercent69());

		model.addAttribute("frequency70", ""+ dto.getFrequency70());
		model.addAttribute("percent70", ""+ dto.getPercent70());

		model.addAttribute("frequency71", ""+ dto.getFrequency71());
		model.addAttribute("percent71", ""+ dto.getPercent71());

		model.addAttribute("frequency72", ""+ dto.getFrequency72());
		model.addAttribute("percent72", ""+ dto.getPercent72());

		model.addAttribute("frequency73", ""+ dto.getFrequency73());
		model.addAttribute("percent73", ""+ dto.getPercent73());

		model.addAttribute("frequency74", ""+ dto.getFrequency74());
		model.addAttribute("percent74", ""+ dto.getPercent74());

		model.addAttribute("frequency75", ""+ dto.getFrequency75());
		model.addAttribute("percent75", ""+ dto.getPercent75());

		model.addAttribute("frequency76", ""+ dto.getFrequency76());
		model.addAttribute("percent76", ""+ dto.getPercent76());

		model.addAttribute("frequency77", ""+ dto.getFrequency77());
		model.addAttribute("percent77", ""+ dto.getPercent77());

		model.addAttribute("frequency78", ""+ dto.getFrequency78());
		model.addAttribute("percent78", ""+ dto.getPercent78());

		model.addAttribute("frequency79", ""+ dto.getFrequency79());
		model.addAttribute("percent79", ""+ dto.getPercent79());

		model.addAttribute("frequency80", ""+ dto.getFrequency80());
		model.addAttribute("percent80", ""+ dto.getPercent80());

		model.addAttribute("frequency81", ""+ dto.getFrequency81());
		model.addAttribute("percent81", ""+ dto.getPercent81());

		model.addAttribute("frequency82", ""+ dto.getFrequency82());
		model.addAttribute("percent82", ""+ dto.getPercent82());

		model.addAttribute("frequency83", ""+ dto.getFrequency83());
		model.addAttribute("percent83", ""+ dto.getPercent83());

		model.addAttribute("frequency84", ""+ dto.getFrequency84());
		model.addAttribute("percent84", ""+ dto.getPercent84());

		model.addAttribute("frequency85", ""+ dto.getFrequency85());
		model.addAttribute("percent85", ""+ dto.getPercent85());

		model.addAttribute("frequency86", ""+ dto.getFrequency86());
		model.addAttribute("percent86", ""+ dto.getPercent86());

		model.addAttribute("frequency87", ""+ dto.getFrequency87());
		model.addAttribute("percent87", ""+ dto.getPercent87());

		model.addAttribute("frequency88", ""+ dto.getFrequency88());
		model.addAttribute("percent88", ""+ dto.getPercent88());

		model.addAttribute("frequency89", ""+ dto.getFrequency89());
		model.addAttribute("percent89", ""+ dto.getPercent89());

		model.addAttribute("frequency90", ""+ dto.getFrequency90());
		model.addAttribute("percent90", ""+ dto.getPercent90());

		model.addAttribute("frequency91", ""+ dto.getFrequency91());
		model.addAttribute("percent91", ""+ dto.getPercent91());

		model.addAttribute("frequency92", ""+ dto.getFrequency92());
		model.addAttribute("percent92", ""+ dto.getPercent92());

		model.addAttribute("frequency93", ""+ dto.getFrequency93());
		model.addAttribute("percent93", ""+ dto.getPercent93());

		model.addAttribute("frequency94", ""+ dto.getFrequency94());
		model.addAttribute("percent94", ""+ dto.getPercent94());

		model.addAttribute("frequency95", ""+ dto.getFrequency95());
		model.addAttribute("percent95", ""+ dto.getPercent95());

		model.addAttribute("frequency96", ""+ dto.getFrequency96());
		model.addAttribute("percent96", ""+ dto.getPercent96());

		model.addAttribute("frequency97", ""+ dto.getFrequency97());
		model.addAttribute("percent97", ""+ dto.getPercent97());

		model.addAttribute("frequency98", ""+ dto.getFrequency98());
		model.addAttribute("percent98", ""+ dto.getPercent98());

		model.addAttribute("frequency99", ""+ dto.getFrequency99());
		model.addAttribute("percent99", ""+ dto.getPercent99());
		
		StaticDTO staticDTO  = new StaticDTO();
		model.addAttribute("staticDTO", staticDTO);
		
		return "static";
	}
	
}
