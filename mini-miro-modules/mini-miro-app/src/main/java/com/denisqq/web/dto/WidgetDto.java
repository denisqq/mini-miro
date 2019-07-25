package com.denisqq.web.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class WidgetDto implements Serializable {
    private static final long serialVersionUID = 8505711429601458338L;

    private UUID id;
    private double x;
    private double y;
    private double zIndex;
    private double height;
    private double width;

}
