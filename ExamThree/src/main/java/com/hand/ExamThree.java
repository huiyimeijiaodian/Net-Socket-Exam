package com.hand;
import java.io.BufferedReader;
import java.io.File;
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
			String[] strs= buffer.toString().split(",");
			strs[0]=strs[0].substring(21);
			strs[strs.length-1]=strs[strs.length-1].substring(0,2);
//			System.out.println("\n"+strs[0]);
			
			CreatXml(strs);//创建xml文件
			
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
}
