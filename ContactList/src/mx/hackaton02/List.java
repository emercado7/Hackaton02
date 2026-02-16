package mx.hackaton02;

import java.util.ArrayList;
import java.util.Collections;

public class List {

    // orden alfabetico
    public static String ordenarYContar(ArrayList<String> contacts) {

        // guarmamos en un array la lista asi no hacemos modificaciones
        ArrayList<String> copia = new ArrayList<>(contacts);

        // Ordenamos alfabéticamente
        Collections.sort(copia, String.CASE_INSENSITIVE_ORDER);

        StringBuilder sb = new StringBuilder();
        sb.append("--- Contact List (Alphabetical Order) ---\n");

        if (copia.isEmpty()) {
            sb.append("(Empty)\n");
        } else {
            for (int i = 0; i < copia.size(); i++) {
                sb.append((i + 1) + ". " + copia.get(i) + "\n");
            }
        }

        sb.append("\nTotal Contacts: ").append(copia.size());

        return sb.toString();
    }
}
