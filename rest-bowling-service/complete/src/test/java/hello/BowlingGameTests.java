/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Unit Tests
 * Class testing BowlingGame.java with JUnit
 * 
*/

package hello;


import org.junit.Test;
import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
import hello.*;


import static org.junit.Assert.*;
import org.junit.Before;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.beans.factory.annotation.Autowired;



@RunWith(SpringRunner.class)

public class BowlingGameTests {
static int MAX = 21;
	static int PERFECTGAME = 12;
	private Bowling bowlingUnderTest;	
	int [] referenceData,dataFromBowlingGame;

	@Before 
	public void initializeBowlingGameTests() {
	bowlingUnderTest = new BowlingGame();
	referenceData = new int[MAX];;
  	dataFromBowlingGame = new int[MAX];	
  	for (int i=0;i<(MAX);i++){
  		referenceData[i]=0;
  		dataFromBowlingGame[i]=0;
  		}
	}
	
	
	@Test 
	public void noBonusesTestAfterSomeRolls() {

		int noBonusPins=4; // less that 5
		int someRolls=20;	//max 20 (without extra roll)
		
  	for (int i=0;i<someRolls;i++){
  		bowlingUnderTest.roll(noBonusPins);
  		dataFromBowlingGame[i]=bowlingUnderTest.calculateScore();
  		referenceData[i]=noBonusPins*(i+1);
  	}
  	// comparison of tables/array
  	assertArrayEquals(referenceData, dataFromBowlingGame); 	
	
  } // public void noBonusesTestAfterSomeRolls
	
	@Test 
	public void perfectGameScores300() {
		// Perfect Game 12 x STRIKE
  	//bowlingUnderTest = new BowlingGame();
  	int [] patternData = new int[PERFECTGAME];
  	int somePerfectRolls=PERFECTGAME; //change it if error
  	
  	//the pattern of MAX STRIKE bowling game
  	patternData[0]=10;  // 1st round
  	patternData[1]=30;  //10+10B1+10
  	patternData[2]=60;	//30+10B1+10B2+10
  	patternData[3]=90;
  	patternData[4]=120;
  	patternData[5]=150;
  	patternData[6]=180;
  	patternData[7]=210;
  	patternData[8]=240;
  	patternData[9]=270;
  	patternData[10]=290;
  	patternData[11]=300; // 12 round
  	// get scores from BowlingGame
  	//patternData[12]=300;
  	//patternData[13]=300;
  	
  	for (int i=0;i<somePerfectRolls;i++){
  		bowlingUnderTest.roll(10);
  		dataFromBowlingGame[i]=bowlingUnderTest.calculateScore();
  		referenceData[i]=patternData[i];
      	
  	}
  	
  	assertArrayEquals("Perfect Game Error",referenceData, dataFromBowlingGame);
	}
	
