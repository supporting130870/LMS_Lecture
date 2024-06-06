package view;

import model.DAOUser;
import model.MUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;

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

        // 이미지 라벨을 상단 가운데에 배치
        ImageIcon originalIcon = new ImageIcon("data/Myongji-ui_BIG/5-1.png");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // 이미지 크기 조정
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(imageLabel, gbc);
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.mju.ac.kr/mjukr/index.do"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        // ID 필드와 라벨 추가
        addLabelAndField("ID:", idField = new JTextField(15), gbc, 1);

        // 패스워드 필드와 라벨 추가
        addLabelAndField("Password:", passwordField = new JPasswordField(15), gbc, 2);

        // 로그인 버튼 추가
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        // 회원가입 버튼 추가
        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VSignUpDialog signUpDialog = new VSignUpDialog(parent);
                signUpDialog.setVisible(true);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(signUpButton, gbc);

        setSize(500, 400);  // 로그인 창 크기 조정
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
                JOptionPane.showMessageDialog(this,
                        "Login Successful",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
                ((VMainFrame) parent).initialize();  // 로그인 성공 시 메인 프레임의 initialize 메서드 호출
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid ID or Password",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading user information",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}

