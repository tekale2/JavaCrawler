import java.util.concurrent.BlockingQueue;

public class Get_List_Docs implements Runnable
{
	BlockingQueue<String> Requests;
	BlockingQueue<String> Urls;
	Get_List_Docs(BlockingQueue<String> Rin, BlockingQueue<String> Uin)
	{
		Requests = Rin;
		Urls = Uin;
	}
	
	public void run()
	{
		//do things
	}
}
