package main;

import java.util.Random;


public class Wzorzec {
	static int MAXGAMES = 10;
	static int FRAME_MAXSCOPE = 10;
	int los[],wynik[];
	Random generator;
	
	
	public Wzorzec(){
	Random generator = new Random();
	int first=-1,second=-1,third=-1;  // random pins in round, -1 oznacza brak rzutu 	
	int i=0; //counter start from "0";
	int k=0; // licznik kolejek
	los= new int[(2*MAXGAMES)+1]; // max liczba rzutów to 2*10 plus 1 extra 
	wynik= new int[(2*MAXGAMES)+1]; // max liczba rzutów to 2*10 plus 1 extra 
	
	for(int a=0;a<((2*MAXGAMES)+1);a++){ // zerowanie bazy
		los[a]=-1; // "-1" mains null
		wynik[a]=-1;
	}
		
	while (k<(MAXGAMES-1)) {  //
		first=generator.nextInt(FRAME_MAXSCOPE);
		los[i++]=first;
		if (first==FRAME_MAXSCOPE){
			// brak drugiego rzutu w kolejce
			k++;
//			System.out.println(""+k+"rounda, rzuty:"+first);

		}	
		else{	
			los[i++]=generator.nextInt(FRAME_MAXSCOPE-first);
			k++;

//			System.out.println(""+k+"rounda, rzuty:"+first+", drugi="+los[i-1]+" ");
		}
	} // k<(MAXGAMES-1)

	// Last round i=(MAXGAME-1) 
	first=generator.nextInt(FRAME_MAXSCOPE);
	if (first==FRAME_MAXSCOPE){
		second=generator.nextInt(FRAME_MAXSCOPE);
			
		if ((second==FRAME_MAXSCOPE)){
			third=generator.nextInt(FRAME_MAXSCOPE);
		}	
		else{
			third=generator.nextInt(FRAME_MAXSCOPE-second);
		}
	}	//(first==FRAME_MAXSCOPE)
	else{	
		second=generator.nextInt(FRAME_MAXSCOPE-first);
		//second=(FRAME_MAXSCOPE-first); //testing rule
		if (((first+second)==FRAME_MAXSCOPE)){
			third=generator.nextInt(FRAME_MAXSCOPE);
		}	
		else{	
			third=-1; // "-1" means "null"
		}


	
	}//if first==FRAME_MAXSCOPE)
		
		los[i++]=first;
		los[i++]=second;
		los[i++]=third;
	//	System.out.println("Ostatnia runda: pierwszy="+first+", drugi="+second+", trzeci"+third);
	
	// po wygenerowaniu wszystkich rzutów poniższa metoda wylicza punkty 
		//wylicz();
	}
	
	
public void wylicz(){
	int i=0,runda=0;//,first=0,second=0;//,third=0;
	//runda 0 (pierwsza)
	wynik[0]=los[0];
	System.out.println("Runda "+runda+";Rzut: "+(i+1)+"; spins:"+los[0]+";wynik "+wynik[0]+" ");
	
// starting
	
	if (los[0]==FRAME_MAXSCOPE){
		//wynik[1]=wynik[0]+los[1]+los[1];
		//runda=1;
		i=1;
	}
	else{
		wynik[1]=wynik[0]+los[1];
		i=2;
	}

	System.out.println("Runda "+runda+";Rzut: "+i+"; spins:"+los[1]+";wynik "+wynik[1]+" ");	
	
	runda=1;	
	//i=2;
	while (runda<MAXGAMES) {  //
		
		wynik[i]=wynik[i-1]+los[i]; // dodaj rzut do wyniku z poprzedniego rzutu
		
/*		if (los[i-1] == FRAME_MAXSCOPE){ // gdy strike dodaj wyniki z aktualnego rzutów;
			wynik[i]=wynik[i]+los[i]; 
		} //if (los[i-1] == FRAME_MAXSCOPE)
		if (los[i-2] == FRAME_MAXSCOPE){ // gdy strike dodaj wyniki z aktualnego rzutu;
			wynik[i]=wynik[i]+los[i]; 
		} //if (los[i-2] == FRAME_MAXSCOPE)
		if ((los[i-2] +los[i-1])== FRAME_MAXSCOPE){ // gdy spare był to dodaj punkty
			wynik[i]=wynik[i]+los[i]; 
		} //if (los[i-2]+los[i-1]) == FRAME_MAXSCOPE)
*/		
		System.out.println("Runda "+runda+"; Rzut"+i+"; spins:"+los[i]+"; wynik "+wynik[i]+" ");
		if (los[i] == FRAME_MAXSCOPE){ // gdy aktualnie strike => koniec kolejki;
			//
			//runda++;
		} //if (los[i] == FRAME_MAXSCOPE)
		else{
			i++;  // drugi rzut w rundzie
			wynik[i]=wynik[i-1]+los[i];
			if (los[i-2] == FRAME_MAXSCOPE){ // gdy strike dodaj wyniki z aktualnego rzutu;
				wynik[i]=wynik[i]+los[i]; 
			} //if (los[i-2] == FRAME_MAXSCOPE)
		}
		
		i++; // 

		System.out.println("Runda "+runda+"; Rzut"+(i-1)+"; spins:"+los[i-1]+"; wynik "+wynik[i-1]+" ");
		runda++; //
	} // while (runda<MAXGAMES)

	if (los[i]!=-1){ // jest ekstra rzut
		wynik[i]=wynik[i-1]+los[i];
		System.out.println("Jest ekstra rzut");
	}
//	else{ // nie ma ekstra rzutu
//		i--;
//		System.out.println("Nie ma ekstra rzutu");

//	}
	System.out.println("Runda końcowa: "+runda+"; Rzut"+i+"; spins:"+los[i]+"; wynik "+wynik[i]+" ");			
	return ;
}

public int [] getpins() {
	return los;
}
public int [] getscore() {
	return wynik;
}

public void wypisz(){
	System.out.println("Wygenerowano dane testowe dla gry:");
	for (int i=0;i<(2*MAXGAMES+1);i++) {
		if (los[i]!=-1){
			System.out.println("Rzut"+(i+1)+": "+los[i]+", Wynik"+wynik[i]);
		}		
		}		
	}// wypisz
	
}// class Wzorzec