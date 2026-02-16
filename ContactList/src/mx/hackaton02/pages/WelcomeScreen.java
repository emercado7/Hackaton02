package mx.hackaton02.pages;

import javax.swing.*;
import java.awt.*;


// --- CLASE 2: PANTALLA DE BIENVENIDA ---
public class WelcomeScreen extends JFrame {

    public WelcomeScreen() {
        setTitle("Setup");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla
        setLayout(new BorderLayout());

        // Panel Central
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel lblTitle = new JLabel("Contact Manager Setup", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblAsk = new JLabel("Enter contact list size:", SwingConstants.CENTER);

        // Usamos JSpinner en lugar de TextField para forzar números
        JSpinner numberSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));

        centerPanel.add(lblTitle);
        centerPanel.add(lblAsk);
        centerPanel.add(numberSpinner);

        add(centerPanel, BorderLayout.CENTER);

        // Botón de Inicio
        JButton btnStart = new JButton("Launch Application");
        btnStart.setFont(new Font("Arial", Font.BOLD, 12));
        btnStart.setBackground(new Color(70, 130, 180)); // Azul acero
        btnStart.setForeground(Color.WHITE);

        add(btnStart, BorderLayout.SOUTH);

        // Lógica del botón
        btnStart.addActionListener(e -> {
            // 1. Obtener el valor del spinner
            int size = (Integer) numberSpinner.getValue();

            // 2. Cerrar esta ventana de bienvenida
            dispose();

            // 3. Abrir la app principal pasándole el tamaño
            new ContactTableApp(size).setVisible(true);
        });
    }
}