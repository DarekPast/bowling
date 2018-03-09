package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/bowling")
@Api(value="onlinebowlinggame", description="Operations Bowling Games")
public class BowlingController {
	static int OUT_OF_RANGE =-1;
	@Autowired
	private BowlingService bowlingService;// = new BowlingService();

  
 	@ApiOperation(value = "Add new Game",response = BowlingOutputForm.class)
	@RequestMapping(value="/games/add", method=RequestMethod.POST, produces = "application/json")
	public ResponseEntity newPlayer(@RequestParam(value="name", defaultValue= "NoName") String name) {
		//place for validation		
		// place for action
		int playerId= bowlingService.addNewGame(name);
		//place for validation after action
		if (playerId==OUT_OF_RANGE) { // Out of range
			return new ResponseEntity(HttpStatus.FORBIDDEN); // HttpStatus.40x - No Id in base
		}
		// collect the output data		
		BowlingOutputForm bowlingOutputData= bowlingService.getBowlingOutputFormData(playerId);
		bowlingOutputData.setComments("New player game added");
		return new ResponseEntity<BowlingOutputForm>(bowlingOutputData,HttpStatus.CREATED);//CREATED?
}


	@ApiOperation(value = "Info about bowling game with an ID",response = BowlingOutputForm.class)
	@RequestMapping(value="/games/{id}", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity getPlayerInfo(@PathVariable(value="id") int playerId){
		//place for validation	
		if ((playerId > bowlingService.getGameCounter())||(playerId <=0)) { // ??? or BowlingService validation
			return new ResponseEntity(HttpStatus.NOT_FOUND); // or NOT_ACCEPTABLE HttpStatus.40x - No Id in base
		}
		//place for action
		//place for validation after action	
		// collect the output data
		BowlingOutputForm bowlingOutputData= bowlingService.getBowlingOutputFormData(playerId);
		bowlingOutputData.setComments("Player Game info - ID in patch request");
		return new ResponseEntity<BowlingOutputForm>(bowlingOutputData, HttpStatus.OK);
	}


 	@ApiOperation(value = "Add the pins to the game with Id on the path",response = BowlingOutputForm.class)
	@RequestMapping(value="/games/{id}", method=RequestMethod.PUT, produces = "application/json")
    public ResponseEntity addPinsWithIdOnPatch(	@PathVariable("id") int playerId, 
							@RequestBody BowlingInputForm bowlingInputData){
		//place for validation							
		if (playerId != bowlingInputData.getPlayerId()) { 
			return new ResponseEntity(HttpStatus.MULTIPLE_CHOICES); // HttpStatus.300 - conflict Id from path != Id from body 
		}
		if ((playerId > bowlingService.getGameCounter())||(playerId <=0)) { 
			return new ResponseEntity(HttpStatus.NOT_FOUND); // HttpStatus.40x - No Id in base
		}
		//place for action
		if(bowlingService.isOkPrevalidation(bowlingInputData)){ // pins value 
			bowlingService.addPins(bowlingInputData); //add pins to game
		}
		else{
			return new ResponseEntity(HttpStatus.FORBIDDEN); // wrong pins value
		} 	
		// place for validation after action		
		// collect the output data	

		BowlingOutputForm bowlingOutputData= bowlingService.getBowlingOutputFormData(bowlingInputData);
		bowlingOutputData.setComments("Roll pins added");
		return new ResponseEntity<BowlingOutputForm>(bowlingOutputData, HttpStatus.ACCEPTED); // http.Status  HttpStatus.CREATED???	
	}


    @ApiOperation(value = "Delete a Game")
    @RequestMapping(value="/games/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable (value="id") int playerId){// BindingResult result) {
		//place for validation	
		if (playerId > bowlingService.getGameCounter()) { 
			return new ResponseEntity(HttpStatus.FORBIDDEN); // HttpStatus.40x - No Id in base
		}
		//place for action		
		int whatHappend = bowlingService.delete(playerId);
		//place for validation after action
		if (whatHappend == OUT_OF_RANGE) { 
			return new ResponseEntity(HttpStatus.FORBIDDEN); // NOT_FOUND
		}
		// collect the output data		

		return new ResponseEntity(HttpStatus.OK);
    }
}
