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
import org.w3c.dom.Element;


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
			t .transform ( new DOMSource (document ) ,new StreamResult( new File ("hand.xml" ))); 
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}
}


//class JsonC{
//	public JsonC(String[] res) {
//		// TODO Auto-generated constructor stub
//		JsonObject jo = new JsonObject() ;
//		JsonArray ja = new JsonArray () ;
//		JsonObject lan1 = new JsonObject () ;
//		lan1 .addProperty ( "name", res[0] );
//		lan1 .addProperty ( "open", res[1] );
//		lan1 .addProperty ( "close", res[2] );
//		lan1 .addProperty ( "current", res[3] );
//		lan1 .addProperty ( "high", res[4] );
//		lan1 .addProperty ( "low", res[5] );
//
//		ja.add ( lan1) ;
//		jo .add ( "stock", ja );
//		
//		
//		
//		File file = new File("hand.json");
//		FileOutputStream fos;
//		try {
//			fos = new FileOutputStream(file);
//			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
//			osw.write(jo.toString());
//			osw.flush();
//			osw.close();
//			fos.close();
//
//
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//	}
//}


public class App 
{
	public static void main( String[] args )
	{

		Tell re = new Tell();


		try {
			String[] res = re.Mss();
			if(new XmlTrans(res) != null){
				System.out.println("XML文件创建成功");
			}
//			if (new JsonC(res)!=null) {
//				System .out . println("Json文件创建成功");
//
//			}



		} catch (IOException e) {
			e.printStackTrace();
		}





	}
}	
