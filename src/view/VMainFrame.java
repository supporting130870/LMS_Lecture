package view;

import constants.Constant;

import javax.swing.JFrame;
import java.io.Serial;

public class VMainFrame extends JFrame {
	// attributes
	@Serial
	private static final long serialVersionUID = Constant.MainFrame.VERSIONID;

	// components
	private VSugangSincheong vSugangSincheong;

	public VMainFrame() {
		// attributes
		//this.setLocation(Constant.MainFrame.X, Constant.MainFrame.Y);

		this.setSize(Constant.MainFrame.WIDTH, Constant.MainFrame.HEIGHT);
		this.setLocationRelativeTo(null);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// components
		this.vSugangSincheong = new VSugangSincheong();
		this.add(vSugangSincheong);


	}

	public void initialize() {
		// TODO Auto-generated method stub
		this.vSugangSincheong.initialize();
	}

	// methods
}
