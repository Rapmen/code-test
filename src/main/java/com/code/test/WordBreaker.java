package com.code.test;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

public class WordBreaker {

    public Set<String> breakWord(String input, Dictionary dict) {
        if (input == null) {
            throw new NullPointerException();
        }
        String[] strs = input.split("");
        StringBuilder sentence = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        StringBuilder fail = new StringBuilder();
        ArrayList<String> queue = new ArrayList<>();
        HashMap<String, Set<String>> synonyms = new HashMap<>();
        String letter;
        String nextLetter = null;
        String syn = null;
        for (int i = 0; i < strs.length; i++) {
            letter = strs[i];
            if (i < strs.length - 1) {
                nextLetter = strs[i + 1];
            }
            temp.append(letter);
            if (CollectionUtils.isEmpty(queue)) {
                if (dict.getIndex().containsKey(letter)) {
                    queue.addAll(dict.getIndex().get(letter));
                    if (fail.length() > 0) {
                        sentence.append(fail.toString());
                        if (i < strs.length - 1) {
                            sentence.append(" ");
                        }
                        fail.setLength(0);
                    }
                }

            }
            String nextWord = temp.toString() + nextLetter;
            if (queue.stream().noneMatch(q -> q.replace(" ", "").startsWith(nextWord))) {
                List<String> restWords =
                    queue.stream().filter(q -> q.replace(" ", "").equals(temp.toString())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(restWords)) {
                    String target = restWords.get(0);
                    restWords.remove(0);
                    sentence.append(target);
                    if (i < strs.length - 1) {
                        sentence.append(" ");
                    }
                    if (restWords.size() > 0) {
                        for (String others : restWords) {
                            if (synonyms.containsKey(target)) {
                                synonyms.get(target).add(others);
                            } else {
                                HashSet<String> tempSet = new HashSet<>();
                                tempSet.add(others);
                                synonyms.put(target, tempSet);
                            }
                        }
                    }
                    if (syn != null) {
                        String otherWord = target.replace(syn, "");
                        if (dict.getWords().contains(otherWord)) {
                            HashSet<String> tempSet = new HashSet<>();
                            tempSet.add(syn + " " + otherWord);
                            synonyms.put(target, tempSet);
                        }
                        syn = null;
                    }
                } else {
                    fail.append(letter);
                }
                queue.clear();
                temp.setLength(0);
            } else {
                if (queue.stream().anyMatch(q -> q.replace(" ", "").equals(temp.toString().replace(" ", "")))) {
                    syn = temp.toString();
                }
                queue = (ArrayList<String>)queue.stream().filter(q -> q.replace(" ", "").startsWith(temp.toString()))
                    .collect(Collectors.toList());

            }
        }

        Set<String> results = new HashSet<>();
        results.add(sentence.toString());
        if (!CollectionUtils.isEmpty(synonyms)) {
            for (String key : synonyms.keySet()) {
                Set<String> replacement = synonyms.get(key);
                for (String r : replacement) {
                    results.add(sentence.toString().replace(key, r));
                }
            }
        }

        return results;
    }

}
