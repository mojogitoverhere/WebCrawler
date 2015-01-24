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
	
	private String readPageData(URL url)
	{
		HttpURLConnection httpConnect = null;
		StringBuilder builder = null;
		try {
			// Connect to the next URL in the queue
			httpConnect = (HttpURLConnection) url.openConnection();
			builder = new StringBuilder(); //buffer holding contents of the URL
			
			InputStream inStream = (InputStream) httpConnect.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
			
			String line;
			
			// Read in the data from the URL
			while((line = reader.readLine()) != null){
				builder.append(line);
				builder.append('\n');
			}
			
			// Close the reader
			reader.close();
		}catch( Exception e) {
			e.printStackTrace();
		}finally{
			
			// Close the connection
			httpConnect.disconnect();
		}
		return builder.toString();
	}
	
	private void processURL(URL url)
	{
		// Read in the page data
		String pageData = readPageData(url);
		
		// Save the page data in a new file
		if(pageData != null)
		{
			writePageData(url, pageData);
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







