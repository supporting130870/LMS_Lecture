package view;

import constants.Constant;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.LayoutManager;



public class VSugangSincheong extends JPanel {
	// attributes
	private static final long serialVersionUID = Constant.VSugangSincheong.VERSIONID;
	// components
	private VSelectionPanel vSelectionPanel;
	private VControlPanel vControlPanel1;
	private VLectureTable vMiridamgi;
	private VControlPanel vControlPanel2;	
	private VLectureTable vSincheong;
	
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
		
		//association
		this.vControlPanel1.associate(this.vSelectionPanel.getLectureTable(),this.vMiridamgi);
		this.vControlPanel2.associate(this.vMiridamgi,this.vSincheong);
		
	}
	public void initialize() {
		this.vControlPanel1.initialize();
		this.vMiridamgi.initialize();
		this.vControlPanel2.initialize();
		this.vSincheong.initialize();
		this.vSelectionPanel.initialize();
		
	}
	public void finish() {
	}	
	public void run() {
	}
}
