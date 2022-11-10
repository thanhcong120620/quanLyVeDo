package projectSpringboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

	@RequestMapping("/test")
	public String viewHomePage(Model model) {
		String abc = "Nguyễn Thành Công";
		String abc1 = "Nguyễn Thành Công 10";
		String abc2 = "Nguyễn Thành Công 11";
		String abc3 = "Nguyễn Thành Công 12";
		List<String> list = new ArrayList<>();
		list.add(abc);
		list.add(abc1);
		list.add(abc2);
		list.add(abc3);
		model.addAttribute("list",list);
		return "index";
	}
	
	
}
