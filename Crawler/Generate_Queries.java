import java.util.concurrent.BlockingQueue;

public class Generate_Queries implements Runnable
{
	Date_Tracker d;
	BlockingQueue<String> q;
	Generate_Queries(BlockingQueue<String> qin, Date_Tracker din)
	{
		d = din;
		q = qin;
	}
	public void run()
	{
		
	}
}
