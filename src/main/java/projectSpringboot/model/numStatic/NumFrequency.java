package projectSpringboot.model.numStatic;

public class NumFrequency {

	private String num;
	private int frequency;
	private double percent;
	
	public NumFrequency() {}
	
	public NumFrequency(String num, int frequency, double percent) {
		this.num = num;
		this.frequency = frequency;
		this.percent = percent;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	
	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	@Override
	public String toString() {
		return "NumFrequency [num=" + num + ", frequency=" + frequency + ", percent=" + percent + "]";
	}

	
	
	
}
