package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JMainMenuBar extends JMenuBar {

    public JMainMenuBar() {
        // Create the Help menu
        JMenu helpMenu = new JMenu("Help");

        // Create the Program Description menu item
        JMenuItem programDescriptionItem = new JMenuItem("Program Description");

        // Add action listener to the Program Description item
        programDescriptionItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the popup with program description
                JOptionPane.showMessageDialog(JMainMenuBar.this,
                        "This is a Lecture Management System program.",
                        "Program Description",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Add the menu item to the Help menu
        helpMenu.add(programDescriptionItem);

        // Add the Help menu to the menu bar
        this.add(helpMenu);
    }
}
