import java.util.concurrent.*;

public class Crawler 
{

	public static void main(String[] args) 
	{
		Date_Tracker dateTraking = new Date_Tracker();
		BlockingQueue<String> queue2014 = new ArrayBlockingQueue<String>(365);
		BlockingQueue<String> queue2015 = new ArrayBlockingQueue<String>(365);
		Generate_Queries queries2014 = new Generate_Queries(queue2014, "2014-01-01");
		//Generate_Queries queries_2015 = new Generate_Queries(queue_2015, "2015-01-01");

		
		Thread threadOne = new Thread(queries2014);
		// Thread threadTwo = new Thread(queries_2015);

		threadOne.start();
		try
		{
			threadOne.join();
		} 
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		BlockingQueue<String> urls2014 = new ArrayBlockingQueue<String>(365);
		Get_List_Docs getListDocs2014 = new Get_List_Docs(queue2014, urls2014);
		Thread threadGetListDocs2014 = new Thread(getListDocs2014);

		threadGetListDocs2014.start();

		try
		{
			threadGetListDocs2014.join();
		} 
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		

		while(!getListDocs2014.urls.isEmpty())
		{
			try
			{
				System.out.println(getListDocs2014.urls.take());
			} 
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
