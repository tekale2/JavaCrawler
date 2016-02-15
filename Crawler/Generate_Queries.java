import java.util.concurrent.*;

public class Generate_Queries implements Runnable
{
	String date;
	BlockingQueue<String> queue;
	boolean keep_running = true;
	//this query might be too long
	String baseQuery = "www.federalregister.gov/api/v1/articles.json?fields%5B%5D=abstract&fields%5B%5D=full_text_xml_url&per_page=1000&order=relevance&conditions%5Bpublication_date%5D%5Bis%5D=";
	Generate_Queries(BlockingQueue<String> queue, String date)
	{
		this.date = date;
		this.queue = queue;
	}

	public void run()
	{
		Date_Tracker dateTracking = new Date_Tracker();
		while(keep_running)
		{
			
			try 
			{
				date = dateTracking.get_next_date(date);
				String query = baseQuery + date;
				queue.put(query);
				if(queue.size() == 365)
					keep_running = false;	
			} 
			catch(InterruptedException e)
			{
				break;
			}
		}

		Get_List_Docs getListDoc = new Get_List_Docs(queue);
		Thread threadGetListDoc = new Thread(getListDoc);
		threadGetListDoc.start();
		// System.out.println(queue);
		// Get_Doc getDoc = new Get_Doc(queue);
		// Thread threadGetDoc = new Thread(getDoc);
		// threadGetDoc.start();
	}
}
