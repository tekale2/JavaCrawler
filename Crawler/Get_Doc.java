import java.util.concurrent.BlockingQueue;

public class Get_Doc implements Runnable
{
	BlockingQueue<String> Urls;
	boolean keep_running = true;
	Get_Doc(BlockingQueue<String> urlin)
	{
		Urls = urlin;
	}
	
	public void run()
	{
		while(keep_running)
		{
			String url;
			try 
			{
				url = Urls.take();
			} 
			catch(InterruptedException e) 
			{
				break;
			}
			String page = Network.fetch(url);
			//write the document to disk for now
		}
		
	}
}
