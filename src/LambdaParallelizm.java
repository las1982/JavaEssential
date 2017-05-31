import java.util.*;

/**
 * Created by Aliaksandr on 26.05.2017.
 */
public class LambdaParallelizm {
    public static void main(String[] args) {
        Integer[] intArray = {1, 2, 3, 4, 5, 6, 7, 8};
        List<Integer> listOfIntegers =
                new ArrayList<>(Arrays.asList(intArray));

        System.out.println("listOfIntegers:");
        listOfIntegers
                .stream()
                .forEach(e -> System.out.print(e + " "));
        System.out.println("");

        System.out.println("listOfIntegers sorted in reverse order:");
        Comparator<Integer> normal = Integer::compare;
        Comparator<Integer> reversed = normal.reversed();
        Collections.sort(listOfIntegers, reversed);
        listOfIntegers
                .stream()
                .forEach(e -> System.out.print(e + " "));
        System.out.println("");

        System.out.println("Parallel stream");
        listOfIntegers
                .parallelStream()
                .forEach(e -> System.out.print(e + " "));
        System.out.println("");

        System.out.println("Another parallel stream:");
        listOfIntegers
                .parallelStream()
                .forEach(e -> System.out.print(e + " "));
        System.out.println("");

        System.out.println("With forEachOrdered:");
        listOfIntegers
                .parallelStream()
                .forEachOrdered(e -> System.out.print(e + " "));
        System.out.println("");

// ****************************** part 2
        System.out.println("part2:");
        List<Integer> serialStorage = new ArrayList<>();

        System.out.println("Serial stream:");
        listOfIntegers
                .stream()

                // Don't do this! It uses a stateful lambda expression.
                .map(e -> {
                    serialStorage.add(e);
                    return e;
                })

                .forEachOrdered(e -> System.out.print(e + " "));
        System.out.println("");

        serialStorage
                .stream()
                .forEachOrdered(e -> System.out.print(e + " "));
        System.out.println("");

        System.out.println("Parallel stream:");
        List<Integer> parallelStorage = Collections.synchronizedList(
                new ArrayList<>());
        listOfIntegers
                .parallelStream()

                // Don't do this! It uses a stateful lambda expression.
                .map(e -> {
                    parallelStorage.add(e);
                    return e;
                })

                .forEachOrdered(e -> System.out.print(e + " "));
        System.out.println("");

        parallelStorage
                .stream()
                .forEachOrdered(e -> System.out.print(e + " "));
        System.out.println("");
    }
}
