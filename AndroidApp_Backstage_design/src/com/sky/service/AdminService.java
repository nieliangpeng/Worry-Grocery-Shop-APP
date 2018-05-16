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
	
//  例子
//	@Transactional
//	public boolean checkVerf(String adminVerf, HttpServletRequest request) {
//		// TODO Auto-generated method stub
//		HttpSession session=request.getSession();
//		String rand= (String) session.getAttribute("rand");
//		if(adminVerf.equals(rand)) {
//			return true;
//		}else {
//			return false;
//		}
//	}
	
	
	
	
	
	
	
	
	
	
	
}
