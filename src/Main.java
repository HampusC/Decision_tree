
public class Main {

	public static void main(String[] args) {
		String path = null;
		if(args.length == 1){
			path = args[0];
		}
		TreeBuilder treeBuilder = new TreeBuilder();
		treeBuilder.setup(path);
		treeBuilder.build();
		treeBuilder.print();
	}

}
