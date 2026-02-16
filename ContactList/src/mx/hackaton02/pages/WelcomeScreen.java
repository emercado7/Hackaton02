package mx.hackaton02.pages;

import javax.swing.*;
import java.awt.*;


// --- CLASE 2: PANTALLA DE BIENVENIDA ---
public class WelcomeScreen extends JFrame {

    public WelcomeScreen() {
        setTitle("Agenda");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla
        setLayout(new BorderLayout());

        // Panel Central
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        centerPanel.setBackground(new Color(220, 245, 244));

        JLabel lblTitle = new JLabel(" \uD83D\uDCD3 Bienvenido a tu agenda", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));

        JLabel lblAsk = new JLabel("\uD83D\uDD8B\uFE0F Indica la cantidad de contactos que deseas tener:", SwingConstants.CENTER);
        lblAsk.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));

        // Usamos JSpinner en lugar de TextField para forzar números
        JSpinner numberSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        numberSpinner.setFont(new Font("Arial", Font.BOLD, 15));

        centerPanel.add(lblTitle);
        centerPanel.add(lblAsk);
        centerPanel.add(numberSpinner);

        add(centerPanel, BorderLayout.CENTER);

        // Botón de Inicio
        JButton btnStart = new JButton("\uD83D\uDCD6 Abrir mi agenda");
        btnStart.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        btnStart.setBackground(new Color(27, 43, 92));
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