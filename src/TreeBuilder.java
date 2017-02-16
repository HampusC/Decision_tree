import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

public class TreeBuilder {
	
	private ArrayList<ArrayList<String>> examples;
	private ArrayList<ArrayList<String>> originalExamples;
	private ArrayList<Attribute> attributes;
	private Node tree;
	private Attribute last;
	
	public TreeBuilder(){
		examples = new ArrayList<ArrayList<String>>();
		attributes = new ArrayList<Attribute>();
	}
	
	public void setup(){
		Reader reader = new Reader();
		reader.read();
		examples = reader.getExamples();
		originalExamples = reader.getExamples();
		attributes = reader.getAttributes();
		last = attributes.get(attributes.size() - 1);
		attributes.remove(last);
	}
	
	public void build(){
		tree = continueTree(tree, examples, attributes, "Root:");
	}
	
	private Node decisionTreeLearning(ArrayList<ArrayList<String>> examples, Attribute targetAttribute, ArrayList<Attribute> attributes, String cameFrom){
		Node newTree = new Node();
		
		String allSameClassification = allSameClassification(targetAttribute, examples);
		if(allSameClassification != null){
			newTree.setCameFrom(cameFrom);
			newTree.setValue((allSameClassification));
			return newTree;
		}
		
		if(attributes.isEmpty()){
			String mostCommon = mostCommonValue(examples, targetAttribute);
			newTree.setCameFrom(cameFrom);
			newTree.setValue(mostCommon);
			return newTree;
		} else {
			newTree = continueTree(newTree, examples, attributes, cameFrom);
			return newTree;
		}
	}
	
	private Node continueTree(Node tree, ArrayList<ArrayList<String>> examples, ArrayList<Attribute> attributes, String cameFrom){
		Attribute a = importance(attributes, examples);
		attributes.remove(a);
		tree = new Node();
		tree.setCameFrom(cameFrom);
		tree.setValue(a.getName());
		for(String value : a.getValues()){
			Node subTree;
			ArrayList<ArrayList<String>> newExamples = new ArrayList<ArrayList<String>>();
			for(ArrayList<String> example : examples) {
				if(example.get(a.getIndex()).equals(value)){
					newExamples.add(example);
				}
			}
			if(newExamples.isEmpty()){ 
				String mostCommon = mostCommonClassification(originalExamples, a, value);
				subTree = new Node();
				subTree.setCameFrom(value); 
				subTree.setValue(mostCommon);
			} else {
				subTree = decisionTreeLearning(newExamples, a, attributes, value);
			}
			tree.addChild(subTree);
		}
		return tree;
	}

	private Attribute importance(ArrayList<Attribute> attributes, ArrayList<ArrayList<String>> examples) {
		if(attributes.size() == 1){
			return attributes.get(0);
		}
		double max = Double.MIN_VALUE;
		Attribute best = null;
		double entropyBefore = entropyBefore(examples);
		for(Attribute current : attributes){
			double entropyAfter = 0;
			ArrayList<String> values = current.getValues();
			for(String classi : values){
				double entropy = entropy(examples, current, classi);
				entropyAfter = entropyAfter + entropy;
			}
			entropyAfter = entropyBefore - entropyAfter; 
			if(entropyAfter > max){
				max = entropyAfter;
				best = current;
			}
		}
		return best;
	}
	
	private double entropyBefore(ArrayList<ArrayList<String>> examples){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for(ArrayList<String> example : examples){			
			String lastOne = example.get(example.size() - 1);
			if(map.containsKey(lastOne)){
				map.put(lastOne, map.getOrDefault(lastOne, 0) + 1);
			} else {
				map.put(lastOne, 1);
			}
		}
		double entropy = calculateEntropy(map, examples.size());
		return entropy;
	}
	
	private double entropy(ArrayList<ArrayList<String>> examples, Attribute current, String classi){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		double sum = 0;;
		for(ArrayList<String> example: examples){
			String value = example.get(current.getIndex());
			if(classi.equals(value)){
				String lastOne = example.get(example.size() - 1);
				if(map.containsKey(lastOne)){
					map.put(lastOne, map.getOrDefault(lastOne, 0) + 1);
				} else {
					map.put(lastOne, 1);
				}
			sum++;
			}
		}
		double entropy = calculateEntropy(map, sum);
		entropy = sum/(double)examples.size() * entropy;
		return entropy;
	}
	
	private double calculateEntropy(HashMap<String, Integer> map, double size){
		double entropy = 0; 
		for (Entry<String, Integer> entry : map.entrySet()) {
			double probability = entry.getValue()/(double)size;
			double currentEntropy = probability * Math.log(probability)/Math.log(2);
			entropy = entropy + currentEntropy;
		}
		entropy = - entropy;
		return entropy;
	}
	
	private String mostCommonValue(ArrayList<ArrayList<String>> examples, Attribute targetAttribute){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for(ArrayList<String> example : examples){
			int index = targetAttribute.getIndex();
			String temp = example.get(index);
			if(map.containsKey(temp)){
				map.put(temp, map.getOrDefault(temp, 0) + 1);
			} else {
				map.put(temp, 1);
			}
		}
		String max = Collections.max(map.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
		return max;
	}
	
	private String mostCommonClassification(ArrayList<ArrayList<String>> examples, Attribute targetAttribute, String value){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for(ArrayList<String> example : examples){
			int index = targetAttribute.getIndex();
			if(example.get(index).equals(value)){				
				String temp = example.get(example.size() - 1);
				if(map.containsKey(temp)){
					map.put(temp, map.getOrDefault(temp, 0) + 1);
				} else {
					map.put(temp, 1);
				}
			}
		}
		String max = null;
		int maxValueInMap=(Collections.max(map.values()));
		for (Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue()==maxValueInMap) {
                max = entry.getKey();
            }
        }
		return max;
	}
	
	private String allSameClassification(Attribute targetAttribute, ArrayList<ArrayList<String>> examples){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for(ArrayList<String> currentExample : examples){
			String classi = currentExample.get(currentExample.size() - 1);
			if(map.containsKey(classi)){
				map.put(classi, map.getOrDefault(classi, 0) + 1);
			} else {
				map.put(classi, 1);
			}
		}
		if(map.size() == 1){
			return map.keySet().iterator().next();
		}
		return null;
	}

	private class Node {
		
		private String cameFrom;
        private String value;
        private ArrayList<Node> children;
		
		public Node(){
			cameFrom = "";
			value = ""; 
			children = new ArrayList<Node>();
		}
		
		public void setValue(String value){
			this.value = value;
		}
		
		public void setCameFrom(String cameFrom){
			this.cameFrom = cameFrom;
		}
		
		public void addChild(Node child){
			children.add(child);
		}
		
		public void print() {
	        print("", true);
	    }

	    private void print(String prefix, boolean isTail) {
	    	String print = prefix + (isTail ? "└── " : "├── ") + cameFrom + " -> " + value;
	        System.out.println(print);
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
