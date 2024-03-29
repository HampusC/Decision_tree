
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {
	
	private ArrayList<Attribute> attributes;
	private ArrayList<ArrayList<String>> examples;
	private int attributeCounter;

	public Reader(){
		attributes = new ArrayList<Attribute>();
		examples = new ArrayList<ArrayList<String>>();
		attributeCounter = 0;
	}

	public void read(String path){
		Scanner scan = null; 
		try{
			scan = new Scanner(new File(path));
		} catch(Exception e){
			try {
				scan = new Scanner(new File("willWait.arff"));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
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
				Attribute currentAttribute = new Attribute(name, attributeCounter);
				attributeCounter++;
				String values = split[2];
				values = values.substring(1, values.length()-1);
				String[] valueArray = values.split(",");
				for(String s : valueArray){
					currentAttribute.addValue(s);
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
