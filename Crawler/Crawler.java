import java.util.concurrent.*;
import java.util.ArrayList;

public class Crawler 
{

	public static void main(String[] args) 
	{
		int numGenerate = 1;
		int numJSON = 10;
		int numXML = 10;
		String startDate = "2005-12-31";

		BlockingQueue<String> queue = new ArrayBlockingQueue<String>(20000);
		BlockingQueue<String> urls = new ArrayBlockingQueue<String>(20000);
		Generate_Queries queries = new Generate_Queries(queue, startDate);
		Get_List_Docs getListDocs = new Get_List_Docs(queue, urls);
		Get_Doc getDoc = new Get_Doc(urls);

		/* Spawn threads */
		ArrayList<Thread> generateQueriesThread = new ArrayList<Thread>();
		ArrayList<Thread> getListDocThread = new ArrayList<Thread>();
		ArrayList<Thread> getDocThread = new ArrayList<Thread>();

		for(int i = 0; i < numGenerate; i++)
			generateQueriesThread.add(new Thread(queries));
		
		for(int i = 0; i <numJSON; i++)
			getListDocThread.add(new Thread(getListDocs));

		for(int i = 0; i < numXML; i++)
			getDocThread.add(new Thread(getDoc));


		for(int i = 0; i < numGenerate; i++)
			generateQueriesThread.get(i).start();
		
		try
		{
			for(int i = 0; i < numGenerate; i++)
				generateQueriesThread.get(i).join();

			for(int i = 0; i < numJSON; i++)
				queue.add("STOP");	
		} 
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}

		for(int i = 0; i < numGenerate; i++)
			getListDocThread.get(i).start();
	
		try
		{
			for(int i = 0; i < numGenerate; i++)
				getListDocThread.get(i).join();

			for(int i = 0; i < numXML; i++)
				urls.add("STOP");	
		} 
		catch(InterruptedException e)
		{
			e.printStackTrace();//
		}

		for(int i = 0; i < numXML; i++)
			getDocThread.get(i).start();

		try
		{
			for(int i = 0; i < numXML; i++)
				getDocThread.get(i).join();
		} 
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		

	}
}
