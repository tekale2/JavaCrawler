import java.util.concurrent.BlockingQueue;

public class Get_Doc implements Runnable
{
	BlockingQueue<String> urls;
	boolean keepRunning = true;

	Get_Doc(BlockingQueue<String> urlin)
	{
		this.urls = urlin;
	}
	
	public void run()
	{
		while(keepRunning)
		{
			String url;
			try 
			{
				url = urls.take();
				//System.out.println(url);
				keepRunning = false;
			} 
			catch(InterruptedException e) 
			{
				break;
			}
			//String page = Network.fetch(url);
			//TODO
			//write the document to disk for now
			//Apache
		}
		
	}
}
