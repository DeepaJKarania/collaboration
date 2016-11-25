package com.niit.collaboration.dao;

import java.util.List;

import com.niit.collaboration.model.User;

public interface UserDAO {
	
	public boolean save(User user);
	public boolean update(User user);
	public User get(String userID);
	public User validate(String userID,String password);
	public List<User> getAllUsers();
	

}
