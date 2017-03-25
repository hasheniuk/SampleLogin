package net.samplelogin.controller;

import net.samplelogin.service.ConnectionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(IndexController.class)
public class IndexControllerTest {
    private static final String REDIRECTION_URL_AUTH = "/auth";
    private static final String REDIRECTION_URL_PROFILE = "/profile";

    @Autowired
    private IndexController indexController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConnectionService connectionService;

    @Test
    public void testContextLoads() throws Exception {
        assertThat(indexController).isNotNull();
    }

    @Test
    public void testRedirectAuthControllerWhenUserNotSignedIn() throws Exception {
        when(connectionService.isAnyConnected()).thenReturn(false);
        mockMvc.perform(get("/"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(REDIRECTION_URL_AUTH));
    }

    @Test
    public void testRedirectAuthControllerWhenUserSignedIn() throws Exception {
        when(connectionService.isAnyConnected()).thenReturn(true);
        mockMvc.perform(get("/"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(REDIRECTION_URL_PROFILE));
    }
}
