package org.featurehouse.mctest.streams;

import java.util.stream.Stream;

public class StreamUtils {
    <T> Stream<T> withStream(Stream<T> stream) {
        return stream.peek(System.out::println);
    }
}
