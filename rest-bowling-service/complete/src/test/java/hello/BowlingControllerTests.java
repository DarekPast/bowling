/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Integration Tests
 * Class testing BowlingGame.java with MockMvc
 * 
*/

package hello;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

// Testing class
import hello.BowlingController;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BowlingControllerTests {

  
	@Autowired
    private MockMvc mockMvc;

//	@Before
	@Test
    public void testAddNewGame() throws Exception {
		// first game creation
        this.mockMvc.perform(post("/bowling/games/add"))
			.andDo(print())
			.andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("NoName"))
			.andExpect(jsonPath("$.playerId").value(1))
			.andExpect(jsonPath("$.score").value(0));
		// second game creation
        this.mockMvc.perform(post("/bowling/games/add?name=Darek")).andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Darek"))
				.andExpect(jsonPath("$.playerId").value(2));
		// third game creation
        this.mockMvc.perform(post("/bowling/games/add").param("name","Jarek")).andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Jarek"))
				.andExpect(jsonPath("$.playerId").value(3));  

        this.mockMvc.perform(post("/bowling/games/add").param("name","Marek")).andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Marek"))
				.andExpect(jsonPath("$.playerId").value(4));  
    }

	@Test
    public void testGetGameInfo() throws Exception {

		// get name from first game
       this.mockMvc.perform(get("/bowling/games/1"))
			.andDo(print())
			.andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("NoName"))
			.andExpect(jsonPath("$.playerId").value(1))
			.andExpect(jsonPath("$.score").value(0));

		// get name from second game
       this.mockMvc.perform(get("/bowling/games/{id}",2)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Darek"));
		// 	// get name from third game
       this.mockMvc.perform(get("/bowling/games/{id}",3)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jarek"));
    }



   @Test // Adding pins to the game
    public void testAddPinsToGame() throws Exception {
        this.mockMvc.perform(put("/bowling/games/1")
				.contentType(MediaType.APPLICATION_JSON) //
				.content(createDataInJson (1,3))) // end perform()
			.andDo(print())
			.andExpect(status().isAccepted()) //202 - game contius OR .isOk()??
            .andExpect(jsonPath("$.name").value("NoName"))//;
			.andExpect(jsonPath("$.playerId").value(1))
			.andExpect(jsonPath("$.score").value(3));

      	this.mockMvc.perform(put("/bowling/games/1")
				.contentType(MediaType.APPLICATION_JSON) //
				.content(createDataInJson (1,7))) // end perform()
			.andDo(print())
			.andExpect(status().is(202)) //.isOk()??
            .andExpect(jsonPath("$.name").value("NoName"))//;
			.andExpect(jsonPath("$.playerId").value(1))
			.andExpect(jsonPath("$.score").value(10));

   		this.mockMvc.perform(put("/bowling/games/1")
				.contentType(MediaType.APPLICATION_JSON) //
				.content(createDataInJson (1,7))) // end perform()
			.andDo(print())
			.andExpect(status().is(202)) //.isOk()??
            .andExpect(jsonPath("$.name").value("NoName"))//;
			.andExpect(jsonPath("$.playerId").value(1))
			.andExpect(jsonPath("$.score").value(24));
	}
	
	@Test // Error testing in to the game
    public void testAddPinsToGameErrors() throws Exception {
		// Id in Patch != Id in body
        this.mockMvc.perform(put("/bowling/games/2")
				.contentType(MediaType.APPLICATION_JSON) //
				.content(createDataInJson (1,1))) 
			.andDo(print())
			.andExpect(status().is(300));
		// ID out of scope numbers of games
      	this.mockMvc.perform(put("/bowling/games/999")
				.contentType(MediaType.APPLICATION_JSON) //
				.content(createDataInJson (999,7))) // end perform()
			.andDo(print())
			.andExpect(status().is(404)); //NOT_FUND

		// pins of scope. Max pins number is 10
   		this.mockMvc.perform(put("/bowling/games/1")
				.contentType(MediaType.APPLICATION_JSON) //
				.content(createDataInJson (1,11))) // end perform()
			.andDo(print())
			.andExpect(status().is(403)); //FORBIDDEN
		// pins of scope. Max pins number is 10
	}

	@Test // Error testing in to the end of game
    public void testAddPinsAfterInLastFrameSpare() throws Exception {
		// filling up to the last round		
		for (int i=1;i<=18;i++){
   			this.mockMvc.perform(put("/bowling/games/2")
					.contentType(MediaType.APPLICATION_JSON) //
					.content(createDataInJson (2,1))) // end perform()
				.andDo(print())
				.andExpect(status().is(202)); //OK
		}
		// last round first roll;
   		this.mockMvc.perform(put("/bowling/games/2")
				.contentType(MediaType.APPLICATION_JSON) //
				.content(createDataInJson (2,1))) // end perform()
			.andDo(print())
			.andExpect(status().is(202)); //OK
		// last round secound roll and spare;
   		this.mockMvc.perform(put("/bowling/games/2")
				.contentType(MediaType.APPLICATION_JSON) //
				.content(createDataInJson (2,9))) // end perform()
			.andDo(print())
			.andExpect(status().is(202)); //OK
		// last round extra roll after spare;
   		this.mockMvc.perform(put("/bowling/games/2")
				.contentType(MediaType.APPLICATION_JSON) //
				.content(createDataInJson (2,1))) // end perform()
			.andDo(print())
			.andExpect(status().is(202)) //OK
			.andExpect(jsonPath("$.score").value(29))
			.andExpect(jsonPath("$.frameNb").value(10))
			.andExpect(jsonPath("$.rollInFrame").value(3));
		// Error - something after last round with extra roll after spare;
  		this.mockMvc.perform(put("/bowling/games/2")
				.contentType(MediaType.APPLICATION_JSON) //
				.content(createDataInJson (2,9))) // end perform()
			.andDo(print())
			.andExpect(status().isForbidden()); //Input data error

	}

	@Test // Error testing in to the end of game 
    public void testAddPinsAfterInLastFrameStrike() throws Exception {
		// filling up to the end of last round in PerfectGame		
		for (int i=1;i<=11;i++){
   			this.mockMvc.perform(put("/bowling/games/3")
					.contentType(MediaType.APPLICATION_JSON) //
					.content(createDataInJson (3,10))) // end perform()
				.andDo(print())
				.andExpect(status().is(202)); //OK

		}
		
		// last round extra roll in Perfect Game - roll nr 12;
   		this.mockMvc.perform(put("/bowling/games/3")
				.contentType(MediaType.APPLICATION_JSON) //
				.content(createDataInJson (3,10))) // end perform()
			.andDo(print())
			.andExpect(status().is(202)) //OK
			.andExpect(jsonPath("$.score").value(300))
			.andExpect(jsonPath("$.frameNb").value(10))
			.andExpect(jsonPath("$.rollInFrame").value(3));
		// Error - something after Perfect Game;
  		this.mockMvc.perform(put("/bowling/games/3")
				.contentType(MediaType.APPLICATION_JSON) //
				.content(createDataInJson (3,9))) // end perform()
			.andDo(print())
			.andExpect(status().isForbidden()); //Input data error

	}
	@Test // Error testing to the end of game 
    public void testAddPinsAfterInLastFrameStandard() throws Exception {
		// filling up to the end of last round in PerfectGame		
		for (int i=1;i<=19;i++){
   			this.mockMvc.perform(put("/bowling/games/4")
					.contentType(MediaType.APPLICATION_JSON) //
					.content(createDataInJson (4,1))) // end perform()
				.andDo(print())
				.andExpect(status().is(202)); //OK
		}
		
		// last round standard Game - roll nr 20;
   		this.mockMvc.perform(put("/bowling/games/4")
				.contentType(MediaType.APPLICATION_JSON) //
				.content(createDataInJson (4,1))) // end perform()
			.andDo(print())
			.andExpect(status().is(202)) //OK
			.andExpect(jsonPath("$.score").value(20))
			.andExpect(jsonPath("$.frameNb").value(10))
			.andExpect(jsonPath("$.rollInFrame").value(2));
		// Error - something after Perfect Game;
  		this.mockMvc.perform(put("/bowling/games/4")
				.contentType(MediaType.APPLICATION_JSON) //
				.content(createDataInJson (4,9))) // end perform()
			.andDo(print())
			.andExpect(status().isForbidden()); //Input data error

	}
	@Test // Delete games
		public void testDeleteGame() throws Exception {
		// get name from first game
       this.mockMvc.perform(delete("/bowling/games/delete/1"))
			.andDo(print())
			.andExpect(status().isOk());
    	}



private static String createDataInJson (int playerId, int pins ) {
        return "{ \"playerId\": \"" + playerId + "\", " +
                            "\"pins\":\"" + pins + "\" }";
    }

}
