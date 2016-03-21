/* 
Notice:
Solr core is name test_core

1. SolrClient vs SolrServer? Not sure which one to use but I chosed SolrServer.
2. Fetching files from documents/ for now. We should integrate this with Crawler.
3. Multi-threading not implemented yet.
4. Deleting index in core: http://localhost:8983/solr/test_core/update?stream.body=%3Cdelete%3E%3Cquery%3E*:*%3C/query%3E%3C/delete%3E&commit=true
5. Run Make to compile and run program.
6. Might have to index url too?
*/

import java.util.concurrent.*;
import java.util.ArrayList;
import java.io.*;
import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;


public class Indexer {

	public static void main(String[] args) 
	{
		int num_Threads = 10;
		int buf_Size =60000;
		//see number of files
		File folder = new File("documents/");
		File[] list = folder.listFiles();

		int file_count = 2*buf_Size;
		//create getter thread objects
		Thread get_threads [] = new Thread[num_Threads];
		Thread parsingXMLThreads [] = new Thread[num_Threads];
		Get_Doc get;
		Parse_Document parsingXML;
		// SolrClient client = new SolrClient(/*initializer here*/);
		HttpSolrServer server = new HttpSolrServer("http://localhost:8983/solr/test_core");

		BlockingQueue<String> xmlDocuments = new ArrayBlockingQueue<String>(buf_Size+1);
		BlockingQueue<SolrInputDocument> solrDocs = new ArrayBlockingQueue<SolrInputDocument>(buf_Size+1);
		ArrayList<SolrInputDocument> solrBuffer = new ArrayList<SolrInputDocument>();

		for(int i =0;i<((file_count-1)/buf_Size +1);i++)
		{	
			//block index calculations

			int start = i*buf_Size;
			int end = start + buf_Size;
			if(end>file_count)
				end = file_count;
			int size = (end - start);
			for(int j=0;j<num_Threads;j++)
			{
				//thread index calculations
				int f_start = start + j*size/num_Threads;
				int f_end = f_start + size/num_Threads -1 ;

				if(j == num_Threads-1)
					f_end = end -1;
				System.out.println("start: "+f_start+"  end: "+f_end);
				//producer threads

				get = new Get_Doc(xmlDocuments,list,f_start,f_end);
				get_threads[j] = new Thread(get);
				get_threads[j].start();
				/*
				try
				{
					get_thread.join();
					xmlDocuments.add("STOP");
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				*/
				
				// System.out.println(xmlDocuments.size());
				//ArrayList of Parse_Document thread
				//consumer threads
				parsingXML = new Parse_Document(xmlDocuments, solrDocs);
				parsingXMLThreads[j] = new Thread(parsingXML);
				parsingXMLThreads[j].start();
				//join all the threads
			    /*
				try
				{
					parsingXMLThread.join();
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				*/
			}
			
			//join all getter threads
			for (int k =0;k<num_Threads;k++)
			{
				 try
				{
					get_threads[k].join();
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}	
			}
			xmlDocuments.add("STOP");
			//join all parsingThreads
			System.out.println("Waiting for Parsing");
			for(int l =0;l<num_Threads;l++)
			{
				try
				{
					parsingXMLThreads[l].join();
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			//from here main thread only
			System.out.println("Draining");
			xmlDocuments.remove("STOP");
			solrDocs.drainTo(solrBuffer);

			try
			{
				server.add(solrBuffer);
				server.commit(); 
				solrBuffer.clear();

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}


		//NOTE!! drainTo NOT THREAD SAFE!! Only main thread may be running!
		//ALL OTHER THREADS MUST BE DEAD HERE! USE JOIN!
		// solrDoc.drainTo(solrBuffer);
		
		//adds documents to the client
		// client.add(solrBuffer);
		//commits documents to client
		// client.commit();

	}
}
