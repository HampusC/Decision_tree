import java.util.ArrayList;

public class TreeBuilder {
	
	private ArrayList<Attribute> attributes;
	private ArrayList<ArrayList<String>> examples;
	private ArrayList<ArrayList<String>> parentExamples;
	
	public TreeBuilder(){
		attributes = new ArrayList<Attribute>();
		examples = new ArrayList<ArrayList<String>>();
		parentExamples = new ArrayList<ArrayList<String>>();
	}
	
	public void setup(){
		Reader reader = new Reader();
		reader.read();
		attributes = reader.getAttributes();
		examples = reader.getExamples();
	}
	
	public void build(){
		Node tree = decisionTreeLearning(examples, attributes, parentExamples);
	}
	
	public Node decisionTreeLearning(ArrayList<ArrayList<String>> examples, ArrayList<Attribute> attributes, ArrayList<ArrayList<String>> parentExamples){
		if(examples.isEmpty()){
			return pluralityValue(parentExamples);
		}
		return null;
		//TODO Add more parts of the algorithm.
	}
	
	private Node pluralityValue(ArrayList<ArrayList<String>> parentExamples) {
		for(ArrayList<String> currentExamples : parentExamples){
			
		}
		return null;
		//TODO Fix this function.
	}

	private class Node {
		
		private Node parent;
		private String path;
        private String value;
		
		public Node(Node parent, String path, String value){
			this.parent = parent;
			this.path = path;
			this.value = value;
		}
		
		//TODO probably needs more functions, lite get and add.
		
        private ArrayList<Node> children;
    }

}
