# stream
java-stream-api-practice
Stream API

JDK 8부터 stream API 와 람다식, 함수형 인터페이스 등을 지원한다.

// 스트림 사용전

String[] nameArr = {"IronMan", "Captain", "Hulk", "Thor"}
List<String> nameList = Arrays.asList(nameArr);

// 원본의 데이터가 직접 정렬됨
Arrays.sort(nameArr);
Collections.sort(nameList);

for (String str: nameArr) {
  System.out.println(str);
}

for (String str : nameList) {
  System.out.println(str);
}

// 스트림 사용 후

String[] nameArr = {"IronMan", "Captain", "Hulk", "Thor"}
List<String> nameList = Arrays.asList(nameArr);

// 원본의 데이터가 아닌 별도의 Stream을 생성함
Stream<String> nameStream = nameList.stream();
Stream<String> arrayStream = Arrays.stream(nameArr);

// 복사된 데이터를 정렬하여 출력함
nameStream.sorted().forEach(System.out::println);
arrayStream.sorted().forEach(System.out::println);


[Stream API의 특징]

- 원본의 데이터를 변경하지 않는다.
- 일회용이다.  -> 한번 사용한 스트림을 사용하게 되면 IllegalStateException 예외 발생
- 내부 반복으로 작업을 처리한다. -> for나 while같은 반복문을 안쓰고 반복문을 내부에 숨기고 있어 코드가 간결해진다.

[ Java에서 제공하는 함수형 인터페이스 ]

1. Supplier<T>
2. Consumer<T>
3. Function<T, R>
4. Predicate<T>

1. Supplier <T>
Supplier는 매개변수 없이 반환값 만을 갖는 함수형 인터페이스이다.
Supplier는 T get()을 추상 메소드로 갖고 있다.


2. Consumer <T>
Consumer는 객체 T를 매개변수로 받아서 사용하며, 반환값은 없는 함수형 인터페이스이다.

Consumer는 void accept(T t)를 추상메소드로 갖는다.

또한 Consumer는 andThen이라는 함수를 제공하고 있는데, 이를 통해 하나의 함수가 끝난 후 다음 Consumer를 연쇄적으로 이용할 수 있다. 
아래의 예제에서는 먼저 accept로 받아들인 Consumer를 먼저 처리하고, andThen으로 받은 두 번째 Consumer를 처리하고 있다. 
함수형에서 함수는 값의 대입 또는 변경 등이 없기 때문에 첫 번째 Consumer가 split으로 데이터를 변경하였다 하더라도 원본의 데이터는 유지된다.


3. Function <T, R>
Function은 객체 T를 매개변수로 받아서 처리한 후 R로 반환하는 함수형 인터페이스다.

Function은 R apply(T t)를 추상메소드로 갖는다.

또한 Function은 Consumer와 마찬가지로 andThen을 제공하고 있으며, 추가적으로 compose를 제공하고 있다. 
앞에서 andThen은 첫 번째 함수가 실행된 이후에 다음 함수를 연쇄적으로 실행하도록 연결해준다고 하였다. 
하지만 compose는 첫 번째 함수 실행 이전에 먼저 함수를 실행하여 연쇄적으로 연결해준다는 점에서 차이가 있다.

또한 identity 함수가 존재하는데, 이는 자기 자신을 반환하는 static 함수이다.

4. Predicate <T>
Predicate는 객체 T를 매개 변수로 받아 처리한 후 Boolean을 반환한다.

Predicate는 Boolean test(T t)을 추상 메소드로 갖고 있다.


[ 메소드 참조(Method Reference) ]

메소드 참조란 함수형 인터페이스를 람다식이 아닌 일반 메소드를 참조시켜 선언하는 방법이다. 일반 메소드를 참조하기 위해서는 다음의 3가지 조건을 만족해야 한다.

함수형 인터페이스의 매개변수 타입 = 메소드의 매개변수 타입
함수형 인터페이스의 매개변수 개수 = 메소드의 매개변수 개수
함수형 인터페이스의 반환형 = 메소드의 반환형
참조가능한 메소드는 일반 메소드, Static 메소드, 생성자가 있으며 클래스이름::메소드이름 으로 참조할 수 있다. 
이렇게 참조를 하면 함수형 엔터페이스로 반환이 된다.


[ FlatMap ]

* FlatMap
* 처리해야할 데이터가 2중배열 또는 2중 리스트라고 가정하자.
* 이를 1차원적으로 처리를해야한다면 map을 이용해도 2중 스트림이 된다.
* 이를 위한 중간 연산이 FlatMap이다.

[ FlatMap의 동작 방식 ]

이번에는 FlatMap의 동작 방식에 대해 이해해보도록 하자.

예를 들어 ["Hello", "World"]를 갖는 String의 List가 존재한다고 할 때, 
이를 1개의 알파벳씩을 갖도록 String으로 나누고, 중복된 알파벳을 갖지 않는 List로 변환하는 작업을 한다고 하자. 
이러한 경우 Map을 이용하면 해결이 불가능하고 flatMap을 이용해야만 한다.

map을 사용할 경우

Hello가 split("")에 의해 ["H", "e", "l", "l", "o"] 로 분리되고, World가 split("")에 의해 ["W", "o", "r", "l", "d"]로 분리된다.
map에 의해 Stream의 소스가 ["H", "e", "l", "l", "o"] 와 ["W", "o", "r", "l", "d"] 로 변환된다.
distinct()에 의해 중복된 소스가 제거된다. (해당 사항 없음)
2개의  ["H", "e", "l", "l", "o"] 와 ["W", "o", "r", "l", "d"]가 collect(toList())에 의해 수집된다.

flatMap을 사용할 경우

Hello가 split("")에 의해 ["H", "e", "l", "l", "o"] 로 분리되고, World가 split("")에 의해 ["W", "o", "r", "l", "d"]로 분리된다.
Arrays.stream(T[] array)를 사용해 ["H", "e", "l", "l", "o"] 와 ["W", "o", "r", "l", "d"]를 각각 Stream<String>으로 만든다.
flatMap()을 사용해 여러 개의 Stream<String>을 1개의 Stream<String>으로 평평하게 합치고, 
Stream의 소스는 ["H", "e", "l", "l", "o", W", "o", "r", "l", "d"] 가 된다.
distinct()에 의해 중복된 소스(l, o)가 제거된다.
중복이 제거된 ["H", "e", "l", "o", "W", "r", "d"]가 collect(toList())에 의해 수집된다.

