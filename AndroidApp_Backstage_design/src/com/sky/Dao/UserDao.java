package com.sky.Dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
import com.sky.Bean.Word;

@Repository
public class UserDao {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public boolean saveUser(User user) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		Transaction tran =session.beginTransaction();
		try {
			session.save(user);
			tran.commit();
			session.close();
			return true;
		}catch(Exception e) {
			System.out.print(e);
			tran.rollback();
			session.close();
			return false;
		}
	} 
	public User selctById(int user_id) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("select u from User u where u.user_id=?");
		query.setParameter(0, user_id);
		User user=(User) query.uniqueResult();
		return user;
	}
	public User checkPhone(String user_phone) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("select u from User u where u.user_phone=?");
		query.setParameter(0, user_phone);
		User user=(User)query.uniqueResult();
		return user;	
	}
	public boolean checkPwd(String user_phone,String user_pwd) {
		Session session=sessionFactory.getCurrentSession();
		User user=checkPhone(user_phone);
		boolean a=user.getUser_pwd().equals(user_pwd);
		if(a) {
			return true;
		}else {
			return false;
		}
	}
	//修改密码
	public void updatePswd(String password,Integer user_id) {
		Session session=sessionFactory.getCurrentSession();
		User user=selctById(user_id);
		user.setUser_pwd(password);
	    session.update(user);
	}
	//修改头像
	public void updateHeader(User user){
		Session session=sessionFactory.getCurrentSession();
		session.update(user);
	}
	//心灵大师
	public void heartSenior(Integer user_id,String master_profile,String detailintroduction) {
		Session session=sessionFactory.getCurrentSession();
		System.out.println("进入页面");
		System.out.println("dao"+user_id);
		User user=selctById(user_id);
		System.out.println("用户的名字"+user.getUser_id());
		user.setUser_state("senior");
		user.setMaster_profile(master_profile);
		user.setDetailintroduction(detailintroduction);
		session.update(user);
	}
	//查询所有的心灵大师
	public List selectSenior() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("select u from User u where u.user_state=?");
		query.setParameter(0,"senior");
		return query.list();
	}
	//写信
	public boolean writeLetter(TreeHoles letter,int user_id) {
		Session session=sessionFactory.getCurrentSession();
		User user=selctById(user_id);
		user.getLetterSet().add(letter);
		letter.setUser(user);
		letter.setIsShowInTheTree("1");
		session.save(user);
		return true;
	}
	//树洞中信的总个数
	public int sumLetter() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from TreeHoles");
		
		return query.list().size();
	}
	//取信
	public TreeHoles readLetter(Integer user_id) {
		Session session=sessionFactory.getCurrentSession();	
		 Query query=session.createQuery("SELECT t FROM TreeHoles t WHERE t.user.user_id!=? and t.isShowInTheTree=?");
		 query.setParameter(0,user_id).setParameter(1,"1");
		List treeholes =(List<TreeHoles>)query.list();
		if(treeholes.size()!=0) {
		 int rand = new Random().nextInt(treeholes.size()); 
		 
		 TreeHoles letter= (TreeHoles) treeholes.get(rand);
		 System.out.println(letter.getLetter_id()+"信id");
		TreeHoles letter2= session.get(TreeHoles.class,letter.getLetter_id());
		letter2.setIsShowInTheTree("0");
		session.update(letter2);
		 return letter;
		 }else {
			 return null ; 
		 }
		
	}
	//查看我的信件
	public List<TreeHoles> read(Integer user_id) {
		List<TreeHoles> set=new ArrayList<TreeHoles>();
		Session session=sessionFactory.getCurrentSession();
		//User user=session.load(User.class,user_id);
		Query query=session.createQuery("select u from TreeHoles u where u.user.user_id=?");
		query.setParameter(0,user_id);
		set=query.list();
		return set;	
	}
	//slectById信件
	public TreeHoles selectById(Integer letter_id) {
		Session session=sessionFactory.getCurrentSession();	
		TreeHoles letter=session.get(TreeHoles.class,letter_id);
		return letter;
	}
	//从树洞中移除信件
	public void removeLetter(Integer letter_id) {
		Session session=sessionFactory.getCurrentSession();	
		TreeHoles letter=selectById(letter_id);
		letter.setIsShowInTheTree("0");
		session.update(letter);//???
		
	}
	//从树洞中删除信件
	public void delete(Integer user_id,Integer letter_id) {
		Session session=sessionFactory.getCurrentSession();	
		User t=session.get(User.class,user_id);
		Set set=new HashSet();
		set=t.getLetterSet();
//		if(null!=set) {
//			Iterator<TreeHoles> it = set.iterator();  
//			while (it.hasNext()) {  
//			 TreeHoles letter= it.next();  
//			 if(letter.getLetter_id()==letter_id) {  
//				 it.remove();
//			 }
//			}  
//		}
		for(Object tree:set) {
			TreeHoles tt=(TreeHoles)tree;
			if(tt.getLetter_id()==letter_id) {
				System.out.println(tt.getLetter_content());
				set.remove(tt);
				
				session.delete(tt);
				
				break;
			}
			
			
		}
		t.setLetterSet(set);
		
		session.update(t);
		
	}
	//放回树洞
	public void backLetter(Integer letter_id) {
		Session session=sessionFactory.getCurrentSession();	
		TreeHoles t=session.get(TreeHoles.class,letter_id);
		t.setIsShowInTheTree("1");
		session.update(t);
		
	}
	//回信
	public void replyLetter(Integer user_id,Integer letter_id,String letter_content) {
		Session session=sessionFactory.getCurrentSession();
		TreeHoles tree=session.load(TreeHoles.class,letter_id);
		LetterReply letterreply=new LetterReply();
		letterreply.setUser_id(user_id);
		letterreply.setLetter_content(letter_content);
		 Date d = new Date();
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     letterreply.setLetter_time(sdf.format(d));
		tree.getLetterReplySet().add(letterreply);
		letterreply.setLetter(tree);
		session.save(tree);			
	}
	//查看回信
	public Set readReplyLetter(Integer letter_id){
		Session session=sessionFactory.getCurrentSession();
		TreeHoles t=session.load(TreeHoles.class,letter_id);
		Set set=new HashSet<LetterReply>();
		set=t.getLetterReplySet();
		return set;
	}
	//删除回信
	public boolean deleteReplyLetter(Integer letter_reply_id,Integer letter_id){
		Session session=sessionFactory.getCurrentSession();
		LetterReply letterReply=session.get(LetterReply.class,letter_reply_id);
		if(letterReply!=null) {
		TreeHoles t=session.get(TreeHoles.class,letter_id);
		Set set=new HashSet<LetterReply>();
		set=t.getLetterReplySet();
		Iterator i=set.iterator();
		while(i.hasNext()) {
			LetterReply r=(LetterReply) i.next();
			if(r.getLetter_reply_id()==letter_reply_id) {
				i.remove();
			}
		}
		t.setLetterReplySet(set);
		session.update(t);
		session.delete(letterReply);
		System.out.println(t.getLetterReplySet());
		System.out.println(t.getLetterReplySet().size());
		return true;
		}else {
			return false;
		}
		
	}
	//插入音频
	public void inmp3(Mmsic m) {
		Session session=sessionFactory.getCurrentSession();
		session.save(m);
	}
	//传音频
	public List chan() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Mmsic");
		return query.list();
		
	}
	//保存文字呐喊
    public void saveTextShout(User user,TextShout textshout) {
    	Session session=sessionFactory.getCurrentSession();
    	user.getTextShoutSet().add(textshout);
    	textshout.setUser(user);
    	session.update(user);
    	
    }
