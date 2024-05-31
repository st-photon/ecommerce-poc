package com.photon.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity 
//@Table(name ="Users")
public class User {
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int UserId;

	private String userFirstName;
	
	private String userLastName;
	
	private String userEmailId;
	
	private int userMobileNumber;
	
	private String userGender;
	
	private String userDOB;
	
	private String password;

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserEmailId() {
		return userEmailId;
	}

	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}

	public int getUserMobileNumber() {
		return userMobileNumber;
	}

	public void setUserMobileNumber(int userMobileNumber) {
		this.userMobileNumber = userMobileNumber;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserDOB() {
		return userDOB;
	}

	public void setUserDOB(String userDOB) {
		this.userDOB = userDOB;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User(int userId, String userFirstName, String userLastName, String userEmailId, int userMobileNumber,
			String userGender, String userDOB, String password) {
		super();
		UserId = userId;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userEmailId = userEmailId;
		this.userMobileNumber = userMobileNumber;
		this.userGender = userGender;
		this.userDOB = userDOB;
		this.password = password;
	}

	public User() {
		super();
	}
	
	
	

}
