package main.java;
//2018.02.23
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service ("bowlingUnderTest")
public class AnnotatedBowlingGame2 {
	private static int MAXFRAME = 11; // MAX count of frame/round + we start from "1" in table
	private static int MAXROLLS = 24; // 2*MAXFRAME+EKSTRA ROLL + we start from "1" in table
	private static int ROLL_MAXSCOPE = 10;
	private static int PINS =0; 
	private static int BONUS =1;
	private static int SCORE =2;
	private static int STRIKE =3; 
	private static int SPARE =1;
	private static int FIRST_ROLL =1;
	private static int SECOND_ROLL =2;
	private static int THIRD_ROLL =3;
	
	private int roll_nb, frame_nb, roll_in_frame;
	private int rolls[][];// rolls[roll_nb][0-pins,1-bonus,2-score]; roll_nb <1,...,MAXROLLS-1[=23]>
//	private int gameScore[];// gameScore[frame_nb]; frame_nb <1,...,MAXFRAME-1[=10]>

	public AnnotatedBowlingGame2(){
		roll_nb=0; 
		frame_nb=1;  
		roll_in_frame=0;
		rolls= new int[MAXROLLS][3]; // rolls table
//		gameScore= new int[MAXFRAME+1]; // game scope table; we start from 1
		rolls[0][PINS]=0;
		rolls[0][SCORE]=0;
		rolls[0][BONUS]=0;
//		gameScore[0]=0;
		
		for(int a=1;a<MAXROLLS;a++){ 
			rolls[a][PINS]=0; 
			rolls[a][SCORE]=0; 
			rolls[a][BONUS]=0; 

		}//for
	}// BowlingGame2()
	
	
	public void roll(int pins) {
		validation(pins); // if out of field or not game structure pins => error
		roll_nb++; // next roll number in all game
		roll_in_frame++; // next roll in current frame/round
		rolls[roll_nb][PINS]=pins; 	// remember pins in rollstable;
		frameNbControl();		// check frame/round number
		
		setBonuses();
		
		addPins();					// add current pins;
		addBonuses(); 				// add bonuses to previous rolls in rollstable 
		}
	


	private void frameNbControl() {
		//System.out.print("1. Frame="+frame_nb+"; roll_in_frame="+roll_in_frame);
		if ((frame_nb)!=MAXFRAME){
			if (roll_in_frame==THIRD_ROLL){ // only 2 rolls in frame. When is third roll => next frame/round. Except extra roll in last frame (frame_nb==MAXFRAME)
				frame_nb++;      // new frame/round
				roll_in_frame=1; // in new frame it is first roll;
			}
		}
		if ((rolls[roll_nb][PINS]==ROLL_MAXSCOPE)&&(rolls[roll_nb-1][BONUS]==STRIKE)){ // STRIKE in previous roll
			if (((frame_nb)<MAXFRAME)){ // when last frame/round => not new frame
				frame_nb++;
				roll_in_frame=FIRST_ROLL;			
			}
			if ((frame_nb==MAXFRAME)&&(roll_in_frame==FIRST_ROLL)){ // when last frame/round => not new frame
				roll_in_frame=SECOND_ROLL;			
			}
		}
	}// frameNbControl(int pins)
	
	private void setBonuses(){
		//in last frame if STRIKE in second round => not bonus
		if ((rolls[roll_nb][PINS] == ROLL_MAXSCOPE)&&(roll_in_frame==FIRST_ROLL)) { 		// when in first frame if STRIKE 
			rolls[roll_nb][BONUS]=STRIKE;
		}
		// SPARE only when it is second roll in frame/round (last frame too)
		if (((rolls[roll_nb-1][PINS]+ rolls[roll_nb][PINS]) == ROLL_MAXSCOPE)&&(roll_in_frame==SECOND_ROLL)) {
			rolls[roll_nb][BONUS]=SPARE;
		}
	}
	private void addPins() {
		rolls[roll_nb][SCORE]=rolls[roll_nb-1][SCORE];
		if ((frame_nb)<MAXFRAME) {
			//gameScore[frame_nb]=gameScore[frame_nb-1];
			//gameScore[frame_nb]+=rolls[roll_nb][PINS];
			rolls[roll_nb][SCORE]+=rolls[roll_nb][PINS];
			}

		
	}

