package projectSpringboot.model.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import projectSpringboot.entity.CusHistoryEntity;
import projectSpringboot.entity.UserEntity;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

//	private String nameUser;
//	private String genderUser;
//	private String birthDay;
//	private String phone;
//	private String addressUser;
//	private String passwordRandom;
//	private List <CusHistoryEntity> CusHistoryList ;
	private String mailUser;
	private String passwordUser;
	private boolean isActive;
	//tạm thời ko khai báo List <CusHistoryEntity>
	private List<GrantedAuthority> authorities;

	public UserDetailsImpl(UserEntity user) {
		
		System.out.println("Run: UserDetailsImpl");
		
//		this.nameUser = user.getNameUser();
//		this.genderUser = user.getGenderUser();
//		this.birthDay = user.getBirthDay();
//		this.phone = user.getPhone();
//		this.addressUser = user.getAddressUser();
//		this.passwordRandom = user.getPasswordRandom();
		this.mailUser = user.getMailUser();
		this.passwordUser = user.getPasswordUser();
		this.isActive = user.isActive();
//		this.CusHistoryList = user.getCusHistoryList();
		
		this.authorities = Arrays.stream(user.getRoleUser().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		System.out.println("user.getRoleUser(): "+user.getRoleUser());
		System.out.println("user.getRoleUser(): "+user.getNameUser());
		System.out.println("user.getRoleUser(): "+user.getPhone());
		System.out.println("mailUser: "+mailUser);
		System.out.println("passwordUser: "+passwordUser);
		System.out.println("isActive: "+isActive);
		System.out.println("authorities: "+authorities);
	}

	public UserDetailsImpl() {}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return passwordUser;
	}

	@Override
	public String getUsername() {
		return mailUser;
	}


//	public String getNameUser() {
//		return nameUser;
//	}
//
//	public String getGenderUser() {
//		return genderUser;
//	}
//
//	public String getBirthDay() {
//		return birthDay;
//	}
//
//	public String getPhone() {
//		return phone;
//	}
//
//	public String getAddressUser() {
//		return addressUser;
//	}
//
//	public String getMailUser() {
//		return mailUser;
//	}
//
//	public String getPasswordUser() {
//		return passwordUser;
//	}
//
//	public boolean isActive() {
//		return isActive;
//	}
//
//	public String getPasswordRandom() {
//		return passwordRandom;
//	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isActive;
	}

}

