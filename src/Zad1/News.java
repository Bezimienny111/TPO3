package Zad1;

import java.util.ArrayList;
import java.util.List;

public class News {

	private String name;
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	private List<String> newsList = new ArrayList<String>();
	
	public News(String name, List<String> newsList) {
		super();
		this.name = name;
		this.newsList = newsList;
	}

	
	public void addToList(String in) {
		newsList.add(in);
	}
	
	public void deleteFromList(int in) {
		newsList.remove(in);
	}
	
	//public String getList(String nameIn) {
		//if (this.name.equals(nameIn))
	//			return MyString(this.newsList);
		//return "error";
		
	//}

	public String MyString(String test) {
		StringBuilder sb = new StringBuilder();
		if(test.equals(this.name)) {		
		sb.append(name);
		sb.append("\r\n");
		for (String s : newsList) {
			sb.append(s);
			sb.append("\n");
		}
		return sb.toString();
		}else
		
		return "error";
	}
}
