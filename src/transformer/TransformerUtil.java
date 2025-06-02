package transformer;

import java.util.ArrayList;
import java.util.List;

import transformer.interfaces.ITransformer;

public class TransformerUtil {
 public static <I, O> List<O> applyAll(List<I> inputs, ITransformer<I, O> transformer) {
     List<O> outputs = new ArrayList<>();
     for (I in : inputs) {
         outputs.add(transformer.transform(in));
     }
     return outputs;
 }
}
