/**
 * FileName : TextFileReader.java
 * Created  : 2021. 3. 2.
 * Author   : hokkk
 * Summary  :
 * Copyright (C) 2021 Goldy Project Inc. All rights reserved.
 * 이 문서의 모든 저작권 및 지적 재산권은 Goldy Project에게 있습니다.
 * 이 문서의 어떠한 부분도 허가 없이 복제 또는 수정 하거나, 전송할 수 없습니다.
 */
package com.goldyproject.testqube.tools;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.goldyproject.testqube.exception.InspectionException;
import com.goldyproject.testqube.exception.LogicError;

import lombok.NonNull;

/**
 * @author hokkk
 */
public class TextFileReader implements Iterable<String> {

	private final File file;

	private final URI uri;

	private long size;

	private long allLineCount;

	/**
	 * TextFileReader 클래스의 새 인스턴스를 초기화 합니다.
	 */
	public TextFileReader(@NonNull File file) {

		this.file = file;
		if (file.exists() == false) {
			throw new InspectionException("파일이 존재하지 않음");
		}
		this.uri = file.toURI();
		this.refreshLine();
	}

	/**
	 * 밑에서 10개 출력 (로그 파일 경우)
	 */
	public List<String> endLine(long lineCount) {

		if (this.size != this.file.length()) {
			this.refreshLine();
		}

		return this.stream()
				.skip(this.allLineCount - lineCount)
				.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<String> iterator() {

		long size = this.size;
		return new Iterator<String>() {

			long cursor = 0;

			@Override
			public boolean hasNext() {

				return this.cursor != size;
			}

			@Override
			public String next() {

				String lineText = TextFileReader.this.lineOf(this.cursor).orElseThrow(LogicError::new);
				this.cursor++;
				return lineText;
			}
		};
	}

	/**
	 * 라인 수에 해당하는 한줄
	 */
	public Optional<String> lineOf(long lineNumber) {

		return this.stream()
				.skip(lineNumber)
				.limit(1)
				.findFirst();
	}

	/**
	 * 100만줄 부터 10줄 출력
	 */
	public List<String> range(long start, long size) {

		return this.stream()
				.skip(start)
				.limit(size)
				.collect(Collectors.toList());

	}

	/**
	 * 파일 변동이 있는 경우 전체 라인 수를 다시 개산
	 */
	private void refreshLine() {

		this.allLineCount = this.stream().count();
		this.size = this.file.length();
	}

	public Stream<String> stream() {

		try {
			return Files.lines(Paths.get(this.uri), Charset.defaultCharset());
		} catch (IOException e) {
			throw new LogicError("이 오류가 발생한다면 개발자의 실수임", e);
		}
	}

}
