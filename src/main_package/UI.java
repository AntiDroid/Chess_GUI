package main_package;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javafx.geometry.Point2D;

class UI {

	Game g1;
	
	Boolean whiteTurn = true;
	int player;
	
	JFrame mainFrame;
	JMenuBar menuBar;
	JMenu menu1;
	JMenuItem mItem1, mItem2;
	JPanel gamePanel, player1Panel, player2Panel;
	JLabel player1, player2;
	
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
                "BLACK",
                JOptionPane.PLAIN_MESSAGE);
		
		}while(name1.equals("") || !name1.matches("\\w+"));
		
		do{
		name2 = JOptionPane.showInputDialog(null, "Enter your name!",
                "WHITE",
                JOptionPane.PLAIN_MESSAGE);
		
		}while(name2.equals("") || !name2.matches("\\w+"));
		
		g1 = new Game(new Player(name1, false), new Player(name2, true));
		
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
		
		player1Panel = new JPanel();
		player1Panel.setBounds(900, 50, 250, 250);
		player1Panel.setBackground(Color.WHITE);
		
		player2Panel = new JPanel();
		player2Panel.setBounds(900, 515, 250, 250);
		player2Panel.setBackground(Color.WHITE);
		
		player1 = new JLabel(g1.getPlayer()[0].getName());
		player1.setFont(new Font("Serif", Font.BOLD, 24));
		
		player2 = new JLabel(g1.getPlayer()[1].getName());
		player2.setFont(new Font("Serif", Font.BOLD, 24));
		
		for(int y = 0; y<8; y++) {
			for(int x = 0; x<8; x++) {
				g1.brett.getFelder()[x][y].setBackground(Color.WHITE);
				g1.brett.getFelder()[x][y].addMouseListener(new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent arg0) {
						
						if(whiteTurn)
							player = 1;
						else
							player = 0;
						
						int belegung = ((Field)arg0.getSource()).getBelegung();
						
						if(belegung != 99 && g1.brett.getFigures()[belegung].getIW() == g1.getPlayer()[player].getIsWhite()){
							g1.brett.selectFigur(((Field)arg0.getSource()).getKoordinate());
							List<Point2D> liste = g1.brett.movePossibilities();
							
							hightlightPos(liste);
						}
						else if(g1.brett.movePossibilities().contains(((Field)arg0.getSource()).getKoordinate())){
							g1.brett.move(((Field)arg0.getSource()).getKoordinate());
							((Field)arg0.getSource()).add(g1.brett.getFigures()[g1.brett.getSelFig()].getImage());
							clearPositions();
							gamePanel.repaint();
						}
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						
					}

					@Override
					public void mousePressed(MouseEvent arg0) {

						
					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						
					}
					
				});;
				gamePanel.add(g1.brett.getFelder()[x][y]);
			}
		}
		
		player1Panel.add(player1);
		player2Panel.add(player2);
		
		mainFrame.setJMenuBar(menuBar);
		mainFrame.add(gamePanel);
		mainFrame.add(player1Panel);
		mainFrame.add(player2Panel);
		menuBar.add(menu1);
		menu1.add(mItem1);
		menu1.add(mItem2);
		
		gamePanel.repaint();
		mainFrame.setResizable(false);
		mainFrame.setLayout(null);
		mainFrame.setVisible(true);;
	}

	public void hightlightPos(List<Point2D> list){
		
		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				if(list.contains(g1.brett.getFelder()[i][j].getKoordinate()))
					g1.brett.getFelder()[i][j].setBackground(Color.GREEN);
			}
		}
	}
	
	public void clearPositions(){
		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				g1.brett.getFelder()[i][j].setBackground(Color.WHITE);
			}
		}
	}
		
	}
	
	
