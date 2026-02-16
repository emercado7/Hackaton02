import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class ContactTableApp02 extends JFrame {

    // --- LOGIC VARIABLES ---
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField nameField;
    private JLabel statusLabel;
    private final int MAX_CONTACTS = 10;

    public ContactTableApp02() {
        setTitle("Contact Manager");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- 1. TABLE SETUP ---
        // Columns: Name (Index 0), Modify (Index 1), Delete (Index 2)
        String[] columnNames = {"Contact Name", "Modify", "Delete"};

        // We override DefaultTableModel to make the button columns "editable" (clickable)
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Column 0 (Name) is NOT editable directly (use Modify button)
                // Columns 1 & 2 (Buttons) MUST be editable to receive clicks
                return column > 0;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(35); // Taller rows for better buttons
        table.getTableHeader().setReorderingAllowed(false);

        // --- 2. ASSIGN RENDERERS & EDITORS ---
        // This makes the text columns look like buttons
        table.getColumn("Modify").setCellRenderer(new ButtonRenderer());
        table.getColumn("Modify").setCellEditor(new ButtonEditor(new JCheckBox(), "Modify"));

        table.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        table.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), "Delete"));

        // Add table to scroll pane
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- 3. INPUT PANEL (Top) ---
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("New Contact Name:"));
        nameField = new JTextField(20);
        JButton btnAdd = new JButton("Add Contact");

        inputPanel.add(nameField);
        inputPanel.add(btnAdd);
        add(inputPanel, BorderLayout.NORTH);

        // --- 4. STATUS BAR (Bottom) ---
        statusLabel = new JLabel("Welcome");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        add(statusLabel, BorderLayout.SOUTH);

        // --- 5. ADD BUTTON LOGIC ---
        btnAdd.addActionListener(e -> {
            String name = nameField.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a name.");
                return;
            }

            if (tableModel.getRowCount() >= MAX_CONTACTS) {
                JOptionPane.showMessageDialog(this, "Error: List is FULL (Max " + MAX_CONTACTS + ")");
                return;
            }

            if (contactExists(name)) {
                JOptionPane.showMessageDialog(this, "Error: '" + name + "' is already in the list.");
                return;
            }

            // Add the row. 
            // The empty strings "" in columns 1 & 2 are placeholders for the buttons.
            tableModel.addRow(new Object[]{name, "Edit", "Delete"});

            nameField.setText("");
            updateStatus();
        });

        // Initial status update
        updateStatus();
    }

    // --- HELPER METHODS ---

    private void updateStatus() {
        int currentCount = tableModel.getRowCount();
        int emptySpaces = MAX_CONTACTS - currentCount;

        String color = (emptySpaces == 0) ? "RED" : "BLACK";
        String statusText = String.format("<html>Contacts: %d | Empty Spaces: %d | <font color='%s'>%s</font></html>",
                currentCount, emptySpaces, color,
                (emptySpaces == 0 ? "LIST FULL" : "Ready"));
        statusLabel.setText(statusText);
    }

    private boolean contactExists(String name) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String existingName = (String) tableModel.getValueAt(i, 0);
            if (existingName.equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    // --- INNER CLASS: BUTTON RENDERER (How it looks) ---
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // --- INNER CLASS: BUTTON EDITOR (How it acts) ---
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        private String actionType; // "Modify" or "Delete"

        public ButtonEditor(JCheckBox checkBox, String actionType) {
            super(checkBox);
            this.actionType = actionType;
            button = new JButton();
            button.setOpaque(true);

            // When button is clicked, stop editing and trigger action
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                // Perform the action AFTER the click is registered
                performAction();
            }
            isPushed = false;
            return label;
        }

        private void performAction() {
            // Get the selected row. Note: When clicking a button, that row becomes selected.
            int row = table.getSelectedRow();

            // Safety check
            if (row < 0 || row >= tableModel.getRowCount()) return;

            if ("Delete".equals(actionType)) {
                // Confirm before delete
                int confirm = JOptionPane.showConfirmDialog(button,
                        "Are you sure you want to delete this contact?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    tableModel.removeRow(row);
                    updateStatus();
                }
            }
            else if ("Modify".equals(actionType)) {
                String currentName = (String) tableModel.getValueAt(row, 0);
                String newName = JOptionPane.showInputDialog(button, "Update Name:", currentName);

                if (newName != null && !newName.trim().isEmpty()) {
                    // Check for duplicates (excluding the current name itself)
                    if (!newName.equalsIgnoreCase(currentName) && contactExists(newName)) {
                        JOptionPane.showMessageDialog(button, "That name already exists!");
                    } else {
                        tableModel.setValueAt(newName, row, 0);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ContactTableApp02().setVisible(true));
    }
}