package main.java;
//2018.02.15

public class BowlingGame {
static int MAXGAMES = 10;
static int FRAME_MAXSCOPE = 10;
static int FIRST_ROLL = 1;
static int SECOND_ROLL =2;
static int THIRD_ROLL =3;
static int STRIKE_ROLLS_FRAME_BONUS =2 ; // bonuses from 2 next rolls in 1  or 2 next frames (if strike in next frame) 
static int SPARE_ROLLS_FRAME_BONUS =1 ;
boolean createnewframe,lastround;
int bonuses; //  bonuses counter (in strike =2; spare=1; nothing =0);
int dscore; // the current round score; only sum points from the current round ( with bonuses)
int pins_in_first_roll, pins_in_second_roll; // first and second roll in frame <0,..,10>
int frame_nb, roll_nb; // number of frame/round; number of roll in current frame (and roll from bonuses)
BowlingGame previousframe; // previous frame

// basic constructor	
	public BowlingGame() {
		createnewframe=false;
		lastround=false;
		bonuses=0;
		dscore=0;
		pins_in_first_roll=0;
		pins_in_second_roll=0;
		frame_nb=1; // starting with object for first frame
		roll_nb=0;
		previousframe=null;
	}
	// copier constructor
	public BowlingGame(BowlingGame e ) {
		this.createnewframe=e.createnewframe;
		this.lastround=false;
		this.bonuses=e.bonuses;
		this.dscore=e.dscore; // delta score game = score in round
		this.pins_in_first_roll=0; // importent only in curent "n" round
		this.pins_in_second_roll=0; //// importent only in curent "n" round
		this.frame_nb=e.frame_nb;
		this.roll_nb=e.roll_nb; 
		this.previousframe=e.previousframe;
	}
	
// Bowling methods
public void roll(int pins) {
	
	
		// Build new object "n" 	[ old "n"=>new "n-1"	]	
	if (createnewframe) { 		// create "n-1" round/frame
		createnewframe=false; // 
		previousframe = new BowlingGame(this); // copy "n" round as "n-1" round
		// reset current "n" frame/round  
		pins_in_first_roll=0;
		pins_in_second_roll=0;
		bonuses=0; 
		dscore=0;
		roll_nb=0;
		frame_nb++; //  upgrading rounds counter frame_nb = "n" round
	} //if (createnewfram) 
	
	
	
	try{ // this place is better; when "pins" is out of scope, then it changing pins=0;
		int bladpins=pins;
		Boolean isException=false;
		String komunikat="";
		if ((pins<0)||(pins>FRAME_MAXSCOPE)){ // Exception when pins in first roll is out of <0,...,10>
			if (pins<0)
				pins=0;
			if (pins>FRAME_MAXSCOPE)
				pins=0;
			isException=true;
			komunikat+=("BowlingGame: void roll(int pins). Pins must be from 0 to 10 -> outside the field of input data.\n The value of the given pins="+bladpins+" has been changed to the value ="+pins+"\n");
		}
		if ((pins_in_first_roll+pins>FRAME_MAXSCOPE)&&(roll_nb==FIRST_ROLL)){ // Second roll; Exception when pins in second roll is bigger than "10-pins_in_first_roll".
			if ((lastround)&&(pins_in_first_roll==FRAME_MAXSCOPE)){
				// OK. STRIKE in first roll
			}
			else{// (lastround)&&(pins_in_first_roll==FRAME_MAXSCOPE)&&(pins_in_first_roll+pins>FRAME_MAXSCOPE)
				pins=0;//FRAME_MAXSCOPE-pins_in_first_roll;
				isException=true;
				komunikat+=("BowlingGame: void roll(int pins). Sum of pins in two rolls must be from 0 to 10 -> outside the field of input data.\n The value of the given pins="+bladpins+" has been changed to the value ="+pins+"\n");
			}//(lastround)&&(pins_in_first_roll==FRAME_MAXSCOPE)
		}//if (pins_in_first_roll+pins>FRAME_MAXSCOPE)&&(roll_nb==FIRST_ROLL)
		
	
		if ((lastround)&&(roll_nb==SECOND_ROLL)){//roll == Third 
			
			
			if((pins_in_first_roll == FRAME_MAXSCOPE)||((pins_in_first_roll+pins_in_second_roll)==FRAME_MAXSCOPE)){	
				// OK. SECOND round is SPARE or STRIKE	=> extra roll;
				
				if ((pins_in_second_roll!=FRAME_MAXSCOPE)&&(pins_in_first_roll==FRAME_MAXSCOPE)){ // 
					if((pins_in_second_roll+pins>FRAME_MAXSCOPE)&&((pins_in_first_roll+pins_in_second_roll)!=FRAME_MAXSCOPE)){ // Exception when pins in third roll is bigger than "10-pins_in_second_roll".
						// 
						pins=0;
						isException=true;
						komunikat+=("BowlingGame: void roll(int pins). Without STRIKE in second roll in last frame/round sum of pins in two last rolls must be from 0 to 10 -> outside the field of input data.\n The value of the given pins="+bladpins+" has been changed to the value ="+pins+"\n");
		 	
					}
					else{
						// pins in third roll is less than "10-pins_in_second_rolls".						
					}
				}//if((pins_in_second_roll!=FRAME_MAXSCOPE)&&(pins_in_first_roll==FRAME_MAXSCOPE)) => only STRIKE in first roll
				else{
					// if (pins_in_second_roll==FRAME_MAXSCOPE) => OK (third roll from 0 to 10)
					// or ((pins_in_first_roll+pins_in_second_roll)==FRAME_MAXSCOPE) = OK (third roll from 0 to 10)
					
				}

			
			}
			else{// pins_in_first_roll == FRAME_MAXSCOPE or (pins_in_first_roll+pins_in_first_roll)==FRAME_MAXSCOPE else ERROR
				pins=0;
				isException=true;
				komunikat+=("BowlingGame: void roll(int pins). There is no ekstra roll (third) in last frame/round!!!\n");
				
			}// pins_in_first_roll == FRAME_MAXSCOPE)||((pins_in_first_roll+pins_in_second_roll)==FRAME_MAXSCOPE))
		}//if ((lastround)&&(roll_nb==SECOND_ROLL)){//roll == Third 
		
		if (isException){// sum of Exceptions
			throw new Exception(komunikat);
		} 
	}//try
	catch (Exception e){
		
		System.out.println(e);
		
	}
	finally{
		
	
// "bonuses" action only in "n-1" or "n-2" frame/round/object		
		if ((bonuses>0)&&(!lastround)) { 
			dscore+=pins;
			bonuses--;
		}//(this.bonuses>0)
				
// actions in all frame/round
	if (previousframe!=null) {
		if (previousframe.bonuses>0 ) {
			previousframe.roll(pins);  // do the "bonuses" action in "n-1" and/or "n-2" objects
		}
	} //(previousframe!=null)		

// next roll in round (current round or round with "bonuses" flag) 	
	roll_nb++; // when it is current frame roll_nb++ means there is FIRST_ROLL = 1 or SECOND_ROLL = 2 ; when it is n-1,..,1 frame/object there is roll_nb > SECOND_ROLL; roll_nb = THIRD_ROLL
		
// actions in "n" round (current round)	because roll_nb == FIRST_ROLL or roll_nb == SECOND_ROLL
	if (roll_nb == FIRST_ROLL) {  //if (roll_nb == 1)
		dscore+=pins; // add pins knocked down to delta scope in current round
		pins_in_first_roll = pins; // remember first roll
	}	
	if (roll_nb == SECOND_ROLL) {  //if (roll_nb = 2)
		dscore+=pins; // add pins knocked down to delta scope in current round (secound roll)
		createnewframe=true;
		pins_in_second_roll = pins; // remember second roll
	}			
	if ((roll_nb == SECOND_ROLL)&&((pins+pins_in_first_roll) == FRAME_MAXSCOPE)) { //SPARE
		bonuses = SPARE_ROLLS_FRAME_BONUS;	// remember bonuses from 1 next roll in this object ()		
	}
	if ((roll_nb == FIRST_ROLL)&&(pins == FRAME_MAXSCOPE)) {// STRIKE
		bonuses = STRIKE_ROLLS_FRAME_BONUS; // remember bonuses from 2 next rolls in 1 (if strike in next frame) or 2 next frames 
		createnewframe=true;		// except MAXGAME frame/round
		if (frame_nb!= MAXGAMES) {
			roll_nb=SECOND_ROLL; //Except last round. If Strike there is no roll action in current round (like when roll_nb>=SECOND_ROLL)
		}
	}

// Extra control in the last round. Correcting action.
	if (frame_nb== MAXGAMES) {
		lastround=true;
		createnewframe=false;		//No new rounds/frames  
		
		if (roll_nb == THIRD_ROLL) {  //if (roll_nb = 3)
			dscore=dscore+pins; // add pins knocked down to delta scope in extra roll
		}//(roll_nb == THIRD_ROLL)	
	}//(frame_nb== MAXGAMES)		
	
	}//finally
	
} // end roll(int)


	public int calculateScore() {
		if (previousframe!= null){
			return dscore+previousframe.calculateScore();	// sum from previous rounds	
		}
		else {
			return dscore; // when it is 1-st round;
		}
	}//calculateScore()
}// Bowling class