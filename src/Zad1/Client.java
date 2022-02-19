package Zad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

public class Client {

	private String login = "empty";
	public String allTopics ="";
	public String choosenTopics ="";
	private static final int BSIZE = 1024;
	private ByteBuffer bbuf = ByteBuffer.allocate(BSIZE);
	public String outer = "";


public String getLogin() {
		return login;
	}


public String setLogin(String loginIn) {
	this.login = loginIn;
	StringBuilder sb= new StringBuilder();
	sb.append("1 ");
	sb.append(this.login);
	sb.append(" *");
	return  sb.toString();
}

public String getMyTopics() {
	StringBuilder sb= new StringBuilder();
	sb.append("2 ");
	sb.append(this.login);
	sb.append(" *");
	return  sb.toString();
}


public String addTopic(String topicIn) {
	StringBuilder sb= new StringBuilder();
	sb.append("3 ");
	sb.append(topicIn);
	sb.append(" ");
	sb.append(this.login);
	sb.append(" *");
	return  sb.toString();
}

public String deleteTopic(String topicIn) {
	StringBuilder sb= new StringBuilder();
	sb.append("4 ");
	sb.append(topicIn);
	sb.append(" ");
	sb.append(this.login);
	sb.append(" *");
	return  sb.toString();
}

public String getNews() {
	StringBuilder sb= new StringBuilder();
	sb.append("6 ");
	sb.append(this.login);
	sb.append(" *");
	return  sb.toString();
}

public Client() {
	super();
}
private Socket sock = null;
private PrintWriter out = null;
public BufferedReader in = null;
private SocketChannel sc =null;

public Client(String host, int port) {
  try {
    sock = new Socket(host, port);
 //  
    in = new BufferedReader(
             new InputStreamReader(
                 sock.getInputStream()));
	
	//  sc = SocketChannel.open(new InetSocketAddress(host, port));
	//  sc.configureBlocking(true);
	 out = new PrintWriter(sock.getOutputStream(), true);
	  
	  
	  
	  
   // makeRequest(setLogin("Bezi"));
  ////  in.close();
  
  //  sock.close();
  } catch (UnknownHostException e) {
      System.err.println("Nieznany host: "+host);
      System.exit(2);
  } catch (IOException e) {
      System.err.println("I/O err dla");
      System.exit(3);
  } catch (Exception exc) {
      exc.printStackTrace();
      System.exit(4);
  }
}



public boolean reqForEdit(String req) throws IOException {
	
	System.out.println("Request: " + req);
	 out.println(req);
	 out.flush();
	 in.readLine();
	// System.out.println(in.readLine());
	this.choosenTopics = in.readLine();
//	this.allTopics = this.allTopics.replaceAll("aaa", System.lineSeparator());

	  return true;
	}

public boolean reqForNews(String req) throws IOException {
	
	System.out.println("Request: " + req);
	 out.println(req);
	 out.flush();
	// in.readLine();
	// System.out.println(in.readLine());
	 
	this.outer = in.readLine();
	System.out.println(this.outer);
	this.outer = this.outer.replaceAll(" aaaaaaa",System.lineSeparator());
	in.readLine();
//	this.allTopics = this.allTopics.replaceAll("aaa", System.lineSeparator());
	  return true;
	}


public boolean reqforLoad(String req) throws IOException {
	
	System.out.println("Request: " + req);
	 out.println(req);
	 out.flush();
	 in.readLine();
	// System.out.println(in.readLine());
	this.choosenTopics = in.readLine();
	this.choosenTopics = this.choosenTopics.replaceAll(",", System.lineSeparator());

//	this.allTopics = this.allTopics.replaceAll("aaa", System.lineSeparator());

	  return true;
	}

public boolean reqforAll(String req) throws IOException {
	
  System.out.println("Request: " + req);
  out.println(req);
  out.flush();

this.allTopics = in.readLine();
this.allTopics = this.allTopics.replaceAll("aaa", System.lineSeparator());

  return true;
}

public static void main(String[] args) {
  new Client("192.168.1.101", 6424);
}
}









