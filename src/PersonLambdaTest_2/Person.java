package PersonLambdaTest_2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Person {
    private int personId;
    private String name;
    private Date birthDate;
    private Gender gender;

    public Person(int personId, String name, Date birthDate, Gender gender) {
        super();
        this.personId = personId;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public boolean isMale() {
        return this.gender == Gender.MALE;
    }

    public boolean isFemale() {
        return this.gender == Gender.FEMALE;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        StringBuilder str = null;
        str = new StringBuilder();
        str.append("PersonLambdaTest_2.Person Id:- " + getPersonId() + " Gender:- " + getGender() + " Name:- " + getName() +
                " Birthdate:- " + dateFormat.format(getBirthDate()));
        return str.toString();
    }

    public static enum Gender {
        MALE,
        FEMALE
    }

}