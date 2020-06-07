package com.code.test;

import java.util.*;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Dictionary {

    @Getter
    private List<String> words;

    @Setter
    @Getter
    private HashMap<String, Set<String>> index;

    public Dictionary() {
        words = new ArrayList<>();
        words.addAll(Arrays.asList("i", "like", "sam", "sung", "samsung", "mobile", "ice", "cream", "man go"));
        buildIndex();
    }

    public void buildIndex() {
        log.info("#createIndex start#");
        this.index = new HashMap<>();
        for (String w : words) {
            String initial = w.substring(0, 1);
            if (this.index.containsKey(initial)) {
                this.index.get(initial).add(w);
            } else {
                Set<String> temp = new HashSet<>();
                temp.add(w);
                this.index.put(initial, temp);
            }
        }
        log.info("Index: " + this.index.toString());
        log.info("#createIndex end#");
    }

    public void setWords(List<String> newWords, boolean override) {
        if (override) {
            this.words = newWords;
        } else {
            this.words.addAll(newWords);
        }
        buildIndex();
    }

}
