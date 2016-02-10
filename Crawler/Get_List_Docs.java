import java.util.concurrent.BlockingQueue;

public class Get_List_Docs implements Runnable
{
	BlockingQueue<String> Requests;
	BlockingQueue<String> Urls;
	boolean keep_running = true;
	Get_List_Docs(BlockingQueue<String> Rin, BlockingQueue<String> Uin)
	{
		Requests = Rin;
		Urls = Uin;
	}
	
	public void run()
	{
		while(keep_running)
		{
			String req;
			try 
			{
				req = Requests.take();
			}
			catch(InterruptedException e) 
			{
				break;
			} 
			String unparsedJSON = Network.fetch(req);
			//TODO
			//parse the JSON here
			//url.put(XML_URL)
		}
	}
}
