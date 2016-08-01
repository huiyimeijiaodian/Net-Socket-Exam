package com.hand;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ExamOne {

	public static void main(String[] args) {
		new ReadByGet().start();
	}
	
}

//1. 使用 Http 的 Get 方式读取网络数据
class ReadByGet extends Thread{
	public void run(){
		try {
			URL url=new URL("http://files.saas.hand-china.com/java/target.pdf");
			URLConnection connection =url.openConnection();
			
			InputStream is=connection.getInputStream();
			
			fileCopyByBuff(is);//按字节读取并添加缓冲区复制文件
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//按字节读取并添加缓冲区复制文件
	public void fileCopyByBuff(InputStream in){
		try {
			BufferedInputStream bis=new BufferedInputStream(in);
			OutputStream fos=new FileOutputStream("http_target2.pdf");
			BufferedOutputStream bos=new BufferedOutputStream(fos);
			
			byte[] input =new byte[1024];
			while(bis.read(input)!=-1){
				bos.write(input);
			}
			bis.close();
			bos.close();
			fos.close();
			System.out.println("文件下载成功！已保存在本地工作区");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
