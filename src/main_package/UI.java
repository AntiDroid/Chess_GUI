package main_package;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.geometry.Point2D;

class UI {

	Game g1;
	
	Boolean whiteTurn = true;
	Boolean abort = false;
	
	int player;
	int figMax, figMin;
	
	Scanner scan;
	
	public static void main(String[] args) {	
		UI ui = new UI();
		ui.buildUI();
	}

	/**
	 * Spielverwaltung mit Benutzerinteraktionen
	 */
	void buildUI(){
		
		scan = new Scanner(System.in);
		System.out.println("Player 1(BLACK), Enter your name:");
		Player p1 = new Player(scan.next(), false);
		System.out.println("Player 2(WHITE), Enter your name:");
		Player p2 = new Player(scan.next(), true);
		
		String str;
		List<Point2D> liste = new ArrayList<Point2D>();
		g1 = new Game(p1, p2);

		do{
			
			if(whiteTurn){ 
				player = 1;
				figMax = 15;
				figMin = 0;
			}
			else{
				player = 0;
				figMax = 31;
				figMin = 16;
			}
			
			g1.consolePrint();
			
			do{
				
				
			System.out.println("It's your turn "+g1.getPlayer()[player].getName()+"!");
			System.out.println("\nWhich figure should be moved?(A5, B2, A3, ...)");
			System.out.println("(x to abort)");
			str = scan.next().toUpperCase();
			
			if(str.equals("x")){
				System.out.println("The game was aborted!");
				abort = true;
				break;
			}

			if(!str.matches("^[A-H][1-8]$")){
				System.out.println("Invalid input!");
				str = "";
			}
			else{
				Boolean notEmpty = g1.brett.selectFigur(g1.brett.displayCoordToReal(str));
				
				//Wenn Figuren des falschen Teams ausgewaehlt wurden
				if((g1.brett.getSelFig() < figMin) || (g1.brett.getSelFig() > figMax)){
					str = "";
				}
				else{
					if(notEmpty){
						liste = g1.brett.filter(g1.brett.movePossibilities(), g1.getPlayer()[player].getIsWhite());
						if(liste.size() == 0){
							System.out.println("The figure which was chosen can't move!");
							str = "";
						}
					}
					else{
						str = "";
					}
				}
			}
			}while(str.length() != 2);
			
			
			
			if(abort){
				break;
			}
			
			int input = 0;
			
			do{
				
				for(int i = 0; i < liste.size(); i++){
					System.out.println((i+1)+"\t"+g1.brett.RealToDisplayCoord(liste.get(i)));
				}
				
				System.out.println("\nChoose a position by entering its index!");
				str = scan.next();
				
				try{
					input = Integer.parseInt(str);
				}
				catch(NumberFormatException e){
					str = "";
				}
				
				if(input > liste.size() || input < 1)
					str = "";
				
			}while(str == "");		
			
		g1.brett.move(liste.get(input-1));	
		
		if(g1.brett.SchachMatt(!whiteTurn)){
			g1.Win(whiteTurn);
			break;
		}
		else if(g1.brett.Patt(!whiteTurn)){
			g1.Draw();
			if(g1.getPlayer()[player].equals(g1.getPlayer()[0]))
				System.out.println("\n\nPlayer "+g1.getPlayer()[2].getName()+"'s king can't move!");
			else
				System.out.println("\n\nPlayer "+p1.getName()+"'s king can't move!");
			break;
		}     
		else if(g1.brett.Patt(whiteTurn)){
			g1.Draw();
			System.out.println("\n\nPlayer "+g1.getPlayer()[player].getName()+"'s king can't move!");
			break;
		}
		else if(g1.brett.king1v1()){
			g1.Draw();
			break;
		}
		
		whiteTurn = !whiteTurn;
		
		}while(true);
		
		System.out.println("-----Bye-----");
		scan.close();
	}

	}
