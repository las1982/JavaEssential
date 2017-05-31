package PersonLambdaTest_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.function.IntConsumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Person {
    public String Name;
    public int Age;
    public Sex gender;
    public String Car;
//    private String Sex;
//        LocalDate birthday;
//        String emailAddress;

    public Person(String Name, String Age, String Sex, String Car) {
        this.Name = Name;
        this.Age = Integer.parseInt(Age);
        this.Car = Car;
        if (Sex.equals("m")) {
            this.gender = Person.Sex.MALE;
        } else this.gender = Person.Sex.FEMALE;

    }

    public enum Sex {
        MALE, FEMALE
    }

    public int getAge() {
        return Age;
    }

    public String getName() {
        return Name;
    }

    public String getCar() {
        return Car;
    }

    public Sex getGender() {
        return gender;
    }

    public static void main(String[] args) {
        Scanner sc;
        ArrayList<Person> roster = new ArrayList<Person>();
        try {
            sc = new Scanner(new File("PersonLambdaTest_1/persons.csv")).useDelimiter("\n");
            if (sc.hasNext()) sc.next();
            while (sc.hasNext()) {
                String[] el = sc.next().split("[,\\s]");
                roster.add(new Person(el[0], el[1], el[2], el[3]));
            }
        } catch (
                FileNotFoundException e)

        {
            e.printStackTrace();
        }
        System.out.println("*** All names:");
        roster
                .stream()
                .forEach(e -> System.out.println(e.getName()));

        System.out.println("*** Male's names:");
        roster
                .stream()
                .filter(e -> e.getGender() == Person.Sex.MALE)
                .forEach(e -> System.out.println(e.getName()));

        System.out.println("*** Average male's age:");
        double average = roster
                .stream()
                .filter(p -> p.getGender() == Person.Sex.MALE)
                .mapToInt(Person::getAge)
                .average()
                .getAsDouble();
        System.out.println(average);

        System.out.println("*** Sum of age:");
        Integer totalAge = roster
                .stream()
                .mapToInt(Person::getAge)
                .sum();
        System.out.println(totalAge);

        System.out.println("*** totalAgeReduce:");
        Integer totalAgeReduce = roster
                .stream()
                .map(Person::getAge)
                .reduce(
                        0,
                        (a, b) -> a + b);
        System.out.println(totalAgeReduce);

        Averager averageCollect = roster.stream()
                .filter(p -> p.getGender() == Person.Sex.MALE)
                .map(Person::getAge)
                .collect(Averager::new, Averager::accept, Averager::combine);

        System.out.println("Average age of male members: " +
                averageCollect.average());


        List<String> namesOfMaleMembersCollect = roster
                .stream()
                .filter(p -> p.getGender() == Person.Sex.MALE)
//                .map(p -> p.getName())
                .map(Person::getName)
                .collect(Collectors.toList());
        System.out.println("namesOfMaleMembersCollect: " + namesOfMaleMembersCollect);


        Map<Sex, List<Person>> byGender =
                roster
                        .stream()
                        .collect(
                                Collectors.groupingBy(Person::getGender));
        System.out.println("byGender: " + byGender);


        Map<Person.Sex, List<String>> namesByGender =
                roster
                        .stream()
                        .collect(
                                Collectors.groupingBy(
                                        Person::getGender,
                                        Collectors.mapping(
                                                Person::getName,
                                                Collectors.toList())));
        System.out.println("namesByGender: " + namesByGender);


        Map<Person.Sex, Integer> totalAgeByGender =
                roster
                        .stream()
                        .collect(
                                Collectors.groupingBy(
                                        Person::getGender,
                                        Collectors.reducing(
                                                0,
                                                Person::getAge,
                                                Integer::sum)));
        System.out.println("totalAgeByGender: " + totalAgeByGender);

//  **********************************************************
//  *** GroupBy few columns **********************************
//  **********************************************************
//  Quick and dirty solution is people.collect(groupingBy(p -> Arrays.asList(p.name, p.age)))
        Map<Person.Sex, Integer> totalAgeByGenderCar1 =
                roster
                        .stream()
                        .collect(
                                Collectors.groupingBy(
                                        Person::getGender,
                                        Collectors.reducing(
                                                0,
                                                Person::getAge,
                                                Integer::sum)));

        Map<Person.Sex, Map<Object, Long>> countAgeByGenderCar =
                roster.stream().collect(
                        Collectors.groupingBy(Person::getGender,
                                Collectors.groupingBy(Person::getCar,
                                        Collectors.mapping(Person::getAge,
                                                Collectors.counting())
                                )));
        System.out.println("countAgeByGenderCar: " + countAgeByGenderCar);

        Map<Person.Sex, Map<Object, Integer>> totalAgeByGenderCar =
                roster.stream().collect(
                        Collectors.groupingBy(Person::getGender,
                                Collectors.groupingBy(Person::getCar,
                                        Collectors.reducing(
                                                0,
                                                Person::getAge,
                                                Integer::sum)
                                )));
        System.out.println("totalAgeByGenderCar: " + totalAgeByGenderCar);

        Map<Person.Sex, Double> averageAgeByGender = roster
                .stream()
                .collect(
                        Collectors.groupingBy(
                                Person::getGender,
                                Collectors.averagingInt(Person::getAge)));
        System.out.println("averageAgeByGender: " + averageAgeByGender);

//  **********************************************************
//  *** PARALLELISM ******************************************
//  **********************************************************

        average = roster
                .parallelStream()
                .filter(p -> p.getGender() == Person.Sex.MALE)
                .mapToInt(Person::getAge)
                .average()
                .getAsDouble();
        System.out.println("average: " + average);


        ConcurrentMap<Sex, List<Person>> byGender_1 =
                roster
                        .parallelStream()
                        .collect(
                                Collectors.groupingByConcurrent(Person::getGender));
        System.out.println("byGender_1: " + byGender_1);

//  **********************************************************
//  *** ORDERING *********************************************
//  **********************************************************

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

//  **********************************************************
//  *** Interference *****************************************
//  **********************************************************
        try {
            List<String> listOfStrings =
                    new ArrayList<>(Arrays.asList("one", "two"));

            // This will fail as the peek operation will attempt to add the
            // string "three" to the source after the terminal operation has
            // commenced.

            String concatenatedString = listOfStrings
                    .stream()

                    // Don't do this! Interference occurs here.
                    .peek(s -> listOfStrings.add("three"))

                    .reduce((a, b) -> a + " " + b)
                    .get();

            System.out.println("Concatenated string: " + concatenatedString);

        } catch (Exception e) {
            System.out.println("Exception caught: " + e.toString());
        }

//  **********************************************************
//  *** Stateful Lambda Expressions **************************
//  **********************************************************
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

class Averager implements IntConsumer {
    private int total = 0;
    private int count = 0;

    public double average() {
        return count > 0 ? ((double) total) / count : 0;
    }

    public void accept(int i) {
        total += i;
        count++;
    }

    public void combine(Averager other) {
        total += other.total;
        count += other.count;
    }
}
