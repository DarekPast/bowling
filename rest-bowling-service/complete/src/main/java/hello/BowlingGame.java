package hello;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("bowlingGame") 
public class BowlingGame implements Bowling {
	private static int MAXFRAME = 10; // MAX count of frame/round + we start from "1" in table
	private static int MAXROLLS = 22; // 2*MAXFRAME+EKSTRA ROLL + we start from "1" in table
	private static int ROLL_MAXSCOPE = 10;
	private static int PINS =0; 
	private static int BONUS =1;
	private static int SCORE =2;
	private static int STRIKE =3; 
	private static int SPARE =1;
	private static int FIRST_ROLL =1;
	private static int SECOND_ROLL =2;
	private static int THIRD_ROLL =3;
	
	private int rollNb, frameNb, rollInFrame;
	private int rolls[][];// rolls[rollNb][0-pins,1-bonus,2-score]; rollNb <1,...,MAXROLLS-1[=23]>
	private String comments; ////comments=""; coments is additionaly a flag - is there a Exception or Not. It check prevalidation();
	

	public BowlingGame(){
		rollNb=0; 
		frameNb=1;  
		rollInFrame=0;
		comments="";
		rolls= new int[MAXROLLS][3]; // rolls table
		rolls[0][PINS]=0;
		rolls[0][SCORE]=0;
		rolls[0][BONUS]=0;

		for(int a=1;a<MAXROLLS;a++){ 
			rolls[a][PINS]=0; 
			rolls[a][SCORE]=0; 
			rolls[a][BONUS]=0; 

		}//for
	}// BowlingGame2()
	
	@Override
	public void roll(int pins) {
		validation(pins); // if out of field or not game structure pins => error
		rollNb++; // next roll number in all game
		rollInFrame++; // next roll in current frame/round
		rolls[rollNb][PINS]=pins; 	// remember pins in rollstable;
		frameNbControl();		// check frame/round number
		setBonuses();
		addPins();					// add current pins;
		addBonuses(); 				// add bonuses to previous rolls in rollstable 
		}
	
	private void frameNbControl() {
		if ((frameNb)!=MAXFRAME){ 
			if (rollInFrame==THIRD_ROLL){ // only 2 rolls in frame. When is third roll => next frame/round. Except extra roll in last frame (frameNb==MAXFRAME)
				frameNb++;      // new frame/round number
				rollInFrame=FIRST_ROLL; // if new frame it is first roll;
			}
		}
		if ((rolls[rollNb][PINS]==ROLL_MAXSCOPE)&&(rolls[rollNb-1][BONUS]==STRIKE)){ // STRIKE in previous roll
			if (((frameNb)<(MAXFRAME))){ // MAXFRAME==11?
				frameNb++;
				rollInFrame=FIRST_ROLL;	
		
			}

		}
	}// frameNbControl(int pins)
	
	private void setBonuses(){
		//in last frame if STRIKE in second round => not bonus
		if ((rolls[rollNb][PINS] == ROLL_MAXSCOPE)&&(rollInFrame==FIRST_ROLL)) { 		// when in first frame if STRIKE 
			rolls[rollNb][BONUS]=STRIKE;
		}
		// SPARE only when it is second roll in frame/round (last frame too)
		if (((rolls[rollNb-1][PINS]+ rolls[rollNb][PINS]) == ROLL_MAXSCOPE)&&(rollInFrame==SECOND_ROLL)) {
			rolls[rollNb][BONUS]=SPARE;
		}
	}

	private void addPins() {
		
		if ((frameNb)<(MAXFRAME)) {
			rolls[rollNb][SCORE]=rolls[rollNb-1][SCORE];
			rolls[rollNb][SCORE]+=rolls[rollNb][PINS];
		}
		if ((frameNb)==(MAXFRAME)) {
				rolls[rollNb][SCORE]=rolls[rollNb-1][SCORE];
				rolls[rollNb][SCORE]+=rolls[rollNb][PINS];

			// corrections in last frame
			if ((rollInFrame == SECOND_ROLL)&&(rolls[(rollNb-1)][BONUS]==STRIKE)){
			// strike in last frame - not add new pins. Add only bonuses
				rolls[rollNb][SCORE]=rolls[rollNb-1][SCORE];
			}
			if ((rollInFrame == THIRD_ROLL)&&(rolls[(rollNb-1)][BONUS]==SPARE)){
			// spare in last frame - not add new pins. Add only bonuses
				rolls[rollNb][SCORE]=rolls[rollNb-1][SCORE];
			}
			if ((rollInFrame == THIRD_ROLL)&&(rolls[(rollNb-2)][BONUS]==STRIKE)){
			// strike in last frame - not add new pins. Add only bonuses
				rolls[rollNb][SCORE]=rolls[rollNb-1][SCORE];
			}
		}	
	}