//    //查询文字呐喊
//    public void showTextShout(Integer user_id) {
//    	Session session=sessionFactory.getCurrentSession();
//    	User user=selctById(user_id);
//    	
//    }
//	
    //返回视频信息
    public List video() {
    	Session session=sessionFactory.getCurrentSession();
    	Query query=session.createQuery("from Video");
    	return query.list();
    }
	//返回每日一言
    public Word showWord(String time) {
    	Session session=sessionFactory.getCurrentSession();
    	Query query=session.createQuery("select u from Word u where u.time=?");
    	query.setParameter(0,time);
    	Word word=(Word)query.uniqueResult();
        return word;
    }
	//添加话题
    public void saveTopic(String type_name,String type_image,String type_desc) {
    	Session session=sessionFactory.getCurrentSession();
    	InvtType intype=new InvtType();
    	intype.setType_desc(type_desc);
    	intype.setType_image(type_image);
    	intype.setType_name(type_name);
    	session.save(intype);
    }
    //展示话题
   public List showTopic() {
	   Session session=sessionFactory.getCurrentSession();
	   Query query=session.createQuery("from InvtType");
	   List list=query.list();
	   return list;	   
	   
   }
	//查图片地址
   public String pic(Integer type_id) {
	   Session session=sessionFactory.getCurrentSession();
	   Query query=session.createQuery("select u.type_image from InvtType u where u.type_id=?");
	   query.setParameter(0,type_id);
	String a= (String) query.uniqueResult();
	return a;
	   
   }
   //根据typeid查询 话题
   public InvtType selectintypeById(Integer type_id) {
	   Session session=sessionFactory.getCurrentSession();
	   Query query=session.createQuery("select u from InvtType u where u.type_id=?");
	   query.setParameter(0,type_id);
	  InvtType t=(InvtType) query.uniqueResult();
	  return t;
	   
   }
   //根据帖子id查询帖子
   public Invitation selectInvitationById(Integer invt_id) {
	   Session session=sessionFactory.getCurrentSession();
	   Query query=session.createQuery("select u from Invitation u where u.ivtn_id=?");
	   query.setParameter(0,invt_id);
	  Invitation t=(Invitation) query.uniqueResult();
	  return t;
	   
   }
   
   //写帖子
	public void invitation(Integer type_id,Integer user_id,Invitation invitation) {
		   Session session=sessionFactory.getCurrentSession();
		  User user= selctById(user_id);
		 InvtType in= selectintypeById(type_id);
		 
		  invitation.setInvtType(in);
		  invitation.setUser(user);
		  invitation.setUser_id(user_id);
		  invitation.setCommentNum();
		  invitation.setType_name(in.getType_name());
		  Date date=new Date();
		 SimpleDateFormat simpleDataFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time= simpleDataFormat.format(date);
		 invitation.setIvtn_time(time);
		  in.getInvitationSet().add(invitation);
		  user.getInvitationSet().add(invitation);
		  session.update(in);
		  session.save(user);
		   
	}
	//查询用户的帖子
	public List showInvitation(Integer user_id) {
		
		 Session session=sessionFactory.getCurrentSession();
		 User user= selctById(user_id);
		 Query query =session.createQuery("select u from Invitation u where u.user=?");
		 query.setParameter(0,user);
		return  query.list();
		 
	}
	//查询帖子的图片
	public String inp(Integer Ivtn_id) {
		 Session session=sessionFactory.getCurrentSession();
		 Query query =session.createQuery("select u.image from Invitation u where u.ivtn_id=?");
		 query.setParameter(0,Ivtn_id);
		return  (String)query.uniqueResult();
	}
	//查询所有的帖子
	public List showallInvitation() {
		Session session=sessionFactory.getCurrentSession();
		
		 Query query =session.createQuery("from Invitation");
		
		return  query.list();
	} 
	//查询某个话题的帖子
	public Set showInvitationByType(Integer type_id) {
		Session session=sessionFactory.getCurrentSession();
		InvtType intype=session.get(InvtType.class,type_id);
		return intype.getInvitationSet();
	}
	//所有帖子从小到大排
	public List newin() {
		Session session=sessionFactory.getCurrentSession();
		 Query query =session.createQuery("from Invitation u order by u.ivtn_id desc");
		 return  query.list();
		
	}
	//帖子点赞
	public Invitation dianzan(Integer invt_id) {
		Session session=sessionFactory.getCurrentSession();
		Invitation in=session.load(Invitation.class,invt_id);
		int num=in.getIvtn_PraiseNum();
		in.setIvtn_PraiseNum(num+1);
		session.update(in);
		return in;
		
	}
	//所有帖子按点赞个数排序
	public List hotin() {
		Session session=sessionFactory.getCurrentSession();
		 Query query =session.createQuery("from Invitation u order by u.ivtn_PraiseNum desc");
		 return  query.list();
	}
	//写评论
	public void addComment(String comment_content,Integer ivtn_id,Integer user_id) {
		Session session=sessionFactory.getCurrentSession();
		Comment comment=new Comment();
		Invitation in=session.get(Invitation.class, ivtn_id);
		in.getCommentSet().add(comment);
		comment.setInvitation(in);
		comment.setUser_id(user_id);
	   Date date=new Date();
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 	comment.setComment_time(sdf.format(date));
		comment.setComment_content(comment_content);
		session.update(in);	
	}
	//展示贴子评论
	public List showComment(Integer intv_id) {
		Session session=sessionFactory.getCurrentSession();
		Invitation in=session.get(Invitation.class, intv_id);
		Query query=session.createQuery("select u from Comment u where u.invitation=?");
		query.setParameter(0,in);
		return query.list();
		
	}
	//查询用户是否关注帖子
	public FollowType selefollow(Integer user_id,Integer type_id) {
		Session session=sessionFactory.getCurrentSession();
		InvtType t=selectintypeById(type_id);
		User user= selctById(user_id);
		Query query=session.createQuery("select u from FollowType u where u.user=? and u.invtType=?");
		query.setParameter(0,user);
		query.setParameter(1,t);
		return (FollowType) query.uniqueResult();
	}
	//用户关注帖子
	public void followIntype(User user,InvtType inty) {
		Session session=sessionFactory.getCurrentSession();
	   FollowType followtype=new FollowType();
	   followtype.setUser(user);
	   followtype.setInvtType(inty);
	   user.getFollowTypeSet().add(followtype);
	   inty.getFollowTypeSet().add(followtype);
	   session.update(user);
	   session.update(inty);
	   
	}
	//查询用户关注的帖子
	public List getUserInvtType(Integer user_id) {
		Session session=sessionFactory.getCurrentSession();
		List<InvtType> list=new ArrayList<InvtType>();
		User user=session.load(User.class,user_id);
		Set<FollowType> set=user.getFollowTypeSet();
		for(FollowType t:set) {
			list.add(t.getInvtType());
		}
		return list;
	}
	
	
	
		
}
