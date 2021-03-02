package com.goldyproject.testqube.tools;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

class TextFileReaderTest {

	private URI uri;

	@BeforeEach
	void before() throws URISyntaxException {

		this.uri = this.getClass().getResource("testqube.2021-02-28.log").toURI();
	}

	/**
	 * {@link TextFileReader#lineOf(long)}
	 */
	@Test
	void test1() {

		System.out.println("test1");
		TextFileReader target = new TextFileReader(new File(this.uri));
		StopWatch sw = new StopWatch();
		sw.start();
		System.out.println(target.lineOf(5000).get());
		sw.stop();
		System.out.println(sw.getLastTaskTimeMillis() / 1000D); // 0.003초
	}

	/**
	 * {@link TextFileReader#range(long, long)}
	 */
	@Test
	void test2() {

		System.out.println("test2");
		TextFileReader target = new TextFileReader(new File(this.uri));
		StopWatch sw = new StopWatch();
		sw.start();
		target.range(1235019, 10);
		sw.stop();
		System.out.println(sw.getLastTaskTimeMillis() / 1000D); // 0.517초
	}

	/**
	 * {@link TextFileReader#endLine(long)}
	 */
	@Test
	void test3() {

		System.out.println("tes3");
		TextFileReader target = new TextFileReader(new File(this.uri));

		StopWatch sw = new StopWatch();
		sw.start();
		List<String> endLine = target.endLine(10);
		sw.stop();
		System.out.println(sw.getLastTaskTimeMillis() / 1000D); // 0.515초
	}

	/**
	 * {@link TextFileReader#iterator()}
	 */
	@Test
	void test4() {

		TextFileReader target = new TextFileReader(new File(this.uri));

		int i = 0;
		for (String line : target) {
			System.out.println(line);
			if (i == 10) {
				break;
			}
			i++;
		}
	}

	/**
	 * {@link TextFileReader#stream()}
	 */
	@Test
	void test5() {

		TextFileReader target = new TextFileReader(new File(this.uri));
		target.stream()
				.limit(10)
				.forEach(System.out::println);
	}
}
