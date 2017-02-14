import java.util.ArrayList;

public class Attribute {
	
	private String name;
	private ArrayList<String> classifications;
	private int index;
	
	public Attribute(String name, int index){
		this.name = name;
		classifications = new ArrayList<String>();
		this.index = index;
	}
	
	public String getName(){
		return name;
	}
	
	public ArrayList<String> getClassifications(){
		return classifications;
	}
	
	public int getIndex(){
		return index;
	}
	
	public void addClassification(String attribute){
		classifications.add(attribute);
	}

}
