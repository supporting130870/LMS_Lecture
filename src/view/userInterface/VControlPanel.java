package view.userInterface;

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
	private static int MaxCredit;

	public VControlPanel()
	{



		JPanel yGap = new JPanel();
		yGap.setSize(0, 3);
		this.add(yGap); // 자식으로 등록

		LayoutManager layoutManager = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layoutManager);

		ActionListener actionHandler = new ActionHandler();

		this.buttonLeft = new JButton(Constant.VControlPanel.leftButton);
		this.add(buttonLeft);
		this.buttonLeft.addActionListener(actionHandler);

		yGap = new JPanel();
		yGap.setSize(0, 3);
		this.add(yGap); // 자식으로 등록

		this.buttonRight = new JButton(Constant.VControlPanel.rightButton);
		this.add(buttonRight);
		this.buttonRight.addActionListener(actionHandler);

		yGap = new JPanel();
		yGap.setSize(0, 3);
		this.add(yGap); // 자식으로 등록


	}

	public void initialize()
	{

	}


	public void associate(VLectureTable vLectureTable1, VLectureTable vLectureTable2, VSumCredit vSumCredit) {
		this.vLectureTable1 = vLectureTable1;
		this.vLectureTable2 = vLectureTable2;
		this.vSumCredit = vSumCredit;
	}

	public static void MaxCredit(int temp)
	{
		MaxCredit = temp;
	}
	private void moveRight() {
		//수정대상임.
		Vector<MLecture> selectedLectureList = this.vLectureTable1.getSelectedLecture();
		this.vLectureTable2.addSelectedLectureList(selectedLectureList);
		VSumCredit.updateSincheong();
		this.vLectureTable1.updateUI();
		this.vLectureTable2.updateUI();
		VSumCredit.updateSincheong();
		VSumCredit.updateMiridamgi();
		if (VSumCredit.updateSincheong() > MaxCredit) {
			JOptionPane.showMessageDialog(null, "최대 수강가능한 학점을 초과했습니다", "error", JOptionPane.ERROR_MESSAGE);
			this.vLectureTable2.removeSelectedLectureList(selectedLectureList);
			VSumCredit.updateSincheong();
			VSumCredit.updateMiridamgi();
		}
		if (VSumCredit.updateMiridamgi() > 21) {
			JOptionPane.showMessageDialog(null, "미리담기 가능한 학점을 초과했습니다", "error", JOptionPane.ERROR_MESSAGE);
			this.vLectureTable2.removeSelectedLectureList(selectedLectureList);
			VSumCredit.updateSincheong();
			VSumCredit.updateMiridamgi();
		}
	}

	private void moveLeft() {
		Vector<MLecture> selectedLectureList = this.vLectureTable2.getSelectedLecture();
		this.vLectureTable2.removeSelectedLectureList(selectedLectureList);
		this.vLectureTable1.addSelectedLectureList(selectedLectureList);
		this.vLectureTable1.updateUI();
		this.vLectureTable2.updateUI();

		VSumCredit.updateSincheong();
		VSumCredit.updateMiridamgi();


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
