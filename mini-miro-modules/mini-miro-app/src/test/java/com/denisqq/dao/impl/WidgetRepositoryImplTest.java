package com.denisqq.dao.impl;

import com.denisqq.exception.NotFoundException;
import com.denisqq.model.Widget;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;
import java.util.concurrent.*;


import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class WidgetRepositoryImplTest {

    @InjectMocks
    private WidgetRepositoryImpl repository;

    @Test(expected = NotFoundException.class)
    public void findByNullId_Expected_Not_Found_Exception() {
        repository.findById(null);
    }

    @Test
    public void create() {
        Widget widget = Widget.builder()
                .id(UUID.randomUUID())
                .build();
        repository.create(widget);
        assertEquals(widget, repository.findById(widget.getId()));
    }

    @Test
    public void get_and_update_thread_safety_test() throws InterruptedException, ExecutionException {
        Widget widget =  Widget.builder()
                .id(UUID.randomUUID())
                .build();

        repository.create(widget);
        final int threads = 2;
        Callable<Widget> patchWidget = () -> {
            widget.setX(100);
            widget.setY(100);
            widget.setHeight(500);
            widget.setWidth(400);
            widget.setZIndex(11D);
            return repository.save(widget);
        };
        Callable<Widget> getWidget = () -> repository.findById(widget.getId());
        ExecutorService service = Executors.newFixedThreadPool(threads);
        List<Future<Widget>> futures = service.invokeAll(List.of(getWidget, patchWidget));
        List<Widget> widgets = new ArrayList<>();
        for (Future<Widget> future : futures) {
            Widget widget1 = future.get();
            widgets.add(widget1);
        }
        System.out.println(widgets);
    }



}