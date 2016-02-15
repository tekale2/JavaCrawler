import java.util.concurrent.BlockingQueue;

public class Get_List_Docs implements Runnable
{
	BlockingQueue<String> requests;
	BlockingQueue<String> urls;
	boolean keep_running = true;
	Get_List_Docs(BlockingQueue<String> requests)
	{
		this.requests = requests;
		this.urls = urls;
	}
	
	public void run()
	{
		while(keep_running)
		{
			String req;
			try 
			{
				req = requests.take();
				if(requests.isEmpty())
					keep_running = false;
				System.out.println(req);
			}
			catch(InterruptedException e) 
			{
				break;
			} 
			//String unparsedJSON = Network.fetch(req);
			//TODO
			//parse the JSON here
			//url.put(XML_URL)
			
			//Bill: Parse JSON and pass the url to Get_Doc.java
		}
	}
}
