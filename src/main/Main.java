package main;

import view.loginInterface.VLoginDialog;
import view.userInterface.VMainFrame;

public class Main {
	// attributes

	// components
	private VMainFrame vMainFrame;
	private VLoginDialog vLoginDialog;
	// constructor
	public Main() {
		// attributes

		// component
		//this.vLoginDialog = new VLoginDialog(null);
		//this.vLoginDialog.setVisible(true);
		//this.vMainFrame = new VMainFrame();
		//this.vMainFrame.setVisible(true);
		// associations
	}
	public static void main(String[] args) {
        VMainFrame vMainFrame = new VMainFrame();
        VLoginDialog vLoginDialog = new VLoginDialog(vMainFrame);
        vLoginDialog.setVisible(true);
        vMainFrame.setVisible(true);
        vMainFrame.initialize();
	}

	// methods
	private void run() {
	}

	private void initialize() {


    }
}
