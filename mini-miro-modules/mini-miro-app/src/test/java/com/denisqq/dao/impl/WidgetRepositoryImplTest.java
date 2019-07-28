package com.denisqq.dao.impl;

import com.denisqq.exception.NotFoundException;
import com.denisqq.model.Widget;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
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
                .y(5)
                .x(12)
                .height(12)
                .width(123)
                .zIndex(125D)
                .build();

        repository.create(widget);

        Random random = new Random();
        widget.setX(random.nextDouble());
        widget.setY(random.nextDouble());
        widget.setHeight(random.nextDouble());
        widget.setWidth(random.nextDouble());
        widget.setZIndex(random.nextDouble());

        int threads = 2;
        ExecutorService service = Executors.newFixedThreadPool(threads);
        Callable<Widget> findWidget = () -> repository.findById(widget.getId());
        Callable<Widget> patchWidget = () -> repository.save(widget);

        List<Future<Widget>> futures = service.invokeAll(List.of(findWidget, patchWidget));
        List<Widget> widgets = new ArrayList<>();
        for (Future<Widget> future : futures) {
            assertTrue(future.isDone());
            widgets.add(future.get());
        }
        service.shutdown();

        assertEquals(widgets.get(0), widgets.get(1));
    }



}