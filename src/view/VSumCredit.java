package view;

import javax.swing.*;
import java.awt.*;

public class VSumCredit extends JPanel {
    private static VLectureTable vSincheong;
    private static JLabel totalCreditsLabel;

    public VSumCredit() {
        this.setLayout(new BorderLayout());
        totalCreditsLabel = new JLabel("총 학점: 0");
        this.add(totalCreditsLabel, BorderLayout.SOUTH);

    }



    public static int update() {
        int credits = VSumCredit.vSincheong.updateTotalCredits();
        String creditWord = String.valueOf(credits);
        totalCreditsLabel.setText("총 학점: " + creditWord);
        return credits;
    }


    public void associate(VLectureTable vSincheong) {
        this.vSincheong = vSincheong;
    }
}
