package com.example.stream.quiz5;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Main {

    public void sumInt() {
        //문자열 배열 String[] strArr = {"aaa","bb","c","dddd"}의 모든 문자열의 길이를 더한 결과를 출력하여라.
        String[] strArr = {"aaa","bb","c","dddd"};
        IntStream intStream = Arrays.stream(strArr).mapToInt(String::length);
        System.out.println("intStream = " + intStream.sum());
    }

    //문자열 배열 String[] strArr = {"aaa","bb","c","dddd"}의 문자열 중에서 가장 긴 것의 길이를 출력하시오.
    public void maxLength() {
        String[] strArr = {"aaa","bb","c","dddd"};
        OptionalInt max = Arrays.stream(strArr).mapToInt(String::length).max();
        System.out.println("max = " + max.orElse(0));
    }

    //임의의 로또번호(1~45)를 정렬해서 출력하시오.
    public void lottoSorted() {
        List<Integer> collect1 = new Random().ints(1, 46)
                .distinct()
                .limit(6)
                .sorted()
                .boxed()
                .collect(Collectors.toList());
        System.out.println("collect1 = " + collect1);
    }

    public static void main(String[] args) {
        Main main = new Main();
//        main.sumInt();
//        main.maxLength();
        main.lottoSorted();


    }
}
