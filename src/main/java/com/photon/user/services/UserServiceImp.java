package com.photon.user.services;

import com.photon.user.exceptions.UserException;
import com.photon.user.entity.User;
import com.photon.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

@Autowired
private UserRepository userRepository;


//@Autowired
//ProductRepository productRepository;

public static String uploadDirectory= System.getProperty("user.dir") + "/src/main/resources/image";
public static String FOLDER_PATH=     "C:/Users/sumitkumar_s/Documents/image/";


private Path path;
	@SuppressWarnings("unused")
	@Override
	public String UserRegistration(User user) {
		
		/*
		 * int randomUUID = UUID.randomUUID().variant();
		 * System.out.println("hjjjjjjjjjjjjjjjjjjjjj" + randomUUID);
		 * user.setUserId(randomUUID);
		 */
		Optional<User> getUser = userRepository.findByUserEmailIdAndPassword(user.getUserEmailId(),user.getPassword());
		  if(getUser.isPresent()) {
		if (user.getUserEmailId().equals(getUser.get().getUserEmailId())) {
		        return "registration not successful beacuse emailid already avilable:-  " + getUser.get().getUserEmailId() ;
		    } else {
		        userRepository.save(user);
		        return "registration successful";
		    }  }else {
		    	  userRepository.save(user);
			        return "registration successful";
		    }
		    
		    

		
	}

	@Override
	public User UserById(int id) {
		userRepository.findById(id).orElseThrow(()-> new UserException("invalid user" + id));
		return null;
	}

	@Override
	public String UserLogin(String userEmailId, String password) {
		// TODO Auto-generated method stub
		User getUser = userRepository.findByUserEmailIdAndPassword(userEmailId,password).orElseThrow(()-> new UserException("invalid user" + userEmailId + "--"+password ));
		if( getUser.getUserEmailId().equals(userEmailId)) {
			return "login successfully";
		} else {
			// return "login not  successfully";
			throw new UserException("invalid user-" + userEmailId + "---"+ password);
		}
		
	
		
	}

	@Override
	public String userLogout(User user) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public String userProduct(Product user,MultipartFile file) throws IOException {
//	String filePath=	FOLDER_PATH+file.getOriginalFilename();
//		user.setFileName(file.getOriginalFilename());
//		user.setType(file.getContentType());
//		//user.setImageData(ImageUtils.compressImage(file.getBytes()));
//		user.setFilepath(filePath);
//
//		file.transferTo(new File(filePath));
//		Product product	=productRepository.save(user);
//		  if (product != null) {
//			  return "product is save successfully--" + file.getOriginalFilename() +filePath ;
//		  }
//		return null;
//
//
//	}

//	@Override
//	public Product getProduct(int id) {
//		// TODO Auto-generated method stub
//
//		Product p   =productRepository.findById(id).orElseThrow(()-> new UserException("invalid user" + id));
//		/*
//		 * byte[] imageDataBytes = Base64.getDecoder().decode(p.getImageData());
//		 * p.setImageData(imageDataBytes);
//		 */
//
//		//byte[] decompressImage = ImageUtils.decompressImage(p.getImageData());
//		//String filePath =  uploadDirectory;
//
//		//p.setImageData(decompressImage);
//		return p;
//
//	}
	
//	public byte[] downloadImage(String fileName) {
//
//				Optional<Product> byFileName = productRepository.findByFileName(fileName);
//				String filepath = byFileName.get().getFilepath();
//				//byte[] decompressImage = ImageUtils.decompressImage(byFileName.get().getImageData());
//				byte[] allBytes = null;
//				try {
//					allBytes = Files.readAllBytes(new File(filepath).toPath());
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//				return allBytes;
//
//	}



}
