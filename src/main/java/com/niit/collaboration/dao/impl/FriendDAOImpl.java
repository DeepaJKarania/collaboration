package com.niit.collaboration.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaboration.model.Friend;

public class FriendDAOImpl {

	public static final Logger log = LoggerFactory.getLogger(FriendDAOImpl.class);


	@Autowired
	private SessionFactory sessionFactory;
	
	public FriendDAOImpl(SessionFactory sessionFactory)
	{
		try
		{
			this.sessionFactory=sessionFactory;
		}
		catch(Exception e)
		{
			log.error("Unable to connect to db");
			e.printStackTrace(); 
			
		}
	}
	
	@SuppressWarnings("unused")
	private Integer getMaxId()
	{
		log.debug("Starting of the method getMaxId");
		
		String hql="select max(id) from Friend";
		Query query=sessionFactory.openSession().createQuery(hql);
		Integer maxID=(Integer)query.uniqueResult();
		log.debug("Max id :"+maxID);
		
		return maxID;
		
	}
	
	@Transactional
	public boolean save(Friend friend)
	{
		try
		{
			log.debug("Previous Id : "+getMaxId());
			friend.setId(getMaxId());
			log.debug("Generated id :"+getMaxId());
			sessionFactory.openSession().save(friend);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean update(Friend friend)
	{
		
		try
		{
			sessionFactory.openSession().update(friend);
			return true;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
		
	}
	
	@Transactional
	public void delete(String userID,String friendID)
	{
		Friend friend=new Friend();
		friend.setFriendID(friendID);
		friend.setUserID(userID);
		sessionFactory.openSession().delete(friend);
	}
	
	@Transactional
	public List<Friend> getMyFriends(String userID)
	{
		
		String hql="from Friend where userID="+"'"+userID+"' and status='"+"A'";
		Query query=sessionFactory.openSession().createQuery(hql);
		List<Friend> list=(List<Friend>)query.list();
		
		return list;
		
	}
	
	@Transactional
	public List<Friend> getNewFriendRequests(String userID)
	{
		String hql="from Friend where userID="+"'"+userID+"'and status='"+"N'";
		Query query = sessionFactory.openSession().createQuery(hql);
		
		List<Friend> list = (List<Friend>)query.list();
		
		return list;
	}
	
	@Transactional
	public Friend get(String userID,String friendID)
	{
		String hql="from Friend where userID="+"'"+userID+"'and friendID='"+friendID;
		log.debug("hql : "+hql);
		Query query=sessionFactory.openSession().createQuery(hql);
		
		return (Friend) query.uniqueResult();
	}
	
	@Transactional
	public void setOnline(String userID)
	{
		log.debug("Starting of the method setOnline");
		String hql="UPDATE Friend SET isOnline = 'Y' where friendID='"+userID+"'";
		log.debug("hql : "+hql);
		Query query=sessionFactory.openSession().createQuery(hql);
		query.executeUpdate();
		log.debug("Ending of the method setOnline");
	}
	
	@Transactional
	public void setOffline(String userID)
	{
		log.debug("Starting of the method setOffline");
		String hql="UPDATE Friend SET isOnline = 'N' where friendID='"+userID+"'";
		log.debug("hql : "+hql);
		Query query=sessionFactory.openSession().createQuery(hql);
		query.executeUpdate();
		log.debug("Ending of the method setOffline");
	}
		
}
