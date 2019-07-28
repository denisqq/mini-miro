package com.denisqq.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WidgetRequest implements Serializable {
    private static final long serialVersionUID = -7830985241855903956L;

    @NonNull
    private double x;
    @NonNull
    private double y;
    private Double zIndex;
    @NonNull
    private double height;
    @NonNull
    private double width;

}
