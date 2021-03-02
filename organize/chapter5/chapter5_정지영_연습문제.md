# 스트림API 연산 연습문제

```java
public class Trader {
	private final String name;
	private final String city;
	
	public Trader(String n, String c) {
		this.name = n;
		this.city = c;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public String toString() {
		return "Trader: "+this.name+" in "+this.city;
	}
}

public class Transaction {
	private final Trader trader;
	private final int year;
	private final int value;
	
	public Transaction(Trader trader, int year, int value) {
		this.trader = trader;
		this.year = year;
		this.value = value;
	}

	public Trader getTrader() {
		return trader;
	}

	public int getYear() {
		return year;
	}

	public int getValue() {
		return value;
	}
	
	public String toString() {
		return "{"+ this.trader + ", " + 
				"year: " + this.year +", " + 
				"value: " + this.value + "}";
	}
}

public class ExampleMain {
	public static void main(String[] args) {
		Trader raoul = new Trader("Raoul", "Cambridge");
		Trader mario = new Trader("Mario", "Milan");
		Trader alan = new Trader("Alan", "Cambridge");
		Trader brian = new Trader("Brian", "Cambridge");
		
		List<Transaction> transactions = Arrays.asList(
				new Transaction(brian, 2011, 300),
				new Transaction(raoul, 2012, 1000),
				new Transaction(raoul, 2011, 400),
				new Transaction(mario, 2012, 710),
				new Transaction(mario, 2012, 700),
				new Transaction(alan, 2012, 950)
				);
		
		
		// #1. 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름찬순으로 정렬하시오.
		List<Transaction> answerList1 = transactions.stream()
                                                .filter(t -> t.getYear() == 2011)
                                                .sorted(Comparator.comparing(Transaction::getValue))
                                                .collect(Collectors.toList());
		answerList1.stream().forEach(System.out::println);
		
		// #2. 거래자가 근무하는 모든 도시를 중복없이 나열하시오.
		List<String> answerList2 = transactions.stream()
                                           .map(t -> t.getTrader().getCity())
                                           .distinct()
                                           .collect(Collectors.toList());
		answerList2.stream().forEach(System.out::println);
		
		// #3. 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.
		List<String> answerList3 = transactions.stream()
                                           .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
                                           .map(t -> t.getTrader().getName())
                                           .sorted()
                                           .collect(Collectors.toList());
		answerList3.stream().forEach(System.out::println);
		
		// #4. 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오.
		List<String> answerList4 = transactions.parallelStream()
		                                       .map(t -> t.getTrader().getName())
		                                       .distinct()
		                                       .sorted()
		                                       .collect(Collectors.toList());
		answerList4.stream().forEach(System.out::println);
		
		// #5. 밀라노에 거래자가 있는가?
		boolean isMilanoTrader = transactions.stream()
		                                     .filter(t -> "Milan".equals(t.getTrader().getCity()))
		                                     .findAny()
		                                     .isPresent();
		System.out.println("Is MilanoTrader exist? "+isMilanoTrader);
		
		// #6. 케임브리지에 거주하는 거래자의 모든 트랜잭션 값을 출력하시오.
		transactions.stream()
                .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
                .map(t -> t.getValue())
                .forEach(System.out::println);
		
		// #7. 전체 트랜잭션 중 최댓값은 얼마인가?
		Optional<Integer> maxTransactionValue = transactions.stream().map(Transaction::getValue).reduce(Integer::max);
		System.out.println(">>>>>maxTransactionValue : "+ maxTransactionValue);
		// #8. 전체 트랜잭션 중 최솟값은 얼마인가?
		Optional<Integer> minTransactionValue = transactions.stream().map(Transaction::getValue).reduce(Integer::min);
		System.out.println(">>>>>minTransactionValue : "+ minTransactionValue);
	}
}
```
