package Zad1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.badlogic.gdx.utils.Json;

public class Admin {
	private static String path = "data/";
	
	private ArrayList<String> newsList = new ArrayList<String>();
	private static final int BSIZE = 1024;
	private ByteBuffer bbuf = ByteBuffer.allocate(BSIZE);
	private static String topicspath = "data/topics.json";
	private ArrayList<String> TopicList = new ArrayList<String>();
	private String editor = "";
	private Socket sock = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private String mainHost;
	private int mainPort;
	private static Charset charset  = Charset.forName("UTF-8");
	
	
	public String getEditor() {
		return editor;
	}


	public void setEditor(String editor) {
		this.editor = editor;
	}


	public ArrayList<String> getNewsList() {
		return newsList;
	}


	public void setNewsList(ArrayList<String> newsList) {
		this.newsList = newsList;
	}


	public ArrayList<String> getTopicList() {
		return TopicList;
	}


	public void setTopicList(ArrayList<String> topicList) {
		TopicList = topicList;
	}
	private SocketChannel sChannel = null;
	public Admin() {
		super();
		loadListTopics();
	}
	

	
 /// konstruktor
	public Admin(String host, int port) throws IOException {
		super();
		loadListTopics();
		this.mainHost = host;
		this.mainPort = port;
		 // connect(host, port);SocketChannel socketChannel = createServerSocketChannel();
		

		try {
		    sock = new Socket(this.mainHost, this.mainPort);
		    out = new PrintWriter(sock.getOutputStream(), true);
		    in = new BufferedReader(
		             new InputStreamReader(
		                 sock.getInputStream()));
		 
	

		    
		  } catch (UnknownHostException e) {
		      System.err.println("Nieznany host: "+this.mainHost);
		      System.exit(2);
		  } catch (IOException e) {
		      System.err.println("I/O err dla");
		      System.exit(3);
		  } catch (Exception exc) {
		      exc.printStackTrace();
		      System.exit(4);
		  }
}

	
	
	
	
	
	
/*	public void connect() {
//		try {
//			    sock = new Socket(this.mainHost, this.mainPort);
//			    out = new PrintWriter(sock.getOutputStream(), true);
			    in = new BufferedReader(
			             new InputStreamReader(
			                 sock.getInputStream()));
			 
			  
			  } catch (UnknownHostException e) {
			      System.err.println("Nieznany host: "+this.mainHost);
			      System.exit(2);
			  } catch (IOException e) {
			      System.err.println("I/O err dla");
			      System.exit(3);
			  } catch (Exception exc) {
			      exc.printStackTrace();
			      System.exit(4);
			  }
	}
	
*/	private StringBuffer reqString = new StringBuffer();
			public boolean reloadTopics() throws IOException, InterruptedException {
				out = new PrintWriter(sock.getOutputStream(), true);
				System.out.println("Zmieniono listę tematów");
				StringBuilder sb= new StringBuilder();
				sb.append("5 ");
				sb.append("reload");
				sb.append(" *");
				out.println(sb);
				out.flush();
			//	in.read();
				System.out.println(in.readLine());
				
				//in.close();
	//----------------------------------
		/*		
				boolean tmpbool =true;
				//char[] tmp = System.lineSeparator().toCharArray();
					while(tmpbool) {
						CharBuffer cbuf = charset.decode(bbuf);
						int n = in.read(cbuf);
						if( n > 0) {
						cbuf.flip();
						  
				          while(cbuf.hasRemaining()) {
				            char c = cbuf.get();
				            System.out.print(c);
				            System.out.println("czy tu dotarłem while");
				        	  reqString.append(c);
				        	  if (c == '*') {
				        		  System.out.println("true");
				        		  tmpbool = false;
				        	  }
				        		  
				          }
					}
						
					}
				
				
				
				
				
				
			*/	
				
				
				
				
				// out.close();
				//in.
			//	in.read(null)
			//	String resp = charset.decode(in.read());
				//System.out.print(in.read());
				//  in.close();
			//	    out.close();
			//	    sock.close();
			//	System.out.println(resp);
			 
				return true;
			}
			
			public boolean reloadNews(String s) throws IOException {
				System.out.println("Zmieniono listę tematów");
				StringBuilder sb= new StringBuilder();
				sb.append("6 ");
				sb.append("News");
				sb.append(" *");
				out.println(sb);
				//String resp = in.readLine();
			//	System.out.print("Odpowiedz: ");
			  
			//	System.out.println(resp);
			 
				return true;
			}
			

