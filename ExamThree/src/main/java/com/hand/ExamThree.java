package com.hand;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

// import com.google.gson.JsonObject;

public class ExamThree {

	public static void main(String[] args) {
		new HttpRead().start();
	}
	
	
}
//1. 使用 Http 的 Get 方式读取网络数据
class HttpRead extends Thread{
	public void run(){
		try {
			URL url=new URL("http://hq.sinajs.cn/list=sz300170");
			URLConnection connection =url.openConnection();
			
			InputStream is=connection.getInputStream();
			InputStreamReader isr=new InputStreamReader(is,"gbk");
			BufferedReader br=new BufferedReader(isr);
			String line=null;
			StringBuffer buffer=new StringBuffer();
			while((line=br.readLine())!=null){
				buffer.append(line);
			}
			br.close();
			isr.close();
			is.close();
			System.out.println(buffer.toString());
			String[] firstStr= buffer.toString().split("\"");
			System.out.println(firstStr[1]);
			String[] strs=firstStr[1].split(",");
//			System.out.println("\n"+strs[0]);
			
			CreatXml(strs);//创建xml文件
			// CreatJson(strs);//创建json文件
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void CreatXml(String[] strs){
		//DOM操作创建XML文件
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.newDocument();
				Element root = document.createElement("xml");
				
				Element stock = document.createElement("stock");
				
				Element name = document.createElement("name");
				name.setTextContent(strs[0]);
				Element open = document.createElement("open");
				open.setTextContent(strs[1]);
				Element close = document.createElement("close");
				name.setTextContent(strs[2]);
				Element current = document.createElement("current");
				current.setTextContent(strs[3]);
				Element high = document.createElement("high");
				high.setTextContent(strs[4]);
				Element low = document.createElement("low");
				low.setTextContent(strs[5]);
				
				stock.appendChild(name);
				stock.appendChild(open);
				stock.appendChild(close);
				stock.appendChild(current);
				stock.appendChild(high);
				stock.appendChild(low);
				
				root.appendChild(stock);
				document.appendChild(root);
				
				//-------------
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty("encoding", "UTF-8");
				
				StringWriter writer = new StringWriter();
				transformer.transform(new DOMSource(document), new StreamResult(writer));
				System.out.println("xml结果："+writer.toString());
				
				transformer.transform(new DOMSource(document), new StreamResult(new File("newxml.xml")));
				System.out.println("写入成功！");
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			}
			
	}

	// //创建json文件
	//  public void CreatJson(String[] strs){
	//  	//基于gson.jar导入包创建json对象
	//  			JsonObject obj=new JsonObject();
	//  			obj.addProperty("name", strs[0]);
	//  			obj.addProperty("open", strs[1]);
	//  			obj.addProperty("close", strs[2]);
	//  			obj.addProperty("current", strs[3]);
	//  			obj.addProperty("high", strs[4]);
	//  			obj.addProperty("low", strs[5]);
				
	//  			System.out.println("创建的json对象：");
	//  			System.out.println(obj.toString());
	//  			try {
	//  				FileWriter fw=new FileWriter("hand.json");
	//  				BufferedWriter bw=new BufferedWriter(fw);
	//  				bw.write(obj.toString());
	//  				bw.flush();
	//  				bw.close();
	//  				fw.close();
	//  			} catch (IOException e) {
	//  				// TODO Auto-generated catch block
	//  				e.printStackTrace();
	//  			}
				
	//  }
}
