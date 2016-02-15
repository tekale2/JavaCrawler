import java.util.concurrent.*;

public class Crawler 
{

	public static void main(String[] args) 
	{
		Date_Tracker dateTraking = new Date_Tracker();
		BlockingQueue<String> queue_2014 = new ArrayBlockingQueue<String>(365);
		BlockingQueue<String> queue_2015 = new ArrayBlockingQueue<String>(365);
		Generate_Queries queries_2014 = new Generate_Queries(queue_2014, "2014-01-01");
		Generate_Queries queries_2015 = new Generate_Queries(queue_2015, "2015-01-01");

		
		Thread threadOne = new Thread(queries_2014);
		Thread threadTwo = new Thread(queries_2015);

		threadOne.start();
		threadTwo.start();
	}

}
