import java.util.concurrent.BlockingQueue;
import org.json.JSONObject;


public class Get_List_Docs implements Runnable
{
	BlockingQueue<String> requests;
	BlockingQueue<String> urls;
	boolean keepRunning = true;
	Get_List_Docs(BlockingQueue<String> requests, BlockingQueue<String> urls)
	{
		this.requests = requests;
		this.urls = urls;
	}
	
	public void run()
	{
		while(keepRunning)
		{
			String req;
			try 
			{
				req = requests.take();
				if(requests.isEmpty())
					keepRunning = false;
			}
			catch(InterruptedException e) 
			{
				break;
			} 


			String unparsedJSON = null;
			try
			{
				unparsedJSON = Network.fetch(req);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			JSONObject result = new JSONObject(unparsedJSON);
			if(result.getInt("count") != 0)
			{
				result = result.getJSONArray("results").getJSONObject(0);

				try{
					urls.put(result.getString("full_text_xml_url"));
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
