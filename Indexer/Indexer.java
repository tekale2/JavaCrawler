import java.util.concurrent.*;
import java.util.ArrayList;
import org.apache.solr.common.SolrInputDocument; //download this library
import org.apache.solr.client.solrj.SolrClient; //download this library

public class Indexer {

	public static void main(String[] args) 
	{
		int parserThreads = 4;
		SolrClient client = new SolrClient(/*initializer here*/);
		
		BlockingQueue<String> xmlDocument = new ArrayBlockingQueue<String>(10000);
		BlockingQueue<SolrInputDocument> solrDoc = new ArrayBlockingQueue<SolrInputDocument>(10000);
		ArrayList<SolrInputDocument> solrBuffer = new ArrayList<SolrInputDocument>();
		
		Get_Doc get = new Get_Doc(xmlDocument);
		Thread get_thread = new Thread(get);
		get.run();
		
		//ArrayList of Parse_Document threads
		//etc
		
		//join all the threads
		
		//NOTE!! drainTo NOT THREAD SAFE!! Only main thread may be running!
		//ALL OTHER THREADS MUST BE DEAD HERE! USE JOIN!
		solrDoc.drainTo(solrBuffer);
		
		//adds documents to the client
		client.add(solrBuffer);
		//commits documents to client
		client.commit();

	}

}
