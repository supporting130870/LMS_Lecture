package view;

import model.DAOIndex;
import model.DAOUser;
import model.MUser;
import model.MIndex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Vector;

public class VSignUpDialog extends JDialog {
    private JTextField nameField;
    private JTextField idField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> campusComboBox;
    private JComboBox<String> collegeComboBox;
    private JComboBox<String> departmentComboBox;
    private JTextField birthDateField;
    private JTextField studentIdField;
    private DAOUser daoUser;
    private DAOIndex daoIndex;
    private JButton checkIdButton;
    private boolean isIdChecked = false;
    private Vector<MIndex> currentColleges;  // 현재 선택된 캠퍼스의 컬리지들

    public VSignUpDialog(JFrame parent) {
        super(parent, "회원가입", true);
        daoUser = new DAOUser();
        daoIndex = new DAOIndex();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addLabelAndField("성명:", nameField = new JTextField(), gbc, 0);
        addLabelAndField("ID:", idField = new JTextField(), gbc, 1);
        addCheckIdButton(gbc, 1);
        addLabelAndField("비밀번호:", passwordField = new JPasswordField(), gbc, 2);
        addLabelAndField("비밀번호 확인:", confirmPasswordField = new JPasswordField(), gbc, 3);
        addLabelAndField("캠퍼스:", campusComboBox = new JComboBox<>(new String[]{"Seoul", "Yongin"}), gbc, 4);
        addLabelAndField("소속단과대:", collegeComboBox = new JComboBox<>(), gbc, 5);
        addLabelAndField("학과(학부):", departmentComboBox = new JComboBox<>(), gbc, 6);
        addLabelAndField("생년월일 (YYYYMMDD):", birthDateField = new JTextField(), gbc, 7);
        addLabelAndField("학생번호:", studentIdField = new JTextField(), gbc, 8);

        campusComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateColleges();
            }
        });

        collegeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDepartments();
            }
        });

        JButton signUpButton = new JButton("회원가입");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignUp();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 3;
        add(signUpButton, gbc);

        setSize(400, 600);
        setLocationRelativeTo(parent);

        updateColleges(); // 초기 콜리지 업데이트
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
        checkIdButton = new JButton("ID중복확인");
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
                JOptionPane.showMessageDialog(this, "이미 존재하는 ID입니다.", "Error", JOptionPane.ERROR_MESSAGE);
                isIdChecked = false;
            } else {
                JOptionPane.showMessageDialog(this, "입력하신 ID는 사용가능합니다", "Success", JOptionPane.INFORMATION_MESSAGE);
                isIdChecked = true;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error checking ID", "Error", JOptionPane.ERROR_MESSAGE);
            isIdChecked = false;
        }
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

    private void handleSignUp() {
        if (!isIdChecked) {
            JOptionPane.showMessageDialog(this, "ID중복검사를 먼저 진행해주세요", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String name = nameField.getText();
        String id = idField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String campus = (String) campusComboBox.getSelectedItem();
        String college = (String) collegeComboBox.getSelectedItem();
        String department = (String) departmentComboBox.getSelectedItem();
        String birthDate = birthDateField.getText();
        String studentId = studentIdField.getText();

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (daoUser.isUserIdExist(id)) {
                JOptionPane.showMessageDialog(this, "이미 존재하는 ID입니다.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error checking ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        MUser user = new MUser(name, id, password, campus, birthDate, studentId, college, department);

        try {
            daoUser.saveUser(user);
            JOptionPane.showMessageDialog(this, "회원가입에 성공했습니다.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving user information", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
