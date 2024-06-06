package view;

import constants.Constant;

import javax.swing.JFrame;
import java.awt.*;
import java.io.Serial;

public class VMainFrame extends JFrame {
	// attributes
	@Serial
	private static final long serialVersionUID = Constant.MainFrame.VERSIONID;

	// components
	private VSugangSincheong vSugangSincheong;
	private JMainMenuBar menuBar;
	private VClock vClock;
	public VMainFrame() {
		// attributes
		//this.setLocation(Constant.MainFrame.X, Constant.MainFrame.Y);

		this.setSize(Constant.MainFrame.WIDTH, Constant.MainFrame.HEIGHT);
		this.setLocationRelativeTo(null);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// components
		this.menuBar = new JMainMenuBar();
		this.setJMenuBar(menuBar);

        setLayout(new BorderLayout());


		this.vSugangSincheong = new VSugangSincheong();
        this.add(vSugangSincheong, BorderLayout.CENTER);

        this.vClock = new VClock();
        this.add(vClock, BorderLayout.SOUTH);

	}

	public void initialize() {
		// TODO Auto-generated method stub
		this.vSugangSincheong.initialize();
	}

	// methods
}
