package view.managerInterface;

import view.userInterface.VMainMenuBar;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class VManagerFrame extends JFrame {
    private JScrollPane jScrollPane;
    private VUserList vUserList;
    private VMainMenuBar vMainMenuBar;

    public VManagerFrame() throws IOException {
        // 프레임 설정
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.getImage("data/Myongji-ui_BIG/5-1.gif");
        this.setIconImage(img);
        this.setTitle("관리자 페이지");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.vUserList = new VUserList(this);

        //항목추가
        this.add(this.vUserList, BorderLayout.CENTER);
        //활성화
        this.setVisible(true);
    }

}
