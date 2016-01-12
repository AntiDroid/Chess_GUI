import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import figures.Bishop;
import figures.Figure;
import figures.King;
import figures.Knight;
import figures.Pawn;
import figures.Queen;
import figures.Rook;
import javafx.geometry.Point2D;

class Schachbrett {
	
	private Field[][] felder;
	private Figure[] Fig;
	
	private int selectedFig;
	private Point2D posSelectedFig;
	
	private Boolean whiteCheck;
	private Boolean blackCheck;
	
	static private final Point2D nonSelectable = new Point2D(100000, 100000);
	
	/**
	 * Bei der Initialisierung eines Schachbretts, wird das 8x8 Feld initialisiert 
	 * und die global genutzten Eigenschaften der selected Figur auf Standardwerte gesetzt.
	 * Die Felder bekommen hier auch ihre IDs und Farben (schwarz/weiss)
	 * @throws IOException Durch die Methode buildFigures
	 */
	
	public Schachbrett() throws IOException{
		
		this.felder = new Field[8][8];
		selectedFig = 0;
		posSelectedFig = new Point2D(10,10);
		
		Boolean white = true;;
		//Koordinatensystem - von Oben/Links nach Unten/Rechts
		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				this.felder[i][j] = new Field(new Point2D(i, j), white);
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
	
	public Boolean getWhiteChecK(){
		return whiteCheck;
	}

	public Boolean getBlackChecK(){
		return blackCheck;
	}
	
	public Field[][] getFelder(){
		
		return this.felder;
	}

	/**
	 * Die Figuren beider Teams werden erstellt,
	 * dem jeweiligen Team zugewiesen und mit Startpositionen versehen. 
	 * Anschließend werden die Positionen den Feldern per FigurenID zugewiesen.
	 * @throws IOException durch die Figurenbilder, welche im Figur-Konstruktor eingelesen werden
	 */
	public void buildFigures() throws IOException{
			
			Fig = new Figure[32];
			
			//Figur name LEFT/RIGHT BLACK/WHITE
			Figure rookLW = new Rook(true, new Point2D(0, 7));
			Figure rookRW = new Rook(true, new Point2D(7, 7));
			Fig[0] = rookLW;
			Fig[1] = rookRW;
			Figure rookLB = new Rook(false, new Point2D(0, 0));
			Figure rookRB = new Rook(false, new Point2D(7, 0));
			Fig[16] = rookLB;
			Fig[17] = rookRB;
			
			Figure knightLW = new Knight(true, new Point2D(1, 7));
			Figure knightRW = new Knight(true, new Point2D(6, 7));
			Fig[2] = knightLW;
			Fig[3] = knightRW;
			Figure knightLB = new Knight(false, new Point2D(1, 0));
			Figure knightRB = new Knight(false, new Point2D(6, 0));
			Fig[18] = knightLB;
			Fig[19] = knightRB;
			
			Figure bishopLW = new Bishop(true, new Point2D(2, 7));
			Figure bishopRW = new Bishop(true, new Point2D(5, 7));
			Fig[4] = bishopLW;
			Fig[5] = bishopRW;
			Figure bishopLB = new Bishop(false, new Point2D(2, 0));
			Figure bishopRB = new Bishop(false, new Point2D(5, 0));
			Fig[20] = bishopLB;
			Fig[21] = bishopRB;
					
					
			//left to right
			//BLACK
			Fig[6] = new Pawn(true, new Point2D(0, 6));
			Fig[7] = new Pawn(true, new Point2D(1, 6));
			Fig[8] = new Pawn(true, new Point2D(2, 6));
			Fig[9] = new Pawn(true, new Point2D(3, 6));
			Fig[10] = new Pawn(true, new Point2D(4, 6));
			Fig[11] = new Pawn(true, new Point2D(5, 6));
			Fig[12] = new Pawn(true, new Point2D(6, 6));
			Fig[13] = new Pawn(true, new Point2D(7, 6));
			
			//BLACK
			Fig[22] = new Pawn(false, new Point2D(0, 1));
			Fig[23] = new Pawn(false, new Point2D(1, 1));
			Fig[24] = new Pawn(false, new Point2D(2, 1));
			Fig[25] = new Pawn(false, new Point2D(3, 1));
			Fig[26] = new Pawn(false, new Point2D(4, 1));
			Fig[27] = new Pawn(false, new Point2D(5, 1));
			Fig[28] = new Pawn(false, new Point2D(6, 1));
			Fig[29] = new Pawn(false, new Point2D(7, 1));
			
			
			Figure kingW = new King(true, new Point2D(4, 7));
			Figure kingB = new King(false, new Point2D(4, 0));
			Fig[14] = kingW;
			Fig[30] = kingB;
			
			Figure queenW = new Queen(true, new Point2D(3, 7));
			Figure queenB = new Queen(false, new Point2D(3, 0));
			Fig[15] = queenW;
			Fig[31] = queenB;
			
			for(int x = 0; x < Fig.length; x++){
				for(Field[] f: getFelder()){
					for(Field fx: f){
						if(Fig[x].getSP().equals(fx.getKoordinate())){
							fx.addFigur(x);
						}
					}
				}
			}	
		}

	/**
	 * eine bestimmte Figur wird als selektierte Figur gespeichert
	 * @param koord das Selektieren einer Figur wird anhand der Koordinate auf 
	 * der sie steht abgewickelt
	 * @return ob der Vorgang erfolgreich war
	 */
	public Boolean selectFigur(Point2D coord){
		if(searchFigIndexByCoord(coord) != Field.emptyField){
			selectedFig = searchFigIndexByCoord(coord);
			posSelectedFig = coord;
			return true;
		}
		return false;
	}
	
	/**
	 * Anhand der FigurenID wird die Koordinate bestimmt
	 * @param index Figurenindex
	 * @return Koordinate der uebergebenen Figur
	 */
	public Point2D searchFigCoordByIndex(int index){
		
		for(Field[] fx: felder){
			for(Field f: fx){
				if(f.getBelegung() == index)
					return f.getKoordinate();
			}
		}
		return nonSelectable;
	}
	
	public int searchFigIndexByCoord(Point2D coord){
		return felder[(int)coord.getX()][(int)coord.getY()].getBelegung();
	}

	/**
	 * die eingegebene Koordinate wird in die vom Programm verstaendliche umgewandelt
	 * @param koord eingegebene Koordinate nach dem angezeigten System
	 * @return die bearbeitbare Version der Koordinate
	 */
	public Point2D displayCoordToReal(String koord){
		int x, y;
	
		x = ((int)koord.charAt(0)) - 65; 
		y = 7-(((int)koord.charAt(1)) - 49);
		
		return new Point2D(x, y);
	}
	
	/**
	 * die vom Programm lesbare Koordinate wird in die vom Nutzer verstaendliche umgewandelt
	 * @param vom Programm lesbare Koordinate
	 * @return die bearbeitbare Version der Koordinate
	 */
	public String RealToDisplayCoord(Point2D p){
		String str = "";
		
		str += ((char)(p.getX()+65));
		str += ((char)(56 - p.getY()));
		 
		return str;
	}
	
	/**
	 * Der Bauerntausch mit einem Eingabedialog fuer die gewuenschte Figur.
	 * @param iW die Teamfarbe des Bauern
	 */
	public void promotePawn(Boolean iW){
		
		Scanner scanner = new Scanner(System.in);
		int x = 0;
		Figure f = null;
		do{
		System.out.println("Your pawn has reached the other side!");
		System.out.println("\nYou can select one of the following figures!");
		System.out.println("1 - Knight");
		System.out.println("2 - Rook");
		System.out.println("3 - Bishop");
		System.out.println("4 - Queen");
		
		try
		{
		x = Integer.parseInt(scanner.next());
		}
		catch(NumberFormatException e){
			x = 5;
		}
		}while((x>4) && (x<1));
		
		try{
			
		switch(x){
		
		case 1:
			f = new Knight(iW, Fig[selectedFig].getSP());
			break;
		case 2:
			f = new Rook(iW, Fig[selectedFig].getSP());
			break;
			
		case 3:
			f = new Bishop(iW, Fig[selectedFig].getSP());
			break;
			
		case 4:
			f = new Queen(iW, Fig[selectedFig].getSP());
			break;
		}
		
		}
		catch(Exception e){
			
		}
		
		Fig[selectedFig] = f;
	}

	/**
	 * Die Methode leert das aktuelle Feld und setzt die Figur auf das Zielfeld.
	 * @param pos auf welche Position gewechselt werden soll
	 */
	public void changePos(Point2D pos){
		
		felder[(int)posSelectedFig.getX()][(int)posSelectedFig.getY()].clear();
		felder[(int)pos.getX()][(int)pos.getY()].addFigur(selectedFig);
		
		selectFigur(pos);
	}
	
	/**
	 * Methode, welche bei dem Schlagen einer Figur aufgerufen wird.
	 * Meldungen werden ausgegeben und das angegriffene Feld wird geleert.
	 * @param p Feld des zu Schlagenden
	 */
	public void hit(Point2D p){
		
		System.out.println("\n"+Fig[searchFigIndexByCoord(p)].getName()+" has been terminated!\n");
		felder[(int)p.getX()][(int)p.getY()].clear();
	}

	/**
	 * In dieser Methode begibt sich die Figur auf das jeweilige Feld je nachdem,
	 * um welche es sich handelt.
	 * @param p Punkt auf den die selektierte Figur wechseln soll
	 */
	public void move(Point2D p){
		
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
				((Pawn)Fig[i]).SetEnPassant(false);
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
	
				Point2D origSel = posSelectedFig;
				//die Position des Turms aendern
				selectFigur(searchFigCoordByIndex(rochRook));
				changePos(new Point2D(posSelectedFig.getX()+moveVarX, posSelectedFig.getY()));
				selectFigur(origSel);
			}
		}
		else if(Fig[selectedFig] instanceof Rook){
			((Rook)Fig[selectedFig]).setRochade(false);
		}
		else if(Fig[selectedFig] instanceof Pawn){
			
			//Doppelzug
			if(Math.abs(yGo-posSelectedFig.getY()) == 2){
				((Pawn)Fig[selectedFig]).SetEnPassant(true);
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
		
		//check if the enemy is in check
		if(Schach(!Fig[selectedFig].getIW())){
			if(!Fig[selectedFig].getIW())
				whiteCheck = true;
			else
				blackCheck = true;
		}
		else{
			if(!Fig[selectedFig].getIW())
				whiteCheck = false;
			else
				blackCheck = false;
		}
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
	public List<Point2D> moveCrap(int posX, int posY, int moveX, int moveY, int xLimit, int yLimit){
		
		List<Point2D> p = new ArrayList<Point2D>();
		
		posX += moveX;
		posY += moveY;	
		
		while( (posX != xLimit && posY != yLimit) && (posX >= 0 && posX < 8) && (posY >= 0 && posY < 8)){
			if(this.felder[posX][posY].getBelegung() != Field.emptyField){
				if(Fig[selectedFig].getIW() != Fig[felder[posX][posY].getBelegung()].getIW()){
					p.add(new Point2D(posX, posY));
				}
				break;
			}
			
			p.add(new Point2D(posX, posY));
			posX += moveX;
			posY += moveY;
		}
		return p;
	}
	
	/**
	 * Eine Methode, welche die moeglichen Bewegungsendpunkte einer selektierten Figur berechnet.
	 * @return die Liste mit den Endpunkten
	 */
	public ArrayList<Point2D> movePossibilities(){
		
		ArrayList <Point2D> moveP = new ArrayList<Point2D>();
		
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
			
			if(Fig[selectedFig].getIW()){
				rochRookL = 0;
				rochRookR = 1;
			}
			else{
				rochRookL = 16;
				rochRookR = 17;
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
			
			
			//Rochade		
			//rechts
			if( ((King)Fig[selectedFig]).getRochade() && ((Rook)Fig[rochRookR]).getRochade() ){
				if((this.felder[(x+2)][y].getBelegung() == Field.emptyField) && this.felder[(x+1)][y].getBelegung() == Field.emptyField){
					moveP.add(new Point2D((x+2), y));
				}
			}
			//links
			if( ((King)Fig[selectedFig]).getRochade() && ((Rook)Fig[rochRookL]).getRochade() ){
				if((this.felder[(x-3)][y].getBelegung() == Field.emptyField) && (this.felder[(x-2)][y].getBelegung() == Field.emptyField) && (this.felder[(x-1)][y].getBelegung() == Field.emptyField)){
					moveP.add(new Point2D((x-2), y));
				}
			}
		}
		else if(this.Fig[selectedFig] instanceof Pawn){
			
			int varY;
			
			if(Fig[selectedFig].getIW())
				varY = -1;
			else
				varY = 1;
			
			//normal vor
			if(this.felder[x][y+varY].getBelegung() == Field.emptyField){
				moveP.add(new Point2D(x, y+varY));
				
				//Doppelzug
				if((((Pawn)Fig[selectedFig]).getDoublemove()) && (this.felder[x][y+(2*varY)].getBelegung() == Field.emptyField)){
					moveP.add(new Point2D(x, y+(2*varY)));
				}
				
			}
			
			//vorne links/rechts ein schwarzer/weißer Gegner
			if(x-1 != -1){
				int possibleEnemy = this.felder[x-1][y+varY].getBelegung();
				if(possibleEnemy != Field.emptyField){
					
					if(Fig[possibleEnemy].getIW() != Fig[selectedFig].getIW())
						moveP.addAll(moveCrap(x, y+varY, -1, 0, x-2, 10));
				}
				possibleEnemy = this.felder[x-1][y].getBelegung();
				if(possibleEnemy != Field.emptyField){
					if(Fig[possibleEnemy].getIW() != Fig[selectedFig].getIW() && Fig[possibleEnemy] instanceof Pawn)
						if(((Pawn)Fig[possibleEnemy]).GetEnPassant())
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
				possibleEnemy = this.felder[x+1][y].getBelegung();
				if(possibleEnemy != Field.emptyField){
					if(Fig[possibleEnemy].getIW() != Fig[selectedFig].getIW() && Fig[possibleEnemy] instanceof Pawn)
						if(((Pawn)Fig[possibleEnemy]).GetEnPassant())
							moveP.addAll(moveCrap(x, y, 1, varY, x+2, y+2));		
				}	
			}
		}
		return moveP;
	}

	/**
	 * eine uebergebene Liste wird gefiltert, damit keine schacherzeugenden Bewegungen
	 * mehr vorhanden sind
	 * @param moves die zu filternde Bewegungsliste
	 * @param iW die Teamfarbe der Figur
	 * @return die gefilterte 
	 */
	public List<Point2D> filter(List<Point2D> moves, Boolean iW){
		
		Point2D realPos = posSelectedFig;
		
		int origBelegung;
		
		Boolean remove;
		
		//es wird von oben runtergezaehlt, um beim Entfernen keine Indexprobleme zu erzeugen
		for(int i = moves.size()-1; i >= 0; i--){
			remove = false;
			origBelegung = searchFigIndexByCoord(moves.get(i));
			felder[(int)moves.get(i).getX()][(int)moves.get(i).getY()].clear();
			
			changePos(moves.get(i));
			if(this.Schach(iW)){
				remove = true;
			}
			changePos(realPos);
			felder[(int)moves.get(i).getX()][(int)moves.get(i).getY()].addFigur(origBelegung);
			if(remove)
				moves.remove(i);
		}
	
		return moves;	
	}

	/**
	 * Die jeweils gegnerischen FIguren werden ermittelt.
	 * Diese werden hinsichtlich ihrer Bewegungsmoeglichkeiten analysiert,
	 * ob sie die Position des jeweils gegnerischen Kings beinhalten.
	 * @param isW welches Team ist im Schach
	 * @return ob das jeweilige Team im Schach ist
	 */
	public Boolean Schach(Boolean isW){
		
		int gegnerStart, gegnerMax, myKingIndex;
		
		if(isW){
			gegnerStart = 16;
			gegnerMax = 31;
			myKingIndex = 14;
		}
		else{
			gegnerStart = 0;
			gegnerMax = 15;
			myKingIndex = 30;
		}
		
		Point2D origCoord = posSelectedFig;
		Point2D kingCoord = searchFigCoordByIndex(myKingIndex);
		
		for(int i = gegnerStart; i <= gegnerMax; i++){
			Point2D curCoord = searchFigCoordByIndex(i);
			if(curCoord.equals(nonSelectable))
				continue;
			selectFigur(curCoord);
			
			if(movePossibilities().contains(kingCoord)){
				selectFigur(origCoord);
				return true;
			}
		}
		
		selectFigur(origCoord);
		return false;
	}

	public Boolean king1v1(){
		
		int counter = 0;
		
		for(int i = 0; i < Fig.length; i++){
			if(!searchFigCoordByIndex(i).equals(nonSelectable))
				counter++;
		}
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
	public Boolean Patt(Boolean isW){
		
		int teamStart, teamMax;
		
		if(isW){
			teamStart = 0;
			teamMax = 15;
		}
		else{
			teamStart = 16;
			teamMax = 31;
		}
		
		for(int i = teamStart; i <= teamMax; i++){
			Point2D curCoord = searchFigCoordByIndex(i);
			if(curCoord.equals(nonSelectable))
				continue;
				
			selectFigur(curCoord);
			if(filter(movePossibilities(), isW).size() != 0){
				return false;
			}
		}
		return true;	
	}

	/**
	 * Wenn das ausgewaehlte Team im Schach steht und einem Patt unterliegt.
	 * @param isW welches Team im SchachMatt steht
	 * @return ob das jeweilige Team im SchachMatt steht
	 */
	public Boolean SchachMatt(Boolean isW){
		
		Boolean teamCheck;
		
		if(isW)
			teamCheck = whiteCheck;
		else
			teamCheck = blackCheck;
		
		if(teamCheck && Patt(isW))
			return true;
		
		return false;
	}
	
	}

