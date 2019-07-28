package com.denisqq.dao.impl;

import com.denisqq.dao.WidgetRepository;
import com.denisqq.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class WidgetImplTest {

    @InjectMocks
    private WidgetRepository repository;

    @Test(expected = NotFoundException.class)
    public void findByNullId_Expected_Not_Found_Exception() {
        repository.findById(null);
    }
}