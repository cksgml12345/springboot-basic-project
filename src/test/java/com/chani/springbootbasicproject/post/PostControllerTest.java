package com.chani.springbootbasicproject.post;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void postCrudAndRelationFlow() throws Exception {
        String signupJson = """
                {"username":"writer","email":"writer@example.com","password":"password123"}
                """;

        MvcResult signupResult = mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signupJson))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode signupNode = objectMapper.readTree(signupResult.getResponse().getContentAsString());
        String token = signupNode.get("token").asText();

        String createPostJson = """
                {
                  "title":"JPA 연관관계 정리",
                  "content":"다대다, 일대다를 사용한 예시입니다.",
                  "tags":["spring","jpa","portfolio"]
                }
                """;

        MvcResult postResult = mockMvc.perform(post("/api/posts")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPostJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tags[0]").exists())
                .andReturn();

        Long postId = objectMapper.readTree(postResult.getResponse().getContentAsString()).get("id").asLong();

        String commentJson = """
                {"content":"구조가 깔끔하네요."}
                """;

        mockMvc.perform(post("/api/posts/" + postId + "/comments")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commentJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.author").value("writer"));

        mockMvc.perform(get("/api/posts/" + postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comments[0].content").value("구조가 깔끔하네요."));

        String updatePostJson = """
                {
                  "title":"JPA 연관관계 고급",
                  "content":"연관관계 매핑과 CRUD를 함께 구현",
                  "tags":["spring","jpa"]
                }
                """;

        mockMvc.perform(put("/api/posts/" + postId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePostJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("JPA 연관관계 고급"));

        mockMvc.perform(delete("/api/posts/" + postId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }
}
