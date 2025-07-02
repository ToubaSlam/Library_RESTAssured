package pojo;

public class CreateUser {

    public String getFirstName() {
        return firstName;
    }

    public CreateUser setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public CreateUser setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CreateUser setEmail(String email) {
        this.email = email;
        return this;
    }

    String firstName;
    String lastName;
    String email;
}
