package view;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.MLecture;

public class VControlPanel extends JPanel {
	//attribute
	private static final long serialVersionUID = 1L;
//components
	private JButton buttonLeft;
	private JButton buttonRight;
	//association
	private VLectureTable vLectureTable1;
	private VLectureTable vLectureTable2;
	
	public VControlPanel() 
	{
		ActionListener actionHandler = new ActionHandler();
		LayoutManager layoutManager = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layoutManager);
		
		this.buttonLeft = new JButton("<<<");
		this.add(buttonLeft);
		this.buttonLeft.addActionListener(actionHandler);
		
		this.buttonRight = new JButton(">>>");
		this.add(buttonRight);
		this.buttonRight.addActionListener(actionHandler);

		
		
	}
	public void initialize() 
	{
		// TODO Auto-generated method stub
		
	}
	
	public void associate(VLectureTable vLectureTable1, VLectureTable vLectureTable2) {
		this.vLectureTable1 = vLectureTable1;
		this.vLectureTable2 = vLectureTable2;
	}
	
	private void moveRight()
	{
		Vector<MLecture> selectedLectureList = this.vLectureTable1.getSelectedLecture();
		this.vLectureTable2.addSelectedLectureList(selectedLectureList);
	}
	private void moveLeft() {
		Vector<MLecture> selectedLectureList = this.vLectureTable2.getSelectedLecture();
		this.vLectureTable1.addSelectedLectureList(selectedLectureList);
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
	}}
}
