package view.userInterface;

import constants.Constant;
import model.DAOUser;
import model.MUser;

import javax.swing.JFrame;
import java.awt.*;
import java.io.IOException;
import java.io.Serial;

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
	private DAOUser daoUser;
	private VSumCredit vSumCredit;

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
		this.daoUser = new DAOUser();

		this.vSugangSincheong = new VSugangSincheong();
        this.add(vSugangSincheong, BorderLayout.CENTER);

        this.vClock = new VClock();
        this.add(vClock, BorderLayout.SOUTH);

	}
	public void initialize()
	{
		this.vSugangSincheong.initialize();
	}
	public void initialize(MUser user) throws IOException {
		this.loggedInUser = user;
		this.vSugangSincheong.setLoggedInUser(user);
		// TODO Auto-generated method stub
		String temp = user.getCollege();
		if ("공과대학".equals(temp) || "건축대학".equals(temp) || "ICT융합대학".equals(temp)) {

			VControlPanel.MaxCredit(18);
			VSumCredit.MaxCredit(18);
		}
		else {
			VControlPanel.MaxCredit(17);
			VSumCredit.MaxCredit(17);
		}
		this.daoUser.loadDataTable(user.getId(), this.vSugangSincheong.getVMiridamgi(), this.vSugangSincheong.getVSincheong());

	}
	public void dispose() {
		System.out.println("VMainFrame dispose() called");
		super.dispose();
	}





}

