package hello;

import org.springframework.stereotype.Component;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

@Component("bowlingInputForm") 
public class BowlingInputForm{
	@NotNull
	@Size(min=1,max=21) // 21 - Max rolls in frame 	
    private int playerId;
	@NotNull
	@Size(min=0,max=10)	
	private int pins;

public void setPlayerId(int playerId) {
        this.playerId=playerId;
    }
    public void setPins(int pins) {
        this.pins=pins;
    }  

	public int getPlayerId() {
        return playerId;
    }
    public int getPins() {
        return pins;
    }

}
