package ru.otus.pro.hw1.gradle;

import com.google.common.base.CharMatcher;

public class HelloOtus {
    public static void main(String[] args) {
        String input = "Hello123World456";

        String digitsOnly = CharMatcher.anyOf("0123456789").retainFrom(input);

        System.out.println(digitsOnly);
    }
}
