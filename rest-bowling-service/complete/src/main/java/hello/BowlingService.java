package hello;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import io.swagger.annotations.ApiModelProperty;
//import javax.persistence.*;
import hello.BowlingGame;


//@Entity
@Service("games") 
//@Component("bowlingService") 
public class BowlingService {
	//"The website sport up to MAX_GAME_BAS parallel games"	
	static int MAX_GAME_BASE = 10;	
	static int ROLL_MAXSCOPE = 10;
	static int MAX_ROLL_IN_GAME = 22; // Max roll in game is 21 but table is started from 1 not from 0;
	static int OUT_OF_RANGE =-1;
	static int NOT_ERROR =0;

    private int gameCounter;  // int or long?playerId,
    //The taable of name players"
	private String names[] = new String[MAX_GAME_BASE];
	//"The base of all games"
	private BowlingGame gameBase[] = new BowlingGame[MAX_GAME_BASE];

	//
	public BowlingService() {
       	gameCounter =0;
		names[0] = "No any Name";
		gameBase[0] = null;//new BowlingGame();
		
    }
    
//	public BowlingService(int playerId, String name) {
		//this.super();
// 		this.names[playerId] = name;

//    }

   
	public int getGameCounter() {
        return gameCounter;
    }

    public String getName(int id) {
        return names[id];
    }
		
	public int getScore(int playerId){
    	return gameBase[playerId].calculateScore();
	}	
	public BowlingGame[] getBowlingGame(){
    	return gameBase;
	}	
//	public BowlingGame getBowlingGame(int playerId){
//    	return gameBase[playerId];
//	}

	public void addPins(BowlingInputForm bowlingInputData) {
		gameBase[bowlingInputData.getPlayerId()].roll(bowlingInputData.getPins());		
	}

	public int addNewGame(String name) {
		//validation
		if((gameCounter+1)>MAX_GAME_BASE){
			return OUT_OF_RANGE;	
		}
		//action
		gameBase[gameCounter+1]=new BowlingGame();
		names[gameCounter+1] = name;		
		gameCounter++; // counter is increased only in this place 
		return gameCounter;        
	}
	
	public int delete(int playerId) {
	// ??? Exceptions?
		if ((playerId<=gameCounter)&&(playerId > 0)){
			gameBase[playerId]=null;
			return NOT_ERROR; //       
		}
		else{
			return OUT_OF_RANGE;//Error
		}
	}

	public BowlingOutputForm getBowlingOutputFormData(int playerId){ //for RESTController GET, POST
		BowlingOutputForm bowlingOutputData= new BowlingOutputForm();
		bowlingOutputData.setPlayerId(playerId);
		bowlingOutputData.setName(names[playerId]);
		bowlingOutputData.setAddedPins(0); //not sawed in GameSerwice
		bowlingOutputData.setScore(gameBase[playerId].calculateScore());
		bowlingOutputData.setRollNb(gameBase[playerId].getRollNb());
		bowlingOutputData.setFrameNb(gameBase[playerId].getFrameNb()); 
		bowlingOutputData.setRollInFrame(gameBase[playerId].getRollInFrame()); 
		bowlingOutputData.setComments(gameBase[playerId].getComments()); 	
		return bowlingOutputData;	
	}
	public BowlingOutputForm getBowlingOutputFormData(BowlingInputForm input){	//for PUT
		BowlingOutputForm bowlingOutputData= getBowlingOutputFormData(input.getPlayerId());
		bowlingOutputData.setAddedPins(input.getPins());
		return bowlingOutputData;	
	}
	public boolean isOkPrevalidation(BowlingInputForm inputRest){
		//return false;
		return gameBase[inputRest.getPlayerId()].isOkPrevalidation(inputRest.getPins());

	}




}
