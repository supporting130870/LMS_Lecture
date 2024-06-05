package view;

import model.DAOUser;
import model.MUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class VSignUpDialog extends JDialog {
    private JTextField nameField;
    private JTextField idField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> campusComboBox;
    private JTextField birthDateField;
    private JTextField studentIdField;
    private DAOUser daoUser;
    private JButton checkIdButton;
    private boolean isIdChecked = false;

    public VSignUpDialog(JFrame parent) {
        super(parent, "Sign Up", true);
        daoUser = new DAOUser();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);  // 컴포넌트 간의 간격 설정
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addLabelAndField("Name:", nameField = new JTextField(), gbc, 0);
        addLabelAndField("ID:", idField = new JTextField(), gbc, 1);
        addCheckIdButton(gbc, 1); // 같은 행에 버튼을 추가
        addLabelAndField("Password:", passwordField = new JPasswordField(), gbc, 2);
        addLabelAndField("Confirm Password:", confirmPasswordField = new JPasswordField(), gbc, 3);
        addLabelAndField("Campus:", campusComboBox = new JComboBox<>(new String[]{"Seoul", "Yongin"}), gbc, 4);
        addLabelAndField("Birth Date (YYYYMMDD):", birthDateField = new JTextField(), gbc, 5);
        addLabelAndField("Student ID:", studentIdField = new JTextField(), gbc, 6);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignUp();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        add(signUpButton, gbc);

        setSize(400, 500);
        setLocationRelativeTo(parent);
    }

    private void addLabelAndField(String labelText, JComponent field, GridBagConstraints gbc, int yPos) {
        JLabel label = new JLabel(labelText);
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.gridwidth = 1;
        add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = yPos;
        gbc.gridwidth = 2;
        add(field, gbc);
    }

    private void addCheckIdButton(GridBagConstraints gbc, int yPos) {
        checkIdButton = new JButton("Check ID");
        checkIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheckId();
            }
        });

        gbc.gridx = 3;
        gbc.gridy = yPos;
        gbc.gridwidth = 1;
        add(checkIdButton, gbc);
    }

    private void handleCheckId() {
        String id = idField.getText();
        try {
            if (daoUser.isUserIdExist(id)) {
                JOptionPane.showMessageDialog(this, "ID already exists", "Error", JOptionPane.ERROR_MESSAGE);
                isIdChecked = false;
            } else {
                JOptionPane.showMessageDialog(this, "ID is available", "Success", JOptionPane.INFORMATION_MESSAGE);
                isIdChecked = true;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error checking ID", "Error", JOptionPane.ERROR_MESSAGE);
            isIdChecked = false;
        }
    }

    private void handleSignUp() {
        if (!isIdChecked) {
            JOptionPane.showMessageDialog(this, "Please check ID first", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String name = nameField.getText();
        String id = idField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String campus = (String) campusComboBox.getSelectedItem();
        String birthDate = birthDateField.getText();
        String studentId = studentIdField.getText();

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (daoUser.isUserIdExist(id)) {
                JOptionPane.showMessageDialog(this, "ID already exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error checking ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        MUser user = new MUser(name, id, password, campus, birthDate, studentId);

        try {
            daoUser.saveUser(user);
            JOptionPane.showMessageDialog(this, "Sign Up Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving user information", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
