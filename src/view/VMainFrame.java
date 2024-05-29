package view;

import javax.swing.JFrame;

public class VMainFrame extends JFrame {
	// attributes
	private static final long serialVersionUID = 1L;
		
	// components
	private VSugangSincheong vSugangSincheong;
	
	public VMainFrame() {
		// attributes
		this.setLocation(200,100);
		this.setSize(600, 400);
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
