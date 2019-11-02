package storm.starter.spout;

public class User {
	private String user_id;
	private String email;
	private Character gender;
	private Integer pincode;
	private String dob;
	private String regiterDate;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Character getGender() {
		return gender;
	}
	public void setGender(Character gender) {
		this.gender = gender;
	}
	public Integer getPincode() {
		return pincode;
	}
	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getRegiterDate() {
		return regiterDate;
	}
	public void setRegiterDate(String regiterDate) {
		this.regiterDate = regiterDate;
	}
	
}
