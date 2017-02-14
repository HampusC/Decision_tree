import java.util.ArrayList;

public class Attribute {
	
	private String name;
	private ArrayList<String> attributes;
	
	public Attribute(String name){
		this.name = name;
		attributes = new ArrayList<String>();
	}
	
	public String getName(){
		return name;
	}
	
	public ArrayList<String> getAttributes(){
		return attributes;
	}
	
	public void addAttribute(String attribute){
		attributes.add(attribute);
	}

}
