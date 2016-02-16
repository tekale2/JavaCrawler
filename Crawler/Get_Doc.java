import java.util.concurrent.BlockingQueue;
import java.io.*;

public class Get_Doc implements Runnable
{
	BlockingQueue<String> urls;
	boolean keepRunning = true;
	String name;
	String page = null;
	int countDocument;

	Get_Doc(BlockingQueue<String> urlin, String name)
	{
		this.urls = urlin;
		this.name = name;
	}
	
	public void run()
	{
		while(keepRunning)
		{
			String url;
			try 
			{
				url = urls.take();
			} 
			catch(InterruptedException e) 
			{
				break;
			}


			try
			{
				page = Network.fetch(url);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			page = page.substring(0, 200);
			if(urls.isEmpty())
					keepRunning = false;
			saveFile(page, this.name, countDocument++);
		}
		
	}

	private static void saveFile(String page, String name, int countDocument)
	{
		PrintStream out = null;
		try
		{
			out = new PrintStream(new FileOutputStream("documents/"+name+"-"+Integer.toString(countDocument)+".txt"));
			out.println(page);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		} 
		finally
		{
			out.close();
		}
	}
}