	@Test 
	public void perfectGameSpare190() {
  	int [] patternData = new int[MAX];
  	int somePerfectSpares=MAX; // change it if error
  	//the pattern of MAX SPARE bowling game
  	patternData[0]=9;
  	patternData[1]=10; 
  	patternData[2]=28;
  	patternData[3]=29;
  	patternData[4]=47;
  	patternData[5]=48;
  	patternData[6]=66;
  	patternData[7]=67;
  	patternData[8]=85;
  	patternData[9]=86;
  	patternData[10]=104;
  	patternData[11]=105;
  	patternData[12]=123;
  	patternData[13]=124;
  	patternData[14]=142;
  	patternData[15]=143;
  	patternData[16]=161;
  	patternData[17]=162;
  	patternData[18]=180;
  	patternData[19]=181;
  	patternData[20]=190;
//the pattern of MAX SPARE bowling game
  	
  	for (int i=0;i<somePerfectSpares;i++){
  		bowlingUnderTest.roll(9);
  		dataFromBowlingGame[i]=bowlingUnderTest.calculateScore();
  		referenceData[i]=patternData[i];
  		i++;
  		if (i!=(MAX)) { // without "MAX+1" roll
  			bowlingUnderTest.roll(1);
      		dataFromBowlingGame[i]=bowlingUnderTest.calculateScore();
      		referenceData[i]=patternData[i];
  		}
  	
  	}//(int i=0;i<MAX+1;i++


  	
  	assertArrayEquals(referenceData, dataFromBowlingGame);
  	
	}
	
	
	@Test public void strikeHasBonusPointsFromNextTwoRolls() {
		// INFO: Zeros in frame except STRIKE in "oneStrike" roll and 2 next "noBonusPins" rolls
		//bowlingUnderTest = new BowlingGame();
		int oneStrike=4; // only even numbers and less that 19
		int noBonusPins=1; // less that 5
		int i=0;
		

  	for (i=0;i<oneStrike;i++){
  		bowlingUnderTest.roll(0);
  		dataFromBowlingGame[i]=bowlingUnderTest.calculateScore();
  		referenceData[i]=0;
  	}

  	bowlingUnderTest.roll(10); 
      dataFromBowlingGame[i]=bowlingUnderTest.calculateScore();
      referenceData[i]=10; 
      	
  	bowlingUnderTest.roll(noBonusPins);
      dataFromBowlingGame[i+1]=bowlingUnderTest.calculateScore();
      referenceData[i+1]=10+2*noBonusPins;
      	
  	bowlingUnderTest.roll(noBonusPins); 
      dataFromBowlingGame[i+2]=bowlingUnderTest.calculateScore();
      referenceData[i+2]=10+4*noBonusPins; 
     	
      bowlingUnderTest.roll(0); 
      dataFromBowlingGame[i+3]=bowlingUnderTest.calculateScore();
      referenceData[i+3]=10+4*noBonusPins;// 
      
      bowlingUnderTest.roll(0); 
      dataFromBowlingGame[i+4]=bowlingUnderTest.calculateScore();
      referenceData[i+4]=10+4*noBonusPins;// 
      
  	for (i=(oneStrike+5);i<MAX-2;i++){ //MAX-2 because=  -1:we have one STRIKE; another -1: not  extra roll in last frame (STRIKE or SPARE)
      	bowlingUnderTest.roll(0);
      	dataFromBowlingGame[i]=bowlingUnderTest.calculateScore();
      	referenceData[i]=10+4*noBonusPins;
      }	
  	
  	// comparison of tables/array
  	assertArrayEquals(referenceData, dataFromBowlingGame); 	
	
  } // public void strikeHasBonusPointsFromNextTwoRolls()
	
	@Test public void spareHasBonusPointsFromNextRoll() {
		// INFO: Zeros in frame except SPARE in "oneSpare" roll and 2 next "noBonusPins" rolls
		//bowlingUnderTest = new BowlingGame();
		int oneSpare=4; // roll when will be STRIKE. Only even numbers and less that 19
		int noBonusPins=1; // less that 5
		int i=0;
		

  	for (i=0;i<oneSpare;i++){
  		bowlingUnderTest.roll(0);
  		dataFromBowlingGame[i]=bowlingUnderTest.calculateScore();
  		referenceData[i]=0;
  	}
  	if (i==oneSpare) {
  		bowlingUnderTest.roll(noBonusPins); 
      	dataFromBowlingGame[i]=bowlingUnderTest.calculateScore();
      	referenceData[i]=noBonusPins; 
      	
  		bowlingUnderTest.roll(10-noBonusPins); 
      	dataFromBowlingGame[i+1]=bowlingUnderTest.calculateScore();
      	referenceData[i+1]=10; 
      	
  		bowlingUnderTest.roll(noBonusPins);
      	dataFromBowlingGame[i+2]=bowlingUnderTest.calculateScore();
      	referenceData[i+2]=10+2*noBonusPins;
      	
  		bowlingUnderTest.roll(noBonusPins); 
      	dataFromBowlingGame[i+3]=bowlingUnderTest.calculateScore();
      	referenceData[i+3]=10+3*noBonusPins; 
  	}

  	for (i=(oneSpare+4);i<(MAX-1);i++){
      	bowlingUnderTest.roll(0);
      	dataFromBowlingGame[i]=bowlingUnderTest.calculateScore();
      	referenceData[i]=10+3*noBonusPins;
      }	
  	
  	// comparison of tables/array
  	assertArrayEquals("spareHasBonusPointsFromNextRoll()",referenceData, dataFromBowlingGame); 	
	
  } // public void spareHasBonusPointsFromNextRoll()
	

}
