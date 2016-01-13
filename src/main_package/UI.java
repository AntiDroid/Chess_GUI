package main_package;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
		
		g1 = new Game(new Player("Talip", true), new Player("Luise", false));
		
		mainFrame = new JFrame("Chess");
		mainFrame.setBounds(400, 100, 1200, 900);
		
		menuBar = new JMenuBar();
		menu1 = new JMenu("Game");
		mItem1 = new JMenuItem("New");
		mItem2 = new JMenuItem("Remis");
		gamePanel = new JPanel();
		gamePanel.setBounds(50, 25, 800, 770);
		gamePanel.setBackground(Color.BLACK);
		gamePanel.setLayout(new GridLayout(8, 8, 3 , 3));
		gamePanel.setBorder(BorderFactory.createEmptyBorder( 5, 5, 5, 5));
		
		for(int y = 0; y<8; y++) {
			for(int x = 0; x<8; x++) {
				g1.brett.getFelder()[x][y].setBackground(Color.WHITE);
				gamePanel.add(g1.brett.getFelder()[x][y]);
			}
		}
		
		
		mainFrame.setJMenuBar(menuBar);
		mainFrame.add(gamePanel);
		menuBar.add(menu1);
		menu1.add(mItem1);
		menu1.add(mItem2);
		
		gamePanel.repaint();
		mainFrame.setResizable(false);
		mainFrame.setLayout(null);
		mainFrame.setVisible(true);;
	}

	}
