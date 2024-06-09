package view.userInterface;

import constants.Constant;

import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JPanel;



public class VIndexPanel extends JPanel {
	private static final long serialVersionUID = Constant.VIndexPanel.VERSIONID;
	
	private VIndexTable vCampus;
	private VIndexTable vCollege;
	private VIndexTable vDepartment;
	
	public VIndexPanel() {
		
		LayoutManager layoutmanager = new BoxLayout(this, BoxLayout.X_AXIS);
		this.setLayout(layoutmanager);
		
		this.vCampus = new VIndexTable();
		this.add(vCampus);
		
		this.vCollege = new VIndexTable();
		this.add(vCollege);
		
		
		this.vDepartment = new VIndexTable();
		this.add(vDepartment);
		
		
		
		this.vCampus.setNext(this.vCollege);
		this.vCollege.setNext(this.vDepartment);

		
	}

	public void initialize() {
		this.vCampus.show(Constant.VIndexPanel.home);

	}

	public void associate(VLectureTable vLectureTable) {
		this.vDepartment.setNext(vLectureTable);
		
	}
	

}
