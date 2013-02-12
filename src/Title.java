import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Title {
	static int count=0;
   public static void main(String argv[]) {
 
    try {
 
	SAXParserFactory factory = SAXParserFactory.newInstance();
	SAXParser saxParser = factory.newSAXParser();
	
	DefaultHandler handler = new DefaultHandler() {
 
	boolean btitle = false;
	boolean btext = false;
	boolean bpage = false;
	boolean bid = false;
	//boolean bid_check = true;
	String title = new String();
    String id = new String("");
    String str;
    File file = new File("/home/santoshkosgi/Downloads/Wiki/title_index.txt");
    BufferedWriter outer = new BufferedWriter(new FileWriter(file));
	public void startElement(String uri, String localName,String qName, 
                Attributes attributes) throws SAXException {
 
		//System.out.println("Start Element :" + qName);
 
		if (qName.equalsIgnoreCase("TITLE")) {
			btitle = true;
		}
		if (qName.equalsIgnoreCase("ID")) {
			bid = true;
		}
		if (qName.equalsIgnoreCase("TEXT")) {
			btext = true;
		}
		if (qName.equalsIgnoreCase("PAGE")) {
			//System.out.println("start of page");
			bpage = true;
		}
	}
 
	public void endElement(String uri, String localName,
		String qName) throws SAXException {
		if (qName.equalsIgnoreCase("TEXT")) {
			//System.out.println(id.split(" ")[1]);
		    //System.out.println(parse.parseInfoBox(x));
			id = id.split(" ")[1];
			str = id+"|"+title+"\n";
			try {
				outer.write(str);
				System.out.println(count++);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			btext = false;
			id = "";
			
		}
		if (qName.equalsIgnoreCase("PAGE")) {
			bpage = false;
		}
		
		if (qName.equalsIgnoreCase("FILE")) {
		}
	}
 
	public void characters(char[] ch, int start, int length) throws SAXException {
 
		if (btitle) {
			title=new String(ch, start, length);
			btitle = false;
		}
 
		if (btext) {
		}
		if (bid) {
			//System.out.println("TITLE: " + new String(ch, start, length));
				id =id + " " +new String(ch, start, length);
				bid = false;
			}
 
	}
 
     };
 
       saxParser.parse("/home/santoshkosgi/Downloads/enwiki-latest-pages-articles.xml", handler);
 
     } catch (Exception e) {
       e.printStackTrace();
     }
 
   }
 
}