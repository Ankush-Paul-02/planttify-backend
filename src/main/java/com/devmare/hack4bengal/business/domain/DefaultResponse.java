package com.devmare.hack4bengal.business.domain;

import lombok.*;
import lombok.Setter;

import java.util.HashMap;
import java.util.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class DefaultResponse {

    private Status status;
    private Map data;
    private String message;

    public DefaultResponse(Status status, String message) {
        this(status, new HashMap<>(), message);
    }

    public enum Status {
        FAILED,
        SUCCESS
    }
}