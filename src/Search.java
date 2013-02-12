import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
public class Search{
	//private static long startTime = System.currentTimeMillis();
	public void get_name(String word) throws IOException{
		RandomAccessFile meta = new RandomAccessFile("/home/santoshkosgi/Downloads/Wiki/meta_title.txt","rw");
		RandomAccessFile index = new RandomAccessFile("/home/santoshkosgi/Downloads/Wiki/title_index.txt", "r");
		int s = 0;
		int e = 12961676;
		int word_long,str_long;
		word_long = Integer.parseInt(word);
		String str,str_actual;
		meta.seek(((s+e)/2)*8);
		index.seek(meta.readLong());
		str_actual = index.readLine();
		str = str_actual.substring(0,str_actual.indexOf('|'));
		str_long = Integer.parseInt(str.trim());
		while(str_long !=word_long && (s!=e)){
			if(str_long<word_long)
			{
				s = ((s+e)/2)+1;
				meta.seek(((s+e)/2)*8);
				index.seek(meta.readLong());
				str_actual = index.readLine();
				str = str_actual.substring(0,str_actual.indexOf('|'));
				str_long = Integer.parseInt(str.trim());
			}
			else {
				e = ((s+e)/2)-1;
				meta.seek(((s+e)/2)*8);
				index.seek(meta.readLong());
				str_actual = index.readLine();
				str = str_actual.substring(0,str_actual.indexOf('|'));
				str_long = Integer.parseInt(str.trim());
			}
		}
		if(s==e && str_long!=word_long ){
			System.out.println("din");
		}
		//if document is found
		else{
			System.out.println(str_actual.substring(str_actual.indexOf('|')+1));
		}
		
		index.close();
		meta.close();
	}
	public String[] split(String s) {
		ArrayList<String> out= new ArrayList<String>();
		int idx = 0;
		int next = 0;
		int i=0;
		while ((next = s.indexOf( ',', idx )) > -1) {
			out.add( s.substring( idx, next ) );
			idx = next + 1;
			i++;
			if(i==5000)
			break;
		}
		//if ( idx < s.length() && i<60000 ) {
			//out.add( s.substring( idx ) );
		//}
		String[] array = out.toArray(new String[out.size()]);
		return array;
		}
	public void query(String word) throws IOException{
		tokenizer t1 = new tokenizer();
		Search s1 = new Search();
		Hashtable<String,Integer> tokens_query=new Hashtable<String, Integer>();
		if(!word.contains(":")){
		tokens_query=t1.tokenize(word); 
		Set<String> set=new HashSet<String>();
		Set<String> set_words = new HashSet<String>();
		Set<String> set_temp = new HashSet<String>();
		set.addAll(tokens_query.keySet());
		ArrayList<Set<String>> words = new ArrayList<Set<String>>();
		TreeMap<Integer,Integer> size = new TreeMap<Integer,Integer>();
		TreeMap<Double,String> sort = new TreeMap<Double,String>();
		int i =0;
		for (String s: set){
			set_words = s1.rank(s); 
			words.add(set_words);
			size.put(set_words.size(),i++);
		}
		Map.Entry<Integer, Integer> e= size.pollFirstEntry();
		int l = size.size();
		set_temp = words.get(e.getValue());
		for(int j=1 ; j<=l;j++){
			Map.Entry<Integer, Integer> e1= size.pollFirstEntry();
			set_temp.retainAll(words.get(e1.getValue()));
		}
		for (String s: set_temp){
			sort.put(score_all.get(s), s);
		}
		Set<Double>x = sort.descendingKeySet();
		int d = 0;
		for(Double d1:x){
			if(d++<15){
				System.out.println(sort.get(d1));
				s1.get_name(sort.get(d1));
				//System.out.println(s1.searching(sort.get(d1)));
				//s1.docTitleFromID((Long.parseLong(sort.get(d1))));
			}
			else{
				break;
			}
		}
		}
		else{
			Set<String> set=new HashSet<String>();
			Set<String> set_words = new HashSet<String>();
			Set<String> set_temp = new HashSet<String>();
			
			ArrayList<Set<String>> words = new ArrayList<Set<String>>();
			TreeMap<Integer,Integer> size = new TreeMap<Integer,Integer>();
			TreeMap<Double,String> sort = new TreeMap<Double,String>();
			int i =0;
			
			for(int h = 1;h<=word.split("[: ]").length;h=h+2)
			{
				set.clear();
				if(word.split("[: ]")[h-1].length()==1){
					tokens_query=t1.tokenize(word.split("[: ]")[h]); 
					set.addAll(tokens_query.keySet());
					for (String s: set){
					set_words = s1.rank_field(s,word.split("[: ]")[h-1]);
					
					}
					words.add(set_words);
					size.put(set_words.size(),i++);
				}
				else{
					tokens_query=t1.tokenize(word.split("[: ]")[h]); 
					set.addAll(tokens_query.keySet());
					for (String s: set){
					set_words = s1.rank(s);
					}
					words.add(set_words);
					size.put(set_words.size(),i++);
				}
			}
			
			Map.Entry<Integer, Integer> e= size.pollFirstEntry();
			int l = size.size();
			set_temp = words.get(e.getValue());
			for(int j=1 ; j<=l;j++){
				Map.Entry<Integer, Integer> e1= size.pollFirstEntry();
				set_temp.retainAll(words.get(e1.getValue()));
			}
			for (String s: set_temp){
				sort.put(score_all.get(s), s);
			}
			Set<Double>x = sort.descendingKeySet();
			int d = 0;
			for(Double d1:x){
				if(d++<15){
					System.out.println(sort.get(d1));
					s1.get_name(sort.get(d1));
					//s1.docTitleFromID((Long.parseLong(sort.get(d1))));
					//System.out.println(s1.searching(sort.get(d1)));
				}
				else{
					break;
				}
			}
		}
	}
	public static TreeMap<String,Double> score_all = new TreeMap<String,Double>();
	public Set<String> rank(String word)throws IOException{
		RandomAccessFile meta = new RandomAccessFile("/home/santoshkosgi/Downloads/Wiki/meta_all.txt","rw");
		RandomAccessFile index = new RandomAccessFile("/home/santoshkosgi/Downloads/Wiki/0.txt", "r");
		int s = 0;
		int e = 17877984;
		meta.seek(((s+e)/2)*8);
		index.seek(meta.readLong());
		String str,str_actual;//word is the required word
		//word = "india";
		double score = 0;
		double N = 1500;
		int i,j,p;
		int posting_length;
		//TreeMap<String,Double> score_all = new TreeMap<String,Double>();
		String tmp;//used to calculate intermediate score
		str_actual = index.readLine();
		str = str_actual.split("\\{")[0];
		String posting_single;//for a sinle document thing of a posting list
		int posting_single_length;//length of a single doc thing of a posting list
		String[] posting_single_split;//split of posting_single
		while(str.compareToIgnoreCase(word)!=0 && (s!=e)){
			if(str.compareToIgnoreCase(word)<0)
			{
				s = ((s+e)/2)+1;
				meta.seek(((s+e)/2)*8);
				index.seek(meta.readLong());
				str_actual = index.readLine();
				str = str_actual.split("\\{")[0];
			}
			else{
				e = ((s+e)/2)-1;
				meta.seek(((s+e)/2)*8);
				index.seek(meta.readLong());
				str_actual = index.readLine();
				str = str_actual.split("\\{")[0];
			}
		}
		if(s==e && str.compareToIgnoreCase(word)!=0 ){
			System.out.println("din found");
		
		}
		//if document is found
		else{
			//System.out.println(str_actual);
			String posting = str_actual.split("\\{")[1];//12961996
			if(posting.contains(",")){
				String[] posting_split =  posting.split(",");
				//StringTokenizer st = new StringTokenizer(posting,",");
				posting_length = posting_split.length;
				//posting_length = st.countTokens();
				//System.out.println(posting_length);
				if(posting_length>10000){
					posting = posting.substring(0, posting.indexOf(posting_split[10000]));
					posting_split =  posting.split(",");
					posting_length=10000;
				}
				for( j = 0;j<posting_length;j++){
					//System.out.println(posting.split(",")[j].split("[ticxo]")[0]);
					p = 1;
					score =0;
					posting_single = posting_split[j];
					//posting_single = st.nextToken();
					posting_single_length = posting_single.length();
					int ki;
					char tmp1;
					posting_single_split = posting_single.split("[ticxo]");
					if((ki=posting_single.indexOf('t'))!=-1)
					{
						tmp1 = posting_single.charAt(ki+1);
						score = score + (int)tmp1*10;
						p++;
					}
					if((ki=posting_single.indexOf('i'))!=-1)
					{
						tmp1 = posting_single.charAt(ki+1);
						score = score + (int)tmp1*6;
						p++;
					}
					if((ki=posting_single.indexOf('c'))!=-1)
					{
						tmp1 = posting_single.charAt(ki+1);
						score = score + (int)tmp1*4;
						p++;
					}
					if((ki=posting_single.indexOf('x'))!=-1)
					{
						//tmp1 = posting_single.charAt(ki+1);
						//score = score + (int)tmp1*2;
						tmp = posting_single_split[p++];
						score = score + Integer.parseInt(tmp)*2;
					}
					if((ki=posting_single.indexOf('o'))!=-1)
					{
						tmp1 = posting_single.charAt(ki+1);
						score = score + (int)tmp1*4;
					}
					
					/*for(i=0;i<posting_single_length;i++){
						if(posting_single.charAt(i)=='t'){
							tmp = posting_single_split[p++];
							score = score + Integer.parseInt(tmp)*10;
						}
						if(posting_single.charAt(i)=='i'){
							tmp = posting_single_split[p++];
							score = score + Integer.parseInt(tmp)*6;
						}
						if(posting_single.charAt(i)=='c'){
							tmp = posting_single_split[p++];
							score = score + Integer.parseInt(tmp)*4;
						}
						if(posting_single.charAt(i)=='x'){
							tmp = posting_single_split[p++];
							score = score + Integer.parseInt(tmp)*2;
						}
						if(posting_single.charAt(i)=='o'){
							tmp = posting_single_split[p++];
							score = score + Integer.parseInt(tmp)*4;
						}
					}*/
					//score = (Math.log(1+score))+Math.log(((posting.split(",").length+1)/N));
					score = (Math.log(1+score))+Math.log(((posting_length+1)/N));
					//System.out.println(posting.split(",")[j].split("[ticxo]")[0]+"====="+score);
					if(score_all.containsKey(posting_single_split[0])){
						score_all.put(posting_single_split[0],score+score_all.get(posting_single_split[0]));
					}
					else{
					score_all.put(posting_single_split[0], score);
					}
				}
			}
			else{
				p = 1;
				score = 0;
				posting_single = posting;
				posting_single_length = posting_single.length();
				posting_single_split = posting_single.split("[ticxo]");
				for(i=0;i<posting_single_length;i++){
					if(posting_single.charAt(i)=='t'){
						tmp = posting_single_split[p++];
						score = score + Integer.parseInt(tmp)*10;
					}
					if(posting_single.charAt(i)=='i'){
						tmp = posting_single_split[p++];
						score = score + Integer.parseInt(tmp)*6;
					}
					if(posting_single.charAt(i)=='c'){
						tmp = posting_single_split[p++];
						score = score + Integer.parseInt(tmp)*4;
					}
					if(posting_single.charAt(i)=='x'){
						tmp = posting_single_split[p++];
						score = score + Integer.parseInt(tmp)*2;
					}
					if(posting_single.charAt(i)=='o'){
						tmp = posting_single_split[p++];
						score = score + Integer.parseInt(tmp)*4;
					}
				}
				score = (Math.log(1+score))+Math.log((1/N));
				//System.out.println(posting_single.split("[ticxo]")[0]+"====="+score);
				if(score_all.containsKey(posting_single_split[0])){
					score_all.put(posting_single_split[0],score+score_all.get(posting_single_split[0]));
				}
				else{
				score_all.put(posting_single_split[0], score);
				}
			}
		}
		/*for(Map.Entry<String,Double> entry : score_all.entrySet()) {
			  String key = entry.getKey();
			  Double value = entry.getValue();
			  System.out.println(key + " => " + value);
			}*/
		index.close();
		meta.close();
		
		return score_all.keySet();
	}
	public Set<String> rank_field(String word,String field)throws IOException{
		RandomAccessFile meta = new RandomAccessFile("/home/santoshkosgi/Downloads/Wiki/meta_all.txt","rw");
		RandomAccessFile index = new RandomAccessFile("/home/santoshkosgi/Downloads/Wiki/0.txt", "r");
		System.out.println(word);
		int s = 0;
		int e = 17877984;
		meta.seek(((s+e)/2)*8);
		index.seek(meta.readLong());
		String str,str_actual;//word is the required word
		//word = "india";
		double score = 0;
		double N = 1500;
		int i,j,p;
		//TreeMap<String,Double> score_all = new TreeMap<String,Double>();
		String tmp;//used to calculate intermediate score
		str_actual = index.readLine();
		str = str_actual.split("\\{")[0];
		String posting_single;//for a sinle document thing of a posting list
		int posting_single_length;//length of a single doc thing of a posting list
		String[] posting_single_split;//split of posting_single
		while(str.compareToIgnoreCase(word)!=0 && (s!=e)){
			if(str.compareToIgnoreCase(word)<0)
			{
				s = ((s+e)/2)+1;
				meta.seek(((s+e)/2)*8);
				index.seek(meta.readLong());
				str_actual = index.readLine();
				str = str_actual.split("\\{")[0];
			}
			else{
				e = ((s+e)/2)-1;
				meta.seek(((s+e)/2)*8);
				index.seek(meta.readLong());
				str_actual = index.readLine();
				str = str_actual.split("\\{")[0];
			}
		}
		if(s==e && str.compareToIgnoreCase(word)!=0 ){
			System.out.println("din found");
		
		}
		//if document is found
		else{
			//System.out.println(str_actual);
			String posting = str_actual.split("\\{")[1];
			if(posting.contains(",")){
				String[] posting_split =  posting.split(",");
				//String[] posting_split =  se.split(posting);
				int posting_length = posting_split.length;
				//System.out.println(posting_length);
				if(posting_length>10000){
					posting = posting.substring(0, posting.indexOf(posting_split[3000]));
					posting_split =  posting.split(",");
					posting_length=3000;
				}
				for( j = 0;j<posting_length;j++){
					//System.out.println(posting.split(",")[j].split("[ticxo]")[0]);
					p = 1;
					score =0;
					posting_single = posting_split[j];
					posting_single_length = posting_single.length();
					posting_single_split = posting_single.split("[ticxo]");
					int ki;
					char tmp1;
					if(posting_single.contains(field)){
					if((ki=posting_single.indexOf('t'))!=-1)
					{
						tmp1 = posting_single.charAt(ki+1);
						score = score + (int)tmp1*10;
						p++;
					}
					if((ki=posting_single.indexOf('i'))!=-1)
					{
						tmp1 = posting_single.charAt(ki+1);
						score = score + (int)tmp1*6;
						p++;
					}
					if((ki=posting_single.indexOf('c'))!=-1)
					{
						tmp1 = posting_single.charAt(ki+1);
						score = score + (int)tmp1*4;
						p++;
					}
					if((ki=posting_single.indexOf('x'))!=-1)
					{
						//tmp1 = posting_single.charAt(ki+1);
						//score = score + (int)tmp1*2;
						tmp = posting_single_split[p++];
						score = score + Integer.parseInt(tmp)*2;
					}
					if((ki=posting_single.indexOf('o'))!=-1)
					{
						tmp1 = posting_single.charAt(ki+1);
						score = score + (int)tmp1*4;
					}
					}
					/*if(posting_single.contains(field)){
					for(i=0;i<posting_single_length;i++){
						if(posting_single.charAt(i)=='t'){
							tmp = posting_single_split[p++];
							score = score + Integer.parseInt(tmp)*10;
						}
						if(posting_single.charAt(i)=='i'){
							tmp = posting_single_split[p++];
							score = score + Integer.parseInt(tmp)*6;
						}
						if(posting_single.charAt(i)=='c'){
							tmp = posting_single_split[p++];
							score = score + Integer.parseInt(tmp)*4;
						}
						if(posting_single.charAt(i)=='x'){
							tmp = posting_single_split[p++];
							score = score + Integer.parseInt(tmp)*2;
						}
						if(posting_single.charAt(i)=='o'){
							tmp = posting_single_split[p++];
							score = score + Integer.parseInt(tmp)*4;
						}
					}
					}*/
					score = (Math.log(1+score))+Math.log(((posting_length+1)/N));
					//System.out.println(posting.split(",")[j].split("[ticxo]")[0]+"====="+score);
					if(score_all.containsKey(posting_single_split[0])){
						score_all.put(posting_single_split[0],score+score_all.get(posting_single_split[0]));
					}
					else{
					score_all.put(posting_single_split[0], score);
					}
				}
			}
			else{
				p = 1;
				score = 0;
				posting_single = posting;
				posting_single_length = posting_single.length();
				posting_single_split = posting_single.split("[ticxo]");
				for(i=0;i<posting_single_length;i++){
					if(posting_single.charAt(i)=='t'&& field.equalsIgnoreCase("t")){
						tmp = posting_single_split[p++];
						score = score + Integer.parseInt(tmp)*10;
					}
					if(posting_single.charAt(i)=='i'&& field.equalsIgnoreCase("i")){
						tmp = posting_single_split[p++];
						score = score + Integer.parseInt(tmp)*6;
					}
					if(posting_single.charAt(i)=='c'&& field.equalsIgnoreCase("c")){
						tmp = posting_single_split[p++];
						score = score + Integer.parseInt(tmp)*4;
					}
					if(posting_single.charAt(i)=='x'&& field.equalsIgnoreCase("x")){
						tmp = posting_single_split[p++];
						score = score + Integer.parseInt(tmp)*2;
					}
					if(posting_single.charAt(i)=='o'&& field.equalsIgnoreCase("o")){
						tmp = posting_single_split[p++];
						score = score + Integer.parseInt(tmp)*4;
					}
				}
				score = (Math.log(1+score))+Math.log((1/N));
				//System.out.println(posting_single.split("[ticxo]")[0]+"====="+score);
				if(score_all.containsKey(posting_single_split[0])){
					score_all.put(posting_single_split[0],score+score_all.get(posting_single_split[0]));
				}
				else{
				score_all.put(posting_single_split[0], score);
				}
			}
		}
		/*for(Map.Entry<String,Double> entry : score_all.entrySet()) {
			  String key = entry.getKey();
			  Double value = entry.getValue();
			  System.out.println(key + " => " + value);
			}*/
		index.close();
		meta.close();
		return score_all.keySet();
	}
	public static void main(String argv[]) throws IOException{
		long startTime;
		long endTime;
		Search s = new Search();
		while(true){
		score_all.clear();
		System.err.println("Enter the query");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String query = br.readLine();
		//s.query("india");
		startTime = System.currentTimeMillis();
		s.query(query);
		endTime = System.currentTimeMillis();
        System.out.println("It took " + (endTime - startTime) + " milliseconds");
		}
	}
	}