package projectSpringboot.dto;

import java.util.ArrayList;
import java.util.List;

public class AbstractDTO<T> {

	private Long id;//ID of Primary key (One to many)
	private List<T> listResult = new ArrayList<>();
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<T> getListResult() {
		return listResult;
	}
	public void setListResult(List<T> listResult) {
		this.listResult = listResult;
	}
	
	
	
}
