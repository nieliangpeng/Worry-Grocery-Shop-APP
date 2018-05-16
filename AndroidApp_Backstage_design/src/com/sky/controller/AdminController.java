package com.sky.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sky.service.AdminService;

@Controller
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	
//	例子
//	//找回密码
//	@RequestMapping("/findPasswordSubmitInAdmin")
//	public String findPasswordSubmitInAdmin(String email,HttpServletRequest request) {
//		Admin admin=adminService.findByEmail(email);
//		if(admin!=null) {
//			//邮件的内容
//			StringBuffer sb = new StringBuffer();
//			sb.append("亲爱的管理员【");
//			sb.append(admin.getAdmin_username());
//			sb.append("】,您的一家小店的后台登录密码为【");
//			sb.append(admin.getAdmin_passwd());
//			sb.append("】,请勿向其他人泄露您的密码，以免账号被盗,如果不是您本人的操作，忽略该信息！");
//			boolean bool = SendEmail.send(admin.getAdmin_email(), sb.toString());
//			request.setAttribute("emailMsg", "<script>alert('已发送邮件至注册邮箱，请及时查看，找回密码，进行登录！！！');</script>");
//			
//			return "adminInUserLogin";
//		}else {
//			request.setAttribute("emailMsg", "<script>alert('该邮箱并未注册账号，请核对是否输入正确！！');</script>");
//			request.setAttribute("email", email);
//			return "adminFindPassword";
//		}
//		
//	}	
	
	
	
	
}
