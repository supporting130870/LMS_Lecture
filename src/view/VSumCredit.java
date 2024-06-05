package view;

import javax.swing.*;
import java.awt.*;

public class VSumCredit extends JPanel {
    private VLectureTable vSincheong;
    private JLabel totalCreditsLabel;

    public VSumCredit() {
        this.setLayout(new BorderLayout());
        totalCreditsLabel = new JLabel("총 학점: 0");
        this.add(totalCreditsLabel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        // 테스트를 위한 프레임 생성
        JFrame frame = new JFrame("VSumCredit Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        // VSumCredit 패널 추가
        VSumCredit panel = new VSumCredit();
        frame.add(panel);

        // 프레임 표시
        frame.setVisible(true);
    }

    public void update() {
        int credits = this.vSincheong.updateTotalCredits();
        String creditWord = String.valueOf(credits);
        totalCreditsLabel.setText("총 학점: " + creditWord);
    }

    public void associate(VLectureTable vSincheong) {
        this.vSincheong = vSincheong;
    }


}
