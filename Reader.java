import weka.core.Instances;
import java.io.BufferedReader;
import java.io.FileReader;

public class Reader {

	public Reader(){

	}

	public void read(){
		BufferedReader reader = null;
		Instances data = null;
		try{
			  reader = new BufferedReader(new FileReader("./data.arff"));
			  data = new Instances(reader);
			//  reader.close();
		} catch(Exception e){
			e.printStackTrace();
		}

		// data.setClassIndex(data.numAttributes() - 1);
	}

}
