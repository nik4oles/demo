package ru.skillbox.demo.webappRestController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.skillbox.demo.DemoApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    @BeforeEach
    private void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    /**
     * ТЕСТ-КЕЙС
     * Успешное создание пользователя
     */

    @Test
    void createUserSuccess() throws Exception {

        String request = "{\"name\": \"Ivan\", \"lastname\": \"Petrov\",\"email\":\"nik\", \"password\":\"1234\"}";
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());

    }


    /*
     * ТЕСТ-КЕЙС
     * Неуспешное создание пользователя при отсутвии тела запроса
     */

    @Test
    void createUserFailed() throws Exception {
        String request = "";
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    /**
     * ТЕСТ-КЕЙС
     * Успешное получение пользователя
     */

    @Test
    void getUser() throws Exception {
        mockMvc.perform(get("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    /**
     * ТЕСТ-КЕЙС
     * Успешное обновление пользователя
     */

    @Test
    void updateUser() throws Exception {
        String request = "{\"name\": \"TEST\", \"lastname\": \"TEST\",\"email\":\"nik\", \"password\":\"1234\"}";
        mockMvc.perform(put("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());
    }
    /**
     * ТЕСТ-КЕЙС
     * Успешное удаление пользователя
     */

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isOk());
    }



    @Test
    void subscribe() throws Exception {
        mockMvc.perform(post("/users/{id}/subscribe/{subscriberId}", 10, 7))
                .andExpect(status().isOk());
        mockMvc.perform(post("/users/{id}/unsubscribe/{subscriberId}", 10, 7))
                .andExpect(status().isOk());
    }


    @Test
    void unsubscribe() throws Exception {

        mockMvc.perform(post("/users/{id}/unsubscribe/{subscriberId}", 1, 2))
                .andExpect(status().isOk());
    }
}