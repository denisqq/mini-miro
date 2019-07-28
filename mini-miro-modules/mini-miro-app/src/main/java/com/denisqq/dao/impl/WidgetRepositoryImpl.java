package com.denisqq.dao.impl;

import com.denisqq.dao.WidgetRepository;
import com.denisqq.exception.NotFoundException;
import com.denisqq.model.Widget;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class WidgetRepositoryImpl implements WidgetRepository {
    private Map<UUID, Widget> repository = new ConcurrentHashMap<>();

    @Override
    public Widget findById(UUID id) {
        if(id == null) throw new NotFoundException("Widget id must not be null");

        return Optional.ofNullable(this.repository.get(id)).orElseThrow(() -> new NotFoundException("Widget not found"));
    }

    @Override
    public List<Widget> findAll(int limit, int offset) {
        List<Widget> widgets = new ArrayList<>(this.repository.values());

//        widgets.sort(Comparator.comparingDouble(Widget::getZIndex));

        widgets.sort((o1, o2) -> {
            if(o1.getZIndex() == null) {
                return 1;
            } else if(o2.getZIndex() == null) {
                return -1;
            }

            return Double.compare(o1.getZIndex(), o2.getZIndex());
        });

        return widgets.stream()
                .limit(limit)
                .skip(offset)
                .collect(Collectors.toList());
    }

    @Override
    public Widget create(Widget widget) {
        widget.setModifiedAt(LocalDateTime.now());
        return this.repository.put(widget.getId(), widget);
    }

    @Override
    public void remove(UUID id) {
        this.repository.remove(id);
    }

    @Override
    public Widget save(Widget widget) {
        widget.setModifiedAt(LocalDateTime.now());
        this.repository.replace(widget.getId(), widget);
        return widget;
    }
}
