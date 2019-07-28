package com.denisqq.dao;


import com.denisqq.model.Widget;

import java.util.List;
import java.util.UUID;


public interface WidgetRepository {

    Widget findById(UUID id);
    List<Widget> findAll(int limit, int offset);
    void remove(UUID id);
    Widget save(Widget widget);
    Widget create(Widget widget);
}
