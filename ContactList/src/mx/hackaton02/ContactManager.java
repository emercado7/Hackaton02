package mx.hackaton02;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ContactManager extends JFrame {

    // Logic Variables
    private ArrayList<String> contacts;
    private Agenda agenda;
    private final int MAX_CONTACTS = 10;

    private SearchService searchService = new SearchService();
    private Pattern pattern = Pattern.compile("^\\d{10}$");

    // GUI Components
    private JTextField nameField;
    private JTextField numberField;
    private JTextArea displayArea;
    private JLabel statusLabel;

    public ContactManager() {
        // Initialize data
        contacts = new ArrayList<>();
        agenda = new Agenda(10);

        // Window Setup
        setTitle("Contact Manager");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- TOP PANEL (Inputs) ---
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField(15);
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Number:"));
        numberField = new JTextField(15);
        inputPanel.add(numberField);
        add(inputPanel, BorderLayout.NORTH);

        // --- CENTER PANEL (Display) ---
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // --- BOTTOM PANEL (Buttons & Status) ---
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));

        // Button Row
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Add");
        JButton btnList = new JButton("List");
        JButton btnSearch = new JButton("Search");
        JButton btnDelete = new JButton("Delete");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnList);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnDelete);

        // Status Row
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusLabel = new JLabel("Status: Ready");
        statusPanel.add(statusLabel);

        bottomPanel.add(buttonPanel);
        bottomPanel.add(statusPanel);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- EVENT LISTENERS (The Logic) ---

        // 1. ADD CONTACT
        btnAdd.addActionListener(e -> {
            String name = nameField.getText().trim();
            String number = numberField.getText().trim();

            if (name.isEmpty()) {
                showMessage("Please enter a name.");
                return;
            }

            if (contacts.size() >= MAX_CONTACTS) {
                showMessage("Error: Contact list is FULL.");
                return;
            }

            if (containsContact(name)) {
                showMessage("Error: Contact '" + name + "' already exists.");
                return;
            }
            if (agenda.validationName(name) || agenda.validationNumber(number)){
                showMessage("Error: Contact '" + name + "' or Number '" + number + "' already exists.");
                return;
            }

            if (!pattern.matcher(number).matches())  {
                showMessage("Error: The number doesn't have enough digits");
                return;
            }

            agenda.añadirContacto(name,number);
            nameField.setText("");
            numberField.setText("");
            updateStatus("Added: " + name);
            listContacts(); // Auto-refresh list
        });

        // 2. LIST CONTACTS
        btnList.addActionListener(e -> listContacts());

        // 3. SEARCH CONTACT
        //----Conecta con el método de buscaContacto()----
        btnSearch.addActionListener(e -> {
            String name = nameField.getText();
            String result = searchService.buscaContacto(contacts, name);
            displayArea.setText(result);
        });

        //Lo que estaba primero en el codigo de borrador:
        /*
        btnSearch.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (contacts.contains(name)) {
                displayArea.setText("Found: " + name + " is in your list.");
            } else {
                displayArea.setText("Not Found: " + name + " is not in the list.");
            }
        });

        // 4. DELETE CONTACT
        btnDelete.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (contacts.remove(name)) {
                updateStatus("Deleted: " + name);
                listContacts();
            } else {
                showMessage("Cannot delete: " + name + " not found.");
            }
        });

        // Initial Status Update
        updateStatus("Welcome!");
    }

    // --- HELPER METHODS ---

    // Updates the status label with empty space count
    private void updateStatus(String message) {
        int emptySpaces = MAX_CONTACTS - contacts.size();
        statusLabel.setText(String.format("%s | Spaces left: %d/%d", message, emptySpaces, MAX_CONTACTS));

        if (emptySpaces == 0) {
            statusLabel.setForeground(Color.RED); // Visual warning when full
        } else {
            statusLabel.setForeground(Color.BLACK);
        }
    }

    // Helper to print all contacts to the text area
    private void listContacts() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Contact List ---\n");
        if (agenda.isEmpty()) {
            sb.append("(Empty)\n");
        } else {
            /*for (int i = 0; i < contacts.size(); i++) {
                sb.append((i + 1) + ". " + contacts.get(i) + "\n");
            }*/
            sb.append(agenda.showContacts());
        }
        displayArea.setText(sb.toString());
    }

    // Helper to check case-insensitive existence
    private boolean containsContact(String name) {
        for (String c : contacts) {
            if (c.equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public static void main(String[] args) {
        // Run the GUI safely
        SwingUtilities.invokeLater(() -> {
            new ContactManager().setVisible(true);
        });
    }
}