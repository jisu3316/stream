package com.example.stream.quiz6;

import com.example.stream.basicstream.Temp;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

class Main {

    private Student[] stuArr;

    public Main () {
        init();
    }

    public void init() {
         stuArr = new Student[]{
                new Student("나자바", true, 1, 1, 300),
                new Student("김지미", false, 1, 1, 250),
                new Student("김자바", true, 1, 1, 200),
                new Student("이지미", false, 1, 2, 150),
                new Student("남자바", true, 1, 2, 100),
                new Student("안지미", false, 1, 2, 50),
                new Student("황지미", false, 1, 3, 100),
                new Student("강지미", false, 1, 3, 150),
                new Student("이자바", true, 1, 3, 200),
                new Student("나자바", true, 2, 1, 300),
                new Student("김지미", false, 2, 1, 250),
                new Student("김자바", true, 2, 1, 200),
                new Student("이지미", false, 2, 2, 150),
                new Student("남자바", true, 2, 2, 100),
                new Student("안지미", false, 2, 2, 50),
                new Student("황지미", false, 2, 3, 100),
                new Student("강지미", false, 2, 3, 150),
                new Student("이자바", true, 2, 3, 200)
        };
    }

    //stuArr에서 불합격(150점 미만)한 학생의 수를 남자와 여자로 구별하여라. (Boolean, List)
    public void answer1() {
        Map<Boolean, List<Student>> collect = Arrays.stream(stuArr).filter(student -> student.getScore() <= 150).collect(groupingBy(Student::isMale));
        System.out.println("collect = " + collect);
    }

    //각 반별 총점을 학년 별로 나누어 구하여라 (Map<Integer, Map<Integer, Integer>>)
    public void answer2() {
        Map<Integer, Map<Integer, Integer>> collect = Arrays.stream(stuArr).collect(groupingBy(Student::getHak, groupingBy(Student::getBan, summingInt(Student::getScore))));
        System.out.println("collect = " + collect);

    }
    public static void main(String[] args) {
        Main main = new Main();
//        main.answer1();
        main.answer2();


    }
}
