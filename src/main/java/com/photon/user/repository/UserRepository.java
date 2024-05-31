package com.photon.user.repository;

import com.photon.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

//	Optional<User> findByUserEmailId(String userEmailId);

	Optional<User> findByUserEmailIdAndPassword(String userEmailId, String password);

	//Optional<User> findByUserId(int userId);

	//Optional<User> findByUserEmailId(String userEmailId);

	//Optional<User> findByUserId(int userId);


	//@Query("SELECT s FROM Student s WHERE s.id = :rollNum")
	//void getUserId(int id);
	
	

}

