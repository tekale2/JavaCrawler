import java.util.concurrent.*;
import java.util.ArrayList;
import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument; //download this library
// import org.apache.solr.client.solrj.SolrClient; //download this library


public class Indexer {

	public static void main(String[] args) 
	{
		int parserThreads = 4;
		// SolrClient client = new SolrClient(/*initializer here*/);
		HttpSolrServer server = new HttpSolrServer("http://localhost:8983/solr/test_core");

		BlockingQueue<String> xmlDocuments = new ArrayBlockingQueue<String>(10000);
		BlockingQueue<SolrInputDocument> solrDocs = new ArrayBlockingQueue<SolrInputDocument>(10000);
		ArrayList<SolrInputDocument> solrBuffers = new ArrayList<SolrInputDocument>();
		
		Get_Doc get = new Get_Doc(xmlDocuments);
		Thread get_thread = new Thread(get);
		get_thread.start();

		try
		{
			get_thread.join();
			xmlDocuments.add("STOP");
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
		
		// System.out.println(xmlDocuments.size());
		//ArrayList of Parse_Document threads
		Parse_Document parsingXML = new Parse_Document(xmlDocuments, solrDocs);
		Thread parsingXMLThread = new Thread(parsingXML);
		parsingXMLThread.start();
		//join all the threads
		
		try
		{
			parsingXMLThread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}

		//NOTE!! drainTo NOT THREAD SAFE!! Only main thread may be running!
		//ALL OTHER THREADS MUST BE DEAD HERE! USE JOIN!
		// solrDoc.drainTo(solrBuffer);
		
		//adds documents to the client
		// client.add(solrBuffer);
		//commits documents to client
		// client.commit();

	}

	// public static void main(String[] args) throws IOException, SolrServerException {
	// 	HttpSolrServer server = new HttpSolrServer("http://localhost:8983/solr/test_core");
	//     for(int i=0;i<1000;++i) {
	// 		SolrInputDocument doc = new SolrInputDocument();
	// 		doc.addField("test", "book");
	// 		doc.addField("test", "book-" + i);
	// 		doc.addField("name", "I am hero " + i);
	// 		server.add(doc);
	// 		if(i%100==0) server.commit();  // periodically flush
	//     }
	//     server.commit(); 
	// }

}
