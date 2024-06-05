package view;

import constants.Constant;
import model.MLecture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.Vector;




public class VControlPanel extends JPanel {
	//attribute
	@Serial
	private static final long serialVersionUID = Constant.VControlPanel.VERSIONID;
//components
	private JButton buttonLeft;
	private JButton buttonRight;
	//association
	private VLectureTable vLectureTable1;
	private VLectureTable vLectureTable2;
	private VSumCredit vSumCredit;

	public VControlPanel()
	{
		ActionListener actionHandler = new ActionHandler();
		LayoutManager layoutManager = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layoutManager);

		this.buttonLeft = new JButton(Constant.VControlPanel.leftButton);
		this.add(buttonLeft);
		this.buttonLeft.addActionListener(actionHandler);

		this.buttonRight = new JButton(Constant.VControlPanel.rightButton);
		this.add(buttonRight);
		this.buttonRight.addActionListener(actionHandler);


	}

	public void initialize()
	{
		// TODO Auto-generated method stub

	}

	public void associate(VLectureTable vLectureTable1, VLectureTable vLectureTable2, VSumCredit vSumCredit) {
		this.vLectureTable1 = vLectureTable1;
		this.vLectureTable2 = vLectureTable2;
		this.vSumCredit = vSumCredit;
	}

	private void moveRight() {
		Vector<MLecture> selectedLectureList = this.vLectureTable1.getSelectedLecture();
		this.vLectureTable2.addSelectedLectureList(selectedLectureList);
		if (this.vSumCredit != null) {
			this.vSumCredit.update();
		}

	}

	private void moveLeft() {
		Vector<MLecture> selectedLectureList = this.vLectureTable2.getSelectedLecture();
		this.vLectureTable2.removeSelectedLectureList(selectedLectureList);
		this.vLectureTable1.addSelectedLectureList(selectedLectureList);
		if (this.vSumCredit != null) {
			this.vSumCredit.update();
		}
	}


	class ActionHandler implements ActionListener{

		  public void actionPerformed(ActionEvent e){
		{
			if(e.getSource() == buttonLeft)
			{
				moveLeft();

			}
			else if(e.getSource() == buttonRight)
			{
				moveRight();
			}
		}
		  }
	}
}
