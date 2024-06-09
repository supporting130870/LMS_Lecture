package view.loginInterface;

import model.DAOIndex;
import model.DAOUser;
import model.MUser;
import model.MIndex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
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


    private String name;
   private String id;
    private String password;
    private String confirmPassword;
    private String campus;
    private String  college;
    private String department;
    private String birthDate;
    private String studentId;
    private String role;






    public VSignUpDialog(JFrame parent) {
        super(parent, "회원가입", true);
        daoUser = new DAOUser();
        daoIndex = new DAOIndex();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addLabelAndField("성명:", nameField = new JTextField(), gbc, 0);
        addLabelAndField("ID:", idField = new JTextField(),"8자리 이상 입력해주세요", gbc, 1);
        addCheckIdButton(gbc, 1);
        addLabelAndFieldWithPassword("비밀번호:", passwordField = new JPasswordField(),"8자리 이상 입력해주세요", gbc, 2);
        addLabelAndFieldWithPassword("비밀번호 확인:", confirmPasswordField = new JPasswordField(),"동일한 비밀번호를 입력해주세요.", gbc, 3);
        addLabelAndField("캠퍼스:", campusComboBox = new JComboBox<>(new String[]{"Seoul", "Yongin"}), gbc, 4);
        addLabelAndField("소속단과대:", collegeComboBox = new JComboBox<>(), gbc, 5);
        addLabelAndField("학과(학부):", departmentComboBox = new JComboBox<>(), gbc, 6);
        addLabelAndField("생년월일:", birthDateField = new JTextField(), "예)20050827의 형식으로 입력하시오.", gbc,7);
        addLabelAndField("학생번호:", studentIdField = new JTextField(),"예)OOOOOOOO의 형식으로 입력하시오.", gbc, 8);

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

        setSize(600, 800);
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
        gbc.gridwidth = 4;


        add(field, gbc);

    }

    private void addLabelAndField(String labelText, JComponent field, String description, GridBagConstraints gbc, int yPos) {
        JLabel label = new JLabel(labelText);
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.gridwidth = 1;
        add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = yPos;
        gbc.gridwidth = 4;

        addPlaceholder((JTextField) field, description);

        add(field, gbc);

    }
    private void addLabelAndFieldWithPassword(String labelText, JPasswordField field, String description, GridBagConstraints gbc, int yPos) {
        JLabel label = new JLabel(labelText);
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.gridwidth = 1;
        add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = yPos;
        gbc.gridwidth = 4;

        addPlaceholderWithPassword(field, description);

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

        gbc.gridx = 5;
        gbc.gridy = yPos;
        gbc.gridwidth = 1;
        add(checkIdButton, gbc);
    }

    private void handleCheckId() {
        this.updates();

        if(id.length() <8)
        {
            JOptionPane.showMessageDialog(this,
                    "8자리 이상의 아이디를 설정하세요.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
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


        this.updates();
        if(password.length() <8)
        {
            JOptionPane.showMessageDialog(this,
                    "8자리 이상의 비밀번호를 설정하세요.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

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

        MUser user = new MUser(name, id, password, campus, birthDate, studentId, college, department, role);

        if (!name.isEmpty()
                && !id.isEmpty()
                && !password.isEmpty()
                && !confirmPassword.isEmpty()
                && !campus.isEmpty()
                && !college.isEmpty()
                && !department.isEmpty()
                && !birthDate.isEmpty()
                && !studentId.isEmpty()
                && !role.isEmpty()
        ) {

            try
            {
                daoUser.saveUser(user);
                JOptionPane.showMessageDialog(this,
                        "회원가입에 성공했습니다.",
                        "회원가입성공",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
            catch(IOException e)
            {
                JOptionPane.showMessageDialog(this,
                        "Error saving user information",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "공백란이 있거나 올바른 값을 입력했는지 확인하세요.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void updates()
    {
        this.name = nameField.getText();
        this.id = idField.getText();
        this.password = new String(passwordField.getPassword());
        this.confirmPassword = new String(confirmPasswordField.getPassword());
        this.campus = (String) campusComboBox.getSelectedItem();
        this.college = (String) collegeComboBox.getSelectedItem();
        this.department = (String) departmentComboBox.getSelectedItem();
        this.birthDate = birthDateField.getText();
        this.studentId = studentIdField.getText();
        this.role = "student";
    }
    //--------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------
    private void addPlaceholder(JTextField textField, String placeholderText) {
        textField.setText(placeholderText);
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholderText)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholderText);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }
    private void addPlaceholderWithPassword(JPasswordField passwordField, String placeholderText) {
        passwordField.setText(placeholderText);
        passwordField.setForeground(Color.GRAY);

        passwordField.setEchoChar((char) 0);
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText(placeholderText);
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0);  // echo char 비활성화
                }
            }
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals(placeholderText)) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('*');  // 설정된 echo char 사용
                }
            }


        });
    }
}
