public class BowlingGame {
static int MAXGAMES = 10; // 10+1 - because table is used from 1 not from 0 
static int FRAME_MAXSCOPE = 10;
static int ROLL_MAXSCOPE = 10;
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
		roll_nb=0; // roll number in frame [0-first,1-second]
		previousframe=null;
	}
	// copier constructor
	public BowlingGame(BowlingGame e ) {
		this.createnewframe=e.createnewframe;
		this.lastround=false;
		this.bonuses=e.bonuses;
		this.dscore=e.dscore; // delta score game = score in round
		this.pins_in_first_roll=0; // important only in current "n" round
		this.pins_in_second_roll=0; //// important only in current "n" round
		this.frame_nb=e.frame_nb;
		this.roll_nb=e.roll_nb; 
		this.previousframe=e.previousframe;
	}
	
// Bowling methods
public void roll(int pins) {
	
	// Build new object "n" 	[ old "n"=>new "n-1"	]	
	buildNewFrame();
	// input pins validation
	validation( pins);

	// "bonuses" action only in "n-1" or "n-2" frame/round/object		
	addBonusesInOldFrame(pins);
				
	// actions in all frame/round
	callActionInPreviousFrame(pins);	

	// next roll in round (current round or round with "bonuses" flag) 	
	roll_nb++; // when it is current frame roll_nb++ means there is FIRST_ROLL = 1 or SECOND_ROLL = 2 ; when it is n-1,..,1 frame/object there is roll_nb > SECOND_ROLL; roll_nb = THIRD_ROLL
		
	// actions in "n" round (current round)	
	addPinsInCurrentFrame(pins);
	setBonuses(pins);

	// Extra control only in the last round. Correcting action.
	lastFrameCorectingAction(pins);
		
} // end roll(int)

private void buildNewFrame(){
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
}



private void validation(int pins2) {
	validationRollOutOfScope(pins2); // pins validation <0,..,ROLL_MAXSCOPE>
	if (roll_nb == 1){ // when started second roll in frame/round
		// except STRIKE in last frame (frame_nb==MAXFRAME)
		validationSecoundRollPinsInFrame(pins2); // first + second roll in frame must be in <0,..,ROLL_MAXSCOPE-first_roll> except strike		
	}
	if ((lastround)){ //  when started third roll in last frame/round;  
		validationExtraRollInLastFrame(pins2);  
		validationTwoRollPinsInLastFrame(pins2);
		validationRollAfterGameOver(pins2);
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
		
		if ((pins_in_first_roll+pins2>FRAME_MAXSCOPE)&&(pins_in_first_roll!=ROLL_MAXSCOPE)){ // Second roll in frame/round; Exception when pins in second roll is bigger than "10-pins_in_first_roll". // except STRIKE in last frame (frame_nb==MAXFRAME)
			throw new IllegalArgumentException();//("BowlingGame: void roll(int pins). Sum of pins in two rolls must be from 0 to 10 -> outside the field of input data.\n");
		}
	}
	catch (IllegalArgumentException e){
		//System.out.println(e);
		throw new IllegalArgumentException("Sum of pins must be from 0 to 10 in two rolls in one frame/round .\n");// 
	}
}


private void validationExtraRollInLastFrame(int pins2) {
	// Extra roll in last frame validation
	try{ 
		// if no STRIKE (in first roll) or SPARE in last frame => no extra roll
		if ((lastround)&&(roll_nb==SECOND_ROLL)){//roll_nb++ == Third 
			if((pins_in_first_roll == ROLL_MAXSCOPE)||((pins_in_first_roll+pins_in_second_roll)==FRAME_MAXSCOPE)){	
				// OK. SECOND round is SPARE or STRIKE	=> extra roll;
			}
			else{
				throw new IllegalArgumentException();//"When no STRIKE or SPARE there is no ekstra roll (third) in last frame/round!!!");
			}
		}
		
	}		
	catch (IllegalArgumentException e){
	// System.out.println(e);
	
		throw new IllegalArgumentException("When no STRIKE or SPARE there is no ekstra roll (third) in last frame/round!!!");
	
}
}
private void validationRollAfterGameOver(int pins2) {
	// after the end of the game
	try{ 
		// if no STRIKE (in first roll) or SPARE in last frame => no extra roll
		if (roll_nb>SECOND_ROLL){//roll_nb++ == Third 
			throw new IllegalArgumentException();//"When no STRIKE or SPARE there is no ekstra roll (third) in last frame/round!!!");
			
		}
		
	}		
	catch (IllegalArgumentException e){
	// System.out.println(e);
	
		throw new IllegalArgumentException("Roll after the end of the game!!!");
	
}
}

