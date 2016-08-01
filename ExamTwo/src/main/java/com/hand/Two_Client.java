package com.hand;

import java.io.*;  
import java.net.*;  
  
public class Two_Client{  
    Socket socket;  
    BufferedInputStream in;  
    BufferedOutputStream out;  
    
    public static void main(String[] args)   { 
        new Two_Client();  
    }  
    
    public Two_Client()  {  
        try   {  
            socket = new Socket("127.0.0.1", 8099);  
              
            in = new BufferedInputStream(socket.getInputStream());  
            out = new BufferedOutputStream(socket.getOutputStream());  
            FileInputStream fis=new FileInputStream("target.pdf");
			BufferedInputStream bis=new BufferedInputStream(fis);
			
			byte[] input =new byte[1024];
			while(bis.read(input)!=-1){
				out.write(input);
			}
            
			bis.close();
			fis.close();
            out.close();  
            in.close();  
              
            socket.close();  
            System.out.println("文件发送成功！");
        } catch (IOException e) {  
        	
        }  
    }  
  
   
}  
