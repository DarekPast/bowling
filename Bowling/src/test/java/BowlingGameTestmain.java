package test;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import main.BowlingGame;


public class BowlingGameTestmain extends BowlingGame {
	static int MAXGAMES = 10;
	static int FRAME_MAXSCOPE = 10;
	
	
	
BowlingGameTestmain() {
		super();
		// TODO Auto-generated constructor stub
	}




private static Scanner input;
static boolean extra_rzut;

	public static void main(String[] args) throws IOException {
		BowlingGameTestmain test = new BowlingGameTestmain();
		int rzut1=0, rzut2=0, rzut3=0;
		int i=1;
		extra_rzut =false;
		input = new Scanner(System.in);
		
		System.out.println("Program liczy wynik gry w kregle ("+MAXGAMES+" rund/frame) ");
		
		while(i <(MAXGAMES)) {
		System.out.print("Podaj wynik w "+i+" kolejce: pierwszy rzut - ");
			try
			{
				rzut1  =  input.nextInt(); 
			}
			catch(InputMismatchException e)
			{
				System.out.println("To nie liczba (konczymy) :[");
				return;
			}
			test.roll(rzut1);
			if (rzut1 == FRAME_MAXSCOPE) {
				System.out.print("Strike !!!");
			}
			if (rzut1 != FRAME_MAXSCOPE) {
				System.out.print(" a teraz drugi rzut - ");
			
				try
				{
					rzut2  =  input.nextInt(); //dla typ�w Long, Float i Double wstaw w�a�ciw� nazw� i typ
				}
				catch(InputMismatchException e)
				{
					System.out.println("To nie liczby (ko�czymy) :[");
					return;
				}
				if ((rzut1+rzut2) == FRAME_MAXSCOPE) {
					System.out.print("SPARE !!!");
				}
				test.roll(rzut2);	
			}
			i++;
			// System.out.print(rzut2);
		}//while (i <(MAXGAMES)
			
if (i ==MAXGAMES) {
	System.out.print("Podaj wynik w ostatniej kolejce: pierwszy rzut - ");
	try {
		rzut1  =  input.nextInt(); 
	}
	catch(InputMismatchException e) {
		System.out.println("To nie liczba (ko�czymy) :[");
		return;
	}
	test.roll(rzut1);
	if (rzut1 == FRAME_MAXSCOPE) {
		System.out.print("Strike !!!");
		extra_rzut=true;
		
	}

	System.out.print(" a teraz drugi rzut - ");
	try{
		rzut2  =  input.nextInt(); //dla typ�w Long, Float i Double wstaw w�a�ciw� nazw� i typ
	}
	catch(InputMismatchException e){
		System.out.println("To nie liczby (ko�czymy) :[");
		return;
	}
	test.roll(rzut2);
	if ( rzut2 == FRAME_MAXSCOPE){
		System.out.print("Strike !!!");
		extra_rzut=true;		
	}
	if ( (rzut1+rzut2) == FRAME_MAXSCOPE){
		System.out.print("SPARE !!!");
		extra_rzut=true;		
	}
	if (extra_rzut) {
		System.out.print(" EXTRA RZUT - ");
		try{
			rzut3  =  input.nextInt(); //
		}
		catch(InputMismatchException e){
			System.out.println("To nie liczby (konczymy) :[");
			return;
		}
		test.roll(rzut3);
	}
	
	System.out.print("\n Wynik koncowy: "+test.calculateScore()+"\n");
	i++;
	}
		input.close();
	}

	
	
	
}
