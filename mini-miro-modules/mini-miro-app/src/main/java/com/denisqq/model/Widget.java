package com.denisqq.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Widget {

    private UUID id;
    private double x;
    private double y;
    private double zIndex;
    private double height;
    private double width;
    private LocalDateTime modifiedAt;

}
