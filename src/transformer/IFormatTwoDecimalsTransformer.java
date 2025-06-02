package transformer;

import transformer.interfaces.ITransformer;

//Datei: FormatTwoDecimalsTransformer.java
public class IFormatTwoDecimalsTransformer implements ITransformer<Double, String> {
 @Override
 public String transform(Double input) {
     // Formatiert auf zwei Nachkommastellen
     return String.format("%.2f", input);
 }
}
