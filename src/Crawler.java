import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;

public class Crawler {
	Queue<URL> urlQueue = new LinkedList<URL>();
	String dataDirectory;
	int pageCount;
	int maxDepth;
	int maxPages;
	
	
	//constructor(no default constructor)
	public Crawler(String seedFile, String dataDir, int maxPages, int maxDepth) throws IOException{
		
		initQueue(seedFile);
		dataDirectory = dataDir;
		this.pageCount = 0;
		this.maxDepth = maxDepth;
		this.maxPages = maxPages;
		
	}
	
	private void writePageData(URL source, String data)
	{
		// Create filename
		String filename = source.getHost().replace('.', '_');
		
		//Create a new file
		try {
 
			File file = new File("C:\\Development\\Workspace\\172part1\\source_pages\\" + filename);
 
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			// Write the data to the file
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(data);
			bw.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private Document readPageData(URL url)
	{
		Document doc = null;
		try{
			doc = Jsoup.connect(url.toString()).get();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	private void processURL(URL url)
	{
		//  Get the page data
		Document pageData = readPageData(url);
		
		if(pageData != null)
		{
			// Save the page data in a new file
			writePageData(url, pageData.html());
		
			// Extract all links
			Elements links = pageData.select("a[href]");
			for(Element link : links)
			{
				System.out.println(link.attr("abs:href"));
			}
			
		}	
	}
	
	public void start() throws IOException{
		while( !urlQueue.isEmpty() )
		{
			System.out.println(urlQueue.peek().toString());
			processURL(urlQueue.poll());
			
		}
	}
	
	private void initQueue(String seedFile)
	{	
		// Open seed file
		File file = new File( seedFile );
		BufferedReader reader = null;
		try{
				reader = new BufferedReader(new FileReader(file));
				String newURL;
				while((newURL = reader.readLine()) != null)
				{
					urlQueue.offer(new URL(newURL));
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}







