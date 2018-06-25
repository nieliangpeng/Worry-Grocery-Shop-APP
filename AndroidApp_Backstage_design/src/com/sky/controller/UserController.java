package com.sky.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sky.Bean.Comment;
import com.sky.Bean.FollowType;
import com.sky.Bean.Invitation;
import com.sky.Bean.InvtType;
import com.sky.Bean.LetterReply;
import com.sky.Bean.Mmsic;
import com.sky.Bean.TextShout;
import com.sky.Bean.TreeHoles;
import com.sky.Bean.User;
import com.sky.Bean.Video;
import com.sky.Bean.Word;
import com.sky.common.Page;
import com.sky.common.indexPage;
import com.sky.service.UserService;
import com.sun.mail.iap.Response;
import com.sun.org.apache.regexp.internal.RE;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import sun.print.resources.serviceui;
 



@Controller
public class UserController<Music> {
	@Autowired
	private UserService userService;
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String genxinUser(int user_id) {
		User user=userService.selectById(user_id);
		user.setLetter_num();
		user.setCollect_num();
		user.setFllow_num();
		user.setInvitation_num();
		System.out.println("新建数量"+user.getLetter_num());
		 JsonConfig jsonConfig = new JsonConfig();		
		   jsonConfig.setIgnoreDefaultExcludes(false); 
		  
		   jsonConfig.setExcludes(new String[]{"letterSet","textShoutSet","voiceScreamSet","getLetterSet","replySet","commentSet","collectSet","invitationSet","followTypeSet","followSet" });
		 
		   JSONObject json = JSONObject.fromObject(user,jsonConfig);   
		return json.toString();
		
	}
	@RequestMapping("/saveUser1")
	public void saveUser(HttpServletRequest request,HttpServletResponse response,String user_name,String user_pwd,String user_phone,@RequestParam MultipartFile user_image) throws IOException {
	//	System.out.print("连接"+user_name+user_pwd+user_phone);	
		User user=new User();
		user.setUser_name(user_name);//不可重复
		user.setUser_pwd(user_pwd);
		user.setUser_phone(user_phone);//不可重复
		user.setUser_image(user_image.getOriginalFilename());
		user.setUser_state("normal");
		boolean bool= userService.saveUser(user,user_image,request);	
		if(bool) {
			
			response.getWriter().write("success");
			
		}else {
			response.getWriter().write("failure");
		}
	}

