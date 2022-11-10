package com.example.stream.quiz4;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Main {

    private List<Transaction> transactions;

    public Main() {
        init();
    }
    public void init() {
        Trader a = new Trader("a", "Seoul");
        Trader b = new Trader("b", "Gyeonggi");
        Trader c = new Trader("c", "Incheon");
        Trader d = new Trader("d", "Busan");
        transactions = Arrays.asList(
                new Transaction(a, 2019, 30000),
                new Transaction(b, 2020, 12000),
                new Transaction(c, 2020, 40000),
                new Transaction(c, 2020, 7100),
                new Transaction(d, 2019, 5900),
                new Transaction(d, 2020, 4900)
        );


    }

    public void answer1() {
        //2020년에 일어난 모든 거래 내역을 찾아 거래값을 기준으로 오름차순 정렬하라.
        List<Transaction> collect = transactions.stream().filter(trader -> trader.getYear() == 2020).sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());
        System.out.println("collect = " + collect);
    }

    public void answer2() {
        //거래 내역이 있는 거래자가 근무하는 모든 도시를 중복 없이 나열하라.
        List<String> collect = transactions.stream()
                .map(t -> t.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        System.out.println("collect2 = " + collect);
    }

    public void answer3() {
        //서울에서 근무하는 모든 거래자를 찾아서 이름순서대로 정렬하라.
        List<Trader> collect = transactions.stream().map(Transaction::getTrader).filter(t -> t.getCity().contains("Seoul")).sorted(Comparator.comparing(Trader::getName)).collect(Collectors.toList());
        System.out.println("collect3 = " + collect);
    }

    public void answer4() {
     //모든 거래자의 이름을 순서대로 정렬하라.
        List<Trader> collect4 = transactions.stream().map(transaction -> transaction.getTrader()).sorted(Comparator.comparing(Trader::getName)).collect(Collectors.toList());
        System.out.println("collect4 = " + collect4);
    }

    public void answer5() {
        //부산에 거래자가 있는지를 확인하라.
        boolean busan = transactions.stream().map(trader -> trader.getTrader().getCity()).anyMatch(c -> c.equals("Busan"));
        System.out.println("busan = " + busan);
    }

    public void answer6() {
        //서울에 거주하는 거래자의 모든 거래 내역을 구하라.
        List<Integer> seoul = transactions.stream().filter(trader -> trader.getTrader().getCity().equals("Seoul")).map(transaction -> transaction.getValue()).collect(Collectors.toList());
        System.out.println("seoul = " + seoul);
    }

    public void answer7() {
        //모든 거래 내역중에서 최댓값과 최솟값을 구하라. 단, 최댓값은 reduce를 이용하고 최솟값은 stream의 min()을 이용하라.
        Integer[] arr = new Integer[2];
        arr[0] = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max)
                .orElse(-1);

        arr[1] = transactions.stream()
                .min(Comparator.comparingInt(Transaction::getValue))
                .orElseThrow(RuntimeException::new).getValue();
        System.out.println("arr = " + Arrays.toString(arr));
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.init();
//        main.answer1();
//        main.answer2();
//        main.answer3();
//        main.answer4();
//        main.answer5();
        main.answer6();
        main.answer7();

    }

}
