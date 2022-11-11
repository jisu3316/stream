package com.example.stream.quiz1;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

class Main {

    private static final String TARGET = "좋아";
    private static final int TARGET_LENGTH = TARGET.length();

    public void quiz1() throws IOException {
        // https://jeong-pro.tistory.com/212
        CSVReader csvReader = new CSVReader(new FileReader(Objects.requireNonNull(getClass().getResource("/user.csv")).getFile()));
        csvReader.readNext();
        List<String[]> csvLines = csvReader.readAll();

        csvLines.forEach(strings -> System.out.println("strings = " + Arrays.toString(strings)));
//
//        return csvLines.stream()
//                .map(line -> line[1].replaceAll("\\s", ""))
//                .flatMap(hobbies -> Arrays.stream(hobbies.split(":")))
//                .collect(Collectors.toMap(hobby -> hobby, hobby -> 1, (oldValue, newValue) -> newValue += oldValue));
    }

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.quiz1();
    }
}
