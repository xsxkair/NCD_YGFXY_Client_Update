package org.com.xsx.DownService;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class DownService extends Service<Void>{

	@Override
	protected Task<Void> createTask() {
		// TODO Auto-generated method stub
		return new download();
	}
	
	class download extends Task<Void>{

		@Override
		protected Void call() throws Exception {
			// TODO Auto-generated method stub
			
			URLConnection connection = new URL("http://123.57.94.39:8080/NCD_YGFXY_Server/Image/logo.png").openConnection();
			InputStream in = connection.getInputStream();
			FileOutputStream out = new FileOutputStream("logo.png"); 
			
			System.out.println(connection.getContentLength());
			
			byte[] buffer = new byte[4 * 1024];  
		      int read;  
		      while ((read = in.read(buffer)) > 0) {  
		    	  out.write(buffer, 0, read);  
		           }
		      out.close();  
		        in.close();
			
			return null;
		}
		
	}

}
