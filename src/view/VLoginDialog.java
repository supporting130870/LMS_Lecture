package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class VLoginDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    // 사용자 ID 입력 필드
    private JTextField textId;
    // 비밀번호 입력 필드
    private JPasswordField textPassword;
    // 사용자 ID 라벨
    private JLabel labelId;
    // 비밀번호 라벨
    private JLabel labelPassword;
    // 로그인 버튼
    private JButton buttonOk;
    // 취소 버튼
    private JButton buttonCancel;

    public VLoginDialog(Frame parent) {
        super(parent, "Login", true); // 부모 생성자를 호출하여 "Login" 제목의 모달 다이얼로그 생성

        // 컴포넌트 초기화
        textId = new JTextField(20); // 20 열의 사용자 ID 입력 필드 생성
        textPassword = new JPasswordField(20); // 20 열의 비밀번호 입력 필드 생성
        labelId = new JLabel("User ID:"); // 사용자 ID 라벨 생성
        labelPassword = new JLabel("Password:"); // 비밀번호 라벨 생성
        buttonOk = new JButton("OK"); // OK 버튼 생성
        buttonCancel = new JButton("Cancel"); // 취소 버튼 생성

        // 레이아웃 설정 및 컴포넌트 추가
        JPanel panel = new JPanel(new GridBagLayout()); // GridBagLayout을 사용하는 패널 생성
        GridBagConstraints constraints = new GridBagConstraints(); // 컴포넌트 배치를 위한 GridBagConstraints 생성
        constraints.fill = GridBagConstraints.HORIZONTAL; // 컴포넌트가 수평으로 리사이즈되도록 설정

        // 사용자 ID 라벨을 패널에 추가
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(labelId, constraints);

        // 사용자 ID 입력 필드를 패널에 추가
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(textId, constraints);

        // 비밀번호 라벨을 패널에 추가
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(labelPassword, constraints);

        // 비밀번호 입력 필드를 패널에 추가
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(textPassword, constraints);

        // OK 버튼을 패널에 추가
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2; // 두 열에 걸쳐서 배치
        panel.add(buttonOk, constraints);

        // 취소 버튼을 패널에 추가
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2; // 두 열에 걸쳐서 배치
        panel.add(buttonCancel, constraints);

        // 패널을 다이얼로그에 추가
        add(panel, BorderLayout.CENTER);

        // 버튼에 액션 리스너 설정
        buttonOk.addActionListener(new ActionHandler());
        buttonCancel.addActionListener(new ActionHandler());

        // 기본 닫기 동작 및 다이얼로그 속성 설정
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // 닫을 때 다이얼로그 해제
        pack(); // 서브컴포넌트의 선호 크기와 레이아웃에 맞춰 다이얼로그 크기 조정
        setLocationRelativeTo(parent); // 부모 프레임을 기준으로 다이얼로그를 중앙에 위치시킴

        //윈도우 창에서 닫기버튼을 누르면 종료됨.
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0); // 프로그램 종료
            }
        });

    }

    // 버튼 동작을 처리하는 내부 클래스
    private class ActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand(); // 이벤트로부터 액션 명령어를 가져옴
            if (command.equals("OK")) {
                String userId = textId.getText(); // 텍스트 필드에서 사용자 ID를 가져옴
                char[] password = textPassword.getPassword(); // 비밀번호 필드에서 비밀번호를 가져옴
                // 로그인 로직 처리
                // 예를 들어, userId와 비밀번호를 검증
                if(Objects.equals(userId, "root") && new String(password).equals("test"))
                {
                    dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(
                            null,
                            "ID와 비밀번호가 일치하지 않습니다.",
                            "에러",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

                System.out.println("User ID: " + userId); // 사용자 ID 출력
                System.out.println("Password: " + new String(password)); // 비밀번호 출력 (데모 목적으로)

            } else if (command.equals("Cancel")) {

                System.exit(0); // 다이얼로그의 기본 닫기 동작 설정
            }
        }
    }
}
