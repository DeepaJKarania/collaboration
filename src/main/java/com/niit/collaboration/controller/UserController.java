package com.niit.collaboration.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaboration.dao.UserDAO;
import com.niit.collaboration.model.User;

@RestController
public class UserController {

	
	//public static final Logger log=LoggerFactory.getLogger(UserDAOImpl.class);
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private User user;
	
	@RequestMapping(value="/users",method=RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUsers()
	{
		List<User> userList=userDAO.getAllUsers();
		
		if(userList.isEmpty())
		{
			user.setErrorCode("404");
			user.setErrorMessage("Users are not available");
			
			userList.add(user);
			
		}
		
		return new ResponseEntity<List<User>>(userList,HttpStatus.OK);
	}
	
	@RequestMapping(value="/user/{id}",method=RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable(value="userID")String userID)
	{
		user=userDAO.get(userID);
		
		if(user==null)
		{
			user=new User();
			user.setErrorCode("404");
			user.setErrorMessage("User does not exist with the ID : "+userID);
			
			
		}
		
		return new ResponseEntity<User>(user,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/login/",method=RequestMethod.POST)
	public ResponseEntity<User> isValidUser(@RequestBody User user)
	{
		user=userDAO.validate(user.getId(), user.getPassword());
		
		if(user==null)
			{
				user=new User();
				user.setErrorCode("404");
				user.setErrorMessage("Invalid credentials. Please try again...");
			}
			
		return new ResponseEntity<User>(user,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ResponseEntity<User> logout(HttpSession httpSession)
	{
	String loggedInUserID = (String)httpSession.getAttribute("loggedInUserID");
	user=userDAO.get(loggedInUserID);
	/*user.setOffLine('N');
	friendDAO.setOffLine(loggedInUserID);
*/
	if(userDAO.update(user))
	{
	user.setErrorCode("200");
	user.setErrorMessage("You successfully logged out.");
	}
	else
	{
	user.setErrorCode("404");
	user.setErrorMessage("Some problem occurred during logged");
	}
	httpSession.invalidate();

	return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	

	@RequestMapping(value="/accept/{id}")
	public ResponseEntity<User> acceptRegistration(@PathVariable("userID") String userID)
	{
		user=userDAO.get(userID);
		
		user.setStatus('A');
		
		if(userDAO.update(user)==true)
		{
			user.setErrorCode("200");
			user.setErrorMessage("Successfully updated");
		}
		else
		{
			user.setErrorCode("404");
			user.setErrorMessage("Not able to update the status");
		}
		
		
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}	
}
