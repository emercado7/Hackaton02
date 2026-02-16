package mx.hackaton02.pages;

import javax.swing.*;
import java.awt.*;


// --- CLASE 2: PANTALLA DE BIENVENIDA ---
public class AddContact extends JFrame {

    private JTextField nameField, numberField;

    private String[] countryCodes = {
            "+1 (USA/Canada)",
            "+52 (México)",
            "+34 (España)",
            "+44 (Reino Unido)",
            "+54 (Argentina)",
            "+57 (Colombia)"
    };

    public AddContact() {
        setTitle("Añadir Contacto");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla
        setLayout(new BorderLayout());
        setBackground(new Color(220, 245,  244));

        // Panel Central
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel lblTitle = new JLabel("Página de detalles de contactos", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        centerPanel.add(lblTitle);

        // Panel de atributos de contacto
        JPanel contactPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        contactPanel.add(new JLabel("Nombre:"));
        nameField = new JTextField(20);
        contactPanel.add(nameField);

        contactPanel.add(new JLabel("Clave de País"));
        JComboBox<String> prefixCombo = new JComboBox<>(countryCodes);
        contactPanel.add(prefixCombo);

        contactPanel.add(new JLabel("Número:"));
        numberField = new JTextField(20);
        contactPanel.add(numberField);

        // Usamos JSpinner en lugar de TextField para forzar números

        add(contactPanel, BorderLayout.CENTER);

        // Botón de Inicio
        JButton btnStart = new JButton("Agregar Contacto");
        btnStart.setFont(new Font("Arial", Font.BOLD, 12));
        btnStart.setBackground(new Color(70, 130, 180)); // Azul acero
        btnStart.setForeground(Color.WHITE);

        add(btnStart, BorderLayout.SOUTH);

        // Lógica del botón
        btnStart.addActionListener(e -> {
            // 1. Obtener el valor del spinner
            int name = nameField.getColumns();

            // 2. Cerrar esta ventana de bienvenida
            dispose();

            // 3. Abrir la app principal pasándole el tamaño
            // new ContactTableApp(name).setVisible(true);
        });
    }
}

/*
* String name = nameField.getText().trim();

            if (name.isEmpty()) return;

            if (tableModel.getRowCount() >= maxContacts) {
                JOptionPane.showMessageDialog(this, "List is FULL (Max " + maxContacts + ")");
                return;
            }

            if (contactExists(name)) {
                JOptionPane.showMessageDialog(this, "Name already exists.");
                return;
            }

            tableModel.addRow(new Object[]{name, "Edit", "Delete"});
            nameField.setText("");
            updateStatus();
* */