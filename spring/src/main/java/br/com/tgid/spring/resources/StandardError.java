package br.com.tgid.spring.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StandardError {
    private Integer status;
    private String msg;
    private Long timeStamp;
}
