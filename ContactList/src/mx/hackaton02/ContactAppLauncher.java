package mx.hackaton02;

import mx.hackaton02.pages.WelcomeScreen;
import javax.swing.*;

// --- CLASE 1: EL LANZADOR (Main) ---
public class ContactAppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Iniciamos con la pantalla de bienvenida
            new WelcomeScreen().setVisible(true);
        });
    }
}