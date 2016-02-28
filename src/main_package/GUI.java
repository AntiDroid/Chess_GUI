package main_package;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import utilities.Point;

class GUI {

	Game curGame;
	int player;
	String name1, name2;
	
	JFrame mainFrame;
	JMenuBar menuBar;
	JMenu menu1;
	JMenuItem mItem1, mItem2;
	JPanel gamePanel, player1Panel, player2Panel;
	JPanel terminatedFig1, terminatedFig2;
	JLabel player1, player2;
	
	Color bField = new Color(100, 102, 0);
	Color wField = new Color(140, 255, 255);
	Color turnColor = Color.yellow;
	Color hightlightColor = Color.darkGray;
	
	/**
	 * Reaktionen auf Eingaben im normalen Spielverlauf
	 */
	MouseListener Func;
	
	/**
	 * Reaktionen auf Eingaben mit Fehlermeldungen, wenn das Spiel 
	 * aktuell nicht mehr laeuft
	 */
	MouseListener noFunc;
	
	public static void main(String[] args) {	
		GUI ui = new GUI();
		ui.buildGUI();
	}

	void buildGUI(){
		
		try{
		
		do{
		name1 = JOptionPane.showInputDialog(null, "Enter your name!",
                "BLACK",
                JOptionPane.PLAIN_MESSAGE);
		
		if(name1.equals(""))
			throw new NullPointerException();
		
		}while(!name1.matches("\\w+"));
		
		}
		catch(NullPointerException n){
			name1 = "Player 1";
		}
		
		try{
		
		do{
		name2 = JOptionPane.showInputDialog(null, "Enter your name!",
                "WHITE",
                JOptionPane.PLAIN_MESSAGE);
		
		if(name2.equals(""))
			throw new NullPointerException();
		
		}while(name2.equals("") || !name2.matches("\\w+") || name2.equals(name1));
		
		}
		catch(NullPointerException n){
			name2 = "Player 2";
		}
		
		curGame = new Game(new Player(name1, false), new Player(name2, true));
		
		mainFrame = new JFrame("Chess");
		mainFrame.setBounds(400, 100, 1400, 900);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		menuBar = new JMenuBar();
		menu1 = new JMenu("Game");
		
		mItem1 = new JMenuItem("New");
		mItem1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				curGame = new Game(new Player(name1, false), new Player(name2, true));	
				setUpNewGame();
				
			}
			
		});
			
		mItem2 = new JMenuItem("Give Up");
		mItem2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				curGame.Win(!curGame.getWhiteTurn());
				makeResponsive(false);
			}
		});
		
		gamePanel = new JPanel();
		gamePanel.setBounds(50, 25, 800, 770);
		gamePanel.setBackground(Color.BLACK);
		gamePanel.setLayout(new GridLayout(8, 8, 3, 3));
		gamePanel.setBorder(BorderFactory.createEmptyBorder( 7, 7, 7, 7));
		
		player1Panel = new JPanel();
		player1Panel.setLayout(new BoxLayout(player1Panel, BoxLayout.PAGE_AXIS));
		player1Panel.setBounds(900, 50, 450, 250);
		player1Panel.setBorder(BorderFactory.createEmptyBorder( 7, 7, 7, 7));
		player1Panel.setBackground(Color.white);
		
		player2Panel = new JPanel();
		player2Panel.setLayout(new BoxLayout(player2Panel, BoxLayout.PAGE_AXIS));
		player2Panel.setBounds(900, 515, 450, 250);
		player2Panel.setBorder(BorderFactory.createEmptyBorder( 7, 7, 7, 7));
		player2Panel.setBackground(turnColor);
		
		player1 = new JLabel("Black: "+curGame.getPlayer()[0].getName());
		player1.setAlignmentX(Component.CENTER_ALIGNMENT);
		player1.setFont(new Font("Serif", Font.BOLD, 24));
		
		player2 = new JLabel("White: "+curGame.getPlayer()[1].getName());
		player2.setAlignmentX(Component.CENTER_ALIGNMENT);
		player2.setFont(new Font("Serif", Font.BOLD, 24));
		
		terminatedFig1 = new JPanel();
		terminatedFig1.setLayout(new GridLayout(2, 8, 0, 0));
	
		terminatedFig2 = new JPanel();
		terminatedFig2.setLayout(new GridLayout(2, 8, 0, 0));

		Func = new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(curGame.getWhiteTurn())
					player = 1;
				else
					player = 0;
				
				updateVisual();
				
				Boolean pIsWhite = curGame.getPlayer()[player].getIsWhite();
				Field f = ((Field)arg0.getSource());
				
				//die Figurenauswahl - kein leeres Feld UND die Figur muss deiner Farbe entsprechen
				if(f.getBelegung() != Field.emptyField && curGame.brett.getFigures()[f.getBelegung()].getIW() == pIsWhite){
					
					curGame.brett.selectFigur(f.getKoordinate());
					List<Point> liste = curGame.brett.checkFilteredMovePossibilities(pIsWhite);
					
					hightlightPos(liste, f.getKoordinate());
				}
				//die Positionsauswahl - das ausgewaehlte Feld muss einer Bewegungsmoeglichkeit der selektierten Figur entsprechen
				else if(pIsWhite == curGame.brett.getFigures()[curGame.brett.getSelFig()].getIW() && curGame.brett.checkFilteredMovePossibilities(pIsWhite).contains(f.getKoordinate())){
					
					curGame.brett.move(f.getKoordinate());
					    
					terminatedFig2.removeAll();
					terminatedFig1.removeAll();
					
					for(int i = 0; i < curGame.brett.getFigures().length; i++){
						if(curGame.brett.searchFigCoordByIndex(i) == Schachbrett.NO_FIG_COORD){

							if(i<16){
								terminatedFig1.add(curGame.brett.getFigures()[i].getImage());
								terminatedFig1.revalidate();
							}
							else{
								terminatedFig2.add(curGame.brett.getFigures()[i].getImage());
								terminatedFig2.revalidate();
							}
							
						}
					}
					
					updateVisual();
					checkForEnd();
				
					curGame.setWhiteTurn(!curGame.getWhiteTurn());
					
					if(curGame.getWhiteTurn()){
						player2Panel.setBackground(turnColor);
						player1Panel.setBackground(Color.white);
					}
					else{
						player1Panel.setBackground(turnColor);
						player2Panel.setBackground(Color.white);
					}
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

		player1Panel.add(player1);
		player1Panel.add(terminatedFig1);
		player2Panel.add(player2);
		player2Panel.add(terminatedFig2);
		
		mainFrame.setJMenuBar(menuBar);
		mainFrame.add(gamePanel);
		mainFrame.add(player1Panel);
		mainFrame.add(player2Panel);
		
		menuBar.add(menu1);
		menu1.add(mItem1);
		menu1.add(mItem2);
		
		mainFrame.setResizable(false);
		mainFrame.setLayout(null);
		mainFrame.setVisible(true);;
		
		setUpNewGame();
	}

	/**
	 * Initialisieren des Feldes am Anfang jedes Spieles
	 */
	public void setUpNewGame(){
		
		terminatedFig1.removeAll();
		terminatedFig2.removeAll();
		gamePanel.removeAll();
		
		for(int y = 0; y<8; y++) {
			for(int x = 0; x<8; x++) {
				Field f = curGame.brett.getFelder()[x][y];
				
				if(f.getIsWhite())
					f.setBackground(wField);
				else
					f.setBackground(bField);
				
				f.addMouseListener(Func);
				gamePanel.add(f);
			}
		}
		
		updateVisual();
	}
	
	/**
	 * das Feld des Kings, welcher im Schach steht, faerben
	 */
	public void highlightCheck(){
		
		int kingID = 0;
		
		if(curGame.brett.getWhiteChecK())
			kingID = 14;
		else if(curGame.brett.getBlackChecK())
			kingID = 30;
		
		if(kingID != 0){
			Point king = curGame.brett.searchFigCoordByIndex(kingID);
			curGame.brett.getFelder()[(int)king.getX()][(int)king.getY()].setBackground(Color.MAGENTA);
		}
	}

	/**
	 * highlights auf Feldern erzeugen auf die man sich bewegen kann und die eigene
	 * @param list Felder auf die man sich bewegen kann
	 * @param figPos Position der Figur, welche auf besagte Felder kann
	 */
	public void hightlightPos(List<Point> list, Point figPos){
		
		for(Field[] f: curGame.brett.getFelder()){
			for(Field fx: f){
				if(list.contains(fx.getKoordinate()))
					fx.setBackground(hightlightColor);
				else if(figPos.equals(fx.getKoordinate()))
					fx.setBackground(Color.GRAY);
			}
		}
		
	}
	
	/**
	 * die Felder neu mit den Figurenbildern initialisieren, highlights entfernen
	 * und gegebenfalls ein Schach-Highlight hinzufuegen
	 */
	public void updateVisual(){
		
		for(Field[] f: curGame.brett.getFelder()){
			for(Field fx: f){
				fx.removeAll();
				if(fx.getBelegung() != Field.emptyField){
					fx.add(curGame.brett.getFigures()[fx.getBelegung()].getImage());
					curGame.brett.getFigures()[fx.getBelegung()].getImage().setText("");
				}
				if(fx.getIsWhite())
					fx.setBackground(wField);
				else
					fx.setBackground(bField);	
			}
		}		
		
		highlightCheck();
		gamePanel.repaint();
		gamePanel.validate();
	}
	
	/**
	 * Ob das Brett auf Eingaben reagieren soll oder nicht
	 * @param respond Reaktionsfaehig oder nicht
	 */
	public void makeResponsive(Boolean respond){
		
		for(Field[] f: curGame.brett.getFelder()){
			for(Field fx: f){

				if(respond){
					fx.removeMouseListener(noFunc);
					fx.addMouseListener(Func);
				}
				else{
					fx.removeMouseListener(Func);
					fx.addMouseListener(noFunc);
				}
			}
		}
	}
	
	/**
	 * Situationen, welche nach der Bewegung einer Figur auftreten koennen,
	 * werden durchgegangen
	 */
	public void checkForEnd(){
		
		if(curGame.brett.SchachMatt(!curGame.getWhiteTurn())){
			JOptionPane.showMessageDialog(null, "Checkmate!");
			curGame.Win(curGame.getWhiteTurn());
			makeResponsive(false);
		}
		else if(curGame.brett.Patt(!curGame.getWhiteTurn())){
			JOptionPane.showMessageDialog(null, "Patt!");
			curGame.Remis();
			makeResponsive(false);
		}     
		else if(curGame.brett.king1v1()){
			JOptionPane.showMessageDialog(null, "King vs King Situation!");
			curGame.Remis();
			makeResponsive(false);
		}
		
	}
	
	}
	
	
