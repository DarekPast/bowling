/**
 * 
 */
package pl.opl.java.scorer;

/**
 * @author DarekPast
 *
 */
public class BowlingGame {
static int MAXGAMES = 11;
static int FRAME_MAXSCOPE = 10;
static int FIRST_ROLL = 1;
static int SECOND_ROLL =2;
static int THIRD_ROLL =3;
static int STRIKE_ROLLS_FRAME_BONUS =2 ; // bonuses from 2 next rolls in 1  or 2 next frames (if strike in next frame) 
static int SPARE_ROLLS_FRAME_BONUS =1 ;
Boolean createnewframe;
Boolean lastround;
int bonuses; //  bonuses counter (in strike =2; spare=1; nothing =0);
int dscore; // the current round score; only sum points from the current round ( with bonuses)
int pins_in_first_roll; // first roll in frame <0,..,10>

int frame_nb, roll_nb; // number of frame/round; number of roll in current frame (and roll from bonuses)
BowlingGame previousframe; // previous frame

// basic constructor	
	BowlingGame() {
		createnewframe=false;
		lastround=false;
		bonuses=0;
		dscore=0;
		frame_nb=1; // starting with object for first frame
		roll_nb=0;
		previousframe=null;

	}
	// copier
	BowlingGame(BowlingGame e ) {

		this.bonuses=e.bonuses;
		this.lastround=false;
		this.createnewframe=e.createnewframe;
		this.dscore=e.dscore; // delta score game = score in round 
		this.frame_nb=e.frame_nb;
		this.roll_nb=e.roll_nb; 
		this.previousframe=e.previousframe;

	}
	
// Bowling methods

	public void roll(int pins) {
		
		// handling Exception (analysis pins) 
		if (!lastround) {
			if ((pins<0)||((pins+pins_in_first_roll)>FRAME_MAXSCOPE)){
				// Exception
			}
		}		
		
		// Build new object "n" 	[ old "n"=>new "n-1"	]	
		if (createnewframe) {
						
		// create "n-1" round/frame
		pins_in_first_roll=0; // 
		createnewframe=false;
		previousframe = new BowlingGame(this); 
		
		// reset current "n" frame/round  
		dscore=0;
		bonuses=0; 
		roll_nb=0;
		frame_nb++; //  upgrading rounds counter 
		} //if (createnewfram) 
		
// action in "n-1" or "n-2" frame/round/object		
		if (bonuses>0) {
			dscore+=pins;
			bonuses--;
		}//(this.bonuses>0)
				
// actions in all frame/round

	if (previousframe!=null) {
		if (previousframe.bonuses>0 ) {
			previousframe.roll(pins);  // do the "bonuses" action in "n-1" and/or "n-2" objects
		}
	} //(previousframe!=null)		

// new roll in round (current round or round with "bonuses" flag) 	
	roll_nb=roll_nb+1; // when it is current frame roll_nb++ there is FIRST_ROLL = 1 or SECOND_ROLL = 2 ; when it is n-1,..,1 frame/object there is roll_nb > SECOND_ROLL; roll_nb = THIRD_ROLL
		
// actions in "n" round (current round)		
	if (roll_nb == FIRST_ROLL) {  //if (roll_nb == 1)
		dscore+=pins; // add pins knocked down to delta scope in current round
		pins_in_first_roll = pins; // remember first roll
	}	
	if (roll_nb == SECOND_ROLL) {  //if (roll_nb = 2)
		dscore=dscore+pins; // add pins knocked down to delta scope in current round (secound roll)
		createnewframe=true;
	}			
	if ((roll_nb == SECOND_ROLL)&&((pins+pins_in_first_roll) == FRAME_MAXSCOPE)) {
		bonuses = SPARE_ROLLS_FRAME_BONUS;	// remember bonuses from 1 next roll in this object ()		
	}
	if ((roll_nb == FIRST_ROLL)&&(pins == FRAME_MAXSCOPE)) {
		bonuses = STRIKE_ROLLS_FRAME_BONUS; // remember bonuses from 2 next rolls in 1 (if strike in next frame) or 2 next frames 
		createnewframe=true;		// except MAXGAME frame/round
		if (!lastround) {
			roll_nb=SECOND_ROLL; //Except last round 
		}
	}

// Extra control in the last round. Correcting actions.
	if (frame_nb== MAXGAMES) {
		lastround=true;
		createnewframe=false;		//No new rounds/frames
		if ( (roll_nb ==SECOND_ROLL) && (bonuses==0) ){
//			System.out.print("GAME OVER. This is "+frame_nb+" round.\n Score="+this.calculateScore()+"\n");
			return;				
		}
		
		if (roll_nb == THIRD_ROLL) {  //if (roll_nb = 3)
			dscore=dscore+pins; // add pins knocked down to delta scope in current round (secound roll)

//			System.out.print("GAME OVER with extra roll. This is "+frame_nb+" round.\n Score="+this.calculateScore()+"\n");
			return;	
		}//(roll_nb == THIRD_ROLL)	
	}//(frame_nb== MAXGAMES)		
		
	} // end roll(int)
	
	public int calculateScore() {
		if (previousframe!= null){
			return dscore+previousframe.calculateScore();			
		}
		else {
			return dscore;
		}
	}
}
