package com.sky.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sky.Bean.Admin;
import com.sky.Dao.AdminDao;



@Service
public class AdminService {
	@Autowired
	private AdminDao adminDao;
	public AdminDao getAdminDao() {
		return adminDao;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}
	@Transactional
	public boolean checkVerf(String adminVerf, HttpServletRequest request) {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		String rand= (String) session.getAttribute("rand");
		if(adminVerf.equals(rand)) {
			return true;
		}else {
			return false;
		}
	}
	@Transactional
	public Admin findLoginAdmin(String AdminTelephone, String adminPassword) {
		// TODO Auto-generated method stub
		return adminDao.findLoginAdmin(AdminTelephone,adminPassword);
	}

	@Transactional
	public boolean adminLoginOut(HttpServletRequest request) {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		try {
			session.removeAttribute("admin");
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		return true;
	}
	@Transactional
	public boolean findAdminTelephone(String telephone) {
		// TODO Auto-generated method stub
		return adminDao.findAdminTelephone(telephone);
	}
	//注册
	@Transactional
	public boolean saveAdmin(Admin admin, MultipartFile phone, HttpServletRequest request) throws IOException {
		// TODO Auto-generated method stub
		ServletContext servletContext = request.getServletContext();
		String realPath = servletContext.getRealPath("/");
		File dir = new File(realPath + "upload");
		File dir1=new File(dir,"admin");
		File dir2 = new File(dir1, admin.getAdmin_phone());
		if (!dir.exists()) {
			dir.mkdir();
		}
		if (!dir1.exists()) {
			dir1.mkdir();
		}
		if (!dir2.exists()) {
			dir2.mkdir();
		}
		if (phone != null) {
			File file = new File(dir2,admin.getAdmin_header());
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(phone.getBytes());
			fos.flush();
			fos.close();

		}
		boolean bool = adminDao.saveAdmin(admin);
		return bool;
	}	
	@Transactional
	public boolean updateAdminPhone(HttpServletRequest request, String AdminTelephone, MultipartFile phone) throws IOException {
		// TODO Auto-generated method stub
		ServletContext servletContext = request.getServletContext();
		String realPath = servletContext.getRealPath("/");
		File dir = new File(realPath + "upload");
		File dir1 = new File(dir, "admin");
		File dir2 = new File(dir1, AdminTelephone);
		if (!dir.exists()) {
			dir.mkdir();
		}
		if (!dir1.exists()) {
			dir1.mkdir();
		}
		if (!dir2.exists()) {
			dir2.mkdir();
		}
		if (phone != null) {
			File file = new File(dir2, phone.getOriginalFilename());
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(phone.getBytes());
			fos.flush();
			fos.close();

		}
		//更新用户数据中的头像名字
		return adminDao.updateAdminPhone(AdminTelephone,phone.getOriginalFilename());
		
	}
	@Transactional
	public Admin findLoginAdminByTelephone(String AdminTelephone) {
		return adminDao.findLoginAdminByTelephone(AdminTelephone);
	}
	@Transactional
	public boolean updateAdminPassword(String telephone, String newPassword) {
		// TODO Auto-generated method stub
		return adminDao.updateAdminPassword(telephone,newPassword);
	}
	
	
	
	
	
}
