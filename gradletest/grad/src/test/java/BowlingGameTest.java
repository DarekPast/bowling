import org.junit.Test;
import static org.junit.Assert.*;


public class BowlingGameTest {
	static int MAX = 21;
	static int PERFECTGAME = 12;
	@Test public void testBowlingGameMethod() {
        BowlingGame bowlingUnderTest = new BowlingGame();
    	int [] danezwzorca,danezBowlingGame;


    	// Test I (wszystkie rzuty =9)
    	danezwzorca = new int[MAX];;
    	danezBowlingGame = new int[MAX];	
    	for (int i=0;i<MAX;i++){
    		bowlingUnderTest.roll(9);
    		danezBowlingGame[i]=bowlingUnderTest.calculateScore();
    		danezwzorca[i]=9*(i+1);
    	}
    	// metoda porownuje 2 tablice wartoœci int[];
    	assertArrayEquals(danezwzorca, danezBowlingGame); 	
		
    	
//  Test II (wszystkie STRIKE rzuty =10)
    	bowlingUnderTest = new BowlingGame();
    	danezwzorca = new int[PERFECTGAME];;
    	danezBowlingGame = new int[PERFECTGAME];	
    	for (int i=0;i<PERFECTGAME;i++){
    		bowlingUnderTest.roll(10);
    		danezBowlingGame[i]=bowlingUnderTest.calculateScore();
    	}
    	
// wzorzec dla perfect game (12 x 10punktw)
    	danezwzorca[0]=10;  // 1st round
    	danezwzorca[1]=30; 
     	danezwzorca[2]=60;
    	danezwzorca[3]=90;
    	danezwzorca[4]=120;
    	danezwzorca[5]=150;
    	danezwzorca[6]=180;
    	danezwzorca[7]=210;
    	danezwzorca[8]=240;
    	danezwzorca[9]=270;
    	danezwzorca[10]=290;
    	danezwzorca[11]=300; // 12 round
   	
    	assertArrayEquals(danezwzorca, danezBowlingGame);	
 
 //  Test III (wszystkie rzuty SAPRE 9+1)
    	
    	bowlingUnderTest = new BowlingGame();
    	danezwzorca = new int[MAX];;
    	danezBowlingGame = new int[MAX];	
    	for (int i=0;i<MAX;i++){
    		bowlingUnderTest.roll(9);
    		danezBowlingGame[i]=bowlingUnderTest.calculateScore();
       		i++;
     		if (i!=MAX) { // bez wype³nienia pozycji 22
    			bowlingUnderTest.roll(1);
        		danezBowlingGame[i]=bowlingUnderTest.calculateScore();
    		}
    	
    	}//(int i=0;i<MAX;i++
// wzorzec dla all MAX SPARE
    	danezwzorca[0]=9;
    	danezwzorca[1]=10; 
     	danezwzorca[2]=28;
    	danezwzorca[3]=29;
    	danezwzorca[4]=47;
    	danezwzorca[5]=48;
    	danezwzorca[6]=66;
    	danezwzorca[7]=67;
    	danezwzorca[8]=85;
    	danezwzorca[9]=86;
    	danezwzorca[10]=104;
    	danezwzorca[11]=105;
		danezwzorca[12]=123;
    	danezwzorca[13]=124;
    	danezwzorca[14]=142;
    	danezwzorca[15]=143;
    	danezwzorca[16]=161;
    	danezwzorca[17]=162;
    	danezwzorca[18]=180;
    	danezwzorca[19]=181;
    	danezwzorca[20]=190;
    	
    	assertArrayEquals(danezwzorca, danezBowlingGame);
    	
 
// Test4 - generator losowy?   	
// Inna "wzorcowa" klasa generuj¹ca rzuty i wyniki. Napisaæ czy poszukaæ?
    	
    } // public void testBowlingGameMethod()
	
} //BowlingGameTes
