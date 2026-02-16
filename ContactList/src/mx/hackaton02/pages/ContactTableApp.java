package mx.hackaton02.pages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ContactTableApp extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField nameField;
    private JLabel statusLabel;

    // AHORA ESTE VALOR ES DINÁMICO
    private int maxContacts;

    // El constructor ahora recibe el tamaño "limit"
    public ContactTableApp(int limit) {
        this.maxContacts = limit;

        setTitle("Tu Agenda");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- CONFIGURACIÓN DE TABLA ---
        String[] columnNames = {"Nombre de contacto", "Modificar", "Borrar"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.getTableHeader().setReorderingAllowed(false);

        // Renderers y Editors para botones
        table.getColumn("Modificar").setCellRenderer(new ButtonRenderer());
        table.getColumn("Modificar").setCellEditor(new ButtonEditor(new JCheckBox(), "Modificar"));

        table.getColumn("Borrar").setCellRenderer(new ButtonRenderer());
        table.getColumn("Borrar").setCellEditor(new ButtonEditor(new JCheckBox(), "Borrar"));

        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- PANEL SUPERIOR ---
        JPanel inputPanel = new JPanel(new FlowLayout());
        // inputPanel.add(new JLabel("Nuevo nombre de contacto: "));
        nameField = new JTextField(20);
        JButton btnAdd = new JButton("Agregar Contacto");

        // inputPanel.add(nameField);
        inputPanel.add(btnAdd);
        add(inputPanel, BorderLayout.NORTH);

        // --- BARRA DE ESTADO ---
        statusLabel = new JLabel("Welcome");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(statusLabel, BorderLayout.SOUTH);

        // --- LÓGICA AGREGAR ---
        btnAdd.addActionListener(e -> {

            // 2. Cerrar esta ventana de bienvenida

            // 3. Abrir la app principal pasándole el tamaño
            new AddContact().setVisible(true);

        });

        updateStatus();
    }

    private void updateStatus() {
        int currentCount = tableModel.getRowCount();
        int emptySpaces = maxContacts - currentCount;

        String color = (emptySpaces == 0) ? "RED" : "BLACK";
        // Usamos HTML básico para formatear el color del texto
        statusLabel.setText(String.format("<html>Capacity: %d | Used: %d | Empty: <b>%d</b> | Status: <font color='%s'>%s</font></html>",
                maxContacts, currentCount, emptySpaces, color,
                (emptySpaces == 0 ? "FULL" : "Active")));
    }

    private boolean contactExists(String name) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String existingName = (String) tableModel.getValueAt(i, 0);
            if (existingName.equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    // --- CLASES INTERNAS (Renderer y Editor) ---
    // (Son idénticas al ejemplo anterior)

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() { setOpaque(true); }
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        private String actionType;

        public ButtonEditor(JCheckBox checkBox, String actionType) {
            super(checkBox);
            this.actionType = actionType;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) performAction();
            isPushed = false;
            return label;
        }

        private void performAction() {
            int row = table.getSelectedRow();
            if (row < 0 || row >= tableModel.getRowCount()) return;

            if ("Delete".equals(actionType)) {
                int confirm = JOptionPane.showConfirmDialog(button,
                        "Delete this contact?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    tableModel.removeRow(row);
                    updateStatus();
                }
            } else if ("Modify".equals(actionType)) {
                String currentName = (String) tableModel.getValueAt(row, 0);
                String newName = JOptionPane.showInputDialog(button, "Update Name:", currentName);
                if (newName != null && !newName.trim().isEmpty()) {
                    if (!newName.equalsIgnoreCase(currentName) && contactExists(newName)) {
                        JOptionPane.showMessageDialog(button, "Name exists!");
                    } else {
                        tableModel.setValueAt(newName, row, 0);
                    }
                }
            }
        }
    }
}