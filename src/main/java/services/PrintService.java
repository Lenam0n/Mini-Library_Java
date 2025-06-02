package services;

import interfaces.IPrinter;

import java.util.List;

/**
* Bietet bequeme Methoden, um Listen und Einzel‐Ergebnisse zu drucken,
* ohne dass Main System.out/err direkt aufruft.
*/
public class PrintService {

 public void printList(String title, List<?> items) {
     IPrinter.out(title);
     for (Object o : items) {
         IPrinter.out(o.toString());
     }
     IPrinter.out(""); // Leerzeile
 }

 public <T> void printFound(T item, String notFoundMessage, Object key) {
     if (item != null) {
         IPrinter.out(item.toString());
     } else {
         IPrinter.err(notFoundMessage + ": " + key);
     }
     IPrinter.out("");
 }
}
