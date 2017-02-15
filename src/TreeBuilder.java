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
		ArrayList<Attribute> tempusAttributes = new ArrayList<Attribute>();
		for(Attribute at : attributes){
			if(!attributes.get(attributes.size() - 1).equals(at)){
				tempusAttributes.add(at);
			}
		}
		
//		Attribute a = attributes.get(0);
		Attribute a = importance(attributes, examples);
//		System.out.println(a.getName());
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
			//Attribute a = attributes.get(0);
			System.out.println("===========================");
			Attribute a = importance(attributes, examples);
//			System.out.println();
			for(Attribute sq : attributes){
				System.out.print(sq.getName() + " ");
			}
			System.out.println();
			System.out.println(a.getName());
			
			//Attribute a = importance(attributes, examples);
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

	private Attribute importance(ArrayList<Attribute> attributes, ArrayList<ArrayList<String>> examples) {
		if(attributes.size() == 1){
			System.out.println(";)");
			return attributes.get(0);
		}
		else if(attributes.size() == 2){
			if(attributes.get(0).equals(attributes.get(attributes.size() - 1))){
				return attributes.get(1);
			} else {
				return attributes.get(0);
			}
		}
		double max = Double.MIN_VALUE;
		Attribute best = null;
		double beforeYes = 0;
		double beforeSum = 0;
		for(ArrayList<String> example : examples){
			beforeSum++;
			if(example.get(example.size() - 1).equals("yes")){
				beforeYes++;
			} 
		}
		double entropyBefore = B((double)(beforeYes/beforeSum));
//		System.out.println("beforeYes: " + beforeYes);
//		System.out.println("beforeSum: " + beforeSum);
////		System.out.println("in B: " + (double)(beforeYes/beforeSum)	);
//		System.out.println("entropy before: " + entropyBefore);
		for(Attribute current : attributes){
			if(attributes.get(attributes.size() - 1).equals(current)){
				break;
			}
			//System.out.println(current.getName());
			double finalSum = 0;
			ArrayList<String> classifications = current.getClassifications();
			for(String classi : classifications){
				//System.out.println(classi);
				double yes = 0;
				double sum = 0;;
				for(ArrayList<String> example: examples){
					String value = example.get(current.getIndex());
					if(classi.equals(value)){
						sum++;
						if(example.get(example.size() - 1).equals("yes")){
							yes++;
						} 
					}
				}
//				System.out.println("yes: " + yes);
//				System.out.println("sum: " + sum);
				double B = B((double)(yes/sum));
//				System.out.println("example: " + examples.size());
//				System.out.println("B: " + B);
//				System.out.println("Before: " + (double)sum/examples.size());
				finalSum = finalSum +  ((double)(sum/examples.size())) * B;
			}
//			System.out.println("finalsum: " + finalSum);
			finalSum = entropyBefore - finalSum; 
			if(finalSum > max){
				max = finalSum;
				best = current;
			}
			System.out.println(current.getName() + " " + finalSum);
		}
		return best;
	}
	
	private double B(double q){
		double qlog = q*Math.log(q)/Math.log(2);
		if(Double.isNaN(qlog)){
			qlog = 0;
		}
		double oneminusqlog = (1.0-q)*Math.log(1.0-q)/Math.log(2);
		if(Double.isNaN(oneminusqlog)){
			oneminusqlog = 0;
		}
		double result = - (qlog + oneminusqlog);
		return result;
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
