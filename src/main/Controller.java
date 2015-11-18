package main;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicIntegerArray;

import objects.Result;


public class Controller {
	
	private static final int MAX_NUM_OF_THREADS = 5;
	private volatile static boolean flag = true;
	
	static String domainName;
	static int maxPage;
	static HashSet<String> urls;
	static HashSet<Result> results;
	
	
	public static void initialize(String domain, int maxnumPage){
		domainName = domain;
		maxPage = maxnumPage;
		startController(domainName, maxPage);
	}
	public static void startController(String domainName, int maxPage) {
		// TODO Auto-generated method stub
		
		//User Input
		//int maxPage = 100;
		//String domainName = "1";
		
		int threadCount = 0;

		ConcurrentLinkedQueue<String> urlQueue = new ConcurrentLinkedQueue<String>();
		urlQueue.add(domainName);
		
		Set<String> urlSet = Collections.synchronizedSet(new HashSet<String>());
		Set<Result> resultSet = Collections.synchronizedSet(new HashSet<Result>());
		
		AtomicIntegerArray arr = new AtomicIntegerArray(MAX_NUM_OF_THREADS);
		for(int i=0;i<MAX_NUM_OF_THREADS;i++){
			arr.set(i, -100);
		}
		
		//List of runnable and threads that will be used to terminate threads
		ArrayList<PageCrawler> runnableList = new ArrayList<PageCrawler>();
		ArrayList<Thread> threadList = new ArrayList<Thread>();
		
		while(urlSet.size()<maxPage && threadCount < MAX_NUM_OF_THREADS){
			//create new threads
			
			PageCrawler runnable = new PageCrawler(urlQueue, urlSet, maxPage, arr, threadCount, resultSet);
			Thread crawler = new Thread(runnable);
			runnableList.add(runnable);
			threadList.add(crawler);
			
			crawler.start();
			threadCount++;
			
		}
		
		boolean done=false;
		
		//Stop threads when the number of pages crawled reach the maxPage.
		while(urlQueue.size()>0 || done == false || urlSet.size() <=maxPage){
			
			int numPagesAdded=0;
			for(int i=0;i<threadCount;i++){
				numPagesAdded+=arr.get(i);
			}
			if(numPagesAdded == 0)
				done = true;
			if(done == true){
				
				try {
				    Thread.sleep(1000);                 
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				
				numPagesAdded=0;
				for(int i=0;i<threadCount;i++){
					numPagesAdded+=arr.get(i);
				}
				if(numPagesAdded == 0)
					done = true;
			}
			
			if(urlSet.size() >= maxPage || (urlQueue.size()==0 && done==true)){
				
				System.out.println("***************Controller if*****************");
				int numThreadsCreated = runnableList.size();
				for(int i=0;i<numThreadsCreated;i++){
					
					runnableList.get(i).finish();
					try {
						threadList.get(i).join();
						System.out.println("No more pages to crawl. A thread is terminated by controller.");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				break;
			}
			
			
		}
		
		System.out.println("***************Controller terminating*****************");
		
		urls = new HashSet<String>(urlSet);
		results = new HashSet<Result>(resultSet);
	}
	
	
	

	
	
	
	/*
	 * Clear results from previous crawl
	 */
	public static void clearResults() {
		Controller.urls = new HashSet<String>();
		Controller.results = new HashSet<Result>();
	}
		
	/*
	 * Get urls that where previously traversed
	 */
	public static HashSet<String> getUrls() {
		return urls;
	}
	
	public static HashSet<Result> getResults() {
		return results;
	}
	/*public static void finish() {
		System.out.println("Controller's finish method called"); 
        flag = false;
    }*/
	
}