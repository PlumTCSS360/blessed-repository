package model;

/**
 * The Person class will store a name and an email to represent the necessary information regarding all Persons on the
 * AboutFrame. A Person is an immutable Object.
 * @author Devin Peevy
 * @version 0.1
 */
public final class Person {

    // INSTANCE FIELDS

    /** The name of the Person. */
    String name;

    /** The email address of the person */
    String email;

    // CONSTRUCTORS

    /**
     * This constructor creates a new Person object.
     * @param name The name of the Person.
     * @param email The email address of the Person.
     */
    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // GETTERS

    /**
     * @return the name of the Person.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the email of the Person.
     */
    public String getEmail() {
        return email;
    }

    // OTHER INSTANCE METHODS

    @Override
    public String toString() {
        return name + ", " + email;
    }
}
