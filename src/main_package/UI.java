package main_package;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	String name1, name2;
	
	JFrame mainFrame;
	JMenuBar menuBar;
	JMenu menu1;
	JMenuItem mItem1, mItem2;
	JPanel gamePanel, player1Panel, player2Panel;
	JLabel player1, player2;
	
	MouseListener Func, noFunc;
	
	public static void main(String[] args) {	
		UI ui = new UI();
		ui.buildGUI();
	}

	/**
	 * Spielverwaltung mit Benutzerinteraktionen
	 */
	void buildGUI(){
		
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
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		menuBar = new JMenuBar();
		menu1 = new JMenu("Game");
		
		mItem1 = new JMenuItem("New");
		
		/*
		mItem1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				g1 = new Game(new Player(name1, false), new Player(name2, true));
				update();
				makeResponsive();
				gamePanel.repaint();
				whiteTurn = true;
			}
			
		});
		*/
		
		mItem2 = new JMenuItem("Remis");
		mItem2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				g1.Remis();
				JOptionPane.showMessageDialog(mainFrame, "Remis was manually triggered!");
				makeNonResponsive();
				
			}
		});
		
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
		
		Func = new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(whiteTurn)
					player = 1;
				else
					player = 0;
				
				int belegung = ((Field)arg0.getSource()).getBelegung();
				
				//die Figurenauswahl - kein leeres Feld UND die Figur muss deiner Farbe entsprechen
				if(belegung != Field.emptyField && g1.brett.getFigures()[belegung].getIW() == g1.getPlayer()[player].getIsWhite()){
					int previousFig = g1.brett.getSelFig();
					g1.brett.selectFigur(((Field)arg0.getSource()).getKoordinate());
					List<Point2D> liste = g1.brett.filter(g1.brett.movePossibilities(), g1.getPlayer()[player].getIsWhite());
					if(g1.brett.getFigures()[g1.brett.getSelFig()].getIW() == g1.brett.getFigures()[previousFig].getIW())
						clearPositions();
					hightlightPos(liste);
				}
				//die Positionsauswahl - das ausgewaehlte Feld muss einer Bewegungsmoeglichkeit der selektierten Figur entsprechen
				else if(g1.brett.filter(g1.brett.movePossibilities(), g1.getPlayer()[player].getIsWhite()).contains(((Field)arg0.getSource()).getKoordinate())){
					
					g1.brett.move(((Field)arg0.getSource()).getKoordinate());
					
					update();
					clearPositions();
					gamePanel.repaint();
					checkForEnd();
					whiteTurn = !whiteTurn;
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
			
		};
		noFunc = new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				JOptionPane.showMessageDialog(mainFrame, "The game is over!\nStart a new one!");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			
		};
		
		for(int y = 0; y<8; y++) {
			for(int x = 0; x<8; x++) {
				if(g1.brett.getFelder()[x][y].getIsWhite())
					g1.brett.getFelder()[x][y].setBackground(Color.WHITE);
				else
					g1.brett.getFelder()[x][y].setBackground(Color.LIGHT_GRAY);
				
				g1.brett.getFelder()[x][y].addMouseListener(Func);
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

	//highlights auf Feldern erzeugen auf die man sich bewegen kann
	public void hightlightPos(List<Point2D> list){
		
		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				if(list.contains(g1.brett.getFelder()[i][j].getKoordinate()))
					g1.brett.getFelder()[i][j].setBackground(Color.cyan);
			}
		}
	}
	
	//die highlights entfernen und das Spielfeld neu "bemalen"
	public void clearPositions(){

		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				if(g1.brett.getFelder()[i][j].getIsWhite())
					g1.brett.getFelder()[i][j].setBackground(Color.WHITE);
				else
					g1.brett.getFelder()[i][j].setBackground(Color.LIGHT_GRAY);
				
				int kingID = 0;
				
				if(g1.brett.getWhiteChecK())
					kingID = 14;
				else if(g1.brett.getBlackChecK())
					kingID = 30;
				
				if(kingID != 0){
					Point2D king = g1.brett.searchFigCoordByIndex(kingID);
					g1.brett.getFelder()[(int)king.getX()][(int)king.getY()].setBackground(Color.RED);
				}
					
			}
		}
	}
	
	//die Felder neu mit den Figurenbildern initialisieren
	public void update(){
		
		for(Field[] f: g1.brett.getFelder()){
			for(Field fx: f){
				fx.removeAll();
				if(fx.getBelegung() != 99)
					fx.add(g1.brett.getFigures()[fx.getBelegung()].getImage());
			}
		}
	}
	
	public void makeResponsive(){
		for(Field[] f: g1.brett.getFelder()){
			for(Field fx: f){
				fx.removeAll();
				if(fx.getBelegung() != Field.emptyField)
					fx.addMouseListener(Func);
			}
		}
	}
	
	//dem Spielfeld die Klick-Funktionen nehmen
	public void makeNonResponsive(){
		for(Field[] f: g1.brett.getFelder()){
			for(Field fx: f){
				fx.addMouseListener(noFunc);
			}
		}
	}
		
	//Situationen, welche nach der Bewegung einer Figur auftreten koennen
	public void checkForEnd(){
		if(g1.brett.SchachMatt(!whiteTurn)){
			g1.Win(whiteTurn);
			JOptionPane.showMessageDialog(mainFrame, "Checkmate!");
			makeNonResponsive();
		}
		else if(g1.brett.Patt(!whiteTurn)){
			g1.Remis();
			JOptionPane.showMessageDialog(mainFrame, "Patt!");
			makeNonResponsive();
		}     
		else if(g1.brett.Patt(whiteTurn)){
			g1.Remis();
			JOptionPane.showMessageDialog(mainFrame, "Patt!");
			makeNonResponsive();
		}
		else if(g1.brett.king1v1()){
			g1.Remis();
			JOptionPane.showMessageDialog(mainFrame, "Patt!");
			makeNonResponsive();
		}
	}
	}
	
	
