package Zad1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import com.badlogic.gdx.utils.Json;



public class Server {
	private static String pathclients = "data/clients.json";
	private static String pathtopics = "data/topicsclients.json";

	private ArrayList<String> clients = new ArrayList<String>();
	private HashMap<String,ArrayList<String>> topicsNClietns = new HashMap<String,ArrayList<String>>();
	private HashMap<String,ArrayList<String>> topicsNnews = new HashMap<String,ArrayList<String>>();
	
	private static String topicspath = "data/topics.json";
	private ArrayList<String> TopicList = new ArrayList<String>();
	
	
	
	
	
	public HashMap<String, ArrayList<String>> getTopicsNClietns() {
		return topicsNClietns;
	}



	public void setTopicsNClietns(HashMap<String, ArrayList<String>> topicsNClietns) {
		this.topicsNClietns = topicsNClietns;
	}



	public void addClient(String s) {
		this.clients.add(s);
	}
	
	
	
	public String getAllNewsforClient(String name) {
		ArrayList<String> tmp = new ArrayList<String>();
	for (Map.Entry<String, ArrayList<String>> entry: topicsNClietns.entrySet()) {
		if (entry.getValue().contains(name))
			tmp.add(entry.getKey());
		
	}
	
	
		return tmp.toString().replaceAll(System.lineSeparator(), " ").replace("\n", "").replace("\r", "");
			
	}
	
	
	public boolean testForClient(String s) throws IOException {
		loadListClients();
		if (this.clients.contains(s)) {
		//	System.out.println("jest");
			return true;
		} else {
			addClient(s);
			saveList();
			return false;
		}		
	}
	
	public void addTopic(String s) throws IOException {
		loadListTopics();
		if (this.TopicList.contains(s)){
		loadListTopicswithClients();
		this.topicsNClietns.put(s, new ArrayList<String>());
		saveListTopicClients();
				}
	}
	
	public void addClientToTopic(String topic,String client) throws IOException {
		
		
		loadListTopicswithClients();
	//	System.out.println(topicsNClietns);
		if (topicsNClietns.isEmpty() || !topicsNClietns.containsKey(topic)) {
			topicsNClietns =  new HashMap<String,ArrayList<String>>();
			this.topicsNClietns.put(topic, new ArrayList<String>());
			this.topicsNClietns.get(topic).add(client);
			saveListTopicClients();
		}
		if (!this.topicsNClietns.get(topic).contains(client)) {
		this.topicsNClietns.get(topic).add(client);
			saveListTopicClients();
		}
	//	else 
		//	System.out.println(client + " ma już przypisany " + topic);
	}
	public void deleteClientToTopic(String topic,String client) throws IOException {
		
		
		loadListTopicswithClients();
	//	System.out.println(topicsNClietns);
		if (topicsNClietns.isEmpty() || !topicsNClietns.containsKey(topic)) {
			return;
		}
		if (this.topicsNClietns.get(topic).contains(client)) {
		this.topicsNClietns.get(topic).remove(client);
			saveListTopicClients();
		}
	}
	
	
	
	
	
