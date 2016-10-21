package org.com.xsx.DownService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.codec.digest.DigestUtils;
import org.com.xsx.Data.SoftVersionData;
import org.com.xsx.Domain.SoftVersion;
import org.com.xsx.Tools.HibernateDao;
import org.com.xsx.Tools.HibernateSessionBean;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class DownService extends Service<Boolean>{

	@Override
	protected Task<Boolean> createTask() {
		// TODO Auto-generated method stub
		return new download();
	}
	
	class download extends Task<Boolean>{

		@Override
		protected Boolean call() throws InterruptedException {
			// TODO Auto-generated method stub
			Boolean status = true;
			URLConnection connection = null;
			InputStream in = null;
			FileOutputStream out = null;
			SoftVersion softVersion = null;
			
			int filesize = 0;

			double progress = 0;
			
			try {
				updateMessage("��ʼ������...");
				
				updateProgress(2, 100);
				HibernateSessionBean.GetInstance().Hibernate_Init();
				
				
				updateMessage("������...");
				softVersion = (SoftVersion) HibernateDao.GetInstance().queryOne("select s from SoftVersion as s WHERE s.softname='YGFXY_Client_Patch'", null);
				if(softVersion.getVersion() <= SoftVersionData.GetInstance().getVersion()){
					updateMessage("�������°汾");
					updateProgress(100, 100);
					return true;
				}
				
				connection = new URL("http://123.57.94.39:8080/NCD_YGFXY_Server/updatefile/UPDATE.zip").openConnection();
				connection.setConnectTimeout(5000);
				filesize = connection.getContentLength();
				updateProgress(6, 100);

				in = connection.getInputStream();
				updateProgress(15, 100);
				
				out = new FileOutputStream("UPDATE.zip");
				updateProgress(20, 100);
				
				updateMessage("�������ظ����ļ�������");
				
				byte[] buffer = new byte[4 * 1024];
				int read;
				
				while ((read = in.read(buffer)) > 0) {
					out.write(buffer, 0, read);
					progress += read;
					updateProgress((progress/filesize)*50 + 20, 100);
				}
				
				updateMessage("���سɹ�");
				
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
				updateMessage("����ʧ�ܣ������ж�");
				updateProgress(100, 100);
				status = false;
			}
			
			try {
				out.close();
				in.close();
					
				if(!status){
					return false;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				updateMessage("���󣬸���ʧ�ܣ�");
				updateProgress(100, 100);
				return false;
			}

			try {
				updateMessage("����У����³���");
				
				
				
				in = new FileInputStream("UPDATE.zip");
				String md5 = DigestUtils.md5Hex(in);

				if(!softVersion.getMD5().equals(md5)){
					updateMessage("У����󣬸���ʧ�ܣ�");
					updateProgress(100, 100);
					return false;
				}
				
				updateMessage("У��ɹ���");
				updateProgress(80, 100);
				
			} catch (Exception e) {
				// TODO: handle exception
				updateMessage("���󣬸���ʧ�ܣ�");
				updateProgress(100, 100);
				return false;
			}
			
			updateMessage("��ѹ���ݰ���");
			//ZipUtil
			try {
				unZipFiles("UPDATE.zip", "./");
			} catch (Exception e) {
					// TODO: handle exception
				updateMessage("��ѹʧ�ܣ�����ʧ�ܣ�");
				updateProgress(100, 100);
				return false;
			}
			
			updateMessage("���³ɹ�������������");
			
			try {
				File deletefile = new File("UPDATE.zip");
				deletefile.delete();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
			Thread.sleep(1000);
			System.exit(0);
			return true;
		}
		
		/** 
	     * ��ѹ��ָ��Ŀ¼ 
	     * @param zipPath 
	     * @param descDir 
	     * @author isea533 
		 * @throws IOException 
		 * @throws ZipException 
	     */  
	    private void unZipFiles(String zipPath,String descDir) throws ZipException, IOException{  
	        unZipFiles(new File(zipPath), descDir);  
	    }  
	    /** 
	     * ��ѹ�ļ���ָ��Ŀ¼ 
	     * @param zipFile 
	     * @param descDir 
	     * @author isea533 
	     * @throws IOException 
	     * @throws ZipException 
	     */ 
	    private void unZipFiles(File zipFile,String descDir) throws ZipException, IOException{
	    	
	    	int size = 0;
	    	int index = 0;
	    	float temp;
	    	
	        File pathFile = new File(descDir);  
	        if(!pathFile.exists()){  
	            pathFile.mkdirs();  
	        }
	        
	        ZipFile zip = new ZipFile(zipFile);
	        size = zip.size();
	        
	    	for (Enumeration entries = zip.entries(); entries.hasMoreElements() ;) {
	    		ZipEntry entry = (ZipEntry)entries.nextElement();
	        	
	    		String zipEntryName = entry.getName();  
	    		InputStream in = zip.getInputStream(entry);  
	    		String outPath = (descDir+zipEntryName).replaceAll("\\*", "/");
		        
	    		updateMessage(zipEntryName);
	    		index++;
	    		temp = index;
	    		temp /= size;
	    		updateProgress(temp*20+80, 100);
	    		
	    		//�ж�·���Ƿ����,�������򴴽��ļ�·��  
	    		File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));  
	    		if(!file.exists()){
	    			file.mkdirs();  
	    		}  
	    		//�ж��ļ�ȫ·���Ƿ�Ϊ�ļ���,����������Ѿ��ϴ�,����Ҫ��ѹ  
	    		if(new File(outPath).isDirectory()){  
	    			continue;  
	    		}
		            
	    		//����ļ�·����Ϣ  
	    		System.out.println(outPath);  
		              
	    		OutputStream out = new FileOutputStream(outPath);  
				byte[] buf1 = new byte[1024];  
				int len;  
				while((len=in.read(buf1))>0){  
					out.write(buf1,0,len);  
				}  
				in.close();  
				out.close();  
	    	}
		        	
	        zip.close();
	    }
	}

}
