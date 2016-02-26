import java.util.concurrent.*;

public class Get_Doc implements Runnable
{
	boolean exists_next = true;
	BlockingQueue<String> doc;
	Get_Doc(BlockingQueue<String> document)
	{
		doc = document;
	}
	public void run() 
	{
		while(exists_next)
		{
			//read xml from file, add string to doc
			//check if next file exists
		}
		// TODO Auto-generated method stub
		
	}

}
