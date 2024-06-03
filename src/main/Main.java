package main;

import view.VLoginDialog;
import view.VMainFrame;

public class Main {
	// attributes
	
	// components
	private VMainFrame vMainFrame;
	private VLoginDialog vLoginDialog;
	// constructor
	public Main() {
		// attributes

		// component
		this.vLoginDialog = new VLoginDialog(this.vMainFrame);
		this.vLoginDialog.setVisible(true);
		this.vMainFrame = new VMainFrame();
		this.vMainFrame.setVisible(true);
		
		// associations
	}
	private void initialize() {
		this.vMainFrame.initialize();
	}
	
	// methods
	private void run() {
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		main.initialize();
		main.run();
	}


	

}
