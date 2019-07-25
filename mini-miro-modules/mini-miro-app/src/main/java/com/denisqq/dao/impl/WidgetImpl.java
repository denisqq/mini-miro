package com.denisqq.dao.impl;

import com.denisqq.dao.WidgetRepository;
import com.denisqq.exception.NotFoundException;
import com.denisqq.model.Widget;
import org.springframework.stereotype.Repository;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Repository
public class WidgetImpl implements WidgetRepository {
    private ConcurrentMap<UUID, Widget> repository = new ConcurrentHashMap<>();

    @Override
    public Widget findById(UUID id) {
        return Optional.ofNullable(this.repository.get(id)).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Widget> findAll(int limit, int offset) {
        List<Widget> widgets = new ArrayList<>(this.repository.values());

        widgets.sort(Comparator.comparingDouble(Widget::getZIndex));

        return widgets.stream()
                .limit(limit)
                .skip(offset)
                .collect(Collectors.toList());
    }

    @Override
    public Widget create(Widget widget) {
        return this.repository.put(widget.getId(), widget);
    }

    @Override
    public Widget remove(UUID id) {
        return this.repository.remove(id);
    }

    @Override
    public Widget save(Widget widget) {
        this.repository.replace(widget.getId(), widget);
        return widget;
    }
}
