package com.denisqq.web.controller;

import com.denisqq.service.WidgetService;
import com.denisqq.web.dto.WidgetDto;
import com.denisqq.web.dto.WidgetRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.List;
import java.util.UUID;

@Controller
public class WidgetController {

    private WidgetService service;

    public WidgetController(WidgetService service) {
        this.service = service;
    }

    @GetMapping(value = "/widgets")
    private ResponseEntity<List<WidgetDto>> getWidgets(@RequestParam(defaultValue = "10") @Max(value = 500) int limit,
                                                       @RequestParam(defaultValue = "0") int offset) {
        List<WidgetDto> widgets = service.findAll(limit, offset);

        return new ResponseEntity<>(widgets, HttpStatus.OK);
    }

    @GetMapping(value = "/widgets/{widgetId}")
    private ResponseEntity<WidgetDto> getWidget(@PathVariable UUID widgetId) {
        WidgetDto widgets = service.findById(widgetId);

        return new ResponseEntity<>(widgets, HttpStatus.OK);
    }

    @PostMapping(value = "/widgets", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<WidgetDto> addWidget(WidgetRequest request) {
        WidgetDto ret = service.create(request);
        return new ResponseEntity<>(ret, HttpStatus.CREATED);
    }


    @PutMapping(value = "/widgets/{widgetId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<WidgetDto> patchWidget(WidgetRequest request, @PathVariable UUID widgetId) {
        WidgetDto ret = service.update(widgetId, request);

        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @DeleteMapping(name = "/widgets/{widgetId}")
    private ResponseEntity<WidgetDto> deleteWidget(@PathVariable UUID widgetId) {
        service.delete(widgetId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
