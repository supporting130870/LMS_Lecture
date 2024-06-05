package view;

import model.DAOUser;
import model.MUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class VLoginDialog extends JDialog {
    private JTextField idField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signUpButton;
    private DAOUser daoUser;
    private JFrame parent;

    public VLoginDialog(JFrame parent) {
        super(parent, "Login", true);
        this.parent = parent;
        daoUser = new DAOUser();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addLabelAndField("ID:", idField = new JTextField(15), gbc, 0);  // 열 수 설정
        addLabelAndField("Password:", passwordField = new JPasswordField(15), gbc, 1);  // 열 수 설정

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VSignUpDialog signUpDialog = new VSignUpDialog(parent);
                signUpDialog.setVisible(true);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(signUpButton, gbc);

        setSize(400, 200);  // 크기 조정
        setLocationRelativeTo(parent);

        // 닫기 버튼 동작 설정
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void addLabelAndField(String labelText, JComponent field, GridBagConstraints gbc, int yPos) {
        JLabel label = new JLabel(labelText);
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.gridwidth = 1;
        add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = yPos;
        gbc.gridwidth = 1;
        add(field, gbc);
    }

    private void handleLogin() {
        String id = idField.getText();
        String password = new String(passwordField.getPassword());

        try {
            MUser user = daoUser.findUserByIdAndPassword(id, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                ((VMainFrame) parent).initialize();  // 로그인 성공 시 메인 프레임의 initialize 메서드 호출
            } else {
                JOptionPane.showMessageDialog(this, "Invalid ID or Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading user information", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
