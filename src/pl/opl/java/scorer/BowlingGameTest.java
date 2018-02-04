package pl.opl.java.scorer;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class BowlingGameTest extends BowlingGame {
	
private static Scanner input;
static Boolean extra_rzut;

	public static void main(String[] args) throws IOException {
		BowlingGameTest test = new BowlingGameTest();
		int rzut1=0, rzut2=0, rzut3=0;
		int i=1;
		extra_rzut =false;
		input = new Scanner(System.in);
		
		System.out.println("Program licz¹cy wynik gry w krêgle ("+MAXGAMES+" rund) ");
		
		while(i <(MAXGAMES)) {
		System.out.print("Podaj wynik w "+i+" kolejce: pierwszy rzut - ");
			try
			{
				rzut1  =  input.nextInt(); 
			}
			catch(InputMismatchException e)
			{
				System.out.println("To nie liczba (koñczymy) :[");
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
					rzut2  =  input.nextInt(); //dla typów Long, Float i Double wstaw w³aœciw¹ nazwê i typ
				}
				catch(InputMismatchException e)
				{
					System.out.println("To nie liczby (koñczymy) :[");
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
		System.out.println("To nie liczba (koñczymy) :[");
		return;
	}
	test.roll(rzut1);
	if (rzut1 == FRAME_MAXSCOPE) {
		System.out.print("Strike !!!");
		extra_rzut=true;
		
	}

	System.out.print(" a teraz drugi rzut - ");
	try{
		rzut2  =  input.nextInt(); //dla typów Long, Float i Double wstaw w³aœciw¹ nazwê i typ
	}
	catch(InputMismatchException e){
		System.out.println("To nie liczby (koñczymy) :[");
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
			rzut3  =  input.nextInt(); //dla typów Long, Float i Double wstaw w³aœciw¹ nazwê i typ
		}
		catch(InputMismatchException e){
			System.out.println("To nie liczby (koñczymy) :[");
			return;
		}
		test.roll(rzut3);
	}
	
	System.out.print("\n Wynik koñcowy: "+test.calculateScore()+"\n");
	i++;
	}
		input.close();
	}

	
	public void wypisz(int a) {
		System.out.println("Runda "+frame_nb+" rzut "+roll_nb+" str¹cono "+a+"; punkty = "+calculateScore());
		
	}
	
}
