import java.util.concurrent.*;

public class Crawler 
{

	public static void main(String[] args) 
	{
		Date_Tracker dateTraking = new Date_Tracker();
		BlockingQueue<String> queue2014 = new ArrayBlockingQueue<String>(365);
		BlockingQueue<String> queue2015 = new ArrayBlockingQueue<String>(365);
		Generate_Queries queries2014 = new Generate_Queries(queue2014, "2014-01-01");
		Generate_Queries queries2015 = new Generate_Queries(queue2015, "2014-02-02");

		
		Thread threadOne = new Thread(queries2014);
		Thread threadTwo = new Thread(queries2015);

		threadOne.start();
		threadTwo.start();
		try
		{
			threadOne.join();
			threadTwo.join();
		} 
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		BlockingQueue<String> urls2014 = new ArrayBlockingQueue<String>(30000);
		BlockingQueue<String> urls2015 = new ArrayBlockingQueue<String>(30000);
		Get_List_Docs getListDocs2014 = new Get_List_Docs(queue2014, urls2014);
		Get_List_Docs getListDocs2015 = new Get_List_Docs(queue2015, urls2015);
		Thread threadGetListDocs2014 = new Thread(getListDocs2014);
		Thread threadGetListDocs2015 = new Thread(getListDocs2015);
		threadGetListDocs2014.start();
		threadGetListDocs2015.start();
		try
		{
			threadGetListDocs2014.join();
			threadGetListDocs2015.join();
		} 
		catch(InterruptedException e)
		{
			e.printStackTrace();//
		}
		
		System.out.println(getListDocs2014.urls.size());
		System.out.println(getListDocs2015.urls.size());


		Get_Doc getDoc2014 = new Get_Doc(getListDocs2014.urls, "2014");
		Get_Doc getDoc2015 = new Get_Doc(getListDocs2015.urls, "2015");
		Thread getDocThread2014 = new Thread(getDoc2014);
		Thread getDocThread2015 = new Thread(getDoc2015);
		getDocThread2014.start();
		getDocThread2015.start();

		try
		{
			getDocThread2014.join();
			getDocThread2015.join();
		} 
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}

	}
}
