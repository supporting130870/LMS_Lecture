package view;

import constants.Constant;

import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class VSelectionPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = Constant.VLectureTable.VERSIONID;
	
	//components
	private VIndexPanel vIndexPanel;
	public VLectureTable vLectureTable;
	

	
	public VSelectionPanel() {

		LayoutManager layoutManager = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layoutManager);

		this.vIndexPanel = new VIndexPanel();
		this.add(vIndexPanel);
		
		vLectureTable = new VLectureTable();
		this.add(vLectureTable);
		
		//association
		this.vIndexPanel.associate(this.vLectureTable);
	}

	public VLectureTable getLectureTable()
	{
		return this.vLectureTable;
	}

	public void initialize() {
		this.vIndexPanel.initialize();
		this.vLectureTable.initialize();
		
		

		
	}

}
