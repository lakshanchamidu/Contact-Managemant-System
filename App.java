import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class App implements ActionListener {
    private JTextField nameField, phoneField;
    private JButton addButton, deleteButton, editButton;
    private JList<String> contactList;
    private DefaultListModel<String> contactListModel;
    private ArrayList<String[]> contacts;
    private int editIndex = -1;

    public static void main(String[] args) {
        new App().AppInterface();
    }

    public void AppInterface() {
        contacts = new ArrayList<>();

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(178, 102, 255));

        JLabel title = new JLabel("Welcome to Contact Management System", JLabel.CENTER);
        title.setForeground(Color.BLACK);
        JLabel label = new JLabel("Name: ");
        label.setForeground(Color.white);
        nameField = new JTextField(20);
        JLabel label2 = new JLabel("Phone Number: ");
        label2.setForeground(Color.white);
        phoneField = new JTextField(20);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        inputPanel.add(title, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        inputPanel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(label2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(phoneField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(178, 102, 255));
        addButton = new JButton("Add Contact");
        addButton.setBackground(new Color(255, 153, 51));
        deleteButton = new JButton("Delete Contact");
        deleteButton.setBackground(new Color(0, 255, 255));
        editButton = new JButton("Edit Contact");
        editButton.setBackground(new Color(155, 255, 51));

        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        editButton.addActionListener(this);

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);

        contactListModel = new DefaultListModel<>();
        contactList = new JList<>(contactListModel);

        JScrollPane contactScrollPane = new JScrollPane(contactList);
        contactList.setBackground(new Color(153,255,255));
        JPanel contactPanel = new JPanel(new BorderLayout());
        contactPanel.add(contactScrollPane, BorderLayout.CENTER);

        JFrame frame = new JFrame();
        frame.setSize(420, 420);
        frame.setTitle("Contact Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setBackground(Color.darkGray);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(contactPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();

            if (!name.isEmpty() && !phone.isEmpty()) {
                if (editIndex == -1) {
                    contacts.add(new String[]{name, phone});
                    contactListModel.addElement("Name: " + name + ", Phone: " + phone);
                } else {
                    contacts.set(editIndex, new String[]{name, phone});
                    contactListModel.set(editIndex, "Name: " + name + ", Phone: " + phone);
                    editIndex = -1;
                    editButton.setText("Edit Contact");
                }
                nameField.setText("");
                phoneField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Please enter both name and phone number.");
            }
        } else if (e.getSource() == deleteButton) {
            int selectedIndex = contactList.getSelectedIndex();
            if (selectedIndex != -1) {
                contacts.remove(selectedIndex);
                contactListModel.remove(selectedIndex);
                if (editIndex == selectedIndex) {
                    editIndex = -1;
                    editButton.setText("Edit Contact");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a contact to delete.");
            }
        } else if (e.getSource() == editButton) {
            int selectedIndex = contactList.getSelectedIndex();
            if (selectedIndex != -1) {
                if (editIndex == -1) {
                    String[] contact = contacts.get(selectedIndex);
                    nameField.setText(contact[0]);
                    phoneField.setText(contact[1]);
                    editIndex = selectedIndex;
                    editButton.setText("Save Contact");
                } else {
                    String name = nameField.getText().trim();
                    String phone = phoneField.getText().trim();

                    if (!name.isEmpty() && !phone.isEmpty()) {
                        contacts.set(editIndex, new String[]{name, phone});
                        contactListModel.set(editIndex, "Name: " + name + ", Phone: " + phone);
                        editIndex = -1;
                        editButton.setText("Edit Contact");
                        nameField.setText("");
                        phoneField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Please enter both name and phone number.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a contact to edit.");
            }
        }
    }
}
