package interfaces;

/**
* Ein Runnable, der eine Exception werfen kann.
*/
@FunctionalInterface
public interface IRunnableException {
 void run() throws Exception;
}
