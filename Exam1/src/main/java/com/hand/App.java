package com.hand;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Scanner;



public class App 
{
	 public static void  Get(String sPath) throws IOException{  
	        URL url = new URL("http://files.saas.hand-china.com/java/target.pdf");    
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
	        InputStream is = conn.getInputStream();    
	        BufferedInputStream bis = new BufferedInputStream(is);
	        File savepath = new File(sPath);  
	        if(!savepath.exists()){  
	        	savepath.mkdir();  
	        }  
	        
	        
	        
	        File file = new File(savepath+File.separator+"target.pdf");  
	        FileOutputStream fos = new FileOutputStream(file);
	        BufferedOutputStream bos = new BufferedOutputStream(fos);
	        int len = 0;    
	        byte[] input = new byte[1024] ;
	        while((len=bis.read(input))!=-1){
	        	bos.write(input,0,len);
	        }
	        bos.close();
	        fos.close();
	        bis.close();
	        is.close();
	        System.out.println("下载成功");   
	    } 
	   
	    public static void main(String[] args) {
	    	  
		    	Scanner scan = new Scanner(System.in);
		    	System.out.print("请输入保存路径：");
		    	String p = scan.next();
	        try {
				Get(p);
			} catch (IOException e) {
				e.printStackTrace();
			} 
	    }  
}
