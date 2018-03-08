package hello;

import org.springframework.stereotype.Component;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Entity;
//import java.io.Serializable;
//import javax.persistence.*;

//@Entity
//@Table(name = "BowlingFormGETForRoll")
@Component("bowlingInputForm") 
public class BowlingInputForm{
	@NotNull
	@Size(min=1,max=11) // 21 - Max rolls in frame 	
    private int playerId;
	@NotNull
	//@Size(min=0,max=10)	
	private int pins;
  
	public int getPlayerId() {
        return playerId;
    }
    public int getPins() {
        return pins;
    }

}
