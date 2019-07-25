package com.denisqq.mapper;

import com.denisqq.model.Widget;
import com.denisqq.web.dto.WidgetDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WidgetMapper {

    WidgetDto toDto(Widget widget);
    List<WidgetDto> toDto(List<Widget> widgets);
}
