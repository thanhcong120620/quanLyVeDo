package projectSpringboot.model.region;

public class SetUpRegion {

	public String setUpDrawTime(String regionCode) {
		if(regionCode.equals("MB-HN")) {
			return "18:00:00";
		}
		if(regionCode.equals("MB-HP")) {
			return "18:15:00";
		}
		if(regionCode.equals("MT-DN")) {
			return "17:00:00";
		}
		if(regionCode.equals("MT-DL")) {
			return "17:15:00";
		}
		if(regionCode.equals("MN-SG")) {
			return "18:00:00";
		}
		if(regionCode.equals("MN-CT")) {
			return "18:15:00";
		}
		
		return null;
	}
	
}
