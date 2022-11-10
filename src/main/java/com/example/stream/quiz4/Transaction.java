package com.example.stream.quiz4;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Transaction {

    private Trader trader;
    private int year;
    private int value;
}
