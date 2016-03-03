package main_package;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import figures.Bishop;
import figures.Figure;
import figures.King;
import figures.Knight;
import figures.Pawn;
import figures.Queen;
import figures.Rook;
import utilities.Point;

public class Schachbrett {
	
	private Field[][] felder;
	private Figure[] Fig;
	
	private int selectedFig;
	private Point posSelectedFig;
	
	private boolean whiteCheck;
	private boolean blackCheck;
	
	public static final Point NO_FIG_COORD = new Point(100000, 100000);
	
	/**
	 * Bei der Initialisierung eines Schachbretts, wird das 8x8 Feld initialisiert 
	 * und die global genutzten Eigenschaften der selected Figur auf Standardwerte gesetzt.
	 * Die Felder bekommen hier auch ihre IDs und Farben (schwarz/weiss)
	 */
	
	public Schachbrett(){
		
		this.felder = new Field[8][8];
		selectedFig = 0;
		posSelectedFig = new Point(10,10);
		
		boolean white = true;;
		//Koordinatensystem - von Oben/Links nach Unten/Rechts
		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				this.felder[i][j] = new Field(new Point(i, j), white);
				white = !white;
			}
			white = !white;
		}
		
		whiteCheck = false;
		blackCheck = false;
		
		buildFigures();
	}
	
	public Figure[] getFigures(){
		return Fig;
	}
	
	public int getSelFig(){
		return selectedFig;
	}
	
	public boolean getWhiteChecK(){
		return whiteCheck;
	}

	public boolean getBlackChecK(){
		return blackCheck;
	}
	
	public Field[][] getFelder(){
		
		return this.felder;
	}

	/**
	 * Die Figuren beider Teams werden erstellt,
	 * dem jeweiligen Team zugewiesen und mit Startpositionen versehen. 
	 * Anschließend werden die Positionen den Feldern per FigurenID zugewiesen.
	 */
	public void buildFigures(){
			
			Fig = new Figure[32];
			
			//Figure name LEFT/RIGHT BLACK/WHITE
			Figure rookLW = new Rook(true, new Point(0, 7));
			Figure rookRW = new Rook(true, new Point(7, 7));
			Fig[0] = rookLW;
			Fig[1] = rookRW;
			Figure rookLB = new Rook(false, new Point(0, 0));
			Figure rookRB = new Rook(false, new Point(7, 0));
			Fig[16] = rookLB;
			Fig[17] = rookRB;
			
			Figure knightLW = new Knight(true, new Point(1, 7));
			Figure knightRW = new Knight(true, new Point(6, 7));
			Fig[2] = knightLW;
			Fig[3] = knightRW;
			Figure knightLB = new Knight(false, new Point(1, 0));
			Figure knightRB = new Knight(false, new Point(6, 0));
			Fig[18] = knightLB;
			Fig[19] = knightRB;
			
			Figure bishopLW = new Bishop(true, new Point(2, 7));
			Figure bishopRW = new Bishop(true, new Point(5, 7));
			Fig[4] = bishopLW;
			Fig[5] = bishopRW;
			Figure bishopLB = new Bishop(false, new Point(2, 0));
			Figure bishopRB = new Bishop(false, new Point(5, 0));
			Fig[20] = bishopLB;
			Fig[21] = bishopRB;
					
					
			//left to right
			//BLACK
			Fig[6] = new Pawn(true, new Point(0, 6));
			Fig[7] = new Pawn(true, new Point(1, 6));
			Fig[8] = new Pawn(true, new Point(2, 6));
			Fig[9] = new Pawn(true, new Point(3, 6));
			Fig[10] = new Pawn(true, new Point(4, 6));
			Fig[11] = new Pawn(true, new Point(5, 6));
			Fig[12] = new Pawn(true, new Point(6, 6));
			Fig[13] = new Pawn(true, new Point(7, 6));
			
			//BLACK
			Fig[22] = new Pawn(false, new Point(0, 1));
			Fig[23] = new Pawn(false, new Point(1, 1));
			Fig[24] = new Pawn(false, new Point(2, 1));
			Fig[25] = new Pawn(false, new Point(3, 1));
			Fig[26] = new Pawn(false, new Point(4, 1));
			Fig[27] = new Pawn(false, new Point(5, 1));
			Fig[28] = new Pawn(false, new Point(6, 1));
			Fig[29] = new Pawn(false, new Point(7, 1));
			
			
			Figure kingW = new King(true, new Point(4, 7));
			Figure kingB = new King(false, new Point(4, 0));
			Fig[14] = kingW;
			Fig[30] = kingB;
			
			Figure queenW = new Queen(true, new Point(3, 7));
			Figure queenB = new Queen(false, new Point(3, 0));
			Fig[15] = queenW;
			Fig[31] = queenB;
			
			for(int x = 0; x < Fig.length; x++){
				for(Field[] f: getFelder()){
					for(Field fx: f){
						if(Fig[x].getSP().equals(fx.getKoordinate())){
							fx.addFigur(x);
							fx.add(Fig[x].getImage());
						}
					}
				}
			}	
		}

	/**
	 * eine bestimmte Figur wird als selektierte Figur gespeichert
	 * @param coord das Selektieren einer Figur wird anhand der Koordinate auf 
	 * der sie steht abgewickelt
	 * @return ob der Vorgang erfolgreich war
	 */
	public boolean selectFigur(Point coord){
		if(searchFigIndexByCoord(coord) != Field.emptyField){
			selectedFig = searchFigIndexByCoord(coord);
			posSelectedFig = coord;
			return true;
		}
		//falls keine Figur ausgewaehlt werden konnte
		return false;
	}
	
	/**
	 * Anhand der FigurenID wird die Koordinate bestimmt
	 * @param index Figurenindex
	 * @return Koordinate der uebergebenen Figur
	 */
	public Point searchFigCoordByIndex(int index){
		
		for(Field[] fx: felder){
			for(Field f: fx){
				if(f.getBelegung() == index)
					return f.getKoordinate();
			}
		}
		return NO_FIG_COORD;
	}
	
	/**
	 * Anhand der Koordinate wird die FigurenID bestimmt
	 * @param coord	Koordinate der gesuchten Figur
	 * @return Index der gesuchten Figur
	 */
	public int searchFigIndexByCoord(Point coord){
		return felder[(int)coord.getX()][(int)coord.getY()].getBelegung();
	}

	/**
	 * die eingegebene Koordinate wird in die vom Programm verstaendliche umgewandelt
	 * @param koord eingegebene Koordinate nach dem angezeigten System
	 * @return die bearbeitbare Version der Koordinate
	 */
	public Point displayCoordToReal(String koord){
		int x, y;
	
		//siehe ASCI Tabelle
		x = ((int)koord.charAt(0)) - 65; 
		y = 7-(((int)koord.charAt(1)) - 49);
		
		return new Point(x, y);
	}
	
	/**
	 * die vom Programm lesbare Koordinate wird in die vom Nutzer verstaendliche umgewandelt
	 * @param p vom Programm lesbare Koordinate
	 * @return die bearbeitbare Version der Koordinate
	 */
	public String realToDisplayCoord(Point p){
		String str = "";
		
		//siehe ASCI Tabelle
		str += ((char)(p.getX()+65));
		str += ((char)(56 - p.getY()));
		 
		return str;
	}
	
	/**
	 * Der Bauerntausch mit einem Eingabedialog fuer die gewuenschte Figur.
	 * @param iW die Teamfarbe des Bauern
	 */
	public void promotePawn(boolean iW){
		
	
		boolean retry = true;;
		String[] choices = {"Knight", "Rook", "Bishop", "Queen"};
		String input = "lol";
		
		do{
	    input = (String) JOptionPane.showInputDialog(null, "Choose a figure!", "Promote your pawn!", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
	    
	    if(input == null)
	    	continue;
	    
	    retry = false;
		switch(input){
		
		case "Knight":
			Fig[selectedFig] = new Knight(iW, Fig[selectedFig].getSP());
			break;
		case "Rook":
			Fig[selectedFig] = new Rook(iW, Fig[selectedFig].getSP());
			break;
			
		case "Bishop":
			Fig[selectedFig] = new Bishop(iW, Fig[selectedFig].getSP());
			break;
			
		case "Queen":
			Fig[selectedFig] = new Queen(iW, Fig[selectedFig].getSP());
			break;
		}
		}while(retry);
	}

	/**
	 * Die Methode leert das aktuelle Feld und setzt die Figur auf das Zielfeld.
	 * @param pos auf welche Position gewechselt werden soll
	 */
	public void changePos(Point pos){
		
		felder[(int)posSelectedFig.getX()][(int)posSelectedFig.getY()].clear();
		felder[(int)pos.getX()][(int)pos.getY()].addFigur(selectedFig);
		felder[(int)pos.getX()][(int)pos.getY()].add(Fig[selectedFig].getImage());
		
		selectFigur(pos);
	}
	
	/**
	 * Methode, welche bei dem Schlagen einer Figur aufgerufen wird.
	 * Meldungen werden ausgegeben und das angegriffene Feld wird geleert.
	 * @param p Feld des zu Schlagenden
	 */
	public void hit(Point p){
		felder[(int)p.getX()][(int)p.getY()].clear();
	}

	/**
	 * In dieser Methode begibt sich die Figur auf das jeweilige Feld je nachdem,
	 * um welche es sich handelt.
	 * @param p Punkt auf den die selektierte Figur wechseln soll
	 */
	public void move(Point p){
		
		int xNow, gegner, xGo, yGo;
	
		xGo = (int)p.getX();
		yGo = (int)p.getY();
		gegner = searchFigIndexByCoord(p);
		xNow = (int)posSelectedFig.getX();
		int moveVarX = 0;
		
		int pawnMin, pawnMax;
		
		//Vorkehrungen damit En Passant nur unmittelbar nach Zug gemacht wird
		if(Fig[selectedFig].getIW()){
			pawnMin = 6;
			pawnMax = 14;
		}
		else{
			pawnMin = 22;
			pawnMax = 30;
		}
		
		for(int i = pawnMin; i < pawnMax; i++){
			if(Fig[i] instanceof Pawn)
				((Pawn)Fig[i]).setEnPassant(false);
		}
		
		
		//Rochade
		if((Fig[selectedFig] instanceof King)){
			((King)Fig[selectedFig]).setRochade(false);
			
			if(Math.abs(xNow-xGo) > 1){
				int rochRook;
			
				if(Fig[selectedFig].getIW()){
					//rechts
					if((xNow-xGo) == -2){
						moveVarX = -2;
						rochRook = 1;
					}
					else{
						moveVarX = 3;
						rochRook = 0;
					}
				}
				else{
					//rechts
					if((xNow-xGo) == -2){
						moveVarX = -2;
						rochRook = 17;
					}
					else{
						moveVarX = 3;
						rochRook = 16;
					}
				}
	
				Point origSel = posSelectedFig;
				//die Position des Turms aendern
				selectFigur(searchFigCoordByIndex(rochRook));
				changePos(new Point(posSelectedFig.getX()+moveVarX, posSelectedFig.getY()));
				selectFigur(origSel);
			}
		}
		else if(Fig[selectedFig] instanceof Rook){
			((Rook)Fig[selectedFig]).setRochade(false);
		}
		else if(Fig[selectedFig] instanceof Pawn){
			
			//Doppelzug
			if(Math.abs(yGo-posSelectedFig.getY()) == 2){
				((Pawn)Fig[selectedFig]).setEnPassant(true);
			}
			else if(xGo != xNow){
				if((yGo-1 != -1 && yGo+1 != 8) && (felder[xGo][yGo].getBelegung() == Field.emptyField)){
					if(felder[xGo][yGo-1].getBelegung() != Field.emptyField)
						gegner = felder[xGo][yGo-1].getBelegung();
					else if(felder[xGo][yGo+1].getBelegung() != Field.emptyField)
						gegner = felder[xGo][yGo+1].getBelegung();
				}
			}
			((Pawn)Fig[selectedFig]).setDoublemove(false);
			
		}
	
		//Wenn auf dem Endfeld einer drauf ist, weghaun!
		if(gegner != Field.emptyField){
			hit(searchFigCoordByIndex(gegner));
			
			//falls der Turm auf der Startposition beseitigt wird
			//Rochade muss entfernt werden
			if(Fig[gegner] instanceof Rook){
				((Rook)Fig[gegner]).setRochade(false);
			}
		}
		
		//Bauernumwandlung
		if(Fig[selectedFig] instanceof Pawn){
			if((Fig[selectedFig].getIW() && p.getY() == 0) || (!Fig[selectedFig].getIW() && p.getY() == 7)){
				promotePawn(Fig[selectedFig].getIW());
			}
		}
		
		changePos(p);
		
		boolean checkedOne = !Fig[selectedFig].getIW();
		
		//check if the enemy is in check
		if(checkedOne)
			whiteCheck = schach(checkedOne);
		else if(!checkedOne)
			blackCheck = schach(checkedOne);
		
		//check if you escaped from a check
		if(!checkedOne)
			whiteCheck = schach(!checkedOne);
		else if(checkedOne)
			blackCheck = schach(!checkedOne);
		
	}

	/**
	 * Eine Methode, welche erstellt wurde, um eine Masse von Code in der movePossibilitie zu sparen.
	 * @param posX die aktuelle X-Position
	 * @param posY die aktuelle Y-Position
	 * @param moveX die schrittweise Aenderung von X
	 * @param moveY die schrittweise Aenderung von Y
	 * @param xLimit ein X-Wert welcher NICHT erreicht werden soll
	 * @param yLimit ein Y-Wert welcher NICHT erreicht werden soll
	 * @return eine Liste mit Teilbewegungsmoeglichkeiten
	 */
	public List<Point> moveCrap(int posX, int posY, int moveX, int moveY, int xLimit, int yLimit){
		
		List<Point> p = new ArrayList<Point>();
		
		posX += moveX;
		posY += moveY;	
		
		while( (posX != xLimit && posY != yLimit) && (posX >= 0 && posX < 8) && (posY >= 0 && posY < 8)){
			if(this.felder[posX][posY].getBelegung() != Field.emptyField){
				if(Fig[selectedFig].getIW() != Fig[felder[posX][posY].getBelegung()].getIW()){
					p.add(new Point(posX, posY));
				}
				break;
			}
			
			p.add(new Point(posX, posY));
			posX += moveX;
			posY += moveY;
		}
		return p;
	}
	
	/**
	 * Eine Methode, welche die moeglichen Bewegungsendpunkte einer selektierten Figur berechnet.
	 * @return die Liste mit den Endpunkten
	 */
	public ArrayList<Point> movePossibilities(){
		
		ArrayList <Point> moveP = new ArrayList<Point>();
		
		int x = (int)posSelectedFig.getX();
		int y = (int)posSelectedFig.getY();
		
		if(this.Fig[selectedFig] instanceof Rook || this.Fig[selectedFig] instanceof Queen){
			
			//rechts
			moveP.addAll(moveCrap(x, y,  1,  0, 10, 10));
			//links
			moveP.addAll(moveCrap(x, y, -1,  0, 10, 10));
			//oben
			moveP.addAll(moveCrap(x, y,  0, -1, 10, 10));
			//unten
			moveP.addAll(moveCrap(x, y,  0,  1, 10, 10));
			
		}
		if(this.Fig[selectedFig] instanceof Bishop || this.Fig[selectedFig] instanceof Queen){
			
			//oben links
			moveP.addAll(moveCrap(x, y, -1, -1, 10, 10));
			//oben rechts
			moveP.addAll(moveCrap(x, y,  1, -1,  10, 10));
			//unten links
			moveP.addAll(moveCrap(x, y, -1,  1, 10,  10));
			//unten rechts
			moveP.addAll(moveCrap(x, y,  1,  1,  10,  10));
			
		}
		else if(this.Fig[selectedFig] instanceof Knight){
			
			//oben
				//oben-links
				moveP.addAll(moveCrap(x, y-2, -1, 0, x-2, 10));
				//oben-rechts
				moveP.addAll(moveCrap(x, y-2,  1, 0,  x+2, 10));
			
			//unten
				//unten-links
				moveP.addAll(moveCrap(x, y+2, -1, 0, x-2, 10));
				//unten-rechts
				moveP.addAll(moveCrap(x, y+2,  1, 0, x+2, 10));
			
			//links
				//links-oben
				moveP.addAll(moveCrap(x-2, y, 0, -1, 10, y-2));
				//links-unten
				moveP.addAll(moveCrap(x-2, y, 0,  1, 10,  y+2));

			//rechts
				//rechts-oben
				moveP.addAll(moveCrap(x+2, y, 0, -1, 10, y-2));
				
				//rechts-unten
				moveP.addAll(moveCrap(x+2, y, 0,  1, 10,  y+2));

		}
		else if(this.Fig[selectedFig] instanceof King){
			
			int rochRookL, rochRookR;
			boolean check;
			
			if(Fig[selectedFig].getIW()){
				rochRookL = 0;
				rochRookR = 1;
				check = whiteCheck;
			}
			else{
				rochRookL = 16;
				rochRookR = 17;
				check = blackCheck;
			}
			
			//rechts
			moveP.addAll(moveCrap(x, y, 1, 0, x+2, 10));
			//links
			moveP.addAll(moveCrap(x, y, -1, 0, x-2, 10));
			
			//oben
			moveP.addAll(moveCrap(x, y, 0, -1, 10, y-2));
			//unten
			moveP.addAll(moveCrap(x, y, 0, 1, 10, y+2));
			
			//oben-links
			moveP.addAll(moveCrap(x, y, -1, -1, x-2, y-2));	
			//oben-rechts
			moveP.addAll(moveCrap(x, y, 1, -1, x+2, y-2));	
			
			//unten-links
			moveP.addAll(moveCrap(x, y, -1, 1, x-2, y+2));
			//unten-rechts
			moveP.addAll(moveCrap(x, y, 1, 1, x+2, y+2));
			
			
			if(!check){
				//Rochade		
				//rechts
				if( ((King)Fig[selectedFig]).getRochade() && ((Rook)Fig[rochRookR]).getRochade() ){
					if((this.felder[(x+2)][y].getBelegung() == Field.emptyField) && this.felder[(x+1)][y].getBelegung() == Field.emptyField){
						moveP.add(new Point((x+2), y));
					}
				}
				//links
				if( ((King)Fig[selectedFig]).getRochade() && ((Rook)Fig[rochRookL]).getRochade() ){
					if((this.felder[(x-3)][y].getBelegung() == Field.emptyField) && (this.felder[(x-2)][y].getBelegung() == Field.emptyField) && (this.felder[(x-1)][y].getBelegung() == Field.emptyField)){
						moveP.add(new Point((x-2), y));
					}
				}
			}
		}
		else if(this.Fig[selectedFig] instanceof Pawn && (y-1 != -1) && (y+1 != 8)){
			
			int varY;
			
			if(Fig[selectedFig].getIW())
				varY = -1;
			else
				varY = 1;
			
			//normal vor
			if(this.felder[x][y+varY].getBelegung() == Field.emptyField){
				moveP.add(new Point(x, y+varY));
				//Doppelzug
				if((((Pawn)Fig[selectedFig]).getDoublemove()) && (this.felder[x][y+(2*varY)].getBelegung() == Field.emptyField)){
					moveP.add(new Point(x, y+(2*varY)));
				}
				
			}
			
			//vorne links/rechts ein schwarzer/weißer Gegner
			if(x-1 != -1){
				int possibleEnemy = this.felder[x-1][y+varY].getBelegung();
				if(possibleEnemy != Field.emptyField){
					
					if(Fig[possibleEnemy].getIW() != Fig[selectedFig].getIW())
						moveP.addAll(moveCrap(x, y+varY, -1, 0, x-2, 10));
				}
				
				//En Passant
				possibleEnemy = this.felder[x-1][y].getBelegung();
				if(possibleEnemy != Field.emptyField){
					if(Fig[possibleEnemy].getIW() != Fig[selectedFig].getIW() && Fig[possibleEnemy] instanceof Pawn)
						if(((Pawn)Fig[possibleEnemy]).getEnPassant())
							moveP.addAll(moveCrap(x, y+varY, -1, 0, x-2, 10));	
				}
			}
			
			//vorne rechts/links ein schwarzer/weißer Gegner
			if(x+1 != 8){
				int possibleEnemy = this.felder[x+1][y+varY].getBelegung();
				if(possibleEnemy != Field.emptyField){
					
					if(Fig[possibleEnemy].getIW() != Fig[selectedFig].getIW())
						moveP.addAll(moveCrap(x, y, 1, varY, x+2, y+2));
				}
				
				//En Passant
				possibleEnemy = this.felder[x+1][y].getBelegung();
				if(possibleEnemy != Field.emptyField){
					if(Fig[possibleEnemy].getIW() != Fig[selectedFig].getIW() && Fig[possibleEnemy] instanceof Pawn)
						if(((Pawn)Fig[possibleEnemy]).getEnPassant())
							moveP.addAll(moveCrap(x, y, 1, varY, x+2, y+2));		
				}	
			}
		}
		return moveP;
	}

	/**
	 * eine uebergebene Liste wird gefiltert, damit keine schacherzeugenden Bewegungen
	 * mehr vorhanden sind
	 * @param iW die Teamfarbe der Figur
	 * @return die gefilterte 
	 */
	public List<Point> checkFilteredMovePossibilities(boolean iW){
		
		Point curRealPos = posSelectedFig, curTestCoord;
		int origBelegung;
		boolean remove;
		
		List<Point> moves = movePossibilities();
		
		//es wird von oben runtergezaehlt, um beim Entfernen keine Indexprobleme zu erzeugen
		for(int i = moves.size()-1; i >= 0; i--){
			
			curTestCoord = moves.get(i);
			
			origBelegung = searchFigIndexByCoord(curTestCoord);
			felder[(int)curTestCoord.getX()][(int)curTestCoord.getY()].clear();
			
			changePos(curTestCoord);
			remove = this.schach(iW);
			changePos(curRealPos);
			
			felder[(int)curTestCoord.getX()][(int)curTestCoord.getY()].addFigur(origBelegung);
			
			if(remove)
				moves.remove(i);
		}
	
		return moves;	
	}

	/**
	 * Die jeweils gegnerischen Figuren werden ermittelt.
	 * Diese werden hinsichtlich ihrer Bewegungsmoeglichkeiten analysiert,
	 * ob sie die Position des jeweils gegnerischen Kings beinhalten.
	 * @param isW welches Team ist im Schach
	 * @return ob das jeweilige Team im Schach ist
	 */
	public boolean schach(boolean isW){
		
		//Standard für Schwarz
		int gegnerStart = 0, myKingIndex = 30;
		
		if(isW){
			gegnerStart += 16;
			myKingIndex -= 16;
		}
		
		Point realCoord = posSelectedFig, curTestCoord;
		Point kingCoord = searchFigCoordByIndex(myKingIndex);
		
		for(int i = gegnerStart; i <= (gegnerStart+15); i++){
			curTestCoord = searchFigCoordByIndex(i);
			
			if(curTestCoord.equals(NO_FIG_COORD))
				continue;
			selectFigur(curTestCoord);
			
			if(movePossibilities().contains(kingCoord)){
				selectFigur(realCoord);
				return true;
			}
		}
		
		selectFigur(realCoord);
		return false;
	}

	/**
	 * Diese Methode dient dazu den seltenen, aber dennoch
	 * automatisiert bearbeitbaren, Fall eines King vs King
	 * zu ermitteln und beim Aufruf der Methode ein Remis auszuloesen
	 * @return ob ein King vs King vorliegt
	 */
	public boolean king1v1(){
		
		int counter = 0;
		
		//Zaehle die Anzahl der noch aktiven Spielfiguren
		for(int i = 0; i < Fig.length; i++){
			if(!searchFigCoordByIndex(i).equals(NO_FIG_COORD))
				counter++;
		}
		//es koennen nur noch zwei Figuren uebrig bleiben, wenn diese Koenige sind
		if(counter == 2)
			return true;
		
		return false;
	}
	
	
	/**
	 * Wenn das ausgewaehlte Team sich mit keiner Figur bewegen kann.
	 * Dazu wird fuer jedes Teammitglied eine Analyse der Bewegungsmoeglichkeiten
	 * aufgestellt, welche im Pattfall alle nicht vorhanden sind.
	 * @param isW welches Team ist im Patt
	 * @return ob das jeweilige Team im Patt ist
	 */
	public boolean patt(boolean isW){
		
		//Standard für Schwarz
		int teamStart = 16;
		
		if(isW){
			teamStart -= 16;
		}
		
		Point origCoord = posSelectedFig, curTestCoord;
		
		for(int i = teamStart; i <= (teamStart+15); i++){
			curTestCoord = searchFigCoordByIndex(i);
			if(curTestCoord.equals(NO_FIG_COORD))
				continue;
				
			selectFigur(curTestCoord);
			
			if(checkFilteredMovePossibilities(isW).size() != 0){
				selectFigur(origCoord);
				return false;
			}
		}
		selectFigur(origCoord);
		return true;	
	}

	/**
	 * Wenn das ausgewaehlte Team im Schach steht und einem Patt unterliegt.
	 * @param isW welches Team im SchachMatt steht
	 * @return ob das jeweilige Team im SchachMatt steht
	 */
	public boolean schachMatt(boolean isW){
		
		boolean teamCheck;
		
		if(isW)
			teamCheck = whiteCheck;
		else
			teamCheck = blackCheck;
		
		if(teamCheck)
			if(patt(isW))
				return true;
		
		return false;
	}
	
	}

