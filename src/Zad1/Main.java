package Zad1;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		News test = new News("test",new ArrayList());
		test.addToList("Pierwszy chujowy news");
		test.addToList("Drugi chujowy news");
		test.addToList("Trzeci chujowy news");
		test.addToList("Czwarty chujowy news");
		
		System.out.print(test.MyString("test"));
		
		System.out.print(test.MyString("aas"));
		System.out.println();
		
		System.out.println("------------");
		
		Client testC = new Client();
		System.out.println(testC.setLogin("Bezi"));
		System.out.println(testC.getLogin());
	//	System.out.println(testC.addTopic("testowy"));
	//	testC.deleteTopic("testowy");
		
//System.out.println();
		
	System.out.println("------------");
		Server testS = new Server();
		testS.addClient("Bezi");
		testS.addClient("Juve");
		testS.saveList();
		testS = new Server();
		testS.loadListClients();
		testS.testForClient("Bezi");
		
	//	testS.testForClient("Norman");
	//	testS.testForClient("Norman");
		
		testS.addTopic("Pizza");
		testS.addTopic("Muzyka");
	//	testS.addTopic("Piwo");
	//	testS.addTopic("Coś");
		testS.addClientToTopic("Pizza", "Bezi");
	//	testS.addClientToTopic("Muzyka", "Bezi");
	//	testS.addClientToTopic("Piwo", "Bezi");
	//	testS.addClientToTopic("Coś", "Bezi");
	//	testS.addClientToTopic("Piwo", "Norman");
		testS.addClientToTopic("Pizza", "Norman");
	//	testS.addClientToTopic("Pizza", "Bezi");
		testS.saveListTopicClients();
		System.out.print(testS.getTopicsNClietns());
	//	testS.saveListTopic();
	//	testS.addClientToTopic("Pizza", "Juve");
	//	testS.loadListtopics();
		
	//	System.out.print(testS.getAllNewsforClient("Norman"));
	//	System.out.print(testS.getAllNewsforClient("Bezi"));
		
		
	//	Admin adminTest = new Admin();
	//	adminTest.addTopic("nature");
	//	adminTest.addTopic("porn");
	//	adminTest.addTopic("music");
	//	adminTest.addTopic("stuff");
	//	System.out.print(adminTest.displayEdTop("a"));
	//	adminTest.whatEdit("nature");
	//	adminTest.whatEdit("dupa");
	//	adminTest.addEdtop("test");
	//	adminTest.addEdtop("test2");
	//	adminTest.addEdtop("test3");
	//	adminTest.whatEdit("porn");
	//	adminTest.addEdtop("test");
	//	adminTest.addEdtop("test4");
	
	//	adminTest.deleteTopic(1);
	//	System.out.println(adminTest.MyString());
	//	adminTest.deleteTopic(2);
		//adminTest.deleteTopic(1);
	//	adminTest.deleteTopic(1);
	//	adminTest.deleteTopic(1);
	//	adminTest.deleteTopic(1);adminTest.deleteTopic(4);
	//	System.out.println(adminTest.MyString());
		System.out.println(testS.getAllNewsforClient("Bezi"));
}
}
