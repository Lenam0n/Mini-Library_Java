package transformer;

import transformer.interfaces.ITransformer;

//Datei: SquareRootTransformer.java
public class ISquareRootTransformer implements ITransformer<Integer, Double> {
 
	@Override
	 public Double transform(Integer input) {
	     // Berechnet die Quadratwurzel
	     return Math.sqrt(input);
	 }
}
