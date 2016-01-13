package main_package;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

class UI {

	Game g1;
	
	Boolean whiteTurn = true;
	int player;
	
	JFrame mainFrame;
	JMenuBar menuBar;
	JMenu menu1;
	JMenuItem mItem1;
	
	public static void main(String[] args) {	
		UI ui = new UI();
		ui.buildGUI();
	}

	/**
	 * Spielverwaltung mit Benutzerinteraktionen
	 */
	void buildGUI(){
		
		mainFrame = new JFrame("Chess");
		mainFrame.setBounds(400, 200, 800, 800);
		
		menuBar = new JMenuBar();
		menu1 = new JMenu("Game");
		mItem1 = new JMenuItem("New");
		
		mainFrame.setJMenuBar(menuBar);
		menuBar.add(menu1);
		menu1.add(mItem1);
		
		mainFrame.setLayout(null);
		mainFrame.setVisible(true);;
	}

	}
