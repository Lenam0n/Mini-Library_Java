package transformer.interfaces;

@FunctionalInterface
public interface ITransformer<I,O> {
	O transform(I input);
	default <P> ITransformer<I,P> andThen(ITransformer<O,P> next){
		return input -> {
			O mittelWert = this.transform(input);
			return next.transform(mittelWert);
		};
	}
}
