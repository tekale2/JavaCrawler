import java.util.concurrent.BlockingQueue;

public class Generate_Queries implements Runnable
{
	Date_Tracker d;
	BlockingQueue<String> q;
	boolean keep_running = true;
	//this query might be too long
	String baseQuery = "www.federalregister.gov/api/v1/articles.json?fields%5B%5D=abstract&fields%5B%5D=full_text_xml_url&per_page=1000&order=relevance&conditions%5Bpublication_date%5D%5Bis%5D=";
	Generate_Queries(BlockingQueue<String> qin, Date_Tracker din)
	{
		d = din;
		q = qin;
	}
	public void run()
	{
		while(keep_running)
		{
			try 
			{
				String query = baseQuery + d.get_next_date();
				q.put(query);
			} 
			catch(InterruptedException e) 
			{
				break;
			}
		}
	}
}
