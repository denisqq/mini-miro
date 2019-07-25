package com.denisqq.service;

import com.denisqq.dao.WidgetRepository;
import com.denisqq.mapper.WidgetMapper;
import com.denisqq.model.Widget;
import com.denisqq.web.dto.WidgetDto;
import com.denisqq.web.dto.WidgetRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class WidgetService {

    private WidgetRepository repository;
    private WidgetMapper mapper;

    public WidgetService(WidgetRepository repository, WidgetMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<WidgetDto> findAll(int limit, int offset) {
        final String DEBUG_STR = "findAll";
        log.info("{}: limit={}, offset={}", DEBUG_STR, limit, offset);

        List<Widget> widgets = repository.findAll(limit, offset);
        return mapper.toDto(widgets);
    }

    public WidgetDto findById(UUID id) {
        final String DEBUG_STR = "findById";
        log.info("{}: id={}", DEBUG_STR, id);

        Widget widget = repository.findById(id);
        return mapper.toDto(widget);
    }

    public WidgetDto create(final WidgetRequest request) {
        final String DEBUG_STR = "create";
        log.info("{}: request={}", DEBUG_STR, request);
        final UUID id = UUID.randomUUID();
        Widget widget = Widget.builder()
                .id(id)
                .width(request.getWidth())
                .height(request.getHeight())
                .x(request.getX())
                .y(request.getY())
                .zIndex(request.getZIndex())
                .build();

        repository.create(widget);

        return mapper.toDto(widget);
    }


    public WidgetDto update(final UUID id, final WidgetRequest request) {
        final String DEBUG_STR = "update";
        log.info("{}: request={}", DEBUG_STR, request);
        Widget widget = repository.findById(id);

        widget.setHeight(request.getHeight());
        widget.setWidth(request.getWidth());

        widget.setX(request.getX());
        widget.setY(request.getY());
        widget.setZIndex(request.getZIndex());

        widget = repository.save(widget);

        return mapper.toDto(widget);
    }


    public void delete(final UUID id) {
        final String DEBUG_STR = "delete";
        log.info("{}: id={}", DEBUG_STR, id);

        repository.remove(id);
    }
}