	//ustawienie edytowanego tematu
	public String whatEdit(String s) {
		if(!(TopicList.contains(s)))
				return "Brak tematu";
		else {
		this.editor = s;
		loadNews();
		return s;
		}
		
	}
	// dodanie tematu do listy
	public void addNews(String s) {
		if (this.newsList == null)
			newsList =  new ArrayList<String>(); 
		this.newsList.add(s);
		saveNews();
		System.out.println(newsList.size());
	}
	
	
	// wyświetlanie newsow
	public String MyString() {
		StringBuilder sb = new StringBuilder();
		for (String s : newsList) {
			sb.append(s);
			sb.append("\n");
		}
		return sb.toString();

	}

	
	// usuniecie newsu
	public void deleteNews(int i) {
		this.newsList.remove(i);
		saveNews();
	}
	
	// dodawanie tematu
	public void addTopic(String s) throws IOException {
		if (TopicList == null)
			TopicList = new ArrayList<String>();
		if(!TopicList.contains(s)) {
			TopicList.add(s);
		String pathT = path + s +".json";
		
		File f = new File(pathT);
		if(!f.exists()){
		  f.createNewFile();
		  saveNews(s);
		}else{
		  System.out.println("File already exists");
		}
		
		}
		saveListTopics();
	}
	
	// usuwanie tematu
	public void deleteTopic(String i) {
		this.TopicList.remove(i);
		saveListTopics();
	}
	
	public String displayEdTop(String s) {
		int i = 0;
		StringBuilder sb = new StringBuilder("");
		for (String str : TopicList) {
			sb.append(i);
			sb.append(": ");
			sb.append(str);
			sb.append("\r\n");
			i++;

		}
			
		return sb.toString();
		
	}
	public String displayAllTopics() {
		
		int i = 0;
		StringBuilder sb = new StringBuilder("");
		for (String str : TopicList) {
			sb.append(i);
			sb.append(": ");
			sb.append(str);
			sb.append("\r\n");
			i++;

		}
			
		return sb.toString();
		
	}
	
	
	
public void saveNews(String s){
	 ArrayList<String> tmp = new ArrayList<String>();
	 tmp.add("aaa");
	 tmp.remove("aaa");
		Json json = new Json();
		
		String pathT = path + s +".json";
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(pathT);
		
	    byte[] strToBytes = json.prettyPrint(tmp).getBytes();
	    outputStream.write(strToBytes);
	    outputStream.flush();
	    outputStream.close();
		
		//file.writeString(wordList[0].toString(), false);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

public void saveNews(){
	
	Json json = new Json();
	
	String pathT = path + this.editor +".json";
	FileOutputStream outputStream;
	try {
		outputStream = new FileOutputStream(pathT);
	
    byte[] strToBytes = json.prettyPrint(newsList).getBytes();
    outputStream.write(strToBytes);
    outputStream.flush();
    outputStream.close();
	
	//file.writeString(wordList[0].toString(), false);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}		
}




public void loadNews()  {
	BufferedReader br;
	try {
		
		String pathT = path + this.editor +".json";
		br = new BufferedReader(new FileReader(pathT));
	    StringBuilder sb = new StringBuilder();
	    String line = br.readLine();

	    while (line != null) {
	        sb.append(line);
	        sb.append(System.lineSeparator());
	        line = br.readLine();
	    }
	    String everything = sb.toString();
	//System.out.println(everything);
	    br.close();
		Json json = new Json();
		//json.setIgnoreUnknownFields(true);
		
		this.newsList = json.fromJson(ArrayList.class, everything);
		//System.out.println(wordList[0]);
	//	json.prettyPrint(list);
	
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
public void saveListTopics(){
		
		Json json = new Json();
		
		
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(topicspath);
		
	    byte[] strToBytes = json.prettyPrint(TopicList).getBytes();
	    outputStream.write(strToBytes);
	    outputStream.flush();
	    outputStream.close();
		
		//file.writeString(wordList[0].toString(), false);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}






public void loadListTopics()  {
	BufferedReader br;
	try {
		

		br = new BufferedReader(new FileReader(topicspath));
	    StringBuilder sb = new StringBuilder();
	    String line = br.readLine();

	    while (line != null) {
	        sb.append(line);
	        sb.append(System.lineSeparator());
	        line = br.readLine();
	    }
	    String everything = sb.toString();
	//System.out.println(everything);
	    br.close();
		Json json = new Json();
		//json.setIgnoreUnknownFields(true);
		
		this.TopicList = json.fromJson(ArrayList.class, everything);
		//System.out.println(wordList[0]);
	//	json.prettyPrint(list);
	
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
}


	
}
