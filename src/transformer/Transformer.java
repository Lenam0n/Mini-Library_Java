package transformer;
import java.util.List;

import transformer.interfaces.ITransformer;

public class Transformer {
	    public static void main(String[] args) {
	        // 1) Drei Transformer erzeugen
	        ITransformer<String, Integer> t1 = new ILengthTransformer();
	        ITransformer<Integer, Double> t2 = new ISquareRootTransformer();
	        ITransformer<Double, String> t3 = new IFormatTwoDecimalsTransformer();

	        // 2) Pipeline zusammen­setzen: String → Integer → Double → String
	        ITransformer<String, String> pipeline =
	            t1.andThen(t2).andThen(t3);

	        // 3) Eingabedaten
	        List<String> inputs = List.of("Hello", "Java", "Interfaces", "Generics");

	        // 4) Pipeline ausführen
	        List<String> outputs = TransformerUtil.applyAll(inputs, pipeline);

	        // 5) Ergebnisse ausgeben
	        System.out.println("Inputs:  " + inputs);
	        System.out.println("Results: " + outputs);

	        // Optional: Zwischenschritte prüfen
	        for (String s : inputs) {
	            Integer len = t1.transform(s);
	            Double sqrt = t2.transform(len);
	            String formatted = t3.transform(sqrt);
	            System.out.printf("'%s' → %d → %.4f → '%s'%n",
	                s, len, sqrt, formatted);
	        }
	    }
	}
