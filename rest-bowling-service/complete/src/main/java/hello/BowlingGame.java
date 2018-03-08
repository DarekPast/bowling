package hello;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Override;

@Component("bowlingGame") 
public class BowlingGame implements Bowling {
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
	
	private int rollNb, FrameNb, rollInFrame;
	private int rolls[][];// rolls[rollNb][0-pins,1-bonus,2-score]; rollNb <1,...,MAXROLLS-1[=23]>
	private String comments;
	

	public BowlingGame(){
		rollNb=0; 
		FrameNb=1;  
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
		//System.out.print("1. Frame="+FrameNb+"; rollInFrame="+rollInFrame);
		if ((FrameNb)!=MAXFRAME-1){ //if ((FrameNb)!=MAXFRAME){
			if (rollInFrame==THIRD_ROLL){ // only 2 rolls in frame. When is third roll => next frame/round. Except extra roll in last frame (FrameNb==MAXFRAME)
				FrameNb++;      // new frame/round
				rollInFrame=FIRST_ROLL; // in new frame it is first roll;
			}
		}
		if ((rolls[rollNb][PINS]==ROLL_MAXSCOPE)&&(rolls[rollNb-1][BONUS]==STRIKE)){ // STRIKE in previous roll
			if (((FrameNb)<(MAXFRAME-1))){ // MAXFRAME==11?

				//if((FrameNb+1)<11){
				FrameNb++;
				rollInFrame=FIRST_ROLL;	
				//} //?????
		
			}
			if ((FrameNb==(MAXFRAME-1))&&(rollInFrame==FIRST_ROLL)){ // when last frame/round => not new frame
			//	rollInFrame=SECOND_ROLL;			
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
//		rolls[rollNb][SCORE]=rolls[rollNb-1][SCORE];
		
		if ((FrameNb)<(MAXFRAME-1)) {
			rolls[rollNb][SCORE]=rolls[rollNb-1][SCORE];
			rolls[rollNb][SCORE]+=rolls[rollNb][PINS];
		}
		if ((FrameNb)==(MAXFRAME-1)) {

			if (rollInFrame == FIRST_ROLL){
				rolls[rollNb][SCORE]=rolls[rollNb-1][SCORE];
				rolls[rollNb][SCORE]+=rolls[rollNb][PINS];
			}
			if ((rolls[(rollNb-1)][BONUS]!=STRIKE)&&(rolls[(rollNb-1)][BONUS]!=SPARE)){
				rolls[rollNb][SCORE]=rolls[rollNb-1][SCORE];
				rolls[rollNb][SCORE]+=rolls[rollNb][PINS];
			}

			if ((rollInFrame == SECOND_ROLL)&&(rolls[(rollNb-1)][BONUS]==STRIKE)){
			// strike in last frame not add new pins. Add only bonuses
				rolls[rollNb][SCORE]=rolls[rollNb-1][SCORE];
			}
			//else{
				//rolls[rollNb][SCORE]=rolls[rollNb-1][SCORE];
			//	rolls[rollNb][SCORE]+=rolls[rollNb][PINS];
			//}
			if ((rollInFrame == THIRD_ROLL)&&(rolls[(rollNb-1)][BONUS]==SPARE)){

				rolls[rollNb][SCORE]=rolls[rollNb-1][SCORE];
			}
			if ((rollInFrame == THIRD_ROLL)&&(rolls[(rollNb-2)][BONUS]==STRIKE)){

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
	
	
	private void validation(int pins) {
		try{
			//comments=""; 
			prevalidation(pins);
			if(!isOkPrevalidation(pins))
			throw new IllegalArgumentException();
			
		}
		catch (IllegalArgumentException e){

			throw new IllegalArgumentException(comments);// 
		}
	}
	
	public boolean isOkPrevalidation(int pins){
		comments="PrevalidationOK"; //
		prevalidation(pins); // prevalidation() is changing commnets when is validation problem
		
		if(comments.equals("PrevalidationOK")){
			return true;
		}
		else{
			return false;
		}
		//return comments.equals("PrevalidationOK"); // return boolean, true when not changing in comments
	}
	
		private void prevalidation(int pins) {

		validationRollOutOfScope(pins); // pins validation <0,..,ROLL_MAXSCOPE>
		if (rollInFrame == FIRST_ROLL){ // when started second roll in frame/round
			// except STRIKE in last frame (FrameNb==MAXFRAME)
			validationSecoundRollPinsInFrame(pins); // first + second roll in frame must be in <0,..,ROLL_MAXSCOPE-first_roll> except strike		
		}

		if ((rollInFrame==SECOND_ROLL)&&(FrameNb>=MAXFRAME)){ // // when started third roll in frame/round; Last round and extra roll  
			validationExtraRollInLastFrame(pins); // 
			validationTwoRollPinsInLastFrame(pins);
		}
		
		if ((rollInFrame>=SECOND_ROLL)||(FrameNb>=MAXFRAME)){ // when started third roll in frame/round; Last round and extra roll  
			validationRollAfterGameOver(pins);
		}
	}
	
//	(rolls[rollNb][PINS]==ROLL_MAXSCOPE)&&(rolls[rollNb-1][BONUS]==STRIKE)
	private void validationRollOutOfScope(int pins2) {

			if ((pins2<0)||(pins2>ROLL_MAXSCOPE)){ // Exception when pins in first roll in frame/round is out of <0,...,10>
				comments="Pins must be from 0 to 10";

			}	

	}
	
	private void validationSecoundRollPinsInFrame(int pins2) {
		//try{ //validation:  when started second roll in(rolls[rollNb][PINS]==ROLL_MAXSCOPE)&&(rolls[rollNb-1][BONUS]==STRIKE) frame/round
			
			if ((rolls[rollNb][PINS]+pins2>ROLL_MAXSCOPE)&&(rolls[rollNb][PINS]!=ROLL_MAXSCOPE)){ // Second roll in frame/round; Exception when pins in second roll is bigger than "10-pins_in_first_roll".
				comments="Sum of pins must be from 0 to 10 in two rolls in one frame/round .";
				//throw new IllegalArgumentException(comments);//("BowlingGame: void roll(int pins). Sum of pins in two rolls must be from 0 to 10 -> outside the field of input data.\n");
			}
		//}
		//catch (IllegalArgumentException e){

		//	throw new IllegalArgumentException("Sum of pins must be from 0 to 10 in two rolls in one frame/round .\n");// 
		//}
	}


	private void validationExtraRollInLastFrame(int pins2) {
		// Extra roll in last frame
		//try{ 
			// if no STRIKE (in first roll) or SPARE in last frame => no extra roll
			if ((rolls[rollNb-1][PINS]==ROLL_MAXSCOPE)||((rolls[rollNb-1][PINS]+rolls[rollNb][PINS])==ROLL_MAXSCOPE)){
				//do nothing is OK
			}	
			else{
				comments="There is no ekstra roll (third) in last frame/round!!!";
				//throw new IllegalArgumentException(comments);
			}
			
		//}		
		//catch (IllegalArgumentException e){

		//throw new IllegalArgumentException("There is no ekstra roll (third) in last frame/round!!!");// 
		//}
}
	
	private void validationTwoRollPinsInLastFrame(int pins2) {
	

			if (rolls[rollNb-1][PINS]==ROLL_MAXSCOPE){ // when STRIKE in FIRST
				if ((rolls[rollNb][PINS]!=ROLL_MAXSCOPE)&&((rolls[rollNb][PINS]+pins2)>ROLL_MAXSCOPE)) {
					comments="In last frame/round sum of pins in two last rolls (second and third) must be from 0 to 10 except STRIKE in second roll \n";					

					}
			}

		
}

private void validationRollAfterGameOver(int pins2) {
	// after the end of the game

//		if ((FrameNb==(MAXFRAME-1))){//&&(rollInFrame>THIRD_ROLL)){ //never woul be rollInFrame>3 in last frame
			if((rollInFrame >= THIRD_ROLL)){
					comments="Roll after the end of the game!!!";
				}
			if((rollInFrame == SECOND_ROLL)){// if no STRIKE (in first roll) or SPARE in last frame => no extra roll
//			if (((rolls[rollNb-1][PINS]+rolls[rollNb][PINS])==ROLL_MAXSCOPE)||(rolls[rollNb-1][PINS]==ROLL_MAXSCOPE)){
				if (rolls[rollNb-1][PINS]!=ROLL_MAXSCOPE){
//					comments="Roll after the end of the game!!!";
					}
				}	

//			}
				
		}// validationRollAfterGameOver
		
	
//}



	@Override
	public int calculateScore() {
		return rolls[rollNb][SCORE];	
		}//calculateScore()

	public int getRollNb() {
		return rollNb;	
		}
	public int getFrameNb() {
		return FrameNb;	
		}
	public int getRollInFrame() {
		return rollInFrame;	
		}
	public String getComments() {
		return comments;	
		}

}// class BowlingGame
