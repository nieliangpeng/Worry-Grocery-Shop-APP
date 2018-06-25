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

import com.sky.Bean.Admin;

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

	@RequestMapping("/adminLoginSubmitInManager")
	public String adminLoginSubmitInManager(Model model,String autoLogin,String AdminTelephone,String AdminPassword,String AdminVerf,HttpServletRequest request,HttpServletResponse response) {
		if(adminService.checkVerf(AdminVerf, request)) {
			Admin admin=adminService.findLoginAdmin(AdminTelephone,AdminPassword);
			if(admin!=null) {
				HttpSession session=request.getSession();
				session.setAttribute("admin", admin);
				if(autoLogin != null && autoLogin.equals("on")) {
					
					session.setMaxInactiveInterval(60*60*24*7);//秒
					
					Cookie cookie=new Cookie("JSESSIONID", session.getId());
					cookie.setMaxAge(60*60*24*7);
					response.addCookie(cookie);//更新
				}
				return "adminIndex";
			
					
				
			}else {
				model.addAttribute("AdminErrorMsg","管理员用户名或者密码不正确，请重新登录");
				return "adminLogin";
			}
		}else {
			model.addAttribute("AdminTelephone",AdminTelephone);
			model.addAttribute("AdminPassword",AdminPassword);
			model.addAttribute("AdminErrorMsg","验证码错误，重新输入");
			return "adminLogin";
		}
	}
	@RequestMapping("/adminLoginOut")
	public String adminLoginOut(Model model,HttpServletRequest request) {
		boolean bool=adminService.adminLoginOut(request);
		if(bool) {
			model.addAttribute("LoginOutMsg","<script>alert('退出登录成功');</script>");
			return "adminLogin";
		}
		model.addAttribute("LoginOutMsg","<script>alert('退出登录失败');</script>");
		return "adminIndex";
		
	}
	@RequestMapping("/addAdmin")
	public String addAdmin() {
		return "adminAdd";
	}
	
	@RequestMapping("/checkAdminTelephone")
	public void checkAdminTelephone(@RequestParam String telephone,HttpServletResponse response) throws IOException {
		 PrintWriter pw=response.getWriter();
		 if(telephone.equals("")) {
			 pw.print("kong");
		 }else if(adminService.findAdminTelephone(telephone)){
			 
		      pw.print(true);
		 }else{
		      pw.print(false);
		 }
    }
	@RequestMapping("/saveAdmin")
	public String saveAdmin(Model model,HttpServletRequest request,String telephone,String RealName,String admin_passwd,@RequestParam MultipartFile phone) throws IOException {
		Admin admin=new Admin();
		admin.setAdmin_phone(telephone);
		admin.setAdmin_name(RealName);
		admin.setAdmin_pwd(admin_passwd);
		admin.setAdmin_header(phone.getOriginalFilename());
		boolean bool= adminService.saveAdmin(admin,phone,request);
		
		if(bool) {
			model.addAttribute("adminMsg1","添加管理员成功,可以使用该账号登录！");
			
		}else {
			model.addAttribute("adminMsg2","添加失败,请重新添加");
		}
		return "adminComplete_Add";
	}
	@RequestMapping("/adminDetail")
	public String adminDetail() {
		return "adminDetail";
	}
	@RequestMapping("/updateAdminPhone")
	public String updateAdminPhone(HttpServletRequest request,String AdminTelephone,@RequestParam MultipartFile phone) throws IOException {
		HttpSession session=request.getSession();
		boolean bool=adminService.updateAdminPhone(request,AdminTelephone,phone);
		if(bool) {
			Admin admin=adminService.findLoginAdminByTelephone(AdminTelephone);
			session.setAttribute("admin", admin);
			request.setAttribute("updateAdminMsg","<script>alert('上传图片成功,点击左侧头像可以刷新头像');</script>");
			return "adminDetail";
		}else {
			request.setAttribute("updateAdminMsg","<script>alert('上传图片失败');</script>");
			return "adminDetail";
		}
		
	}
	@RequestMapping("/flushPhone")
	public String flushPhone() {
		return "adminMain_left";
		
	}
	@RequestMapping("/jumpToUpdateAdminPasswordPag")
	public String updateAdminPassword() {
		return "adminUpdatePwd";
	}
	//更新密码
	@RequestMapping("/updateAdminPassword")
	public String updateAdminPassword(HttpServletRequest request,String telephone,String newPassword) {
		HttpSession session=request.getSession();
		boolean bool=adminService.updateAdminPassword(telephone,newPassword);
		if(bool) {
			Admin admin=adminService.findLoginAdminByTelephone(telephone);
			session.setAttribute("admin", admin);
			request.setAttribute("updateAdminPwd","<script>alert('更新密码成功');</script>");
			return "adminUpdatePwd";
		}else {
			request.setAttribute("updateAdminPwd","<script>alert('更新密码失败');</script>");
			return "adminUpdatePwd";
		}
	}
	
}
