package com.example.stream.quiz6;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Student {

    private String name;
    private boolean isMale; // 성별
    private int hak; // 학년
    private int ban; // 반
    private int score;


}