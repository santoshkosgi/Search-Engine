import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
public class combiner{
	public static void main(String argv[]) throws IOException{
		//InputStream is = new BufferedInputStream(new FileInputStream("/home/santoshkosgi/Downloads/Wiki/out2/1.txt"));
		InputStream    fis;
		BufferedReader br;
		InputStream    fis1;
		BufferedReader br1;
		String         line;
		String first,second;
		int first_check,second_check;
		second_check = 0;
		first_check = 0;
		File folder = new File("/home/santoshkosgi/Downloads/Wiki/merge12/");
		File[] listOfFiles = folder.listFiles(); 
		//System.out.println(listOfFiles.length);
		
		for (int i = 0; i < listOfFiles.length; i=i+2) {
		//for (int i = 0; i < 1; i=i+2) {
			System.out.println(i);
			fis = new FileInputStream("/home/santoshkosgi/Downloads/Wiki/merge12/"+(i)+".txt");
			br = new BufferedReader(new InputStreamReader(fis));
			fis1 = new FileInputStream("/home/santoshkosgi/Downloads/Wiki/merge12/"+(i+1)+".txt");
			br1 = new BufferedReader(new InputStreamReader(fis1));
			Writer output;
			output = new BufferedWriter(new FileWriter("/home/santoshkosgi/Downloads/Wiki/merge13/"+i/2+".txt", true));
			first = br.readLine();
			second = br1.readLine();
			while(true){
				if(first.split("\\{")[0].compareToIgnoreCase(second.split("\\{")[0])<0){
					output.append(first+"\n");
					if((first=br.readLine())!=null)
					{
					}
					else{
						second_check = 1;
						break;
					}
				}
				else if(first.split("\\{")[0].compareToIgnoreCase(second.split("\\{")[0])>0){
					//System.out.println(second);
					output.append(second+"\n");
					if((second=br1.readLine())!=null)
					{
					//	second = second.split("\\{")[0];
					}
					else{
						first_check = 1;
						break;
					}
				}
				else if(first.split("\\{")[0].compareToIgnoreCase(second.split("\\{")[0])==0){
					//System.out.println(second.split("\\{")[0]);
					if(second.split("\\{").length>=2){
					output.append(first+","+second.split("\\{")[1]+"\n");
					}
					if((first=br.readLine())!=null)
					{
						//first = first.split("\\{")[0];
					}
					else{
						second_check = 1;
						break;
					}
					if((second=br1.readLine())!=null)
					{
						//second = second.split("\\{")[0];
					}
					else{
						first_check = 1;
						break;
					}
				}
			}
			if(first_check == 1){
				output.append(first+"\n");
				while((line=br.readLine())!=null)
				{
					output.append(line+"\n");
				}
			}
			if(second_check == 1)
			{
				output.append(second+"\n");
				while((line=br1.readLine())!=null)
				{
					output.append(line+"\n");
				}
			}
			br.close();
			br1.close();
			output.close();
			/*File f1=new File("/home/santoshkosgi/Desktop/index.txt");
			f1.delete();
			File f2=new File("/home/santoshkosgi/Desktop/temp.txt");
			f2.renameTo(new File("/home/santoshkosgi/Desktop/index.txt"));*/
		}
	}
}