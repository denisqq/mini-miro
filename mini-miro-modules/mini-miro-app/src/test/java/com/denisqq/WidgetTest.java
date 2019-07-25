package com.denisqq;

import com.denisqq.dao.WidgetRepository;
import com.denisqq.service.WidgetService;
import com.denisqq.web.dto.WidgetDto;
import com.denisqq.web.dto.WidgetRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = MiniMiroApplication.class)
public class WidgetTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private WidgetService service;
    @Mock
    private WidgetRepository repository;


    ObjectMapper mapper = new ObjectMapper();

    @Test
    @Order(1)
    public void createNewWidget() throws Exception {
        WidgetRequest request = new WidgetRequest();

        request.setHeight(500);
        request.setWidth(500);
        request.setX(1);
        request.setY(1);
        request.setZIndex(5);

        MvcResult result = mockMvc.perform(
                post("/widgets")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request))
        ).andExpect(status().isCreated())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        WidgetDto dto = mapper.readValue(content, WidgetDto.class);
        assertNotNull(dto.getId());
    }


    @Test
    @Order(2)
    public void widgetIsNotEmpty() throws Exception {
        MockHttpServletResponse response =  mockMvc.perform(
                get("/widgets")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse();
        String content = response.getContentAsString();
        List<WidgetDto> widgetDtos = mapper.readValue(content, new TypeReference<List<WidgetDto>>(){});

        assertNotNull(widgetDtos);
    }

    @Test
    @Order(3)
    public void getWidgetByIdNotEmpty() throws Exception {
        MockHttpServletResponse response =  mockMvc.perform(
                get("/widgets")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse();

        String content = response.getContentAsString();
        List<WidgetDto> widgetDtos = mapper.readValue(content, new TypeReference<List<WidgetDto>>(){});
        WidgetDto expectedDto = widgetDtos.get(0);

        assertNotNull(expectedDto);

        response =  mockMvc.perform(
                get("/widgets/" + expectedDto.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse();
        content = response.getContentAsString();
        WidgetDto actualDto = mapper.readValue(content, WidgetDto.class);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    @Order(4)
    public void patchExistedWidget() throws Exception {
        MockHttpServletResponse response =  mockMvc.perform(
                get("/widgets")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse();

        String content = response.getContentAsString();
        List<WidgetDto> widgetDtos = mapper.readValue(content, new TypeReference<List<WidgetDto>>(){});
        WidgetDto widget = widgetDtos.get(0);

        WidgetRequest request = new WidgetRequest();
        request.setY(45);
        request.setX(1);
        request.setWidth(100);
        request.setHeight(100);
        request.setZIndex(4);


        mockMvc.perform(
                put("/widgets/" + widget.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(request))
        ).andExpect(status().isOk())
                .andReturn();

    }


    @Test
    @Order(5)
    public void deleteExistedWidget() throws Exception {
        MockHttpServletResponse response =  mockMvc.perform(
                get("/widgets")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse();

        String content = response.getContentAsString();
        List<WidgetDto> widgetDtos = mapper.readValue(content, new TypeReference<List<WidgetDto>>(){});
        WidgetDto widget = widgetDtos.get(0);

        mockMvc.perform(
                delete("/widgets/" + widget.getId())
        ).andExpect(status().isNoContent())
                .andReturn();
    }
}
