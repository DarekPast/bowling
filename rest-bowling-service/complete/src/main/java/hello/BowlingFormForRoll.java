package hello;

import org.springframework.stereotype.Component;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Entity;
//import java.io.Serializable;
//import javax.persistence.*;

//@Entity
//@Table(name = "BowlingFormGETForRoll")
@Component("BowlingFormForRoll") 
public class BowlingFormForRoll{
	@NotNull
	@Size(min=1,max=21) // 21 - Max rolls in frame 	
    private long playerId;
	@NotNull
	@Size(min=0,max=10)	
	private int pins;
  
	public long getPlayerId() {
        return playerId;
    }
    public int getPins() {
        return pins;
    }

}
