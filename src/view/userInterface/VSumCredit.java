package view.userInterface;

import javax.swing.*;
import java.awt.*;

public class VSumCredit extends JPanel {
    private static VLectureTable vSincheong;
    private static JLabel totalCreditsLabel;
    private static JLabel remainingCredit;
    private static int MaxCredit;

    public VSumCredit() {
        this.setLayout(new BorderLayout());
        totalCreditsLabel = new JLabel("총 학점: 0");

        remainingCredit = new JLabel("신청 가능한 학점: 0");
        this.add(remainingCredit, BorderLayout.CENTER);
        this.add(totalCreditsLabel, BorderLayout.SOUTH);

    }



    public static int update() {
        int credits = VSumCredit.vSincheong.updateTotalCredits();
        String creditWord = String.valueOf(credits);
        String remains = String.valueOf(VSumCredit.MaxCredit - credits);
        totalCreditsLabel.setText("총 학점: " + creditWord);
        remainingCredit.setText("신청 가능한 학점 : " + remains);
        return credits;
    }

    public static void MaxCredit(int i) {
        VSumCredit.MaxCredit = i;
    }


    public void associate(VLectureTable vSincheong) {
        this.vSincheong = vSincheong;
    }
}
