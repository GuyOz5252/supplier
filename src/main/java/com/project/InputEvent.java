package com.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class InputEvent {
    private String id;
    private String content;
}
