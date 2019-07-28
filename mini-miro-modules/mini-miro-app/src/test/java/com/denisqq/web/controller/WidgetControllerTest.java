package com.denisqq.web.controller;


import com.denisqq.service.WidgetService;
import com.denisqq.web.dto.WidgetDto;
import com.denisqq.web.dto.WidgetRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;


import static org.hamcrest.collection.IsCollectionWithSize.hasSize;


import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = WidgetController.class)
public class WidgetControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WidgetService service;

    @Test
    public void getWidgets() throws Exception {
        WidgetDto widgetDto = WidgetDto.builder()
                .id(UUID.randomUUID())
                .x(1)
                .y(1)
                .height(500)
                .width(500)
                .zIndex(5D)
                .build();
        List<WidgetDto> widgetDtoList = List.of(widgetDto);
        given(service.findAll(10, 0)).willReturn(widgetDtoList);


        mvc.perform(get("/widgets")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(widgetDto.getId().toString())));
    }

    @Test
    public void getWidget() throws Exception {
        WidgetDto widgetDto = WidgetDto.builder()
                .id(UUID.randomUUID())
                .x(1)
                .y(1)
                .height(500)
                .width(500)
                .zIndex(5D)
                .build();

        given(service.findById(widgetDto.getId())).willReturn(widgetDto);


        mvc.perform(get("/widgets/" + widgetDto.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(widgetDto.getId().toString())))
                .andExpect(jsonPath("$.x", is(widgetDto.getX())))
                .andExpect(jsonPath("$.y", is(widgetDto.getY())))
                .andExpect(jsonPath("$.height", is(widgetDto.getHeight())))
                .andExpect(jsonPath("$.width", is(widgetDto.getWidth())))
                .andExpect(jsonPath("$.zindex", is(widgetDto.getZIndex())));
    }

    @Test
    public void addWidget() throws Exception {
        WidgetDto widgetDto = WidgetDto.builder()
                .id(UUID.randomUUID())
                .x(31)
                .y(2)
                .height(200)
                .width(250)
                .zIndex(1D)
                .build();

        when(service.create(any(WidgetRequest.class))).thenReturn(widgetDto);

        mvc.perform(
                post("/widgets")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"x\":\"31\",\"y\":\"1\",\"height\":\"200\",\"width\":\"250\",\"zindex\":\"1\"}")
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(widgetDto.getId().toString())))
                .andExpect(jsonPath("$.x", is(widgetDto.getX())))
                .andExpect(jsonPath("$.y", is(widgetDto.getY())))
                .andExpect(jsonPath("$.height", is(widgetDto.getHeight())))
                .andExpect(jsonPath("$.width", is(widgetDto.getWidth())))
                .andExpect(jsonPath("$.zindex", is(widgetDto.getZIndex())));


    }

    @Test
    public void patchWidget() throws Exception {

        WidgetDto widgetDto = WidgetDto.builder()
                .id(UUID.randomUUID())
                .x(10)
                .y(5)
                .height(100)
                .width(20)
                .zIndex(null)
                .build();

        when(service.update(any(UUID.class), any(WidgetRequest.class))).thenReturn(widgetDto);

        mvc.perform(
                put("/widgets/" + widgetDto.getId())
                        .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                        .accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                        .content("{\"x\":\"10\",\"y\":\"5\",\"height\":\"100\",\"width\":\"20\",\"zindex\":\"null\"}")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(widgetDto.getId().toString())))
                .andExpect(jsonPath("$.x", is(widgetDto.getX())))
                .andExpect(jsonPath("$.y", is(widgetDto.getY())))
                .andExpect(jsonPath("$.height", is(widgetDto.getHeight())))
                .andExpect(jsonPath("$.width", is(widgetDto.getWidth())))
                .andExpect(jsonPath("$.zindex", is(widgetDto.getZIndex())));

    }

    @Test
    public void deleteWidget() throws Exception {

        UUID id = UUID.randomUUID();
        doNothing().when(service).delete(id);
        mvc.perform(
                delete("/widgets/" + id)
                        .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                        .accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
        ).andExpect(status().isNoContent());
    }
}