	private void addBonuses(){ 
		
		if (roll_nb==1) {
			return;
		} // if first roll there isn't bonuses
		if ((rolls[(roll_nb-1)][BONUS]==STRIKE)){
//			if (((frame_nb)!=MAXFRAME)||(roll_in_frame!=3)){
			rolls[roll_nb][SCORE]+=(rolls[roll_nb][PINS]);//rolls[roll_nb-2][SCORE]+pins2;
//			}
		}		
		// if STRIKE in n-2 [second bonus] 
		if ((rolls[(roll_nb-2)][BONUS]==STRIKE)){
			rolls[roll_nb][SCORE]+=(rolls[roll_nb][PINS]);//+rolls[roll_nb-1][PINS]);
//			//gameScore[frame_nb]+=pins;
		}


		if ((rolls[roll_nb-1][BONUS]==SPARE)&&((roll_nb-1)>0)){
			rolls[roll_nb][SCORE]+=rolls[roll_nb][PINS];
			//gameScore[frame_nb]+=pins;
		}
		}
	
	
	private void validation(int pins) {
		validationRollOutOfScope(pins); // pins validation <0,..,ROLL_MAXSCOPE>
		if (roll_in_frame == FIRST_ROLL){ // when started second roll in frame/round
			// except STRIKE in last frame (frame_nb==MAXFRAME)
			validationSecoundRollPinsInFrame(pins); // first + second roll in frame must be in <0,..,ROLL_MAXSCOPE-first_roll> except strike		
		}
		if ((roll_in_frame==SECOND_ROLL)&&(frame_nb==MAXFRAME)){ // // when started third roll in frame/round; Last round and extra roll  
			validationExtraRollInLastFrame(pins); // 
			validationTwoRollPinsInLastFrame(pins);
		}
		

	}
	
	private void validationRollOutOfScope(int pins2) {
		try{ 
			if ((pins2<0)||(pins2>ROLL_MAXSCOPE)){ // Exception when pins in first roll in frame/round is out of <0,...,10>
				throw new IllegalArgumentException();//Exception() "Pins must be from 0 to 10");
			}	
		}
		catch (IllegalArgumentException e){
			//System.out.println(e);
			throw new IllegalArgumentException("Pins must be from 0 to 10");// 
		}
	}
	
	private void validationSecoundRollPinsInFrame(int pins2) {
		try{ //validation:  when started second roll in frame/round
			
			if ((rolls[roll_nb][PINS]+pins2>ROLL_MAXSCOPE)&&(rolls[roll_nb][PINS]!=ROLL_MAXSCOPE)){ // Second roll in frame/round; Exception when pins in second roll is bigger than "10-pins_in_first_roll".
				throw new IllegalArgumentException();//("BowlingGame: void roll(int pins). Sum of pins in two rolls must be from 0 to 10 -> outside the field of input data.\n");
			}
		}
		catch (IllegalArgumentException e){
			//System.out.println(e);
			throw new IllegalArgumentException("Sum of pins must be from 0 to 10 in two rolls in one frame/round .\n");// 
		}
	}


	private void validationExtraRollInLastFrame(int pins2) {
		// Extra roll in last frame
		try{ 
			// if no STRIKE (in first roll) or SPARE in last frame => no extra roll
			if ((rolls[roll_nb-1][PINS]==ROLL_MAXSCOPE)||((rolls[roll_nb-1][PINS]+rolls[roll_nb][PINS])==ROLL_MAXSCOPE)){
				//do nothing is OK
			}	
			else{
				throw new IllegalArgumentException("There is no ekstra roll (third) in last frame/round!!!");
			}
			
		}		
		catch (IllegalArgumentException e){
		// System.out.println(e);
		throw new IllegalArgumentException("There is no ekstra roll (third) in last frame/round!!!");// 
	}
}
	
	private void validationTwoRollPinsInLastFrame(int pins2) {
	
		try{ 
			// Last Frame: if (roll in second roll in last frame != ROLL_MAXSCOPE) sum second and third must be <= ROLL_MAXSCOPE
			if (rolls[roll_nb-1][PINS]==ROLL_MAXSCOPE){ // when STRIKE in FIRST
				if ((rolls[roll_nb][PINS]!=ROLL_MAXSCOPE)&&((rolls[roll_nb][PINS]+pins2)>ROLL_MAXSCOPE)) {
					throw new IllegalArgumentException();
					}
			}
		}		
		catch (IllegalArgumentException e){
		// System.out.println(e);
		throw new IllegalArgumentException("In last frame/round sum of pins in two last rolls (second and third) must be from 0 to 10 except STRIKE in second roll \n");// 
	}
		
}

//
	public int calculateScore() {
		//return gameScore[frame_nb];
		return rolls[roll_nb][SCORE];	
		}//calculateScore()
	
}// class BowlingGame