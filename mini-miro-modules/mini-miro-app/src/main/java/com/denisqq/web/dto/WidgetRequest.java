package com.denisqq.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@Getter
@Setter
public class WidgetRequest implements Serializable {
    private static final long serialVersionUID = -7830985241855903956L;

    @NonNull
    private double x;
    @NonNull
    private double y;
    private double zIndex;
    @NonNull
    private double height;
    @NonNull
    private double width;

}
