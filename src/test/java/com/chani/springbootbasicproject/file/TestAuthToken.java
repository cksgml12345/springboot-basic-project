package com.chani.springbootbasicproject.file;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

final class TestAuthToken {
    private TestAuthToken() {
    }

    static String issueToken(MockMvc mockMvc, String signupJson) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signupJson))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode node = new ObjectMapper().readTree(result.getResponse().getContentAsString());
        return node.get("token").asText();
    }
}
