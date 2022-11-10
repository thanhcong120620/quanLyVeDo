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

import projectSpringboot.dto.LotteryDTO;
import projectSpringboot.service.ILotteryBoardService;
import projectSpringboot.service.IRegionService;
import projectSpringboot.service.implement.UserNotFoundException;

@RestController
public class LotteryAPI {
	
	@Autowired
	private IRegionService regionService;
	
	@Autowired
	private ILotteryBoardService lbService;
	

	
	/*
	 * test query native
	 * */
	@GetMapping(value = "/api/lotteryBoard2/{id}")
	public List<LotteryDTO> findAllByRegion_Id(@PathVariable("id") String id) {
		List<LotteryDTO> dto = lbService.findAllByRegion_id(id);
		System.out.println("dto by LotteryBoard: ");
		System.out.println(dto.toString());
		
		return dto;
	}
	
	
	
	/*
	 * Create new Lottery Board record
	 * */
	@PostMapping(value = "/api/lotteryBoard")
	public LotteryDTO createLB(@RequestBody LotteryDTO dto) {
	
		
		return lbService.save(dto);		
	}
	
	
	/*
	 * Update new Lottery Board record
	 * */
	@PutMapping(value = "/api/lotteryBoard/{id}")
	public LotteryDTO updateLB(@RequestBody LotteryDTO model, @PathVariable("id") long id) {
		model.setId(id);

		
		return lbService.save(model);		

	}

	
	/*
	 * Delete new Lottery Board record
	 * */
	@DeleteMapping(value = "/api/lotteryBoard")
	public void deleteLB(@RequestBody String idList) {
		lbService.deleteMany(idList);
	}

	
	/*
	 * Find Lottery Board record by Id
	 * */
	@GetMapping(value = "/api/lotteryBoard/{id}")
	public LotteryDTO findLBById(@PathVariable("id") long id) {
		LotteryDTO dto = lbService.getLotteryBoardById(id);
		System.out.println("dto by LotteryBoard: ");
		System.out.println(dto.toString());
		
		return dto;
	}

	
	/*
	 * Find All Lottery Board record
	 * */
	@GetMapping(value = "/api/lotteryBoard")
	public List<LotteryDTO> findAllLB() {
		List<LotteryDTO> dtoList = lbService.findAllLB();
		System.out.println("dto by LotteryBoard: ");
		for(LotteryDTO dto : dtoList) {
			System.out.println(dto.toString());			
		}

		return dtoList;
	}

	
	/*
	 * Search Lottery Board record by city and year
	 * */
	@GetMapping(value = "/api/lotteryBoardSearch")
	public List<LotteryDTO> findAllLB(@RequestBody LotteryDTO dto) {
		System.out.println(dto.toString());
		List<LotteryDTO> dtoList = lbService.searchList(dto);
		System.out.println("dto by LotteryBoard: ");
		for(LotteryDTO dtoElement : dtoList) {
			System.out.println(dtoElement.toString());			
		}

		return dtoList;
	}
	
	
	
	/*
	 * Create new Region record
	 * */
	@PostMapping(value = "/api/region")
	public LotteryDTO createRegion(@RequestBody LotteryDTO dto) {
		
		
//		return regionService.save(model);		
		return regionService.save(dto);
	}
	
	
	/*
	 * Update new Region record
	 * */
	@PutMapping(value = "/api/region/{id}")
	public LotteryDTO updateRegion(@RequestBody LotteryDTO model, @PathVariable("id") long id) {
		model.setId(id);
		
		return regionService.save(model);

	}

	
	/*
	 * Delete new Region record
	 * */
	@DeleteMapping(value = "/api/region")
	public void deleteRegion(@RequestBody String[] regionCode) {
		regionService.delete(regionCode);
	}

	
	/*
	 * Find Region record by Id
	 * */
	@GetMapping(value = "/api/region/{id}")
	public LotteryDTO findRegionById(@PathVariable("id") long id) {
		LotteryDTO dto = regionService.getRegionById(id);
		System.out.println("dto by region: ");
		System.out.println(dto.toString());
		
		return dto;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
