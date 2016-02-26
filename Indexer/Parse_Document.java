import java.util.concurrent.BlockingQueue;
import org.apache.solr.common.SolrInputDocument;

public class Parse_Document implements Runnable
{
	BlockingQueue<String> xmlDoc;
	BlockingQueue<SolrInputDocument> solrDoc;
	Parse_Document(BlockingQueue<String> xmlDoc, BlockingQueue<SolrInputDocument> solrDoc)
	{
		this.xmlDoc = xmlDoc;
		this.solrDoc = solrDoc;
	}
	public void run() 
	{
		String xml = xmlDoc.take();
		//parse the document here
		SolrInputDocument document = new SolrInputDocument();
		document.addField("Title", "the title itself");
		//
		//
		solrDoc.put(document);
		// TODO Auto-generated method stub
		
	}
	
}
