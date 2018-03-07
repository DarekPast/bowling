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
package hello;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//Added 03.05
//import javax.ws.rs.core.MediaType;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import hello.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BowlingControllerTests {

//	@Before    
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
                .andExpect(jsonPath("$.name").value("Darek"));
		// third game creation
        this.mockMvc.perform(post("/bowling/games/add").param("name","Jarek")).andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Jarek"));  

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
@Test // Adding pins to the game
    public void testAddPinsToGameErrors() throws Exception {
		// Id in Patch != Id in body
        this.mockMvc.perform(put("/bowling/games/2")
				.contentType(MediaType.APPLICATION_JSON) //
				.content(createDataInJson (1,3))) 
			.andDo(print())
			.andExpect(status().is(300));
		// ID out of scope
      	this.mockMvc.perform(put("/bowling/games/10")
				.contentType(MediaType.APPLICATION_JSON) //
				.content(createDataInJson (10,7))) // end perform()
			.andDo(print())
			.andExpect(status().is(406)); //.isOk()??

/*
   		this.mockMvc.perform(put("/bowling/games/1")
				.contentType(MediaType.APPLICATION_JSON) //
				.content(createDataInJson (1,7))) // end perform()
			.andDo(print())
			.andExpect(status().is(202)) //.isOk()??
            .andExpect(jsonPath("$.name").value("NoName"))//;
			.andExpect(jsonPath("$.playerId").value(1))
			.andExpect(jsonPath("$.score").value(24));
*/
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
