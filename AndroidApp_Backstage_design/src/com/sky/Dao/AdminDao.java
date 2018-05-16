package com.sky.Dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class AdminDao {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

//	public Admin findLoginAdmin(String adminEmail, String adminPassword) {
//		// TODO Auto-generated method stub
//		Session session = sessionFactory.openSession();
//		Transaction tran = session.beginTransaction();
//
//		Query<Admin> query = session.createQuery("from Admin where admin_email=:email and admin_passwd=:passwd");
//		query.setParameter("email", adminEmail);
//		query.setParameter("passwd", adminPassword);
//		Admin admin = query.uniqueResult();
//		// System.out.print(admin.getPerson().getRealName());
//		tran.commit();
//		session.close();
//		return admin;
//	}

	
	
	
	
	
	
	
	
	
	
	
	
}
