package main_package;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

class UI {

	Game g1;
	
	Boolean whiteTurn = true;
	int player;
	
	JFrame mainFrame;
	JMenuBar menuBar;
	JMenu menu1;
	JMenuItem mItem1, mItem2;
	JPanel gamePanel;
	
	public static void main(String[] args) {	
		UI ui = new UI();
		ui.buildGUI();
	}

	/**
	 * Spielverwaltung mit Benutzerinteraktionen
	 */
	void buildGUI(){
		
		mainFrame = new JFrame("Chess");
		mainFrame.setBounds(400, 200, 1200, 800);
		
		menuBar = new JMenuBar();
		menu1 = new JMenu("Game");
		mItem1 = new JMenuItem("New");
		mItem2 = new JMenuItem("Remis");
		gamePanel = new JPanel();
		gamePanel.setBounds(50, 25, 700, 700);
		gamePanel.setLayout(null);
		gamePanel.setBackground(Color.RED);
		gamePanel.setLayout(new GridLayout());
		
		mainFrame.setJMenuBar(menuBar);
		mainFrame.add(gamePanel);
		menuBar.add(menu1);
		menu1.add(mItem1);
		menu1.add(mItem2);
		
		mainFrame.setResizable(false);
		mainFrame.setLayout(null);
		mainFrame.setVisible(true);;
	}

	}
