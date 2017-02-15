import java.util.ArrayList;

public class Attribute {
	
	private String name;
	private ArrayList<String> values;
	private int index;
	
	public Attribute(String name, int index){
		this.name = name;
		values = new ArrayList<String>();
		this.index = index;
	}
	
	public String getName(){
		return name;
	}
	
	public ArrayList<String> getValues(){
		return values;
	}
	
	public int getIndex(){
		return index;
	}
	
	public void addValue(String attribute){
		values.add(attribute);
	}

}
