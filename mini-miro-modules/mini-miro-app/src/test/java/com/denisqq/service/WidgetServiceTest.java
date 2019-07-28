package com.denisqq.service;

import com.denisqq.dao.WidgetRepository;
import com.denisqq.mapper.WidgetMapper;
import com.denisqq.model.Widget;
import com.denisqq.web.dto.WidgetDto;
import com.denisqq.web.dto.WidgetRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WidgetServiceTest {


    @Mock
    private WidgetRepository repository;
    @Mock
    private WidgetMapper mapper;

    @InjectMocks
    private WidgetService service;

    @Test
    public void findAll() {
        UUID id = UUID.randomUUID();
        Widget widget = Widget.builder()
                .id(id)
                .x(5)
                .y(5)
                .height(200)
                .width(250)
                .zIndex(1D)
                .build();
        List<Widget> widgetList = List.of(widget);
        WidgetDto expectedDto =  WidgetDto.builder()
                .id(id)
                .x(5)
                .y(2)
                .height(200)
                .width(250)
                .zIndex(1D)
                .build();
        List<WidgetDto> widgetDtoList = List.of(expectedDto);
        when(repository.findAll(anyInt(), anyInt())).thenReturn(widgetList);
        when(mapper.toDto(anyList())).thenReturn(widgetDtoList);
        List<WidgetDto> actual = service.findAll(1, 0);

        assertThat(actual, is(widgetDtoList));
    }

    @Test
    public void findById() {
        UUID id = UUID.randomUUID();
        Widget widget = Widget.builder()
                .id(id)
                .x(5)
                .y(5)
                .height(200)
                .width(250)
                .zIndex(1D)
                .build();
        when(repository.findById(id)).thenReturn(widget);

        WidgetDto expectedDto =  WidgetDto.builder()
                .id(id)
                .x(31)
                .y(2)
                .height(200)
                .width(250)
                .zIndex(1D)
                .build();
        when(mapper.toDto(any(Widget.class))).thenReturn(expectedDto);

        WidgetDto actual = service.findById(id);

        assertThat(actual, is(expectedDto));
    }

    @Test
    public void create() {
        UUID id = UUID.randomUUID();
        WidgetRequest request = WidgetRequest.builder()
                .x(31)
                .y(2)
                .height(200)
                .width(250)
                .zIndex(1D)
                .build();
        Widget widget = Widget.builder()
                .id(id)
                .x(31)
                .y(2)
                .height(200)
                .width(250)
                .zIndex(1D)
                .build();
        WidgetDto expectedDto =  WidgetDto.builder()
                .id(id)
                .x(31)
                .y(2)
                .height(200)
                .width(250)
                .zIndex(1D)
                .build();


        when(repository.create(any(Widget.class))).thenReturn(widget);
        when(mapper.toDto(any(Widget.class))).thenReturn(expectedDto);

        WidgetDto actualDto = service.create(request);
        assertThat(actualDto, is(expectedDto));

    }

    @Test
    public void update() {
        UUID id = UUID.randomUUID();
        WidgetRequest request = WidgetRequest.builder()
                .x(1)
                .y(34)
                .height(120)
                .width(10)
                .zIndex(10D)
                .build();
        Widget widget = Widget.builder()
                .id(id)
                .x(31)
                .y(2)
                .height(200)
                .width(250)
                .zIndex(1D)
                .build();
        WidgetDto expectedDto = WidgetDto.builder()
                .id(id)
                .x(1)
                .y(34)
                .height(120)
                .width(10)
                .zIndex(10D)
                .build();

        when(repository.findById(id)).thenReturn(widget);
        when(repository.save(widget)).thenReturn(widget);
        when(mapper.toDto(any(Widget.class))).thenReturn(expectedDto);


        WidgetDto actual = service.update(id, request);

        assertThat(actual, is(expectedDto));
    }

    @Test
    public void delete() {
        UUID id = UUID.randomUUID();
        doNothing().when(repository).remove(id);

        service.delete(id);
    }

}