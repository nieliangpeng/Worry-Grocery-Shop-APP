package com.sky.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sky.Bean.FollowType;
import com.sky.Bean.Invitation;
import com.sky.Bean.InvtType;
import com.sky.Bean.Mmsic;
import com.sky.Bean.TextShout;
import com.sky.Bean.TreeHoles;
import com.sky.Bean.User;
import com.sky.Bean.Word;
import com.sky.Dao.UserDao;

import com.sky.common.indexPage;

import jdk.nashorn.internal.runtime.UserAccessorProperty;





@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Transactional
	public boolean saveUser(User user, MultipartFile user_image, HttpServletRequest request) throws IOException {
		// TODO Auto-generated method stub
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
			File file = new File(dir2, user.getUser_image());
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(user_image.getBytes());
			fos.flush();
			fos.close();
		}
		user.setUser_image(realPath+"upload/user/"+user.getUser_phone()+"/"+user_image.getOriginalFilename());
		user.setUser_desc("");
		user.setUser_state("normal");
		boolean bool = userDao.saveUser(user);
		return bool;
	}
	@Transactional
	public User selectByPhone(String user_phone) {
		User user= userDao.checkPhone(user_phone);
		if(null!=user) {
			return user;
		}else {
			return null;
		}
		
	}
	@Transactional
	public User selectById(int user_id) {
		return userDao.selctById(user_id);
	}
	@Transactional
	public boolean checkPhone(String user_phone) {
		User user=userDao.checkPhone(user_phone);
		if(null!=user) {
			return true;
		}else {
			return false;
		}
		
	}
	@Transactional
	public boolean checkPwd(String user_phone,String user_pwd) {
		boolean c=userDao.checkPwd(user_phone,user_pwd);
		return c;
		
	}
	@Transactional
	public void  updatePswd(String password,Integer user_id) {
		userDao.updatePswd(password, user_id);
	}
	//修改头像
	@Transactional
	public void updateHeader(User user){
		userDao.updateHeader(user);
		
	}
	//心灵大师
	@Transactional
	public void heartSenior(Integer user_id,String master_profile,String detailintroduction) {
		System.out.println("service"+user_id);
		userDao.heartSenior(user_id,master_profile,detailintroduction);
	}
	//查询所有的心灵大师
	@Transactional
	public List selectSenior() {
		return userDao.selectSenior();
	}
	//写信
	@Transactional
	public boolean writeLetter(TreeHoles letter,int user_id){
		boolean a=userDao.writeLetter(letter,user_id);	
		return a;		
	}
	//取信
	@Transactional
	public 	TreeHoles readLetter(int id) {
		TreeHoles treeHoles=userDao.readLetter(id);
		return treeHoles;
        
	}
	@Transactional
	//查看我的信件
	public List<TreeHoles> read(Integer user_id) {
		System.out.println("diyi");
		List set=userDao.read(user_id);
		System.out.println("diyi2");
		return set;
	}
	//移除树洞
	@Transactional
	public void removeLetter(Integer letter_id) {
		userDao.removeLetter(letter_id);
	}
	//从树洞中删除
	@Transactional
	public void delete(Integer user_id,Integer letter_id) {
		userDao.delete(user_id,letter_id);
	}
	//放回树洞
	@Transactional
	public void backLetter(Integer letter_id) {
		userDao.backLetter(letter_id);
	}
	//回信
	@Transactional
	public void replyLetter(Integer user_id,Integer letter_id,String letter_content) {
		userDao.replyLetter(user_id,letter_id,letter_content);
	}
	//查看回信
	@Transactional
	public Set readReplyLetter(Integer letter_id) {
		Set set=userDao.readReplyLetter(letter_id);
		return set;
	}
	//删除回信
	@Transactional
	public  boolean deleteReplyLetter(Integer letter_reply_id,Integer letter_id){
		return userDao.deleteReplyLetter( letter_reply_id,letter_id);
		
	}
	//插入音频
	@Transactional
	public void inmp3(Mmsic m) {
		userDao.inmp3(m);
	}
	//查询音频
	@Transactional
	public  List chan() {
		return userDao.chan();
	}
	@Transactional
	//保存文字呐喊
   public void saveTextShout(User user,TextShout textshout) {
	    userDao.saveTextShout(user,textshout);
   }
	//查询文字呐喊
	@Transactional
	public void showTextShout(Integer user_id) {
		//Set set=userDao.showTextShout( user_id);
	}
	//返回视频
	@Transactional
	public List showTv() {
		List list=userDao.video();
		return list;
	}
	//返回word每日一言
	@Transactional
	public Word showWord(String time) {
		return userDao.showWord(time);
	}
	//添加话题
	@Transactional
	public void saveTopic(String type_name,String type_image,String type_desc) {
		userDao.saveTopic(type_name, type_image, type_desc);
	}
	//根据话题id查询话题
	@Transactional
	public InvtType selectintyById(Integer type_id) {
		return userDao.selectintypeById(type_id);
	}
	//展示所有话题
	@Transactional
	public List showTopic() {
		return userDao.showTopic();
	}
	//type_id 查图片路径
	@Transactional
	public String pic(Integer type_id) {
		return userDao.pic(type_id);
	}
	//保存帖子
	@Transactional
	public void invitation(Integer type_id,Integer user_id,Invitation invitation) {
		userDao.invitation(type_id,user_id,invitation);
	}
	@Transactional
	//查询用户的帖子
    public List showInvitation(Integer user_id) {
    	return userDao.showInvitation(user_id);
    }
	//查询帖子的图片
	@Transactional
	public  String inp(Integer Ivtn_id) {
		return userDao.inp(Ivtn_id);
	}
	@Transactional
	//查询所有的帖子
	public List showallInvitation() {
		return userDao.showallInvitation();
	}
	//查询某一个话题的所有帖子
	@Transactional
	public Set showInvitationByType(Integer type_id) {
		return userDao.showInvitationByType(type_id);
	}
	//按id大小排序
	@Transactional
	public List newin() {
		return userDao.newin();
	}
	//帖子点赞
	@Transactional
	public Invitation dianzan(Integer invt_id) {
	   return userDao.dianzan(invt_id);
	}
	//按点赞个数排序
	@Transactional
	public List hotin() {
		return userDao.hotin();
	}
	@Transactional
	//写评论
	public void addComment(String comment_content,Integer Ivtn_id,Integer user_id) {
		userDao.addComment(comment_content,Ivtn_id,user_id);
	}
	@Transactional
	//展示帖子的评论
	public List showComment(Integer intv_id) {
	  return userDao.showComment(intv_id);
	}
	//查询用户是否关注了帖子
	@Transactional
	public FollowType selefollow(Integer user_id,Integer type_id) {
		return userDao.selefollow(user_id, type_id);
	}
	//用户关注帖子
	@Transactional
	public void followIntype(User user,InvtType inty) {
		userDao.followIntype(user,inty);
	}
	//查询用户关注的帖子
	@Transactional
	public List getUserInvtType(Integer user_id) {
		return userDao.getUserInvtType(user_id);
	}

}
