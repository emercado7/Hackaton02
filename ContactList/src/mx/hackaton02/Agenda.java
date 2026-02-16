package mx.hackaton02;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class Agenda {
    private TreeMap<String,String> agenda;
    private Pattern patternNumber = Pattern.compile("^\\d{10}$");

    public Agenda(int limit){
        agenda = new TreeMap<>();
    }

    public void addContact(String name, String number){
        agenda.put(name, number);
    }

    public boolean validationName (String name) {return  agenda.containsKey(name);}
    public boolean validationNumber (String name) {return  agenda.containsKey(name);}

    public boolean isEmpty(){return agenda.isEmpty();}

    public String showContacts (){
       String text = "";
        for (Map.Entry<String, String> entry : agenda.entrySet()) {
            text += "Nombre: " + entry.getKey() + "   Numero: " + entry.getValue() +"\n";
        }
        return text;
    }
}
