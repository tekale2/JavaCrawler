import java.util.concurrent.BlockingQueue;

public class Get_Doc implements Runnable
{
	BlockingQueue<String> urls;
	boolean keep_running = true;

	Get_Doc(BlockingQueue<String> urlin)
	{
		this.urls = urlin;
	}
	
	public void run()
	{
		while(keep_running)
		{
			String url;
			try 
			{
				url = urls.take();
				//System.out.println(url);
				keep_running = false;
			} 
			catch(InterruptedException e) 
			{
				break;
			}
			String page = Network.fetch(url);
			//TODO
			//write the document to disk for now
			//Apache
		}
		
	}
}
