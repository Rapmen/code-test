package com.code.test;

import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DictionaryTests {

    @Test
    void breakWordByDefaultDict() {
        log.info("##breakWordByDefaultDict start##");

        Dictionary dict = new Dictionary();
        WordBreaker breaker = new WordBreaker();
        String input1 = "ilikeicecreamandmango";
        log.info("Input1: " + input1);
        Set<String> result1 = breaker.breakWord(input1, dict);
        log.info("Result1: " + result1);
        Assert.isTrue(result1.contains("i like ice cream and man go"), "Sentence is not match.");

        String input2 = "ilikesamsungmobile";
        log.info("Input2: " + input2);
        Set<String> result2 = breaker.breakWord(input2, dict);
        log.info("Result2: " + result2);
        Assert.isTrue(result2.contains("i like samsung mobile"), "Sentence is not match.");
        log.info("##breakWordByDefaultDict end##");
    }

    @Test
    void breakWordByCustomizedDictAll() {
        log.info("##breakWordByCustomizedDict start##");
        Dictionary dict = new Dictionary();
        dict.setWords(Arrays.asList("i", "like", "sam", "sung", "mobile", "icecream", "man go", "mango"), false);
        WordBreaker breaker = new WordBreaker();
        String input1 = "ilikeicecreamandmango";
        log.info("Input1: " + input1);
        Set<String> result1 = breaker.breakWord(input1, dict);
        log.info("Result1: " + result1);
        log.info("##breakWordByCustomizedDict end##");
    }

    @Test
    void breakWordByCustomizedDictOnly() {
        log.info("##breakWordByCustomizedDictOnly start##");
        Dictionary dict = new Dictionary();
        dict.setWords(Arrays.asList("i", "like", "sam", "sung", "mobile", "icecream", "man go", "mango"), true);
        WordBreaker breaker = new WordBreaker();
        String input1 = "ilikeicecreamandmango";
        log.info("Input1: " + input1);
        Set<String> result1 = breaker.breakWord(input1, dict);
        log.info("Result1: " + result1);
        Assert.isTrue(result1.contains("i like icecream and man go"), "Sentence is not match.");
        Assert.isTrue(result1.contains("i like icecream and mango"), "Sentence is not match.");

        String input2 = "ilikesamsungmobile";
        log.info("Input2: " + input2);
        Set<String> result2 = breaker.breakWord(input2, dict);
        log.info("Result2: " + result2);
        Assert.isTrue(result2.contains("i like sam sung mobile"), "Sentence is not match.");
        log.info("##breakWordByCustomizedDictOnly end##");
    }
}
