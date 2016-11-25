package com.niit.collaboration.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaboration.dao.UserDAO;
import com.niit.collaboration.model.User;

public class UserDAOImpl implements UserDAO {

	public static final Logger log = LoggerFactory.getLogger(UserDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public UserDAOImpl(SessionFactory sessionFactory) {

		this.sessionFactory = sessionFactory;

	}

	@Transactional
	public boolean save(User user) {

		log.debug("Calling of the method save.");

		try {
			sessionFactory.getCurrentSession().save(user);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean update(User user) {

		log.debug("Calling of the method update.");

		try {
			sessionFactory.getCurrentSession().update(user);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public User get(String userID) {

		log.debug("calling of the method save");
		log.debug("user Id : " + userID);

		return (User) sessionFactory.getCurrentSession().get(User.class, userID);
	}

	@Transactional
	public User validate(String userID, String password) {
		log.debug("caaling of the method validate");

		log.debug("user Id : " + userID + "and password : " + password);

		String hql = "from User where userID='" + userID + "'and password = '" + password;

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		return (User) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<User> getAllUsers() {
		log.debug("Calling of the method getAllUsers");

		String hql = "from User";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		return query.list();
	}

}
