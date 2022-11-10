package com.example.stream.basicstream;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    List<Temp> temps = Arrays.asList(
            new Temp(1L, "potatoes"),
            new Temp(2L, "orange"),
            new Temp(3L, "lemon"),
            new Temp(4L, "bread"),
            new Temp(5L, "sugar"));

    /**
     * collection stream
     */
    public void collectionStream() {
        List<String> list = Arrays.asList("a", "b", "c");
        Stream<String> listStream = list.stream();
        System.out.println("listStream = " + listStream.collect(Collectors.toList()));
    }

    /**
     * array
     */
    public void arrayStream() {
        Stream<String> stream1 = Stream.of("a", "b", "c");
        Stream<String> stream2 = Stream.of("a", "b", "c");
        Stream<String> stream3 = Arrays.stream(new String[]{"a", "b", "c"});
        Stream<String> stream4 = Arrays.stream(new String[]{"a", "b", "c"}, 0, 3);
    }

    /**
     * 원시 스트림
     */

    public void stream3() {
        IntStream stream = IntStream.range(4, 10);
    }

    /**
     * 필터링
     * list의 name이 a인것을 steam으로 만든다.
     */
    public void filterStream() {
        List<String> list = Arrays.asList("a", "b", "c");
        Stream<String> stream = list.stream().filter(name -> name.contains("a"));
        System.out.println("stream = " + stream.collect(Collectors.toList()));
    }

    /**
     * names 리스트에있는 것들을 대문자로 변경한다.
     * 저장된 값을 특정한 형태로 변환하는데 주로 사용된다.
     * map = 데이터 변환
     * map 함수의 람다식은 메소드 참조가 가능함.
     */
    public void mapStream() {
        List<String> names = Arrays.asList("a", "b", "c");
        Stream<String> stream = names.stream().map(String::toUpperCase);
        System.out.println("stream = " + stream.collect(Collectors.toList()));

        Stream<File> fileStream = Stream.of(new File("Test1.java"), new File("Test2.java"), new File("Test3.java"));

//Stream<File> --> Stream<String> 변환
        Stream<String> fileNameStream = fileStream.map(File::getName);
        System.out.println("fileNameStream = " + fileNameStream.collect(Collectors.toList()));
    }

    /**
     * 정렬 sorted
     */
    public void sortedStream() {
        List<String> list = Arrays.asList("A", "Java", "Scala", "Groovy", "Python", "Go", "Swift", "B");

        Stream<String> stream = list.stream().sorted();  // 오름차순 [Go, Groovy, Java, Python, Scala, Swift]

        Stream<String> stream2 = list.stream().sorted(Comparator.reverseOrder());   // 내림차순 [Swift, Scala, Python, Java, Groovy, Go]
        System.out.println("stream = " + stream.collect(Collectors.toList()));
        System.out.println("stream2 = " + stream2.collect(Collectors.toList()));
    }

    /**
     * 중복 제거 및 정렬
     */
    public void distinctStream() {
        List<String> list = Arrays.asList("Java", "Scala", "Groovy", "Python", "Go", "Swift", "Java", "A", "A", "A", "A");

        Stream<String> stream = list.stream().distinct().sorted();  // [Java, Scala, Groovy, Python, Go, Swift]
        System.out.println("stream = " + stream.collect(Collectors.toList()));
    }

    /**
     * 클래스 스트림
     * equals and hashcode 를 재정의 해주지 않으면 서로 다른 해쉬코드를 가지기 때문에 distinct(중복제거)가 되지 않으므로
     * equals and hashcode 를 재정의 해주어야 한다.
     */
    public void classStream() {

        Temp e1 = new Temp(1L, "Kim ji su");
        Temp e2 = new Temp(1L, "Kim ji su");
        Temp e3 = new Temp(1L, "Kim ji su");
        List<Temp> temps = new ArrayList<>();
        temps.add(e1);
        temps.add(e2);

        boolean contains = temps.contains(e3);
        System.out.println("contains = " + contains);

        Set<Temp> set = new HashSet<>();
        set.add(e1);
        set.add(e2);
        System.out.println("set = " + set.size());

        int size = (int) temps.stream().distinct().count();
        System.out.println(size);
    }

    /**
     * peek : 특정 연산 수행 (확인해본다는 뜻)
     * Stream 의 요소들을 대상으로 Stream 에 영향을 주지 않고 특정 연산을 수행하기 위한 peek 함수가 존재한다.
     * '확인해본다'라는 뜻을 지닌 peek 단어처럼, peek 함수는 Stream 의 각각의 요소들에 대해 특정 작업을 수행할 뿐 결과에 영향을 주지 않는다.
     * 또한 peek 함수는 파라미터로 함수형 인터페이스 Consumer 를 인자로 받는다.
     * 예를 들어 어떤 stream 의 요소들을 중간에 출력하기를 원할 때 다음과 같이 활용할 수 있다.
     */
    public void peekStream() {
        int sum = IntStream.of(1, 3, 5, 7, 9)
                .peek(System.out::println)
                .sum();
    }

    /**
     * 작업을 하다 보면 일반적인 Stream 객체를 원시 Stream 으로 바꾸거나 그 반대로 하는 작업이 필요한 경우가 있다.
     * 이러한 경우를 위해서, 일반적인 Stream 객체는 mapToInt(), mapToLong(), mapToDouble()이라는 특수한 Mapping 연산을 지원하고 있으며, 그 반대로 원시객체는 mapToObject 를 통해 일반적인 Stream 객체로 바꿀 수 있다.
     */
    public void primitiveTypeToPrimitiveTypeStream() {
        // IntStream -> Stream<Integer>
        Stream<String> stringStream = IntStream.range(1, 4)
                .mapToObj(i -> "a" + i);
        System.out.println("stringStream = " + stringStream.collect(Collectors.toList()));


// Stream<Double> -> IntStream -> Stream<String>
        Stream<String> stringStream1 = Stream.of(1.0, 2.0, 3.0)
                .mapToInt(Double::intValue)
                .mapToObj(i -> "a" + i);
        System.out.println("stringStream1 = " + stringStream1.collect(Collectors.toList()));
    }

    /**
     * 데이터 수집 - collect
     * collect() : 스트림의 최종연산, 매개변수로 Collector 를 필요로 한다.
     * Collector : 인터페이스, collect 의 파라미터는 이 인터페이스를 구현해야한다.
     * Collectors : 클래스, static 메소드로 미리 작성된 컬렉터를 제공한다.
     */
    public void toListStream() {

        // Collectors.toList()
        List<String> nameList = temps.stream()
                .map(Temp::getName)
                .collect(Collectors.toList());
        System.out.println("nameList = " + nameList);
    }

    /**
     * Stream 에서 작업한 결과를 1개의 String 으로 이어붙이기를 원하는 경우에 Collectors.joining()을 이용할 수 있다. Collectors.joining()은 총 3개의 인자를 받을 수 있는데, 이를 이용하면 간단하게 String 을 조합할 수 있다.
     * <p>
     * delimiter : 각 요소 중간에 들어가 요소를 구분시켜주는 구분자
     * prefix : 결과 맨 앞에 붙는 문자
     * suffix : 결과 맨 뒤에 붙는 문자
     */
    public void joiningStream() {
        String listToString1 = temps.stream()
                .map(Temp::getName)
                .collect(Collectors.joining());
        // potatoesorangelemonbreadsugar
        System.out.println("listToString1 = " + listToString1);

        String listToString2 = temps.stream()
                .map(Temp::getName)
                .collect(Collectors.joining(" "));
        // potatoes orange lemon bread sugar
        System.out.println("listToString2 = " + listToString2);

        String listToString3 = temps.stream()
                .map(Temp::getName)
                .collect(Collectors.joining(", ", "<", ">"));
        // <potatoes, orange, lemon, bread, sugar>
        System.out.println("listToString3 = " + listToString3);
    }

    /**
     * Collectors.averagingInt(), Collectors.summingInt(), Collectors.summarizingInt()
     * Stream에서 작업한 결과의 평균값이나 총합 등을 구하기 위해서는 Collectors.averagingInt()와 Collectors.summingInt()를 이용할 수 있다.
     * 물론 총합의 경우 이를 구현할 수 있는 방법이 그 외에도 많이 있다.
     */
    public void avgAndSum() {
        Double averageId = temps.stream().collect(Collectors.averagingLong(Temp::getId));
        System.out.println("averageId = " + averageId);
// 86
        Long summingId = temps.stream().collect(Collectors.summingLong(Temp::getId));
        System.out.println("summingId = " + summingId);

// 86
        Long summingId2 = temps.stream().mapToLong(Temp::getId).sum();
        System.out.println("summingId2 = " + summingId2);

        LongSummaryStatistics statistics = temps.stream()
                .collect(Collectors.summarizingLong(Temp::getId));
        System.out.println("statistics = " + statistics);
        //IntSummaryStatistics {count=5, sum=15, min=1, average=3.000000, max=5}
        //위의 작업들을 LongSummaryStatistics 으로 한번에 받아 올 수 있다.
        //statistics.get 해서 값으 꺼내와서 쓰면 된다.
    }

    /**
     * Collectors.groupingBy()
     */
    public void groupByStream() {

        Map<Long, List<Temp>> collectorMapOfLists = temps.stream()
                .collect(Collectors.groupingBy(Temp::getId));
        /*
        {1=[Temp{id=1, name='potatoes'}], 2=[Temp{id=2, name='orange'}], 3=[Temp{id=3, name='lemon'}], 4=[Temp{id=4, name='bread'}], 5=[Temp{id=5, name='sugar'}]}
         */
        System.out.println("collectorMapOfLists = " + collectorMapOfLists);
//        collectorMapOfLists.forEach((aLong, temps1) -> System.out.println("aLong = " + aLong + " temps1= " + temps1.toString()));
    }

    /**
     * 위의 그룹바이가 특정 값을 기준으로 stream내의 요소들을 그룹핑하였다면
     * partitioningBy 은 Boolean 을 key 값으로 partitioning 한다.
     */
    public void booleanPartitioningBy() {
        Map<Boolean, List<Temp>> mapPartitioned = temps.stream()
                .collect(Collectors.partitioningBy(p -> p.getId() >= 3));
        /*
        {false=[Product{amount=14, name='orange'}, Product{amount=13, name='lemon'}, Product{amount=13, name='sugar'}],
         true=[Product{amount=23, name='potatoes'}, Product{amount=23, name='bread'}]}
         */
        System.out.println("mapPartitioned = " + mapPartitioned);
        System.out.println("mapPartitioned = " + mapPartitioned.get(true));
    }

    /**
     * stream의 요소들이 특정한 조건을 충족하는지 검사하고 싶을 경우 사용
     * anyMatch: 1개의 요소라도 해당 조건을 만족하는가
     * allMatch: 모든 요소가 해당 조건을 만족하는가
     * nonMatch: 모든 요소가 해당 조건을 만족하지 않는가
     */
    public void matchStream() {
        List<String> names = Arrays.asList("Eric", "Elena", "Java");

        boolean anyMatch = names.stream()
                .anyMatch(name -> name.contains("a"));
        boolean allMatch = names.stream()
                .allMatch(name -> name.length() > 3);
        boolean noneMatch = names.stream()
                .noneMatch(name -> name.endsWith("s"));
        System.out.println("anyMatch = " + anyMatch);
        System.out.println("allMatch = " + allMatch);
        System.out.println("noneMatch = " + noneMatch);
    }

    public void forEachStream() {
        List<String> names = Arrays.asList("Eric", "Elena", "Java");
        names.forEach(System.out::println);
    }

    /**
     * FlatMap
     * 처리해야할 데이터가 2중배열 또는 2중 리스트라고 가정하자.
     * 이를 1차원적으로 처리를해야한다면 map을 이용해도 2중 스트림이 된다.
     * 이를 위한 중간 연산이 FlatMap이다.
     *
     */
    public void flatMapStream() {
        // flatMap 함수

        // [[a], [b]]
        List<List<String>> list = Arrays.asList(List.of("a"), List.of("b"));

        // [a, b]
        List<String> flatList = list.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        System.out.println("flatList = " + flatList);

        Stream<String[]> strStream = Stream.of(
                new String[] {"a", "b", "c"},
                new String[] {"d", "e", "f"});

        // map을 사용하면 2중 Stream이 반환됨
//        Stream<Stream<String>> stream = strStream.map(Arrays::stream);

        // flatMap을 사용하면 1중 Stream으로 차원을 낮출 수 있음
        Stream<String> stream2 = strStream.flatMap(Arrays::stream);
        System.out.println("stream2 = " + stream2.collect(Collectors.toList()));
    }
    public static void main(String[] args) {
        new Main().classStream();
    }
}
