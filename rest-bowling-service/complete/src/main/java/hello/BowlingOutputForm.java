package hello;

import org.springframework.stereotype.Component;
import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Entity;
//@Entity

//@Table(name = "bowlingOutputForm")
// Data schema for REST output
@Component("bowlingOutputForm") 
public class BowlingOutputForm{
	//@NotNull    
	private int playerId;
	private String name; 
	private int addedPins;
	//@NotNull    
	private int score;
	private int rollNb;
	private int frameNb;
	private int rollInFrame;
	private String comments; 
 
	public BowlingOutputForm(){
	playerId=0;
	name="No Name"; 
	addedPins=0;
	score=0;	
	}

	public BowlingOutputForm(int playerId, String name, int addedPins, int score){
	this.playerId=playerId;
	this.name=name; 
	this.addedPins=addedPins;
	this.score=score;
	}

	public int getPlayerId() {
        return playerId;
    }
	public String getName() {
        return name;
    }
    public int getAddedPins() {
        return addedPins;
    }
    public int getScore() {
        return score;
    }
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
	public void setPlayerId(int playerId) {
        this.playerId=playerId;
    }
	public void setName(String name) {
         this.name=name;
    }
    public void setAddedPins(int addedPins) {
        this.addedPins=addedPins;
    }
    public void setScore(int score) {
        this.score=score;
    }
    public void setRollNb(int rollNb) {
        this.rollNb=rollNb;
    }
    public void setFrameNb(int frameNb) {
        this.frameNb=frameNb;
    }
    public void setRollInFrame(int rollInFrame) {
        this.rollInFrame=rollInFrame;
    }
		public void setComments(String comments) {
         this.comments=comments;
    }
}
