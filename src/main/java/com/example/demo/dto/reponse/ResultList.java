package com.example.demo.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResultList<E> {
    private E data;
}
