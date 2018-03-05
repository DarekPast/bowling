package hello;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import hello.BowlingGame;
//import org.springframework.http.ResponseEntity;
//import io.swagger.annotations.ApiModelProperty;
//import javax.persistence.*;


//@Service("bowlingService") 
//@Component("bowlingService") 
public class BowlingService {
	static int ROLL_MAXSCOPE = 10;
	static int MAX_ROLL_IN_GAME = 22; // Max roll in game is 21 but table is started from 1 not from 0;
	private int playerId;  // id Game or long
	private String name;
	private int idRoll;
	private int score;
	//@Autowired	//??
	private BowlingGame bowlingGame;
	private int roll[];

	public BowlingService() {
        playerId =0;
		score=0;
		name = "Error: BowlingG basic constructor";
		score= 0;
		roll = new int[MAX_ROLL_IN_GAME];
		idRoll = 0; 
		//@Autowired		
		bowlingGame = new BowlingGame();
		
    }
    
	public BowlingService(int playerId, String name) {
		this.playerId = playerId;
		this.name = name;
		score = 0;
		roll = new int[MAX_ROLL_IN_GAME];
		idRoll = 0;
//		@Autowired
		bowlingGame = new BowlingGame();
    }

   
	public long getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }
	public int getIdRoll(){
		return idRoll;
	}	
		
	public int getScore(){
		return score;
	}	
	public BowlingGame getBowlingGame(){
		return bowlingGame;
	}	

	public void setId(int playerId) {
 		this.playerId=playerId;       
	}

	public void addPins(BowlingFormForRoll pins) {
		roll[idRoll+1]+=pins.getPins();
		bowlingGame.roll(pins.getPins());
		score=bowlingGame.calculateScore();
		idRoll++;        
	}
	public void addPinsint(int pins) {
		roll[idRoll+1]+=pins;
		bowlingGame.roll(pins);
		score=bowlingGame.calculateScore();
		idRoll++; 
	}

	public void setName(String name) {
		this.name=name;       
	}

}
