package com.abel.countriesapi.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class AppResponse<T> implements Serializable {

    private String status;

    private Long execTime;

    @Builder.Default
    private Object error = new ArrayList<>();

    private String message;

    private T data;

//    private boolean error;
//
//    private String msg;
//
//    private T data;

}
