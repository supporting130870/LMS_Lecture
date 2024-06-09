package view.userInterface;

import javax.swing.*;
import java.awt.*;

public class VSumCredit extends JPanel {
    private static VLectureTable vSincheong;
    private static VLectureTable vMiridamgi;
    private static JLabel totalCreditsLabel;
    private static JLabel remainingCredit;
    private static JLabel miridamgiTotal;
    private static JLabel remainingMiridamgiCredit;
    private static int MaxCredit;

    public VSumCredit() {
        this.setLayout(new BorderLayout());

        miridamgiTotal = new JLabel("미리담기 한 학점: 0");
        remainingMiridamgiCredit = new JLabel(("담을 수 있는 학점: 0"));

        JPanel pane1 = new JPanel();
        pane1.add(miridamgiTotal);
        pane1.add(remainingMiridamgiCredit);
        LayoutManager layoutManager1 = new BoxLayout(pane1, BoxLayout.Y_AXIS);
        pane1.setLayout(layoutManager1);
        this.add(pane1, BorderLayout.NORTH);

        totalCreditsLabel = new JLabel("총 학점: 0");
        remainingCredit = new JLabel("신청 가능한 학점: 0");

        JPanel pane2 = new JPanel();
        pane2.add(remainingCredit);
        pane2.add(totalCreditsLabel);
        LayoutManager layoutManager2 = new BoxLayout(pane2, BoxLayout.Y_AXIS);
        pane2.setLayout(layoutManager2);
        this.add(pane2, BorderLayout.SOUTH);
    }



    public static int updateSincheong() {
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

    public static int updateMiridamgi() {
        int credits = VSumCredit.vMiridamgi.updateTotalCredits();
        String creditWord = String.valueOf(credits);
        String remains = String.valueOf(21 - credits);
        miridamgiTotal.setText("미리담기 한 학점: " + creditWord);
        remainingMiridamgiCredit.setText("담을 수 있는 학점: " + remains);
        return credits;
    }


    public void associate(VLectureTable vSincheong, VLectureTable vMiridamgi) {

        VSumCredit.vSincheong = vSincheong;
        VSumCredit.vMiridamgi = vMiridamgi;
    }
}
