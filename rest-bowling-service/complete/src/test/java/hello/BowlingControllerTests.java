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
import hello.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BowlingControllerTests {

//	@Before    
	@Autowired
    private MockMvc mockMvc;

    @Test
    public void addNewGame() throws Exception {
		// first game creation
        this.mockMvc.perform(post("/bowling/games/add")).andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("NoName"));
		// second game creation
        this.mockMvc.perform(post("/bowling/games/add?name=Darek")).andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Darek"));
		// third game creation
        this.mockMvc.perform(post("/bowling/games/add").param("name","Jarek")).andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Jarek"));  

    }
	@Test
    public void getGameInfo() throws Exception {

		// get name from first game
       this.mockMvc.perform(get("/bowling/games/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("NoName"));
		// get name from second game
       this.mockMvc.perform(get("/bowling/games/2")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Darek"));
		// 	// get name from third game
       this.mockMvc.perform(get("/bowling/games/3")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jarek"));
    }



//   @Test
//    public void addPinsToGame() throws Exception {
//        this.mockMvc.perform(put("/bowling/games/1").body("pins","3")).andDo(print()).andExpect(status().isOk())
//                .andExpect(jsonPath("$.score").value("3"));
//        this.mockMvc.perform(get("/bowling/games/1")
//				.contentType(MediaType.APPLICATION_JSON)
 //               .content(asJsonString("pins","4")))
//				.andDo(print()).andExpect(status().isOk())
 //               .andExpect(jsonPath("$.name").value("NoName")); 
//       this.mockMvc.perform(put("/bowling/games/1").body("pins","3")).andDo(print()).andExpect(status().isOk())
//                .andExpect(jsonPath("$.score").value("6"));
//   
//}



}
