package com.tesco;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class AppTest {
    @Test
    public void testString() {
        String[] actualString = new String[] {"a", "b"};
        String actual = "Hello, JUnit & AssertJ!";
        assertThat(actual).contains("JUnit").startsWith("Hello");
        assertThat(actualString);
    }

    @Test
    public void testInteger() {
        int actual = 123;
        // Assertions
        assertThat(actual).isGreaterThan(100).isLessThan(200);
    }
}
