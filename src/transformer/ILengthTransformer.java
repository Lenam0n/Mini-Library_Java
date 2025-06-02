package transformer;

import transformer.interfaces.ITransformer;

public class ILengthTransformer implements ITransformer<String, Integer> {
	 @Override
	 public Integer transform(String input) {
	     // Wandelt einen String in seine Länge um
	     return input.length();
	 }
	}