	private void addBonuses(){ 
		
		if (rollNb==1) {
			return;
		} // if first roll there isn't bonuses
		if ((rolls[(rollNb-1)][BONUS]==STRIKE)){
			rolls[rollNb][SCORE]+=(rolls[rollNb][PINS]);
		}		
		// if STRIKE in n-2 [second bonus] 
		if ((rolls[(rollNb-2)][BONUS]==STRIKE)){
			rolls[rollNb][SCORE]+=(rolls[rollNb][PINS]);
		}

		if ((rolls[rollNb-1][BONUS]==SPARE)&&((rollNb-1)>0)){
			rolls[rollNb][SCORE]+=rolls[rollNb][PINS];
		}
	}
	
	/*Validation with Exception when problems with pins */
	private void validation(int pins) {
	try{

		prevalidation(pins); //comments=""; coments is additionaly a flag - is there a Exception or Not. It check prevalidation(); 
		if(!isOkPrevalidation(pins))
			throw new IllegalArgumentException();
		}
		catch (IllegalArgumentException e){
			throw new IllegalArgumentException(comments);// 
		}
	}
	/* Validation without Exceptions probelms with pins */
	public boolean isOkPrevalidation(int pins){
		comments="PrevalidationOK"; //coments is additionaly a flag - is there a accepted pins or not. It check prevalidation; 
		prevalidation(pins); // prevalidation() is changing commnets when is validation problem
		return comments.equals("PrevalidationOK"); // return boolean, true when not changing in comments
	}
	
	/* Checking problem with pins in game status*/
	private void prevalidation(int pins) {

		validationRollOutOfScope(pins); // pins validation <0,..,ROLL_MAXSCOPE>
		if (rollInFrame == FIRST_ROLL){ // validation when started roll after FIRST_ROLL. It is second roll in frame/round
			validationSecoundRollPinsInFrame(pins); // first + second roll in frame must be in <0,..,ROLL_MAXSCOPE-first_roll> except strike		
		}
		if ((rollInFrame>=SECOND_ROLL)){// when started third roll in frame/round; Last round and extra roll  
			validationRollAfterGameOver();
		}
	}
	
	private void validationRollOutOfScope(int pins2) {
	// Exception when pins in first roll in frame/round is out of <0,...,10>
			if ((pins2<0)||(pins2>ROLL_MAXSCOPE)){ 
				comments="Pins must be from 0 to 10";
			}	
	}
	
	private void validationSecoundRollPinsInFrame(int pins2) {
	// Second roll in frame/round; Exception when pins in second roll is bigger than "10-pins_in_first_roll".
			if ((rolls[rollNb][PINS]+pins2>ROLL_MAXSCOPE)&&(rolls[rollNb][PINS]!=ROLL_MAXSCOPE)){ 
				comments="Sum of pins must be from 0 to 10 in two rolls in one frame/round .";
			}
	}


	private void validationRollAfterGameOver() {
	// Rolls after the end of the game validation 
		if ((frameNb>=(MAXFRAME))){//&&(rollInFrame>THIRD_ROLL)){ //never woul be rollInFrame>3 in last frame
			if((rollInFrame >= THIRD_ROLL)){
					comments="Roll after the end of the game!!!";
				}
			//if((rollInFrame == SECOND_ROLL)){// if no STRIKE (in first roll) or SPARE in last frame => no extra roll
			if ((rolls[rollNb-1][BONUS]!=STRIKE)&&(rolls[rollNb][BONUS]!=SPARE)){
				comments="Roll after the end of the game!!!";
				}
			}
		}// validationRollAfterGameOver

	@Override
	public int calculateScore() {
		return rolls[rollNb][SCORE];	
		}//calculateScore()

	// Info of game status
	public int getRollNb() {
		return rollNb;	
		}
	public int getFrameNb() {
		return frameNb;	
		}
	public int getRollInFrame() {
		return rollInFrame;	
		}
	public String getComments() {
		return comments;	
		}

}// class BowlingGame
