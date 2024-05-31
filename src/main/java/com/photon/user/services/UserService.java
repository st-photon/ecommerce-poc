package com.photon.user.services;


import com.photon.user.entity.User;

public interface UserService {

	public String UserRegistration(User user);
	
	public User UserById(int id);
	


	public String userLogout(User user);

	//public String userProduct(Product user, MultipartFile file) throws IOException;

	//public Product getProduct(int id);

	public String UserLogin(String userEmailId, String password);
	
	//public byte[] downloadImage(String fileName);

	
	
	
}
