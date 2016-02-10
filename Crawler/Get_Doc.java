import java.util.concurrent.BlockingQueue;

public class Get_Doc implements Runnable
{
	BlockingQueue<String> Urls;
	Get_Doc(BlockingQueue<String> urlin)
	{
		Urls = urlin;
	}
	
	public void run()
	{
		
	}
}
