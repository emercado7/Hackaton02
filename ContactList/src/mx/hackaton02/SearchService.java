package mx.hackaton02;
import java.util.ArrayList;

public class SearchService {
    //Método de Busqueda
    public String buscaContacto(ArrayList<String> list, String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Nombre de contacto a buscar: ";
        }

        String nameToSearch = name.trim().toLowerCase();

        for (String contact : list) {
            if (contact.toLowerCase().equals(nameToSearch)) {
                return "--- Contacto encontrado ---\n" +
                        "Información: " + contact + "\n";

            } // Cierre if
        } // Cierre for

        return "Contacto '" + name + "' no registrado en la agenda";
    }//cierre del método
}//cierre de la clase
