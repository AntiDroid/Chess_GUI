package main_package;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
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

import javafx.geometry.Point2D;

class GUI {

	Game g1;
	int player;
	String name1, name2;
	
	JFrame mainFrame;
	JMenuBar menuBar;
	JMenu menu1;
	JMenuItem mItem1, mItem2;
	JPanel gamePanel, player1Panel, player2Panel;
	JPanel terminatedFig1, terminatedFig2;
	JLabel player1, player2;
	JLabel time1Cur, time2Cur;
	
	Thread t1, t2;
	
	Color bField = new Color(51, 102, 0);
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

	/**
	 * Spielverwaltung mit Benutzerinteraktionen
	 */
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
		
		g1 = new Game(new Player(name1, false), new Player(name2, true));
		
		mainFrame = new JFrame("Chess");
		mainFrame.setBounds(400, 100, 1400, 900);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		menuBar = new JMenuBar();
		menu1 = new JMenu("Game");
		
		mItem1 = new JMenuItem("New");
		mItem1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				g1 = new Game(new Player(name1, false), new Player(name2, true));	
				
				gamePanel.removeAll();
				setUpGame();
				updateVisual();
			}
			
		});
			
		mItem2 = new JMenuItem("Give Up");
		mItem2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				g1.Win(g1.getWhiteTurn());
				makeNonResponsive();
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
		
		player1 = new JLabel("Player 1: "+g1.getPlayer()[0].getName());
		player1.setAlignmentX(Component.CENTER_ALIGNMENT);
		player1.setFont(new Font("Serif", Font.BOLD, 24));
		
		player2 = new JLabel("Player 2: "+g1.getPlayer()[1].getName());
		player2.setAlignmentX(Component.CENTER_ALIGNMENT);
		player2.setFont(new Font("Serif", Font.BOLD, 24));
		
		terminatedFig1 = new JPanel();
		terminatedFig1.setLayout(new GridLayout(2, 8, 0, 0));
	
		terminatedFig2 = new JPanel();
		terminatedFig2.setLayout(new GridLayout(2, 8, 0, 0));
		
		time1Cur = new JLabel("Time 1");
		time1Cur.setAlignmentX(Component.CENTER_ALIGNMENT);
		time1Cur.setFont(new Font("Serif", Font.BOLD, 20));
		
		time2Cur = new JLabel("Time 2");
		time2Cur.setAlignmentX(Component.CENTER_ALIGNMENT);
		time2Cur.setFont(new Font("Serif", Font.BOLD, 20));

		Func = new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(g1.getWhiteTurn())
					player = 1;
				else
					player = 0;
				
				clearHighlights();
				
				Boolean pIsWhite = g1.getPlayer()[player].getIsWhite();
				Field f = ((Field)arg0.getSource());
				
				//die Figurenauswahl - kein leeres Feld UND die Figur muss deiner Farbe entsprechen
				if(f.getBelegung() != Field.emptyField && g1.brett.getFigures()[f.getBelegung()].getIW() == pIsWhite){
					
					g1.brett.selectFigur(f.getKoordinate());
					List<Point2D> liste = g1.brett.checkFilteredMovePossibilities(pIsWhite);
					
					hightlightPos(liste);
				}
				//die Positionsauswahl - das ausgewaehlte Feld muss einer Bewegungsmoeglichkeit der selektierten Figur entsprechen
				else if(pIsWhite == g1.brett.getFigures()[g1.brett.getSelFig()].getIW() && g1.brett.checkFilteredMovePossibilities(pIsWhite).contains(f.getKoordinate())){
					
					g1.brett.move(f.getKoordinate());
					
					JLabel img;
					
					terminatedFig2.removeAll();
					terminatedFig1.removeAll();
					
					for(int i = 0; i < g1.brett.getFigures().length; i++){
						if(g1.brett.searchFigCoordByIndex(i) == Schachbrett.nonSelectable){
							img = g1.brett.getFigures()[i].getImage();

							if(i<16){
								terminatedFig1.add(img);
								terminatedFig1.revalidate();
							}
							else{
								terminatedFig2.add(img);
								terminatedFig2.revalidate();
							}
							
							img.setText("");
						}
					}
					
					highlightCheck();
					updateVisual();
					checkForEnd();
					g1.setWhiteTurn(!g1.getWhiteTurn());
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
	
		setUpGame();
		
		t2 = new Thread(new Runnable(){

			@Override
			public void run() {
				Date d = new Date();
				
				while(g1.getWhiteTurn()){
					time2Cur.setText(String.valueOf((new Date().getTime()-d.getTime())/1000)+"s");
				}
				
				System.out.println("ENDE FUER WEISS");
			}
			
		});
		
		t1 = new Thread(new Runnable(){

			@Override
			public void run() {
				Date d = new Date();
				
				while(!g1.getWhiteTurn()){
					time1Cur.setText(String.valueOf((new Date().getTime()-d.getTime())/1000)+"s");
				}
				
				System.out.println("ENDE FUER SCHWARZ");
			}
			
		});
		//t2.start();
		
		player1Panel.add(player1);
		player1Panel.add(terminatedFig1);
		player1Panel.add(time1Cur);
		player2Panel.add(player2);
		player2Panel.add(terminatedFig2);
		player2Panel.add(time2Cur);
		
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

	/**
	 * highlights auf Feldern erzeugen auf die man sich bewegen kann
	 * @param list Felder auf die man sich bewegen kann
	 */
	public void hightlightPos(List<Point2D> list){
		
		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				if(list.contains(g1.brett.getFelder()[i][j].getKoordinate()))
					g1.brett.getFelder()[i][j].setBackground(hightlightColor);
			}
		}
	}
	
	/**
	 * Initialisieren des Feldes am Anfang jedes Spieles
	 */
	public void setUpGame(){
		
		terminatedFig1.removeAll();
		terminatedFig2.removeAll();
		
		for(int y = 0; y<8; y++) {
			for(int x = 0; x<8; x++) {
				Field f = g1.brett.getFelder()[x][y];
				
				if(f.getIsWhite())
					f.setBackground(wField);
				else
					f.setBackground(bField);
				
				f.addMouseListener(Func);
				gamePanel.add(f);
			}
		}
		
	}
	
	/**
	 * die highlights entfernen und das Spielfeld neu "bemalen"
	 */
	public void clearHighlights(){

		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				if(g1.brett.getFelder()[i][j].getIsWhite())
					g1.brett.getFelder()[i][j].setBackground(wField);
				else
					g1.brett.getFelder()[i][j].setBackground(bField);				
			}
		}
	}
	
	/**
	 * die Felder neu mit den Figurenbildern initialisieren
	 */
	public void updateVisual(){
		
		for(Field[] f: g1.brett.getFelder()){
			for(Field fx: f){
				fx.removeAll();
				if(fx.getBelegung() != Field.emptyField){
					fx.add(g1.brett.getFigures()[fx.getBelegung()].getImage());
					g1.brett.getFigures()[fx.getBelegung()].getImage().setText("");
				}
			}
		}		
		
		if(g1.getWhiteTurn()){
			player2Panel.setBackground(turnColor);
			player1Panel.setBackground(Color.white);
		}
		else{
			player1Panel.setBackground(turnColor);
			player2Panel.setBackground(Color.white);
		}
		
		gamePanel.repaint();
	}
	
	/**
	 * wenn das Feld nicht mehr auf Eingaben reagiert,
	 * wird es hiermit wiederhergestellt
	 */
	public void makeResponsive(){
		for(Field[] f: g1.brett.getFelder()){
			for(Field fx: f){
				if(fx.getBelegung() != Field.emptyField)
					fx.addMouseListener(Func);
			}
		}
	}
	
	/**
	 * dem Spielfeld die Klick-Funktionen nehmen
	 */
	public void makeNonResponsive(){
		for(Field[] f: g1.brett.getFelder()){
			for(Field fx: f){
				fx.addMouseListener(noFunc);
			}
		}
	}
	
	/**
	 * Situationen, welche nach der Bewegung einer Figur auftreten koennen,
	 * werden durchgegangen
	 */
	public void checkForEnd(){
		
		if(g1.brett.SchachMatt(!g1.getWhiteTurn())){
			JOptionPane.showMessageDialog(null, "Checkmate!");
			g1.Win(g1.getWhiteTurn());
			makeNonResponsive();
		}
		else if(g1.brett.Patt(!g1.getWhiteTurn())){
			JOptionPane.showMessageDialog(null, "Patt!");
			g1.Remis();
			makeNonResponsive();
		}     
		else if(g1.brett.Patt(g1.getWhiteTurn())){
			JOptionPane.showMessageDialog(null, "Patt!");
			g1.Remis();
			makeNonResponsive();
		}
		else if(g1.brett.king1v1()){
			JOptionPane.showMessageDialog(null, "King vs King Situation!");
			g1.Remis();
			makeNonResponsive();
		}
		
	}
	
	/**
	 * das Feld des Kings, welcher im Schach steht, rot machen
	 */
	public void highlightCheck(){
		
		int kingID = 0;
		
		if(g1.brett.getWhiteChecK())
			kingID = 14;
		else if(g1.brett.getBlackChecK())
			kingID = 30;
		
		if(kingID != 0){
			Point2D king = g1.brett.searchFigCoordByIndex(kingID);
			g1.brett.getFelder()[(int)king.getX()][(int)king.getY()].setBackground(Color.MAGENTA);
		}
	}
	
	}
	
	
