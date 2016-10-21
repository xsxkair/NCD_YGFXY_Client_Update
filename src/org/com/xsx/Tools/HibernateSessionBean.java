package org.com.xsx.Tools;

import org.hibernate.*;
import org.hibernate.cfg.*;


public class HibernateSessionBean
{
	private static HibernateSessionBean S_HibernateSessionBean = null;
	
	private static Configuration conf = null;  
	private static SessionFactory factory = null;  
	private static Session session = null;  

	
	private HibernateSessionBean(){
		
	}
	
    public static HibernateSessionBean GetInstance(){
    	if(S_HibernateSessionBean == null){
    		S_HibernateSessionBean = new HibernateSessionBean();
    	}
    	
    	return S_HibernateSessionBean;
    }
    
	public void Hibernate_Init()
	{

		//采用默认的hibernate.cfg.xml来启动一个Configuration的实例
		conf = new Configuration().configure();

		//由Configuration的实例来创建一个SessionFactory实例
		factory = conf.buildSessionFactory();

	}

		/** 
	     * 获得Session连接 
	     * */  
	    public Session getSession(){  
	        session = factory.openSession();  
	        return session;  
	    }  
	    /** 
	     * 关闭session连接 
	     * */  
	    public void closeSession(){  
	        if(session!=null)  
	            session.close();  
	    }  
}