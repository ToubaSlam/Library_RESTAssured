package pojo;

public class CreateWishlist {

    public String getFirstName() {
        return firstName;
    }

    public CreateWishlist setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public CreateWishlist setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CreateWishlist setEmail(String email) {
        this.email = email;
        return this;
    }

    String firstName;
    String lastName;
    String email;
}
