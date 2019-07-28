package com.denisqq.mapper;

import com.denisqq.MiniMiroApplication;
import com.denisqq.model.Widget;
import com.denisqq.web.dto.WidgetDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MiniMiroApplication.class)
public class WidgetMapperTest {

    @Autowired
    private WidgetMapper mapper;

    @Test
    public void toDto() {
        Widget widget = Widget.builder()
                .id(UUID.randomUUID())
                .x(31)
                .y(2)
                .height(200)
                .width(250)
                .zIndex(1D)
                .build();
        WidgetDto widgetDto = mapper.toDto(widget);

        assertEquals(widget.getId(), widgetDto.getId());
        assertEquals(widget.getZIndex(), widgetDto.getZIndex());
        assertThat(widgetDto.getX(), is(widget.getX()));
        assertThat(widgetDto.getY(), is(widget.getY()));
        assertThat(widgetDto.getWidth(), is(widget.getWidth()));
        assertThat(widgetDto.getHeight(), is(widget.getHeight()));
    }

    @Test
    public void toDtoList() {
        Widget widget = Widget.builder()
                .id(UUID.randomUUID())
                .x(31)
                .y(2)
                .height(200)
                .width(250)
                .zIndex(1D)
                .build();
        List<Widget> widgetList = List.of(widget);
        List<WidgetDto> widgetDtos = mapper.toDto(widgetList);
        WidgetDto widgetDto = widgetDtos.get(0);

        assertEquals(widget.getId(), widgetDto.getId());
        assertEquals(widget.getZIndex(), widgetDto.getZIndex());
        assertThat(widgetDto.getX(), is(widget.getX()));
        assertThat(widgetDto.getY(), is(widget.getY()));
        assertThat(widgetDto.getWidth(), is(widget.getWidth()));
        assertThat(widgetDto.getHeight(), is(widget.getHeight()));
    }

}