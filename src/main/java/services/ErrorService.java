package services;

import exeption.BookNotFoundException;
import exeption.MemberNotFoundException;
import interfaces.IRunnableException;
import interfaces.IExceptionSupplier;
import interfaces.IPrinter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
* Zentraler ErrorService: Hier werden alle bekannten Exceptions einem Handler zugeordnet.
* Methoden execute(...) und run(...) fangen Exceptions ab und übergeben sie entsprechend.
*/
public class ErrorService {
 private static final Map<Class<? extends Exception>, Consumer<Exception>> HANDLERS = new HashMap<>();

 static {
     HANDLERS.put(BookNotFoundException.class, ex ->
         IPrinter.err("Buch-Fehler: " + ex.getMessage())
     );
     HANDLERS.put(MemberNotFoundException.class, ex ->
         IPrinter.err("Mitglieder-Fehler: " + ex.getMessage())
     );
     HANDLERS.put(Exception.class, ex ->
         IPrinter.err("Unerwarteter Fehler: " + ex.getMessage())
     );
 }

 private ErrorService() { }

 /**
  * Führt den Supplier aus, fängt Exceptions und leitet sie an den jeweiligen Handler weiter.
  * Gibt null zurück, falls eine Exception aufgetreten ist.
  *
  * @param supplier Block, der eine Exception werfen kann
  * @param <T>      Rückgabetyp
  * @return Ergebnis des Suppliers oder null im Fehlerfall
  */
 public static <T> T execute(IExceptionSupplier<T> supplier) {
     try {
         return supplier.get();
     } catch (Exception e) {
         handle(e);
         return null;
     }
 }

 /**
  * Führt den Runnable aus, fängt Exceptions und leitet sie an den jeweiligen Handler weiter.
  *
  * @param runnable Block, der eine Exception werfen kann
  */
 public static void run(IRunnableException runnable) {
     try {
         runnable.run();
     } catch (Exception e) {
         handle(e);
     }
 }

 private static void handle(Exception e) {
     Consumer<Exception> handler = HANDLERS.get(e.getClass());
     if (handler == null) {
         for (Map.Entry<Class<? extends Exception>, Consumer<Exception>> entry : HANDLERS.entrySet()) {
             if (entry.getKey().isInstance(e)) {
                 handler = entry.getValue();
                 break;
             }
         }
     }
     if (handler != null) {
         handler.accept(e);
     } else {
         HANDLERS.get(Exception.class).accept(e);
     }
 }
}
