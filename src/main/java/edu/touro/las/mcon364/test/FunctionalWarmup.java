package edu.touro.las.mcon364.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionalWarmup {

    /**
     * Problem 1
     * Return a Supplier that gives the current month number (1-12).
     */
    public static Supplier<Integer> currentMonthSupplier() {

        Supplier<Integer> sup = () -> LocalDate.now().getMonthValue();
        return sup;
    }

    /**
     * Problem 2
     * Return a Predicate that is true only when the input string
     * has more than 5 characters.
     */
    public static Predicate<String> longerThanFive() {
        Predicate<String> predicate = s -> s.length() >5;
        return predicate;
    }

    /**
     * Problem 3
     * Return a Predicate that checks whether a number is both:
     * - positive
     * - even
     *
     * Prefer chaining smaller predicates.
     */
    public static Predicate<Integer> positiveAndEven() {
        Predicate<Integer> pred = i -> i% 2==0 && i> 0;
        return pred;
    }

    /**
     * Problem 4
     * Return a Function that counts words in a string.
     *
     * Notes:
     * - Trim first.
     * - Blank strings should return 0.
     * - Words are separated by one or more spaces (use can use regex "\\s+")
     *
     */
    public static Function<String, Integer> wordCounter() {

        Function<String, String> trim = s->s.trim();
        Function<String, Integer> counter = s->{
            if (s.isBlank()){
                return 0;
            }
            int ctr = 1;
            // I know this isn't the most efficient way of doing it but I forgot how to make it into an array
            for (String string : s.split("\\s+")) {
                ctr ++;
            }
            return ctr;
        };
        return trim.andThen(counter);
    }

    /**
     * Problem 5
     * Process the input labels as follows:
     * - remove blank strings
     * - trim whitespace
     * - convert to uppercase
     * - return the final list in the same relative order
     *
     * Example:
     * ["  math ", "", " java", "  "] -> ["MATH", "JAVA"]
     */
    public static List<String> cleanLabels(List<String> labels) {
        Function<List<String>, List<String>> func = lst->{
            List<String> updated =  new ArrayList<>();
            for(String label : lst){
                if (!label.isBlank()){
                    updated.add(label.trim().toUpperCase());
                }
            }
            return updated;
        };

        return func.apply(labels);
    }
}