	public void saveListTopicClients(){
		 HashMap<String,String> tmp = new HashMap<String,String>();
		for (Map.Entry<String, ArrayList<String>> entry: topicsNClietns.entrySet()) {
		tmp.put(entry.getKey(), entry.getValue().toString().replace("[", "").replace("]","").replace(",", "").replace("\"", ""));
		
		}
		
		Json json = new Json();
		
		
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(pathtopics);
		
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
	
	
	public void loadListTopicswithClients() throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(pathtopics));
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
		this.topicsNClietns = new HashMap<String,ArrayList<String>>();
		 HashMap fromJson = json.fromJson(HashMap.class, everything);
		 //System.out.print(json.prettyPrint(fromJson));
		 if (fromJson!=null) {
		HashMap<String,String> tmp =  fromJson;
		 for (Map.Entry<String, String> entry: tmp.entrySet()) {
			 String tmps = entry.getValue().toString();
			 String str[] = tmps.split(" ");
			 ArrayList<String> al = new ArrayList<String>();
			 for (int i = 0; i < str.length;i++) {
				 al.add(str[i]);
			// System.out.println(str[i]);
			 }
			 this.topicsNClietns.put(entry.getKey(), al);
		 }
		 }
		//this.topicsNClietns = json.fromJson(HashMap.class, everything);
	//	System.out.println(topicsNClietns.toString());
	}
	
	
	public void loadListClients() throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(pathclients));
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
		
		this.clients = json.fromJson(ArrayList.class, everything);
		//System.out.println(clients.toString());
	}
	
		
		
	
	
	
	
	public void saveList(){
		
		Json json = new Json();
		
		
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(pathclients);
		
	    byte[] strToBytes = json.prettyPrint(clients).getBytes();
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
	
	
	private ServerSocketChannel ssc = null;
	private Selector selector = null;
	
	
	public Server(String host, int port ) throws IOException {
		loadListTopicswithClients();
		loadListClients();
		loadListTopics();
		//System.out.println(this.TopicList);
		ssc = ServerSocketChannel.open();
		
		ssc.configureBlocking(false);
		ssc.socket().bind(new InetSocketAddress(host,port));
		selector = Selector.open();
		ssc.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("Server działa");
		
		serverWorkflow();
		
		
	}



	private void serverWorkflow() throws IOException {
	//	System.out.println("open");
	//	System.out.println(ssc.isOpen());
		while(ssc.isOpen()) {
			selector.select();
			
			Set keys = selector.selectedKeys();
			Iterator it = keys.iterator();
			while(it.hasNext()) {
				SelectionKey key = (SelectionKey) it.next();
				it.remove();
				if (key.isAcceptable()) {
				//	System.out.println("key");
				//	System.out.println(key.isAcceptable());
					SocketChannel cc = ssc.accept();
					cc.configureBlocking(false);
					cc.register(selector, SelectionKey.OP_READ);
					
				continue;
				}
			if(key.isReadable()) {
			//	System.out.println("readable");
			//	System.out.println(key.isReadable());
				SocketChannel cc =(SocketChannel) key.channel();
				request(cc);
				continue;
			}
			}
			
			}
			
		//System.out.println(ssc.isOpen());	
				
			}

		private static Pattern reqPatt = Pattern.compile(" +", 3);
		private static Charset charset  = Charset.forName("UTF-8");
		private static final int BSIZE = 1024;
		private ByteBuffer bbuf = ByteBuffer.allocate(BSIZE);
		private StringBuffer reqString = new StringBuffer();
		
	private void request(SocketChannel cc) {
	//	System.out.print("open cc");
	//	System.out.println(cc.isOpen());
		if (!cc.isOpen())
			return;
		
		reqString.setLength(0);
		bbuf.clear();
	try {
	//	readLoop: 
		boolean tmpbool =true;
		//char[] tmp = System.lineSeparator().toCharArray();
			while(tmpbool) {
				
				int n = cc.read(bbuf);
				if( n > 0) {
				bbuf.flip();
				  CharBuffer cbuf = charset.decode(bbuf);
		          while(cbuf.hasRemaining()) {
		            char c = cbuf.get();
		            //System.out.print(c);
		          //  System.out.println("czy tu dotarłem while");
		        	  reqString.append(c);
		        	  if (c == '*') {
		        		 // System.out.println(tmpbool);
		        		  
		        		  tmpbool = false;
		        	  }
		        		  
		          }
			}
				
			}
		         // tmpbool = true;
		    //    System.out.println("reqstring:");
		   //     System.out.println(reqString);
		      
		 String[] req = reqPatt.split(reqString, 4);
		// System.out.println(reqPatt);
	      String cmd = req[0];
	      req[1].replaceAll("\\n", "");
	    //  System.out.println(req[1]);
	   //  System.out.println( req[3]);
	     
	      
     if (cmd.equals("1")) {             // koniec komunikacji
	    	  testForClient(req[1]);
	          writeResp(cc, 1, req[1]); 
	        //  break;// - zamknięcie kanału
	        //  cc.close();
	          // i gniazda
	         // cc.socket().close();
	      }
	      
	      else if (cmd.equals("2")) { 
	    	  testForClient(req[1]);// koniec komunikacji
	          writeResp(cc, 2, req[1]);          // - zamknięcie kanału
	     
	      }
	      
	      else if (cmd.equals("3")) {  
	    //	  for (int i = 0; i< req.length; i++ )
	    //	  System.out.println(req[i]);
	    	  
	    	 //System.out.println(testForClient(req[2]));// koniec komunikacji
	          writeResp(cc, 3, req[1], req[2]);          // - zamknięcie kanału
	         
	      }
	      else if (cmd.equals("4")) {    
	    	  testForClient(req[2]);
	    	  writeResp(cc, 4, req[1], req[2]);   
	    	  //cc.close();                      // i gniazda
	        //  cc.socket().close();
	      }
     
	      else if (cmd.equals("5")) {             //zmieniono news
	    	  testForClient(req[2]);
	    	  writeResp(cc, 5, req[1]);          
	         
	      }
     
     
	      else if (cmd.equals("6")) {   
	    	  System.out.println("test");//zmieniono news
	    	  testForClient(req[1]);
	    	  writeResp(cc, 6, req[1]);          
	         
	      }
	      else writeResp(cc, 8, null);             // nieznane zlecenie
		
    } catch (Exception exc) {                  // przerwane polączenie?
        exc.printStackTrace();
        try { cc.close();
              cc.socket().close();
        } catch (Exception e) {}
    }
  }
	


	  private StringBuffer remsg = new StringBuffer(); // Odpowiedź

	  
		private void writeResp(SocketChannel cc, int i, String topic,String client) throws IOException {
			
			String tmplist = "";

			if (i == 3) {
				loadListTopicswithClients();
				//test one
				if (this.topicsNClietns.containsKey(topic)) {
					ArrayList tmp = this.topicsNClietns.get(topic);
					System.out.println(tmp.toString());
							if (!tmp.contains(client))
					this.topicsNClietns.get(topic).add(client);
				}else {
					ArrayList tmp = new ArrayList();
					tmp.add(client);
					this.topicsNClietns.put(topic, tmp);
				}
				saveListTopicClients();
				
				
				//addClientToTopic(topic,client);
				//tmplist = getAllNewsforClient(client);
			//	 remsg.append(tmplist);	   
		    	// remsg.append(System.lineSeparator());
		    	
			}
			
			if (i == 4) {
				loadListTopicswithClients();
				if (this.topicsNClietns.containsKey(topic)) {
					ArrayList tmp = this.topicsNClietns.get(topic);
					if (tmp.contains(client))
						this.topicsNClietns.get(topic).remove(client);
				}
				saveListTopicClients();
		    	
			}
			

			
			
			
			//System.out.println("czy Tu jestem");
			 ByteBuffer buf = charset.encode(CharBuffer.wrap(remsg));
			 System.out.println("mess :" + remsg);
			    cc.write(buf);
			    buf.clear();
			//    System.out.println("czy tu dotarłem Second");

			    
		
		}
	  
	  
	  
	private void writeResp(SocketChannel cc, int i, String mess) throws IOException {
		String tmplist = "";
		loadListTopics();
		System.out.println(this.TopicList.toString());
	//	System.out.println(i);
		remsg.setLength(0);
		System.out.println(mess);
		if (i == 6) {
			//System.out.println("Co ja tutaj");
			loadListTopicswithClients();
			ArrayList<String> tmpo = new ArrayList<String>();
			Iterator it = topicsNClietns.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String,ArrayList<String>> tmp = (Map.Entry<String,ArrayList<String>>) it.next();	
				if(this.TopicList.contains(tmp.getKey())){
				if( tmp.getValue().contains(mess)) {
					System.out.println(tmp.toString());
					
					tmpo.add(tmp.getKey());
				}
				}
			}
		//	System.out.println(tmpo);
			StringBuilder test = new StringBuilder();
			tmpo.forEach((s) -> test.append(loadNews(s)));
		//	System.out.println(test);
			 remsg.append(test);
	    	 remsg.append(System.lineSeparator());
	    	 System.out.println("6 mess == " + remsg);
			}
	    	
		
		
		
		
		
		if (i == 1)
			//System.out.println(displayAllTopics());
			remsg.append(displayAllTopics());
		    remsg.append(System.lineSeparator());
		
		if (i == 2) {
			
			tmplist = getAllNewsforClient(mess);
			tmplist = tmplist.replaceAll(System.lineSeparator(), "");
		//	System.out.println(tmplist);
			
		    remsg.append(tmplist);	   
		    remsg.append(System.lineSeparator());
		  //  System.out.println("mess :" + remsg);
		}
	
		if (i == 5) {
			  loadListTopics();// zmieniono temat
	    	//  System.out.println(this.TopicList);
	    	  remsg.append("Działa");
	    	  remsg.append(" aaa");
	    	  remsg.append(System.lineSeparator());
	    	
		}
		 ByteBuffer buf = charset.encode(CharBuffer.wrap(remsg));
		// System.out.println("mess :" + remsg);
		//    System.out.println("czy tu dotarłem First");
		    cc.write(buf);
		    buf.clear();
		 //  cc.close();
		//    cc.shutdownOutput();
		  
		   
		//    System.out.println(buf);
		  //  System.out.println("czy tu dotarłem Second");

		    
	
	}
	private static String path = "data/";
	public String loadNews(String s)  {
		String str = "";
		BufferedReader br;
		try {
			
			String pathT = path + s +".json";
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
			
			str = json.fromJson(ArrayList.class, everything).toString();
			//System.out.println(wordList[0]);
		//	json.prettyPrint(list);
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		str = str + " aaaaaaa";
		return str;
	}

		
		
		
	
	
	
	
	
	
	
	
	
	
	
	public String displayAllTopics() {
		
		int i = 0;
		StringBuilder sb = new StringBuilder("");
		for (String str : TopicList) {
			sb.append(i);
			sb.append(": ");
			sb.append(str);
			sb.append(" aaa");
			i++;

		}
			
		return sb.toString();
		
	}
			
	public Server() {
		super();
	}
	public static void main(String[] args) {
	    try {
	      int port = 6424;

	    
	      new Server( "192.168.1.101", port);
	    } catch(Exception exc) {
	        exc.printStackTrace();
	        System.exit(1);
	    }
	  }	
		
		
	}
	

