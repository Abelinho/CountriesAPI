package com.abel.countriesapi.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class AppResponse implements Serializable {

    private boolean error;

    private String msg;

    private Object data;

}
