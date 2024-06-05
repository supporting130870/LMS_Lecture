package view;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VClock extends JPanel {

    private JLabel timeLabel;
    private SimpleDateFormat timeFormat;

    public VClock() {
        timeLabel = new JLabel();
        timeFormat = new SimpleDateFormat("HH:mm:ss.SSS");
        setLayout(new BorderLayout());
        add(timeLabel, BorderLayout.EAST);
        updateTime();

        // 타이머 설정 (1초마다 실행)
        Timer timer = new Timer(10, e -> updateTime());
        timer.start();

    }

    private void updateTime() {
        Date now = new Date();
        String timeString = timeFormat.format(now);
        timeLabel.setText(timeString);
    }

}
