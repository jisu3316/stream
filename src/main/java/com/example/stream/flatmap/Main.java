package com.example.stream.flatmap;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Main {

    /**
     * 예를 들어 국어 점수, 영어 점수, 수학 점수를 갖는 Student 클래스가 존재한다고 한다.
     * Student 객체들이 저장된 student 리스트에서 모든 학생들의 모든 과목의 평균을 구해야하는 상황이라고 할 때, 이러한 flatMap을 이용하면 이를 쉽게 해결할 수 있다.
     *
     * 이 예제에서 Student 리스트는 Student보다 한 차원 높게 있으므로, 모든 점수들을 수평하게 갖는 Stream을 생성하기 위해 flatMap을 활용해 줄 수 있다.
     * 추가로 이 예제에서는 모든 점수들이 int 값이므로 flatMapToInt를 활용할 수 있다. 이를 코드로 작성하면 다음과 같다.
     */
     List<Student> students = Arrays.asList(
            new Student(80, 90, 75),
            new Student(70, 100, 75),
            new Student(85, 90, 85),
            new Student(80, 100, 90)
    );

     public void flatMapToInt() {
         students.stream().flatMapToInt(student ->
                         IntStream.of(student.getKor(), student.getEng(), student.getMath()))
                 .average()
                 .ifPresent(avg -> System.out.println(Math.round(avg * 10) / 10.0));
     }


    public void optionalFlatMap() {
        // flatMap 적용 후
        Optional.of(new Outer())
                .flatMap(o -> Optional.ofNullable(o.nested))
                .flatMap(n -> Optional.ofNullable(n.inner))
                .flatMap(i -> Optional.ofNullable(i.foo))
                .ifPresent(System.out::println);
    }

    /**
     * Null - Safe 한 Stream 생성하기
     */
    public void nullSafeStream() {
        List<String> nullList = null;

//        // NPE 발생
//        nullList.stream()
//                .filter(str -> str.contains("a"))
//                .map(String::length)
//                .forEach(System.out::println); // NPE!

    // 빈 Stream으로 처리
        collectionToStream(nullList)
                .filter(str -> str.contains("a"))
                .map(String::length)
                .forEach(System.out::println); // []
    }

    public <T> Stream<T> collectionToStream(Collection<T> collection) {
//        return Optional.ofNullable(collection).map(Collection::stream).orElseGet(Stream::empty);
        return Optional.ofNullable(collection).stream().flatMap(Collection::stream);
    }

    /**
     * Stream API의 실행순서
     * 첫번째 stream을 예상하면 filter: a.b.c.d.e 후의 forEach가 나올것으로 예상되지만 stream은 수직수행으로 aa, bb ,cc 가 출력된다.
     * 그 이유는 두번쨰의 stream을 보면 알 수 있다. 만약 수평 수행으로 첫번째 반복문을 다 돌고 두번쨰 반복문을 돌게 되면 대문자로 변환된 알파벳중에서 A인것을 찾을때
     * 첫번째 반복문 5번 후 두번째 반복문 1번을 해서 6번을 실행 할 것이다.
     * 실행시켜보면 첫번째 map 1번 두전째 anyMatch 1번해서 2번실행이 된다.
     * 이러한 처리 방식은 각각의 원소에 대해 실제로 실행되는 연산의 수를 줄여준다.
     */
    public void streamAPI() {
        Stream.of("a", "b", "c", "d", "e")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                })
                .forEach(s -> System.out.println("forEach: " + s));
        AtomicInteger k = new AtomicInteger();

        for (int j = 0; j < 10; j++) {
            k.getAndIncrement();
            System.out.println("k = " + k);
        }

        AtomicInteger i = new AtomicInteger();
        System.out.println("i: " + i);
        Stream.of("a", "b", "c", "d", "e")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .anyMatch(s -> {
                    System.out.println("anyMatch: " + s);
                    System.out.println("i: " + i.getAndIncrement());
                    return s.startsWith("A");
                });
    }

    public void streamAPI2() {
        Stream.of("a", "b", "c", "d", "e")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("A");
                })
                .forEach(s -> System.out.println("forEach: " + s));

        Stream.of("a", "b", "c", "d", "e")
                .filter(s -> {
                    System.out.println("filter2: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("map2: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach2: " + s));
    }

    /**
     * reduce
     * Reduce는 누산기(Accumulator)와 연산(Operation)으로 컬렉션에 있는 값을 처리하여 더 작은 컬렉션이나 단일 값을 만드는 작업이다.
     * 예를 들어 다음과 같이 List<Integer>에서 총합을 구하는 연산은 sum 함수 말고 reduce로 처리할 수 있다.
     */
    public void reduceAccumulator() {
        OptionalInt reduced = IntStream.range(1, 4) // [1, 2, 3]
                .reduce((a, b) -> {
                    return Integer.sum(a, b);
                });
        System.out.println("reduced = " + reduced);
    }
    public static void main(String[] args){

        new Main().reduceAccumulator();
    }

    static class Outer {
        Nested nested;
    }

    static class Nested {
        Inner inner;
    }

    static class Inner {
        String foo;
    }
}
