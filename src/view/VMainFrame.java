package view;

import constants.Constant;
import model.MUser;

import javax.swing.JFrame;
import java.awt.*;
import java.io.Serial;

import static javax.management.Query.or;

public class VMainFrame extends JFrame {
	// attributes
	@Serial
	private static final long serialVersionUID = Constant.MainFrame.VERSIONID;

	// components
	private VSugangSincheong vSugangSincheong;
	private JMainMenuBar menuBar;
	private VClock vClock;
	private MUser loggedInUser;
	private VControlPanel vControlPanel;
	public int MaxCredit;
	public VMainFrame() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("data/Myongji-ui_BIG/5-1.gif");
		this.setIconImage(img);
		this.setTitle("명지대학교수강신청시스템");

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
	public void initialize()
	{
		this.vSugangSincheong.initialize();
	}
	public void initialize(MUser user) {

		// TODO Auto-generated method stub
		String temp = user.getCollege();
		if ("공과대학".equals(temp) || "건축대학".equals(temp) || "ICT융합대학".equals(temp)) {
			MaxCredit = 18;
			VControlPanel.MaxCredit(MaxCredit);
		}
		else VControlPanel.MaxCredit(17);
	}

	// methods
}

