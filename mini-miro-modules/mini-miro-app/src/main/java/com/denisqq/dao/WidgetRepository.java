package com.denisqq.dao;


import com.denisqq.model.Widget;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


public interface WidgetRepository {

    Widget findById(UUID id);
    List<Widget> findAll(int limit, int offset);
    Widget create(Widget widget);
    Widget remove(UUID id);
    Widget save(Widget widget);
}
