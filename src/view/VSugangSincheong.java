package view;

import constants.Constant;
import model.DAOUser;
import model.MUser;

import javax.swing.*;
import java.awt.LayoutManager;
import java.io.IOException;


public class VSugangSincheong extends JPanel {
	// attributes
	private static final long serialVersionUID = Constant.VSugangSincheong.VERSIONID;
	private final DAOUser daoUser;
	// components
	private VSelectionPanel vSelectionPanel;
	private VControlPanel vControlPanel1;
	private VLectureTable vMiridamgi;
	private VControlPanel vControlPanel2;	
	private VLectureTable vSincheong;
	private VSumCredit vSumCredit;
	private MUser loggedInUser;

	// methods
	public VSugangSincheong() {
		
		LayoutManager layoutmanager = new BoxLayout(this, BoxLayout.X_AXIS);
		this.setLayout(layoutmanager);
		
		// components

		this.vSelectionPanel = new VSelectionPanel();
		this.add(vSelectionPanel);

		this.vControlPanel1 = new VControlPanel();
		this.add(vControlPanel1);
		
		this.vMiridamgi = new VLectureTable();
		this.add(vMiridamgi);
		
		this.vControlPanel2 = new VControlPanel();
		this.add(vControlPanel2);
		
		this.vSincheong = new VLectureTable();
		this.add(vSincheong);

		this.vSumCredit = new VSumCredit();
		this.add(vSumCredit);

		//association
		this.vControlPanel1.associate(this.vSelectionPanel.getLectureTable(), this.vMiridamgi, this.vSumCredit);
		this.vControlPanel2.associate(this.vMiridamgi, this.vSincheong, this.vSumCredit);
		this.vSumCredit.associate(this.vSincheong);


		this.daoUser = new DAOUser();
		this.addTableModelListeners();


	}

	private void addTableModelListeners() {
		vMiridamgi.getModel().addTableModelListener(e -> {
			if (loggedInUser != null) {
				try {
					daoUser.saveDataTable(loggedInUser.getId(), vMiridamgi, vSincheong);
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
		});

		vSincheong.getModel().addTableModelListener(e -> {
			if (loggedInUser != null) {
				try {
					daoUser.saveDataTable(loggedInUser.getId(), vMiridamgi, vSincheong);
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
		});
	}

	public void initialize() {
		this.vControlPanel1.initialize();
		this.vMiridamgi.initialize();
		this.vControlPanel2.initialize();
		this.vSincheong.initialize();
		this.vSelectionPanel.initialize();
	}

	public VLectureTable getVMiridamgi() {
		return vMiridamgi;
	}

	public VLectureTable getVSincheong() {
		return vSincheong;
	}

	public void setLoggedInUser(MUser user) {
		this.loggedInUser = user;
	}
}