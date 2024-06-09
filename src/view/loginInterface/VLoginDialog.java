package view.loginInterface;

import model.DAOUser;
import model.MUser;
import view.managerInterface.VManagerFrame;
import view.userInterface.VMainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;

public class VLoginDialog extends JDialog {
    private JTextField idField;
    private JPasswordField passwordField;
    private JButton showPasswordButton;
    private JButton loginButton;
    private JButton signUpButton;
    private JButton findIdButton;
    private JButton switchToAdminButton;
    private JButton switchToStudentButton;
    private DAOUser daoUser;
    private JFrame parent;
    private int count;
    private boolean locked;
    private boolean isAdminMode;

    public VLoginDialog(JFrame parent) {
        super(parent, "로그인", true);
        this.parent = parent;
        count = 0;
        locked = false;
        daoUser = new DAOUser();
        isAdminMode = false;
        initializeLayout();
    }

    private void initializeLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 이미지 라벨을 상단 가운데에 배치
        ImageIcon originalIcon = new ImageIcon("data/Myongji-ui_BIG/5-1.png");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
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
        addPasswordFieldAndLabel("비밀번호:", passwordField = new JPasswordField(15), gbc, 2);

        // 로그인 버튼 추가
        loginButton = new JButton("로그인");
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
        signUpButton = new JButton("회원가입");
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

        findIdButton = new JButton("아이디 찾기");
        findIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VFindIdDialog findIdDialog = new VFindIdDialog(parent);
                findIdDialog.setVisible(true);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(findIdButton, gbc);

        // 관리자 모드 전환 버튼 추가
        switchToAdminButton = new JButton("관리자 로그인으로 전환");
        switchToAdminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToAdminMode();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(switchToAdminButton, gbc);

        // 학생 모드 전환 버튼 추가
        switchToStudentButton = new JButton("학생 로그인으로 전환");
        switchToStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToStudentMode();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(switchToStudentButton, gbc);

        updateMode(); // 초기 모드 설정

        setSize(500, 400); // 로그인 창 크기 조정
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

    private void addPasswordFieldAndLabel(String labelText, JPasswordField passwordField, GridBagConstraints gbc, int yPos) {
        JLabel label = new JLabel(labelText);
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.gridwidth = 1;
        add(label, gbc);

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        showPasswordButton = new JButton("👁");
        showPasswordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                passwordField.setEchoChar((char) 0);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                passwordField.setEchoChar('*');
            }
        });
        passwordPanel.add(showPasswordButton, BorderLayout.EAST);

        gbc.gridx = 1;
        gbc.gridy = yPos;
        gbc.gridwidth = 1;
        add(passwordPanel, gbc);
    }

    private void handleLogin() {
        if (locked) {
            String input = JOptionPane.showInputDialog(this, "'나는 사람입니다.'를 입력하세요.");
            if ("나는 사람입니다.".equals(input)) {
                count = 0;
                locked = false;
                JOptionPane.showMessageDialog(this, "이제 다시 로그인할 수 있습니다.", "Information", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "잘못입력하셨습니다. 다시 입력해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        String id = idField.getText();
        String password = new String(passwordField.getPassword());

        try {
            MUser user = daoUser.findUserByIdAndPassword(id, password);
            if (user != null) {
                if (isAdminMode && "admin".equals(user.getRole())) {
                    JOptionPane.showMessageDialog(this, "관리자 로그인 성공", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    if (parent instanceof VMainFrame) {
                        System.out.println("Parent is a VMainFrame instance");
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                parent.dispose(); // 강제로 parent 프레임 종료
                            }
                        });
                    } else {
                        System.out.println("Parent is not a VMainFrame instance");
                    }
                    VManagerFrame vmanagerFrame = new VManagerFrame();
                    vmanagerFrame.setVisible(true);
                } else if (!isAdminMode && "student".equals(user.getRole())) {
                    JOptionPane.showMessageDialog(this, "학생 로그인 성공", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    ((VMainFrame) parent).initialize(user);
                    ((VMainFrame) parent).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "잘못된 ID 또는 비밀번호입니다.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                count++;
                JOptionPane.showMessageDialog(this, "잘못된 ID 또는 비밀번호입니다.", "Error", JOptionPane.ERROR_MESSAGE);
                if (count >= 5) {
                    locked = true;
                    JOptionPane.showMessageDialog(this, "5회이상 비밀번호를 틀리셨습니다. '나는 사람입니다.'를 입력하여 다시 로그인을 시도하십시오.", "Error", JOptionPane.ERROR_MESSAGE);
                    handleLogin();
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading user information", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void switchToAdminMode() {
        isAdminMode = true;
        updateMode();
    }

    private void switchToStudentMode() {
        isAdminMode = false;
        updateMode();
    }

    private void updateMode() {
        if (isAdminMode) {
            setTitle("관리자 로그인");
            switchToAdminButton.setVisible(false);
            switchToStudentButton.setVisible(true);
            signUpButton.setVisible(false);
            findIdButton.setVisible(false);
        } else {
            setTitle("학생 로그인");
            switchToAdminButton.setVisible(true);
            switchToStudentButton.setVisible(false);
            signUpButton.setVisible(true);
            findIdButton.setVisible(true);
        }
    }
}
