package view.userInterface;

import model.DAOIndex;
import model.DAOUser;
import model.MIndex;
import model.MUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

public class VMyPage extends JDialog {

    private MUser mUser;
    private DAOUser daoUser;
    private DAOIndex daoIndex;
    private JTextField nameField, birthDateField, studentIdField, roleField;
    private JPasswordField oldPasswordField, newPasswordField, confirmPasswordField;
    private JComboBox<String> campusComboBox, collegeComboBox, departmentComboBox;
    private Vector<MIndex> currentColleges;  // 현재 선택된 캠퍼스의 컬리지들
    private Vector<MIndex> currentDepartments;  // 현재 선택된 단과대의 학과들
    private JPanel userInfoPanel, passwordPanel;
    private VMainFrame parent;

    public VMyPage(VMainFrame parent, MUser user, DAOUser daoUser, DAOIndex daoIndex) {
        super(parent, "My Page", true);
        this.daoUser = daoUser;
        this.daoIndex = daoIndex;
        this.parent = parent;
        associate(user);


        ImageIcon originalIcon = new ImageIcon("data/Myongji-ui_BIG/5-1.png");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);


        // 사용자 정보 패널
        this.userInfoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addLabelAndField("성명:", nameField = new JTextField(20), gbc, 0);
        nameField.setText(mUser.getName());
        addLabelAndField("캠퍼스:", campusComboBox = new JComboBox<>(), gbc, 1);
        addLabelAndField("소속단과대:", collegeComboBox = new JComboBox<>(), gbc, 2);
        addLabelAndField("학과(학부):", departmentComboBox = new JComboBox<>(), gbc, 3);
        addLabelAndField("생년월일 (YYYYMMDD):", birthDateField = new JTextField(20), gbc, 4);
        birthDateField.setText(mUser.getBirthDate());
        addLabelAndField("학생번호:", studentIdField = new JTextField(20), gbc, 5);
        studentIdField.setText(mUser.getStudentId());

        // 역할 필드를 수정할 수 없도록 설정
        addLabelAndField("Role:", roleField = new JTextField(20), gbc, 6);
        roleField.setText(mUser.getRole());
        roleField.setEditable(false);

        campusComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (campusComboBox.getSelectedItem() != null) {
                    updateColleges();
                }
            }
        });

        collegeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (collegeComboBox.getSelectedItem() != null) {
                    updateDepartments();
                }
            }
        });

        // 비밀번호 변경 패널
        this.passwordPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcPassword = new GridBagConstraints();
        gbcPassword.insets = new Insets(5, 5, 5, 5);
        gbcPassword.fill = GridBagConstraints.HORIZONTAL;

        // 필드와 라벨을 passwordPanel에 추가
        addLabelAndField("Old Password:", oldPasswordField = new JPasswordField(20), gbcPassword, 0, passwordPanel);
        addLabelAndField("New Password:", newPasswordField = new JPasswordField(20), gbcPassword, 1, passwordPanel);
        addLabelAndField("Confirm New Password:", confirmPasswordField = new JPasswordField(20), gbcPassword, 2, passwordPanel); // 추가

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton updateInfoButton = new JButton("Update Info");
        updateInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MUser updatedUser = daoUser.updateInfo(mUser, nameField.getText(), (String) campusComboBox.getSelectedItem(), birthDateField.getText(),
                            studentIdField.getText(), (String) collegeComboBox.getSelectedItem(), (String) departmentComboBox.getSelectedItem());
                    JOptionPane.showMessageDialog(VMyPage.this, "정보가 업데이트되었습니다.\n 로그아웃 후 재로그인이 필요합니다.");
                    associate(updatedUser);
                    parent.logOut();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(VMyPage.this, "정보 업데이트 중 오류가 발생했습니다: " + ex.getMessage());
                }
            }
        });

        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePassword();
            }
        });

        buttonPanel.add(updateInfoButton);
        buttonPanel.add(changePasswordButton);

        setLayout(new BorderLayout());
        add(imageLabel, BorderLayout.NORTH);

        // 중앙에 두 개의 패널을 배치하기 위해 Box 레이아웃 사용
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(userInfoPanel);
        centerPanel.add(passwordPanel);

        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);

        // 다이얼로그 크기 설정
        setSize(600, 800); // 다이얼로그 크기 조정
        setLocationRelativeTo(parent);

        initializeComboBoxes();
    }

    private void addLabelAndField(String labelText, JComponent field, GridBagConstraints gbc, int yPos) {
        addLabelAndField(labelText, field, gbc, yPos, userInfoPanel);
    }

    private void addLabelAndField(String labelText, JComponent field, GridBagConstraints gbc, int yPos, JPanel panel) {
        JLabel label = new JLabel(labelText);
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = yPos;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(field, gbc);
    }

    private void initializeComboBoxes() {
        campusComboBox.addItem("Seoul");
        campusComboBox.addItem("Yongin");

        // 캠퍼스를 설정하고 콜리지와 학과를 업데이트
        String campus = mUser.getCampus().equals("Seoul") ? "Seoul" : "Yongin";
        campusComboBox.setSelectedItem(campus);
        updateColleges();
        collegeComboBox.setSelectedItem(mUser.getCollege());
        departmentComboBox.setSelectedItem(mUser.getDepartment());
    }

    private void updateColleges() {
        String selectedCampus = (String) campusComboBox.getSelectedItem();
        currentColleges = daoIndex.getColleges(selectedCampus);
        collegeComboBox.removeAllItems();
        for (MIndex college : currentColleges) {
            collegeComboBox.addItem(college.getName());
        }
        updateDepartments(); // 콜리지 변경 시 디파트먼트도 업데이트
    }

    private void updateDepartments() {
        int selectedCollegeIndex = collegeComboBox.getSelectedIndex();
        if (selectedCollegeIndex != -1) {
            MIndex selectedCollege = currentColleges.get(selectedCollegeIndex);
            Vector<MIndex> departments = daoIndex.getDepartments(selectedCollege.getFileName());
            departmentComboBox.removeAllItems();
            for (MIndex department : departments) {
                departmentComboBox.addItem(department.getName());
            }
        }
    }

    private void changePassword() {
        String oldPassword = new String(oldPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "새 비밀번호가 일치하지 않습니다.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            daoUser.changePassword(mUser, oldPassword, newPassword);
            JOptionPane.showMessageDialog(this, "비밀번호가 변경되었습니다.\n 로그아웃 후 재로그인이 필요합니다.");
            this.parent.logOut();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "비밀번호 변경 중 오류가 발생했습니다: " + ex.getMessage());
        }
    }

    public void associate(MUser mUser) {
        this.mUser = mUser;
    }
}
