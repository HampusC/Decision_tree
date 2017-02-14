import java.util.ArrayList;

public class TreeBuilder {
	
	private ArrayList<Attribute> attributes;
	private ArrayList<ArrayList<String>> examples;
	private ArrayList<ArrayList<String>> parentExamples;
	private Node tree;
	
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
		tree = decisionTreeLearning(examples, attributes, parentExamples);
	}
	
	public Node decisionTreeLearning(ArrayList<ArrayList<String>> examples, ArrayList<Attribute> attributes, ArrayList<ArrayList<String>> parentExamples){
		//Slänga in new attribute här? Verkar inte så och blir null pionter men vet ej annars hur man ska göra.
		if(examples.isEmpty()){
			//return pluralityValue(parentExamples);
			return new Node("example", "empty");
		} else if(false) { //all examples have the same classification.
			return new Node("SUPERLUL", "MEGALOL");
		} else if(attributes.isEmpty()){
			//return pluralityValue(examples);
			return new Node("attributes", "empty");
		}
		else {
			Attribute a = attributes.get(0);
			Node newTree = new Node(a.getName(), "tempus"); //Make new tree. Send better string.
			ArrayList<String> classifications = a.getClassifications();
			for(String s : classifications){
				ArrayList<ArrayList<String>> newExamples = new ArrayList<ArrayList<String>>();
				for(ArrayList<String> tempClassifications : examples) {
					if(tempClassifications.get(a.getIndex()).equals(s)){
						newExamples.add(tempClassifications);
					}
				}
				ArrayList<Attribute> newAttributes = new ArrayList<Attribute>();
				for(Attribute at : attributes){
					newAttributes.add(at);
				}
				newAttributes.remove(a);
				Node subTree = decisionTreeLearning(newExamples, newAttributes, examples);
				newTree.addChild(subTree);
			}
			return newTree;
		}
		//TODO Add more parts of the algorithm.
	}
	
	private Node pluralityValue(ArrayList<ArrayList<String>> parentExamples) {
		for(ArrayList<String> currentExamples : parentExamples){
			
		}
		return new Node("LUL", "LOL");
		//TODO Fix this function.
	}

	private class Node {
		
		private String path;
        private String value;
        private ArrayList<Node> children;
		
		public Node(String path, String value){
			this.path = path;
			this.value = value;
			children = new ArrayList<Node>();
		}
		
		public void addChild(Node child){
			children.add(child);
		}
		
		public void print() {
	        print("", true);
	    }

	    private void print(String prefix, boolean isTail) {
	        System.out.println(prefix + (isTail ? "└── " : "├── ") + path + ", " + value);
	        for (int i = 0; i < children.size() - 1; i++) {
	            children.get(i).print(prefix + (isTail ? "    " : "│   "), false);
	        }
	        if (children.size() > 0) {
	            children.get(children.size() - 1)
	                    .print(prefix + (isTail ?"    " : "│   "), true);
	        }
	    }
		
		//TODO probably needs more functions, lite get and add.
		
        
    }
	
	public void print(){
		tree.print();
	}

}
