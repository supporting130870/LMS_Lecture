package view.userInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class VMainMenuBar extends JMenuBar {
    private VMainFrame vMainFrame;
    public VMainMenuBar(VMainFrame vMainFrame) {

        this.vMainFrame = vMainFrame;
        JMenu helpMenu = new JMenu("도움말");

        // Create the Program Description menu item
        JMenuItem programDescriptionItem = new JMenuItem("프로그램 정보");


        // Add action listener to the Program Description item
        programDescriptionItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the popup with program description
                JOptionPane.showMessageDialog(VMainMenuBar.this,
                        "2024년 1학기 절차적 사고와 프로그래밍 과목 기말평가 프로젝트로 제작된 프로그램입니다.\n프로그램의 코드는 최성운 교수님이 수업시간에 강의하신 내용을 기반으로 확장되었습니다 .",
                        "프로그램 정보",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        JMenuItem InformSincheong = new JMenuItem("수강신청 도움말");
        InformSincheong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the popup with program description
                try {
                    Desktop.getDesktop().browse(new URI("https://www.mju.ac.kr/mjukr/277/subview.do"));
                } catch (IOException | URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JMenu Menu = new JMenu("메뉴");
        JMenuItem myPage = new JMenuItem("내 정보");
        JMenuItem logOut = new JMenuItem("로그아웃");

        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vMainFrame.logOut();
            }
        });

        myPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        Menu.add(myPage);
        Menu.add(logOut);
        this.add(Menu);

        helpMenu.add(programDescriptionItem);
        helpMenu.add(InformSincheong);
        this.add(helpMenu);


    }
}