private void validationTwoRollPinsInLastFrame(int pins2) {

	try{ 
		// Last Frame: if (roll in second roll in last frame != ROLL_MAXSCOPE) sum second and third must be <= ROLL_MAXSCOPE
		if ((lastround)&&(pins_in_first_roll == ROLL_MAXSCOPE)){//roll_nb++ == Third 
			if((pins_in_second_roll != ROLL_MAXSCOPE)&&((pins_in_second_roll+pins2)>FRAME_MAXSCOPE)){	
				
				throw new IllegalArgumentException();
			}
		}
	}		
	catch (IllegalArgumentException e){
	// System.out.println(e);
		throw new IllegalArgumentException("In last frame/round sum of pins in two last rolls (second and third) must be from 0 to 10 except STRIKE in second roll \n");// 
	}
}



private void addBonusesInOldFrame(int pins2){
	// "bonuses" action only in "n-1" or "n-2" frame/round/object	
	if ((bonuses>0)&&(!lastround)) { 
		dscore+=pins2;
		bonuses--;
	}//(this.bonuses>0)
}

private void callActionInPreviousFrame(int pins2){
	// call actions in roll() method in all previous frame/round object
		if (previousframe!=null) {
			if (previousframe.bonuses>0 ) {
				previousframe.roll(pins2);  // do the "bonuses" action in "n-1" and/or "n-2" objects
			}
		} //(previousframe!=null)	
}

private void addPinsInCurrentFrame(int pins2){
	// add pins and set flag "createnewframe"
	if (roll_nb == FIRST_ROLL) {  //if (roll_nb == 1)
		dscore+=pins2; // add pins knocked down to delta scope in current round
		pins_in_first_roll = pins2; // remember first roll
	}	
	if (roll_nb == SECOND_ROLL) {  //if (roll_nb = 2)
		dscore+=pins2; // add pins knocked down to delta scope in current round (secound roll)
		createnewframe=true;
		pins_in_second_roll = pins2; // remember second roll
	}			
}
private void setBonuses(int pins2){
	
	if ((roll_nb == SECOND_ROLL)&&((pins2+pins_in_first_roll) == FRAME_MAXSCOPE)) { //SPARE
		bonuses = SPARE_ROLLS_FRAME_BONUS;	// remember bonuses from 1 next roll in this object ()		
	}
	if ((roll_nb == FIRST_ROLL)&&(pins2 == FRAME_MAXSCOPE)) {// STRIKE
		bonuses = STRIKE_ROLLS_FRAME_BONUS; // In this object  remember bonuses from 2 next rolls in 1 (if strike in next frame) or 2 next frames 
		createnewframe=true;		// except MAXGAME frame/round
		if (frame_nb!= MAXGAMES) {
			roll_nb=SECOND_ROLL; //Except last round. If Strike there is no roll action in current round (like when roll_nb>=SECOND_ROLL)
		}
	}		

}
private void lastFrameCorectingAction(int pins2){
	// Extra control only in the last round. Correcting action.
		if (frame_nb== MAXGAMES) {
			lastround=true;
			createnewframe=false;		//No new rounds/frames  
			if (roll_nb == THIRD_ROLL) {  //if (roll_nb = 3)
				dscore=dscore+pins2; // add pins knocked down to delta scope in extra roll
			}//(roll_nb == THIRD_ROLL)	
		}//(frame_nb== MAXGAMES)		
}


	public int calculateScore() {
		if (previousframe!= null){
			return dscore+previousframe.calculateScore();	// sum from previous rounds	
		}
		else {
			return dscore; // when it is 1-st round;
		}
	}//calculateScore()
}// BowlingGame class