	//少一个单选框记住密码radio
	@RequestMapping("/getHeader")
	public void loginUser(HttpServletRequest request,HttpServletResponse response,String user_phone) throws IOException {
		System.out.println("登录"+user_phone);
		//用户名
		boolean b=userService.checkPhone(user_phone);
		User user=userService.selectByPhone(user_phone);
		String user_image=user.getUser_image();
		System.out.println(user_image);
	
		int img;
		if(b) {
			FileInputStream in=new FileInputStream(new File(user_image));
		    OutputStream out=response.getOutputStream();
		    while((img=in.read())!=-1) {
		    	out.write(img);	
		    	out.flush();
		    }
		}else {
			 response.getWriter().write("用户名不存在拟");	 
		}
		
	}
	@RequestMapping("loginUser")
	public void login(HttpServletRequest request,HttpServletResponse response,String user_phone,String user_pwd) throws IOException {
		//密码
		boolean c=userService.checkPwd(user_phone,user_pwd);
		if(c) {
		//	response.getWriter().write();
			int num=0;
			request.getSession().setAttribute("getLetterNum",num);
			User user=userService.selectByPhone(user_phone);
			user.setLetter_num();
			
			user.setCollect_num();
			user.setFllow_num();
			user.setInvitation_num();
			   JsonConfig jsonConfig = new JsonConfig();		
			   jsonConfig.setIgnoreDefaultExcludes(false); 
			  
			   jsonConfig.setExcludes(new String[]{"letterSet","textShoutSet","voiceScreamSet","getLetterSet","replySet","commentSet","collectSet","invitationSet","followTypeSet","followSet" });
			 
			   JSONObject json = JSONObject.fromObject(user,jsonConfig);  
			  
			   System.out.println(json.toString());
			 response.getWriter().write(json.toString());	 
		}else {
			response.getWriter().write("failure");
		}
	}
	//修改用户的密码
	@RequestMapping("/pwd")
	public void updatePwd(HttpServletResponse response,String user_pwd,Integer user_id) throws IOException {

		userService. updatePswd(user_pwd,user_id);
		response.getWriter().write("success");
	}
	//修改头像
	@RequestMapping("/updateHeader")
	public void updateHeader(HttpServletResponse response,HttpServletRequest request,@RequestParam MultipartFile user_image,Integer user_id) throws IOException {
		String header=user_image.getOriginalFilename();
		User user=userService.selectById(user_id);
		ServletContext servletContext = request.getServletContext();
		String realPath = servletContext.getRealPath("/");
		File dir = new File(realPath + "upload");
		File dir1=new File(dir,"user");//父路径，子路径
		File dir2 = new File(dir1, user.getUser_phone());
		if (!dir.exists()) {
			dir.mkdir();
		}
		if (!dir1.exists()) {
			dir1.mkdir(); 
		}
		if (!dir2.exists()) {
			dir2.mkdir();
		}
		if (user_image != null) {
			File file = new File(dir2,header);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(user_image.getBytes());
			fos.flush();
			fos.close();
		}
		//删除原来的图片
		File file=new File(user.getUser_image());
		file.delete();
		user.setUser_image(realPath+"upload/user/"+user.getUser_phone()+"/"+header);
		userService.updateHeader(user);
		
		
	}
	//树洞
	//写信
	@RequestMapping("/writeLetter")
	public void writeLetter(HttpServletRequest request,HttpServletResponse response,int user_id,String letter_content) throws IOException {
	
		TreeHoles letter=new TreeHoles();
		letter.setLetter_content(letter_content);
		 Date d = new Date();
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		letter.setLetter_time(sdf.format(d));
		boolean a=userService.writeLetter( letter,user_id);
		if(a) {		
			response.getWriter().write(genxinUser(user_id));
		}else {
			response.getWriter().write("failure");
		}
	}
	//取信
	@RequestMapping("/getALetter")
	public void readLetter(HttpServletRequest request,HttpServletResponse response,int user_id) throws IOException{
		response.setCharacterEncoding("utf-8");
		//判断取信的个数？？？？？？遗留问题	
//		HttpSession session=request.getSession();
//		int b=(int)session.getAttribute("getLetterNum");
		
		TreeHoles letter=new TreeHoles();
		letter=userService.readLetter(user_id);	
		if(null!=letter) {
			//插入到getLetter表中

	//	session.setAttribute("getLetterNum",++b);
	
		
			System.out.println(letter.getLetter_id()+"信id");
			response.getWriter().write(letter.getLetter_id()+"+"+letter.getUser().getUser_id()+"+"+letter.getUser().getUser_name()+"+"+letter.getLetter_content()+"+"+letter.getLetter_time());
		}else {
			response.getWriter().write("failure");
		}
	
	}
	//查看我的信件
	@RequestMapping("/read")
	public void read(HttpServletResponse response,Integer user_id) throws IOException {
		List set=new ArrayList<TreeHoles>();
		set=userService.read(user_id);	
////	??堆栈溢出	,因为形成循环toString中
			//System.out.println(((TreeHoles) set.get(1)).getLetter_content());
	    List<String> list=new ArrayList<String>();
	    for(int i=0;i<set.size();i++) {
	    	list.add(((TreeHoles) set.get(i)).getLetter_id()+"+"+((TreeHoles) set.get(i)).getLetter_content()+"+"+((TreeHoles) set.get(i)).getLetter_time()+"+"+((TreeHoles) set.get(i)).getIsShowInTheTree());
	    }
	   System.out.print(list);
	        
		if(null!=list) {		
			response.setCharacterEncoding("utf-8");
			JSONArray json=JSONArray.fromObject(list);
			response.getWriter().write(json.toString());
		}else {
			response.getWriter().write("failure");
		}
	}
	//从树洞中移除信件，该属性
	@RequestMapping("/removeLetter")
	public void removeLetter(HttpServletResponse response,Integer letter_id) throws IOException {
		userService.removeLetter(letter_id);
		response.getWriter().write("success");
	}
	@RequestMapping("deleteLetter")
	//从树洞中删除信件，删除数据库
	public void delete(HttpServletResponse response,Integer user_id,Integer letter_id) throws IOException{
	
		userService.delete(user_id,letter_id);		
		response.getWriter().write(genxinUser(user_id));		
		
	}
	//放回树洞
	@RequestMapping("backLetter")
	public void backLetter(HttpServletResponse response,Integer letter_id) throws IOException {
		userService.backLetter(letter_id);		
		response.getWriter().write("success");
	}
	//回信
	@RequestMapping("replyLetter")
	public void replyLetter(HttpServletResponse response,Integer user_id,Integer letter_id,String letter_content) throws IOException{
		userService.replyLetter(user_id,letter_id,letter_content);
		response.getWriter().write("success");
	}
	//查看回信
	@RequestMapping("readReplyLetter")
	public void readReplyLetter(HttpServletResponse response,Integer letter_id) throws IOException {
		Set set=new HashSet<LetterReply>();
		set=userService.readReplyLetter(letter_id);
		
		if(set.size()!=0) {
		    List<String> list=new ArrayList<String>();
			 for(Object r:set) {
			LetterReply	 rr=(LetterReply)r;
			User user=userService.selectById(rr.getUser_id());
			    	list.add(user.getUser_id()+"+"+user.getUser_name()+"+"+rr.getLetter_content()+"+"+rr.getLetter_time()+"+"+rr.getLetter_reply_id()+"+"+letter_id);
			    }
			   System.out.print(list);		
					response.setCharacterEncoding("utf-8");
					JSONArray json=JSONArray.fromObject(list);
					response.getWriter().write(json.toString());			
		}else {
			response.getWriter().write("failure");
		}
	}
	//删除回信
	@RequestMapping("deleteReplyLetter")
	public void deleteReplyLetter(HttpServletResponse response,Integer letter_reply_id,Integer letter_id) throws IOException {
		if(userService.deleteReplyLetter(letter_reply_id,letter_id)) {
			response.getWriter().write("success");
		}else {
		    response.getWriter().write("failue");
		}
		
	}
	//返回笔友信息
	@RequestMapping("/findPenFriend")
	public void peinFriend(HttpServletResponse response,Integer user_id) throws IOException {
		 System.out.println("笔友"+user_id);
		User user=userService.selectById(user_id);
		user.setLetter_num();
		user.setCollect_num();
		user.setFllow_num();
		user.setInvitation_num();
		   JsonConfig jsonConfig = new JsonConfig();		
		   jsonConfig.setIgnoreDefaultExcludes(false); 
		  
		   jsonConfig.setExcludes(new String[]{"letterSet","textShoutSet","voiceScreamSet","getLetterSet","replySet","commentSet","collectSet","invitationSet","followTypeSet","followSet"});
		 
		   JSONObject json = JSONObject.fromObject(user,jsonConfig);  
		  
		   System.out.println(json.toString());
		 response.getWriter().write(json.toString());	 
		
	}
	//添加笔友
//	@RequestMapping("addPen")
//	public void 
	//插入音频MP3
	@RequestMapping("/mp3")
	public void inmp3(HttpServletResponse response,String music_name,String music_auth,String music_url,String music_img) {
	  Mmsic a=new Mmsic();	
	  a.setMusic_auth(music_auth);
	  a.setMusic_img(music_img);
	  a.setMusic_name(music_name);
	  a.setMusic_url(music_url);
	  userService.inmp3(a);
	}
	//传音频
	@RequestMapping("/music")
	public void chan(HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		List list=new ArrayList<Mmsic>();
		list=userService.chan();
		if(list.size()!=0) {
			 JSONArray json = JSONArray.fromObject(list);  
			 response.getWriter().write(json.toString());
		}else {
			 response.getWriter().write("failure");
		}
	}
	//文字呐喊
	@RequestMapping("/textshout")
	public void nahan(HttpServletResponse response,Integer user_id,String textContent) throws IOException {
		response.setCharacterEncoding("utf-8");
		TextShout textshout=new TextShout();
		textshout.setTextContent(textContent);
	    User user=userService.selectById(user_id);
	    userService.saveTextShout(user,textshout);
	    response.getWriter().write("success");
		
	}
	//查看文字呐喊
//	@RequestMapping("/showTextshout")
//	public void showTextShout(HttpServletResponse response,Integer user_id) {
//		response.setCharacterEncoding("utf-8");
//		userService.showTextShout(user_id);	
//	}
	
