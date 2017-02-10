
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {
	
	private ArrayList<Attribute> attributes;
	private ArrayList<ArrayList<String>> examples;

	public Reader(){
		attributes = new ArrayList<Attribute>();
		examples = new ArrayList<ArrayList<String>>();
	}

	public void read(){
		Scanner scan = null; 
		try{
			scan = new Scanner(new File("./data.arff"));
		} catch(Exception e){
			
		}
		
		boolean inData = false;
		while(scan.hasNext()){
			String currentLine = scan.nextLine();
			String[] split;
			if(inData){
				split = currentLine.split(",");
				ArrayList<String> exampleValues = new ArrayList<String>();
				for(String s : split){
					exampleValues.add(s);
				}
				examples.add(exampleValues);
			}
			split = currentLine.split(" ");
			if(split[0].equals("@attribute")){
				String name = split[1];
				Attribute currentAttribute = new Attribute(name);
				String values = split[2];
				values = values.substring(1, values.length()-1);
				String[] valueArray = values.split(",");
				for(String s : valueArray){
					currentAttribute.addAttribute(s);
				}
				attributes.add(currentAttribute);
			} else if(split[0].equals("@data")){
				inData = true;
			}
		}
		
	}
	
	public ArrayList<Attribute> getAttributes(){
		return attributes;
	}
	
	public ArrayList<ArrayList<String>> getExamples(){
		return examples;
	}
	
}
