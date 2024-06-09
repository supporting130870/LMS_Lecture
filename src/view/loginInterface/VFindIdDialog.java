package view.loginInterface;

import model.DAOUser;
import model.MUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class VFindIdDialog extends JDialog {
    private JTextField nameField;
    private JTextField birthDateField;
    private JTextField studentIdField;
    private JButton findIdButton;
    private DAOUser daoUser;

    public VFindIdDialog(JFrame parent) {
        super(parent, "아이디 찾기", true);
        daoUser = new DAOUser();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 이름 필드와 라벨 추가
        addLabelAndField("이름:", nameField = new JTextField(15), gbc, 0);

        // 생년월일 필드와 라벨 추가
        addLabelAndField("생년월일:", birthDateField = new JTextField(15), gbc, 1);

        // 학번 필드와 라벨 추가
        addLabelAndField("학번:", studentIdField = new JTextField(15), gbc, 2);

        // 아이디 찾기 버튼 추가
        findIdButton = new JButton("아이디 찾기");
        findIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleFindId();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(findIdButton, gbc);

        setSize(400, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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

    private void handleFindId() {
        String name = nameField.getText();
        String birthDate = birthDateField.getText();
        String studentId = studentIdField.getText();

        try {
            String foundId = daoUser.findIdByNameBirthDateAndStudentId(name, birthDate, studentId);
            if (foundId != null) {
                JOptionPane.showMessageDialog(this,
                        "아이디는 " + foundId + " 입니다.",
                        "아이디 찾기 성공",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "일치하는 정보를 찾을 수 없습니다.",
                        "아이디 찾기 실패",
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
