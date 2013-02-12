import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class ReadXMLFile {
	static int count=0;
	static int file_name=0;
    static Stemmer s = new Stemmer();     
    static Hashtable<Integer,String> secondary=new Hashtable<Integer, String>(100000,0.75f);
    static Hashtable<String,Hashtable<Integer,String>> main = new Hashtable<String,Hashtable<Integer,String>>();
    static Hashtable<String,Integer> returnhashtable1=new Hashtable<String, Integer>(100000,0.75f);
  static Hashtable<String,Integer> returnhashtable2=new Hashtable<String, Integer>(100000,0.75f);
  static Hashtable<String,Integer> returnhashtable3=new Hashtable<String, Integer>(100000,0.75f);
  static Hashtable<String,Integer> returnhashtable4=new Hashtable<String, Integer>(100000,0.75f);
  static Hashtable<String,Integer> returnhashtable5=new Hashtable<String, Integer>(100000,0.75f);
  public static void print(){
	   File file = new File("/home/santoshkosgi/Downloads/Wiki/out1/"+Integer.toString(++file_name)+".txt");
      try {BufferedWriter outer = new BufferedWriter(new FileWriter(file)); 
      String key;
      String value;
      TreeMap<String,Hashtable<Integer,String>> main_tree = new TreeMap<String,Hashtable<Integer,String>>(main);
      //Enumeration<String> words=main.keys();
      //System.out.println(words.toString());
      /*while(words.hasMoreElements())
      {
      key=words.nextElement();
      //out1.write();
      value = main.get(key).toString();
      //value = value.replaceAll(",","");
      value = value.replaceAll(" ","");
      value = value.replaceAll("=","");
      value = value.replaceAll("}","");
      StringBuffer data = new StringBuffer(key+value+"\n"); 
      // write it to file 

      outer.write(data.toString()); 

      }*/
      for(Map.Entry<String,Hashtable<Integer,String>> entry : main_tree.entrySet()) {
    	  key=entry.getKey();
          //out1.write();
          value = new TreeMap<Integer,String>(main.get(key)).toString();
          //value = value.replaceAll(",","");
          value = value.replaceAll(" ","");
          value = value.replaceAll("=","");
          value = value.replaceAll("}","");
          StringBuffer data = new StringBuffer(key+value+"\n"); 
          // write it to file 

          outer.write(data.toString()); 
      }

      outer.flush(); 
      outer.close(); 
      main.clear();
      main_tree.clear();
      } catch (Exception e) { 
      e.printStackTrace(); 
      }
  }
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
	String x = new String("");
	parser parse = new parser();
	String title = new String();
    String infobox=new String();
    String text=new String();
    String categories=new String();
    String outlinks=new String();
    String id = new String("");
    int id_int;
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
			x = "";
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
			id_int = Integer.parseInt(id);
			categories=parse.getCategories(x);
			outlinks=parse.getLinks(x);
			infobox=parse.parseInfoBox(x);
			text = new String(x);
			count++;
            Hashtable<Integer,String> temp_secondary= new Hashtable<Integer, String>();
              tokenizer t1 = new tokenizer();
              returnhashtable1=t1.tokenize(title);  
              returnhashtable2=t1.tokenize(infobox);  
              returnhashtable3=t1.tokenize(categories);  
              returnhashtable4=t1.tokenize(text);  
              returnhashtable5=t1.tokenize(outlinks);  
            Set<String> set=new HashSet<String>();
            set.addAll(returnhashtable1.keySet());
            set.addAll(returnhashtable2.keySet());
            set.addAll(returnhashtable3.keySet());
            set.addAll(returnhashtable4.keySet());
            set.addAll(returnhashtable5.keySet());
              
              
              
            for (String s: set){
          	  int [] arr = new int[5];
                for(int i=0;i<arr.length;i++)
                    arr[i] = 0;
              if(returnhashtable1.get(s)!=null)
                  {
                    arr[0] = returnhashtable1.get(s);
                  }//to get count of String s in "infobox" section
                  if(returnhashtable2.get(s)!=null)
                  {
                    arr[1] = returnhashtable2.get(s);
                  }//to get count of String s in "categories" section
                  if(returnhashtable3.get(s)!=null)
                  {
                    arr[2] = returnhashtable3.get(s);
                  }//to get count of String s in "text" section
                  if(returnhashtable4.get(s)!=null)
                  {
                    arr[3] = returnhashtable4.get(s);
                  }//to get count of String s in "outlinks" section
                  if(returnhashtable5.get(s)!=null)
                  {
                    arr[4] = returnhashtable5.get(s);
                  }
              
              
                  /*temp_secondary=main.get(s);
                  if(temp_secondary!=null)
                    {
                    temp_secondary.put(id, Arrays.toString(arr));
                    main.put(s, new Hashtable<String,String>(temp_secondary));
                    temp_secondary.clear();
                    }*/
                    if(main.containsKey(s)){
                      //System.out.println(me.getKey()+"in if");
                      
                      //arr[0] = me.getValue();
                    	String temp_arr = new String("");
                        if(arr[0]!=0){
                        	temp_arr = temp_arr+"t"+Integer.toString(arr[0]);
                        }
                        if(arr[1]!=0){
                        	temp_arr = temp_arr+"i"+Integer.toString(arr[1]);
                        }
                        if(arr[2]!=0){
                        	temp_arr = temp_arr+"c"+Integer.toString(arr[2]);
                        }
                        if(arr[3]!=0){
                        	temp_arr = temp_arr+"x"+Integer.toString(arr[3]);
                        }
                        if(arr[4]!=0){
                        	temp_arr = temp_arr+"o"+Integer.toString(arr[4]);
                        }
                        //end of some logic
                        
                      main.get(s).put(id_int,new String(temp_arr));
           
                      //System.out.println(main);
                    }
                    else{
                      temp_secondary.clear();
                      //arr [0] = me.getValue();
                      String temp_arr = new String("");
                      if(arr[0]!=0){
                      	temp_arr = temp_arr+"t"+Integer.toString(arr[0]);
                      }
                      if(arr[1]!=0){
                      	temp_arr = temp_arr+"i"+Integer.toString(arr[1]);
                      }
                      if(arr[2]!=0){
                      	temp_arr = temp_arr+"c"+Integer.toString(arr[2]);
                      }
                      if(arr[3]!=0){
                      	temp_arr = temp_arr+"x"+Integer.toString(arr[3]);
                      }
                      if(arr[4]!=0){
                      	temp_arr = temp_arr+"o"+Integer.toString(arr[4]);
                      }
                      //end of some logic
                      temp_secondary.put(id_int, new String(temp_arr));
                      //temp_base.put(id, Arrays.toString(arr));
                      //main.put(s, temp_secondary);
                      main.put(s, new Hashtable<Integer,String>(temp_secondary));
                    }
                  }
                  
                  
              
              //System.out.println(t.tokenize(title));
                  //get Title from the page.
              
              
            //if (count%100 == 0){
            	System.out.println(count);
            	
            //}
            //System.out.println(id);
			
			btext = false;
			id = "";
			
		}
		if (qName.equalsIgnoreCase("PAGE")) {
			bpage = false;
			if (count%1000 == 0){
          	  print();
          	  //count =0;
            }
			
			//System.out.println("end of page");
		}
		
		if (qName.equalsIgnoreCase("FILE")) {
			/*
			File file = new File("/home/santoshkosgi/Desktop/sample.txt");
            try {BufferedWriter outer = new BufferedWriter(new FileWriter(file)); 
            String key;
            String value;
            Enumeration<String> words=main.keys();
            //System.out.println(words.toString());
            while(words.hasMoreElements())
            {
            key=words.nextElement();
            //out1.write();
            value = main.get(key).toString();
            //value = value.replaceAll(",","");
            value = value.replaceAll(" ","");
            value = value.replaceAll("=","");
            value = value.replaceAll("}","");
            StringBuffer data = new StringBuffer(key+value+"\n"); 
            // write it to file 

            outer.write(data.toString()); 

            }
            outer.flush(); 
            outer.close(); 
            } catch (Exception e) { 
            e.printStackTrace(); 
            }
            */
			print();
            /*combiner x1 = new combiner();
            try{
            x1.combine();
            }
            catch(Exception e){
            }*/
		}
		
	//System.out.println("End Element :" + qName);
 
	}
 
	public void characters(char[] ch, int start, int length) throws SAXException {
 
		if (btitle) {
		//System.out.println("TITLE: " + new String(ch, start, length));
			title=new String(ch, start, length);
			btitle = false;
		}
 
		if (btext) {
			x = x+new String(ch, start, length);
			
		}
		if (bid) {
			//System.out.println("TITLE: " + new String(ch, start, length));
				id =id + " " +new String(ch, start, length);
				bid = false;
				

			}
 
	}
 
     };
 
       saxParser.parse("/home/santoshkosgi/Downloads/file.xml", handler);
 
     } catch (Exception e) {
       e.printStackTrace();
     }
 
   }
 
}