	//查看视频
	@RequestMapping("/video")
	public void showTv(HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		List list=new ArrayList<Video>();
		list=userService.showTv();
		if(list.size()!=0) {
			 JSONArray json = JSONArray.fromObject(list);  
			 response.getWriter().write(json.toString());
		}else {
			 response.getWriter().write("failure");
		}
	}
	//word每日一言
	@RequestMapping("/EverydayWord")
	public void showWord(HttpServletResponse response) throws IOException {
		Date date=new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String time=sdf.format(date);
	    Word word=userService.showWord(time);
	    if(null!=word) {
	    	JSONObject json=JSONObject.fromObject(word);	
	    	response.getWriter().write(json.toString());
	    }else {
	    	response.getWriter().write("failure");
	    }
	}
	//心灵大师榜
	@RequestMapping("/senior")
	public void heartSenior(HttpServletResponse response,Integer user_id,String master_profile,String detailintroduction) throws IOException {
		System.out.println("控制器"+user_id);
		userService.heartSenior(user_id,master_profile,detailintroduction);			
		response.getWriter().write( genxinUser(user_id));
	}
	//查询所有心灵大师byUserstate
	@RequestMapping("/selectSenior")
	public void selectSenior(HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		List list=userService.selectSenior();
		if(list.size()!=0) {
			 JsonConfig jsonConfig = new JsonConfig();		
			   jsonConfig.setIgnoreDefaultExcludes(false); 
			  
			   jsonConfig.setExcludes(new String[]{"letterSet","textShoutSet","voiceScreamSet","getLetterSet","replySet","commentSet","collectSet","invitationSet","followTypeSet","followSet" });
			 
		        JSONArray json=JSONArray.fromObject(list,jsonConfig);
			   response.getWriter().write(json.toString());
		}else {
			response.getWriter().write("failure");
		}
	}
	//添加话题
	@RequestMapping("/addtopic")
	public void topic(HttpServletResponse response,String type_name,String type_image,String type_desc) throws IOException {
		userService.saveTopic(type_name,type_image, type_desc);
	    response.getWriter().write("success");
	}
	//展示话题
	@RequestMapping("/getTypeList")
	public void showTopic(HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8");
		List list=userService.showTopic();
		if(list.size()!=0) {
			JsonConfig jsonConfig = new JsonConfig();		
			   jsonConfig.setIgnoreDefaultExcludes(false); 
			   jsonConfig.setExcludes(new String[]{"invitationSet","followTypeSet"});
			JSONArray json=JSONArray.fromObject(list,jsonConfig);
			response.getWriter().write(json.toString());
			
		}else {
			response.getWriter().write("failure");
		}
	}
	//查询话题的图片
	@RequestMapping("/picture")
	public void picture(HttpServletResponse response,Integer type_id) throws IOException {
		String picture=userService.pic(type_id);
		FileInputStream in=new FileInputStream(new File(picture));
	    OutputStream out=response.getOutputStream();
	    int img=0;
	    while((img=in.read())!=-1) {
	    	out.write(img);	
	    	out.flush();
	    }
	    
	}
	//写帖子
	@RequestMapping("/invitation")
	public void invitation(HttpServletRequest request,HttpServletResponse response,Integer type_id,Integer user_id,String Ivtn_content,@RequestParam MultipartFile file) throws IOException {
		response.setCharacterEncoding("utf-8");
		System.out.println("控制器"+user_id);
		Invitation invitation=new Invitation();
		invitation.setIvtn_content(Ivtn_content);
		String image=file.getOriginalFilename();
		File file2=new File(request.getServletContext().getRealPath("\\WEB-INF\\"+image));
	    FileOutputStream out=new FileOutputStream(file2);
	    out.write(file.getBytes());
	   invitation.setImage(request.getServletContext().getRealPath("\\WEB-INF\\"+image));
	   userService.invitation(type_id,user_id,invitation);
	   
	   response.getWriter().write(genxinUser(user_id));	
	}
	//查询用户的所有帖子
	@RequestMapping("/showInvitation")
	public void showInvitation(HttpServletResponse response,Integer user_id) throws IOException {
		response.setCharacterEncoding("utf-8");
		List<Invitation> list=userService.showInvitation(user_id);
		if(list.size()!=0) {
			for(Invitation in:list) {
				in.setUser_id(user_id);
				in.setCommentNum();
				in.setUser_name(userService.selectById(user_id).getUser_name());
				in.setUser_phone(userService.selectById(user_id).getUser_phone());
				in.setType_name(in.getInvtType().getType_name());
				System.out.println("帖子内容"+in.getIvtn_content());
				System.out.println("帖子话题 明"+in.getInvtType().getType_name());
			}
			 JsonConfig jsonConfig = new JsonConfig();		
			   jsonConfig.setIgnoreDefaultExcludes(false); 
			   jsonConfig.setExcludes(new String[]{"collectSet","commentSet","user", "invtType"});			 
			JSONArray json=JSONArray.fromObject(list,jsonConfig);
			response.getWriter().write(json.toString());
		}else {
			response.getWriter().write("failure");
		}
	}
	//传给用户
	@RequestMapping("/getUser")
	public void getUser(HttpServletResponse response,Integer user_id) throws IOException {
		System.out.println(user_id);
		response.getWriter().write(genxinUser(user_id));
		
	}
	//根据帖子的id查询帖子的图片
	@RequestMapping("/invitationPicture")
	public void inp(HttpServletResponse response,Integer invt_id) throws IOException {
	System.out.println(invt_id+"根据帖子的id查询帖子的图片");
	String picture=userService.inp(invt_id);
	FileInputStream in=new FileInputStream(new File(picture));
    OutputStream out=response.getOutputStream();
    int img=0;
    while((img=in.read())!=-1) {
    	out.write(img);	
    	out.flush();
    }
	}
	//查询所有的帖子
	@RequestMapping("/showallInvitation")
	public void showallInvitation(HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		List<Invitation> list=userService.showallInvitation();
		if(list.size()!=0) {
			for(Invitation in:list) {
				in.setUser_id(in.getUser().getUser_id());
				in.setCommentNum();
				in.setType_name(in.getInvtType().getType_name());
				in.setUser_name(in.getUser().getUser_name());
				in.setUser_phone(in.getUser().getUser_phone());
				System.out.println("帖子内容"+in.getIvtn_content());
				System.out.println("帖子话题 明"+in.getInvtType().getType_name());
			}
			 JsonConfig jsonConfig = new JsonConfig();		
			   jsonConfig.setIgnoreDefaultExcludes(false); 
			   jsonConfig.setExcludes(new String[]{"collectSet","commentSet","user", "invtType"});
			 
			JSONArray json=JSONArray.fromObject(list,jsonConfig);
			response.getWriter().write(json.toString());
		}else {
			response.getWriter().write("failure");
		}
	}
	//帖子点赞 
	@RequestMapping("/dianzan")
	public void dianzan(HttpServletResponse response,Integer invt_id) throws IOException {
		response.setCharacterEncoding("utf-8");
		System.out.println("大大大大大");
		Invitation in=userService.dianzan(invt_id);
		System.out.println(in);	  
		response.getWriter().write("success");
		
	}
	//查询某个话题的所有帖子
	@RequestMapping("/showInvitationByType")
	public void showInvitationByType(HttpServletResponse response,Integer type_id) throws IOException {
		response.setCharacterEncoding("utf-8");
		Set<Invitation> set=userService.showInvitationByType(type_id);
		if(set.size()!=0) {
			for(Invitation in:set) {	
				in.setCommentNum();
				in.setType_name(in.getInvtType().getType_name());
				in.setUser_id(in.getUser().getUser_id());
				in.setUser_name(in.getUser().getUser_name());
				in.setUser_phone(in.getUser().getUser_phone());
				System.out.println("帖子内容"+in.getIvtn_content());
				System.out.println("帖子话题 明"+in.getInvtType().getType_name());
			}
			 JsonConfig jsonConfig = new JsonConfig();		
			   jsonConfig.setIgnoreDefaultExcludes(false); 
			   jsonConfig.setExcludes(new String[]{"collectSet","commentSet","user", "invtType"});
			 
			JSONArray json=JSONArray.fromObject(set,jsonConfig);
			response.getWriter().write(json.toString());
		}else {
			response.getWriter().write("failure");
		}
	}
	//把所有的帖子id从大到小牌
	@RequestMapping("/showNewInvitation")
	public void newin(HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		List<Invitation> list=userService.newin();
		if(list.size()!=0) {
			for(Invitation in:list) {	
				in.setCommentNum();
				in.setUser_id(in.getUser().getUser_id());
				in.setType_name(in.getInvtType().getType_name());
				in.setUser_name(in.getUser().getUser_name());
				in.setUser_phone(in.getUser().getUser_phone());
				System.out.println("帖子内容"+in.getIvtn_content());
				System.out.println("帖子话题 明"+in.getInvtType().getType_name());
			}
			 JsonConfig jsonConfig = new JsonConfig();		
			   jsonConfig.setIgnoreDefaultExcludes(false); 
			   jsonConfig.setExcludes(new String[]{"collectSet","commentSet","user", "invtType"});
			 
			JSONArray json=JSONArray.fromObject(list,jsonConfig);
			response.getWriter().write(json.toString());
		}else {
			response.getWriter().write("failure");
		}
		
	}
	//把帖子按点赞个数排序
	@RequestMapping("/showHotInvitation")
	public void hotin(HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		List<Invitation> list=userService.hotin();
		if(list.size()!=0) {
			for(Invitation in:list) {	
				in.setCommentNum();
				in.setUser_id(in.getUser().getUser_id());
				in.setType_name(in.getInvtType().getType_name());
				in.setUser_name(in.getUser().getUser_name());
				in.setUser_phone(in.getUser().getUser_phone());
				System.out.println("帖子内容"+in.getIvtn_content());
				System.out.println("帖子话题 明"+in.getInvtType().getType_name());
			}
			 JsonConfig jsonConfig = new JsonConfig();		
			   jsonConfig.setIgnoreDefaultExcludes(false); 
			   jsonConfig.setExcludes(new String[]{"collectSet","commentSet","user", "invtType"});
			 
			JSONArray json=JSONArray.fromObject(list,jsonConfig);
			response.getWriter().write(json.toString());
		}else {
			response.getWriter().write("failure");
		}
		
		
	}	
	//帖子
	//写评论  写评论用户 id，帖子，评论内容
	@RequestMapping("/sendComment")
	public void addComment(HttpServletResponse response,String comment_content,Integer invt_id,Integer user_id) throws IOException {
		response.setCharacterEncoding("utf-8");
		userService.addComment(comment_content,invt_id,user_id);	
		List<Comment>list=userService.showComment(invt_id);
		if(list.size()!=0) {
			for(Comment in:list) {	
				User u=userService.selectById(in.getUser_id());
				in.setUser_name(u.getUser_name());
				in.setUser_phone(u.getUser_phone());
			}
			 JsonConfig jsonConfig = new JsonConfig();		
			   jsonConfig.setIgnoreDefaultExcludes(false); 
			   jsonConfig.setExcludes(new String[]{"invitation","replySet"});
			 
			JSONArray json=JSONArray.fromObject(list,jsonConfig);
			response.getWriter().write(json.toString());
		}else {
			response.getWriter().write("failure");
		}
		
	}
	//展示帖子的评论  给一个帖子id
	@RequestMapping("/getComment")
	public void showComment(HttpServletResponse response,Integer invt_id) throws IOException {
		response.setCharacterEncoding("utf-8");
		List<Comment>list=userService.showComment(invt_id);
		if(list.size()!=0) {
			for(Comment in:list) {	
				User u=userService.selectById(in.getUser_id());
				in.setUser_name(u.getUser_name());
				in.setUser_phone(u.getUser_phone());
			}
			 JsonConfig jsonConfig = new JsonConfig();		
			   jsonConfig.setIgnoreDefaultExcludes(false); 
			   jsonConfig.setExcludes(new String[]{"invitation","replySet"});
			 
			JSONArray json=JSONArray.fromObject(list,jsonConfig);
			response.getWriter().write(json.toString());
		}else {
			response.getWriter().write("failure");
		}
			
	}
	//回复帖子
//	@RequestMapping("/replyComment")
//	public void  
	// 用户关注话题save
	@RequestMapping("/followInv")
	public void followInv(HttpServletResponse response,Integer user_id,Integer type_id) throws IOException {
		response.setCharacterEncoding("utf-8");
		User user=userService.selectById(user_id);
		InvtType inty=userService.selectintyById(type_id);
		FollowType t=userService.selefollow(user_id,type_id);
		if(null!=t) {
			response.getWriter().write("followed");
		}else {
			userService.followIntype(user,inty);
			response.getWriter().write("success");
		}
		
		
	}
	//展示用户关注的话题列表show
	@RequestMapping("/getFollowType")
	public void showUserInvtType(HttpServletResponse response,Integer user_id) throws IOException {
		response.setCharacterEncoding("utf-8");
		List<InvtType>list=userService.getUserInvtType(user_id);
		if(list.size()==0) {
			response.getWriter().write("failure");
		}else {
			JsonConfig jsonConfig = new JsonConfig();		
			   jsonConfig.setIgnoreDefaultExcludes(false); 
			   jsonConfig.setExcludes(new String[]{"invitationSet","followTypeSet"});
			JSONArray json=JSONArray.fromObject(list,jsonConfig);
			response.getWriter().write(json.toString());
		}
	}
	
	
	
	
}
