package com.bankinc.controller;

import com.bankinc.dto.AnulationDTO;
import com.bankinc.dto.BalanceCardDTO;
import com.bankinc.dto.EnrollCardDTO;
import com.bankinc.dto.PurchaseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CardControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
    void generateNumberTest() throws Exception {

        mockMvc.perform(get("/card/{productId}/number", 1212))
                .andExpect(status().isOk());

    }
	
	@Test
    void enrollTest() throws Exception {
		EnrollCardDTO enrollCardDTO=new EnrollCardDTO();
		enrollCardDTO.setIdCard(12122784830004L);
		
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post("/card/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(enrollCardDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("statusCode" , is(201)));

    }
	
	@Test
    void blockCardTest() throws Exception {

        mockMvc.perform(delete("/card/{cardId}", 12122784830004L))
                .andExpect(status().isOk());

    }
	
	@Test
    void addBalanceTest() throws Exception {
		BalanceCardDTO balanceCardDTO=new BalanceCardDTO();
		balanceCardDTO.setIdCard(12122784830004L);
		balanceCardDTO.setBalance(100000.0);
		
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post("/card/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(balanceCardDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("statusCode" , is(201)));

    }
	
	@Test
    void getBalanceTest() throws Exception{
        mockMvc.perform(get("/card/balance/{cardId}",12122784830004L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("statusCode" , is(200)));


    }
	
	@Test
    void purchaseTest() throws Exception {
		PurchaseDTO purchaseDTO=new PurchaseDTO();
		purchaseDTO.setIdCard(12122784830004L);
		purchaseDTO.setPrecio(100.0);
		
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post("/transaction/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(purchaseDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("statusCode" , is(201)));

    }
	
	@Test
    void getTransactionTest() throws Exception{
        mockMvc.perform(get("/transaction/{transactionId}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("statusCode" , is(200)));


    }
	
	@Test
    void anulationTest() throws Exception {
		AnulationDTO anulationDTO=new AnulationDTO();
		anulationDTO.setIdCard(12122784830004L);
		anulationDTO.setTransactionId(1L);
		
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post("/transaction/anulation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(anulationDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("statusCode" , is(201)));

    }

}
