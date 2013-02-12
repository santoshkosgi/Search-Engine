import java.io.IOException;
import java.io.RandomAccessFile;

public class Meta{
	public static void main(String argv[]) throws IOException{
		String line;
		RandomAccessFile input = new RandomAccessFile("/home/santoshkosgi/Downloads/Wiki/title_index.txt", "r");
		RandomAccessFile output = new RandomAccessFile("/home/santoshkosgi/Downloads/Wiki/meta_title.txt","rw");
		long pos;
		pos = 0;
		int i = 0;
		int count=0;
		/*for(int j = 0;j<80000;j++)
		{
			input.readLine();
		}*/
		//pos = input.getFilePointer();
		/*while((line=input.readLine())!=null ){
			if(line.indexOf("{")>=3)
			{
				if(line.split("\\{").length>=2){
					output.writeLong(pos);
					i++;
					System.out.println(count++);
				}
			}
			pos = input.getFilePointer();
		}*/
		pos = input.getFilePointer();
		while((input.readLine()!=null)){
			System.out.println(count++);
			output.writeLong(pos);
			pos = input.getFilePointer();
		}
		//output.seek(358*8);
		//input.seek(output.readLong());
		//System.out.println(input.readLine());
		//System.out.println(output.readLong());
		//System.out.println(output.getFilePointer());
		input.close();
		output.close();
		System.out.println(i);
		
	}
	}



