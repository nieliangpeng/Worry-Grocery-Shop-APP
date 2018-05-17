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

import com.sky.Bean.Admin;


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
	public Admin findLoginAdmin(String AdminTelephone, String adminPassword) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		Query<Admin> query = session.createQuery("from Admin where admin_phone=:phone and admin_pwd=:passwd");
		query.setParameter("phone", AdminTelephone);
		query.setParameter("passwd", adminPassword);
		Admin admin = query.uniqueResult();
		// System.out.print(admin.getPerson().getRealName());
		tran.commit();
		session.close();
		return admin;
	}
	public boolean findAdminTelephone(String telephone) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		Query<Admin> query = session.createQuery("from Admin where admin_phone=:telephone");
		query.setParameter("telephone", telephone);
		List<Admin> list = query.list();
		tran.commit();
		session.close();
		if (list.size() == 0) {

			return false;
		} else {
			return true;
		}
	}
	//
	public boolean saveAdmin(Admin admin) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		try {
			session.save(admin);
			tran.commit();
			session.close();
			return true;
		} catch (Exception e) {
			System.out.print(e);
			tran.rollback();
			session.close();
			return false;
		}
	}
	public Admin findLoginAdminByTelephone(String AdminTelephone) {
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();

		Query<Admin> q = session.createQuery("from Admin where admin_phone=?");
		q.setParameter(0, AdminTelephone);
		Admin admin = q.uniqueResult();
		tran.commit();
		session.close();
		return admin;

	}

	public boolean updateAdminPhone(String AdminTelephone, String phoneName) {
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		Admin admin = findLoginAdminByTelephone(AdminTelephone);
		Query query = session.createQuery("update Admin set admin_header=? where admin_id=?");
		query.setParameter(0, phoneName);
		query.setParameter(1, admin.getAdmin_id());
		int result = query.executeUpdate();
		// System.out.print(result);
		tran.commit();
		session.close();
		if (result > 0) {
			return true;
		}
		return false;
	}
	public boolean updateAdminPassword(String telephone, String newPassword) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tran = session.beginTransaction();
		Admin admin = findLoginAdminByTelephone(telephone);
		Query query = session.createQuery("update Admin set admin_pwd=? where admin_id=?");
		query.setParameter(0, newPassword);
		query.setParameter(1, admin.getAdmin_id());
		int result = query.executeUpdate();
		// System.out.print(result);
		tran.commit();
		session.close();
		if (result > 0) {
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
}
