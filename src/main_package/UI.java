package main_package;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
		
		String name1;
		String name2;
		
		
		do{
		name1 = JOptionPane.showInputDialog(null, "Enter your name!",
                "Player 1",
                JOptionPane.PLAIN_MESSAGE);
		
		}while(name1.equals("") || !name1.matches("\\w+"));
		
		do{
		name2 = JOptionPane.showInputDialog(null, "Enter your name!",
                "Player 2",
                JOptionPane.PLAIN_MESSAGE);
		
		}while(name2.equals("") || !name2.matches("\\w+"));
		
		System.out.println(name1);
		System.out.println(name2);
		g1 = new Game(new Player(name1, true), new Player(name2, false));
		
		
		
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
