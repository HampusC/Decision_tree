import java.util.ArrayList;

public class TreeBuilder {
	
	private ArrayList<ArrayList<String>> examples;
	private ArrayList<Attribute> attributes;
	private Node tree;
	
	public TreeBuilder(){
		examples = new ArrayList<ArrayList<String>>();
		attributes = new ArrayList<Attribute>();
	}
	
	public void setup(){
		Reader reader = new Reader();
		reader.read();
		attributes = reader.getAttributes();
		examples = reader.getExamples();
	}
	
	public void build(){
		Attribute a = attributes.get(0);
		ArrayList<Attribute> newAttributes = new ArrayList<Attribute>();
		for(Attribute at : attributes){
			newAttributes.add(at);
		}
		newAttributes.remove(a);
		tree = decisionTreeLearning(examples, a, newAttributes);
	}
	
	public Node decisionTreeLearning(ArrayList<ArrayList<String>> examples, Attribute targetAttribute, ArrayList<Attribute> attributes){
		Node newTree = new Node();
		
		boolean allPositive = true;
		String allPositiveValue = null;
		for(ArrayList<String> currentExample : examples){
			if(!currentExample.get(currentExample.size() - 1).equals("yes")){
				allPositive = false;
				break;
			}
			allPositiveValue = currentExample.get(targetAttribute.getIndex());
		}
		if(allPositive){
			newTree.setPath(targetAttribute.getName());
			newTree.setValue(allPositiveValue);
			newTree.setOutcome(("Yes"));
			return newTree;
		}
		
		boolean allNegative = true;
		String allNegativeValue = null;
		for(ArrayList<String> currentExample : examples){
			if(!currentExample.get(currentExample.size() - 1).equals("no")){
				allNegative = false;
				break;
			}
			allNegativeValue = currentExample.get(targetAttribute.getIndex());
		}
		if(allNegative){
			newTree.setPath(targetAttribute.getName());
			newTree.setValue(allNegativeValue);
			newTree.setOutcome(("No"));
			return newTree;
		}
		
		if(attributes.isEmpty()){
			//FIX
			newTree.setPath("Sqeeuzy");
			newTree.setValue(";)");
			return newTree;
		}
		else {
			Attribute a = attributes.get(0);
			newTree.setPath(targetAttribute.getName());
			//newTree.setValue("Maybe"); //Ta en funderare.
			ArrayList<String> classifications = a.getClassifications();
			for(String s : classifications){
				//newTree.setValue(s);
				ArrayList<ArrayList<String>> newExamples = new ArrayList<ArrayList<String>>();
				for(ArrayList<String> tempClassifications : examples) {
					if(tempClassifications.get(a.getIndex()).equals(s)){
						newExamples.add(tempClassifications);
					}
				}
				if(newExamples.isEmpty()){
					Node subTree = new Node();
					subTree.setPath("examples"); //FEL
					subTree.setValue("empty"); //FFEL:
					newTree.addChild(subTree);
				}
				ArrayList<Attribute> newAttributes = new ArrayList<Attribute>();
				for(Attribute at : attributes){
					newAttributes.add(at);
				}
				newAttributes.remove(a);
				Node subTree = decisionTreeLearning(newExamples, a, newAttributes);
				newTree.addChild(subTree);
			}
			return newTree;
		}
	}

	private class Node {
		
		private String path;
        private String value;
        private String outcome;
        private ArrayList<Node> children;
		
		public Node(){
			path = "";
			value = ""; 
			outcome = "";
			children = new ArrayList<Node>();
		}
		
		public void setPath(String path){
			this.path = path;
		}
		
		public void setValue(String value){
			this.value = value;
		}
		
		public void setOutcome(String outcome){
			this.outcome = outcome;
		}
		
		public void addChild(Node child){
			children.add(child);
		}
		
		public void print() {
	        print("", true);
	    }

	    private void print(String prefix, boolean isTail) {
	        System.out.println(prefix + (isTail ? "--- " : "|--- ") + path + ", " + value + ", " + outcome);
	        for (int i = 0; i < children.size() - 1; i++) {
	            children.get(i).print(prefix + (isTail ? "    " : "|   "), false);
	        }
	        if (children.size() > 0) {
	            children.get(children.size() - 1)
	                    .print(prefix + (isTail ?"    " : "|   "), true);
	        }
	    }
		
    }
	
	public void print(){
		tree.print();
	}

}
