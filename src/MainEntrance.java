import java.io.IOException;


public class MainEntrance {
	public static void main(String[] args) throws IOException{
		Crawler crawler = new Crawler(	"C:\\Development\\Workspace\\172part1\\urlList.txt",	//seeds
										"C:\\Development\\Workspace\\172part1\\source_pages\\",	//data directory
										10,														//total number of pages to crawl
										3);														//maximum recursion depth
		crawler.start();
	}
}
