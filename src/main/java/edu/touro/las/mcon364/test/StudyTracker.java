package edu.touro.las.mcon364.test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class StudyTracker {

    private final Map<String, List<Integer>> scoresByLearner = new HashMap<>();
    private final Deque<UndoStep> undoStack = new ArrayDeque<>();
    // Helper methods already provided for tests and local inspection.
    public Optional<List<Integer>> scoresFor(String name) {
        return Optional.ofNullable(scoresByLearner.get(name));
    }
    // I added this here so I can use it in all my methods:
    private Predicate<String> exists = student-> learnerNames().contains(student);
    public Set<String> learnerNames() {
        return scoresByLearner.keySet();
    }
    /**
     * Problem 11
     * Add a learner with an empty score list.
     *
     * Return:
     * - true if the learner was added
     * - false if the learner already exists
     *
     * Throw IllegalArgumentException if name is null or blank.
     */
    public boolean addLearner(String name) {

        Consumer<String> func = student-> scoresByLearner.put(name, new ArrayList<>());
        if (exists.test(name)){
            return false;
        }
        func.accept(name);
        return true;
    }

    /**
     * Problem 12
     * Add a score to an existing learner.
     *
     * Return:
     * - true if the score was added
     * - false if the learner does not exist
     *
     * Valid scores are 0 through 100 inclusive.
     * Throw IllegalArgumentException for invalid scores.
     *
     * This operation should be undoable.
     */
    public boolean addScore(String name, int score) {

        if(score<0 || score>100) {
            throw new IllegalArgumentException("Invalid Score");
        }
        if(exists.test(name)) {
            scoresByLearner.get(name).add(score);
            return exists.test(name);
        }
        return exists.test(name);
    }

    /**
     * Problem 13
     * Return the average score for one learner.
     *
     * Return Optional.empty() if:
     * - the learner does not exist, or
     * - the learner has no scores
     */
    public Optional<Double> averageFor(String name) {
        Optional<Double> avg =  Optional.empty();
        // if learner exists and has scores:
        if (exists.test(name) && !scoresByLearner.get(name).isEmpty()){
            int numGrades = scoresByLearner.get(name).size();
            double sumGrades = scoresByLearner.get(name).stream().reduce(0,Integer::sum);
            avg = Optional.of(sumGrades / numGrades);
        }
        return avg;
    }

    /**
     * Problem 14
     * Convert a learner average into a letter band.
     *
     * A: 90+
     * B: 80-89.999...
     * C: 70-79.999...
     * D: 60-69.999...
     * F: below 60
     *
     * Return Optional.empty() when no average exists.
     */
    public Optional<String> letterBandFor(String name) {
        Optional<String> letterBand = Optional.empty();

        if (exists.test(name) && averageFor(name).isPresent()){
            Double avg = averageFor(name).get();
            String grade = switch (avg){
                case Double d when d >= 90->{
                    yield "A";
                }
                case Double d when d >= 80 && d < 90 -> {
                    yield "B";
                }
                case Double d when d >=70 && d<80 ->{
                    yield "C";
                }
                case  Double d when d >= 60 && d < 70 ->{
                    yield "D";
                }
                default -> "F";
            };
            letterBand = Optional.of(grade);
        }
        return letterBand;
    }

    /**
     * Problem 15
     * Undo the most recent state-changing operation.
     *
     * Return true if something was undone.
     * Return false if there is nothing to undo.
     */
    public boolean undoLastChange() {
        Predicate<Deque<UndoStep>> undoTest = deq -> !deq.isEmpty();
        if (undoTest.test(undoStack)) {
            UndoStep lastStep = undoStack.pop();
            lastStep.undo();
            return true;
        }
        return false;
    }

}
