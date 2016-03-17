/* Note: Fetching xml files locally for now */
import java.util.concurrent.*;
import java.io.*;
import org.apache.commons.io.FileUtils;


public class Get_Doc implements Runnable
{
	boolean exists_next = true;
	BlockingQueue<String> docs;
	File [] listOfFiles;
	int start_index;
	int end_index;
	Get_Doc(BlockingQueue<String> documents, File [] list, int start, int end)
	{
		docs = documents;
		listOfFiles = list;
		start_index = start;
		end_index	= end;
	
	}
	public void run()
	{


		for (int i = start_index; i <= end_index; i++) 
		{
			File file = listOfFiles[i];
			if (file.isFile() && file.getName().endsWith(".txt"))
			{
				String content = null;
				try
				{
					content = FileUtils.readFileToString(file);
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				/* do somthing with content */
				//producer
				docs.add(content);
				// System.out.println(content);
				// System.out.println("-------------");
			} 
		}
		// while(exists_next)
		// {
		// 	//read xml from file, add string to doc
		// 	//check if next file exists

		// }
		// TODO Auto-generated method stub
		
	}

}
