package com.denisqq.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WidgetDto implements Serializable {
    private static final long serialVersionUID = 8505711429601458338L;

    private UUID id;
    private double x;
    private double y;
    private Double zIndex;
    private double height;
    private double width;

}
