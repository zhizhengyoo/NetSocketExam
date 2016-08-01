package com.hand;
import java.io.*;
import java.net.*;

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
import org.w3c.dom.Element;;

class Tell{
	public String[] Mss() throws IOException {
		URL url;
		url = new URL("http://hq.sinajs.cn/list=sz300170");
		URLConnection conn = url.openConnection();
		InputStream is = conn.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br =  new BufferedReader(isr);

		String line ;
		StringBuilder builder = new StringBuilder();
		while((line=br.readLine())!=null){
			builder.append(line);
		}
		br.close();
		isr.close();
		is.close();
		String str = builder.toString(); 
		String[] mess = str.split(",");
		String[] name = mess[0].split("\"");
		mess[0] = name[1];

		return mess;
	}
}

class XmlTrans{
	public XmlTrans(String[] res) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory. newInstance ();
		DocumentBuilder db;

		try {
			db = dbf .newDocumentBuilder ();
			Document document = db .newDocument () ;
			Element root = document .createElement( "stock") ;
			Element name1 = document .createElement("name");
			Element open = document .createElement( "open");
			Element close = document .createElement( "close") ;
			Element current = document .createElement( "current") ;
			Element high = document .createElement( "high") ;
			Element low = document .createElement( "low") ;

			name1 .setTextContent (res[0]);
			open.setTextContent (res[1]);
			close .setTextContent (res[2]);
			current.setTextContent (res[3]);
			high .setTextContent (res[4]);
			low .setTextContent (res[5]);

			root.appendChild(name1);
			root.appendChild(open);
			root.appendChild(close);
			root.appendChild(current);
			root.appendChild(high);
			root.appendChild(low);
			document.appendChild(root);

			TransformerFactory tf = TransformerFactory . newInstance() ;
			Transformer t;
			t = tf .newTransformer ();
			StringWriter writer =new StringWriter ();
			t.transform(new DOMSource(document),new StreamResult(writer));
//			System.out.println(writer.toString());            
			t .transform ( new DOMSource (document ) ,new StreamResult( new File ("hand.xml" ))) ;  //这种方法在工程目录内创建了一个XML文件

		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}
}

public class App 
{
	public static void main( String[] args )
	{

		Tell re = new Tell();
	

		try {
			String[] res = re.Mss();
	        new XmlTrans(res);


		} catch (IOException e) {
			e.printStackTrace();
		}





	}
}	
