package model;

import static util.Utililty.generateRandomBookJson;

public class CreateBookBody {

    public static String getCreateBookBody(String title,String author,String isbn,String releaseDate) {
        return "{\n" +
                "    \"title\": \"" + title+"\",\n"+
                 "    \"author\": \"" + author+"\",\n" +
                 "    \"isbn\": \"" +isbn+"\",\n" +
                "    \"releaseDate\": \"" +releaseDate+"\"\n" +
       "}\n";
    }

    public static String getCreateHouseholdBody(String name) {
        return "{\n" +
                "    \"name\": \"" +name+"\"\n" +
                "}\n";
    }

    public static String getCreateUserBody(String firstName,String lastName,String email) {
        return "{\n" +
                "    \"firstName\": \"" + firstName+"\",\n" +
                "    \"lastName\": \"" +lastName+"\",\n" +
                "    \"email\": \"" +email+"\"\n" +
                "}\n";
    }

    public static String getCreateWishlistBody(String firstName,String lastName,String email) {
        return "{\n" +
                "    \"firstName\": \"" + firstName+"\",\n" +
                "    \"lastName\": \"" +lastName+"\",\n" +
                "    \"email\": \"" +email+"\"\n" +
                "}\n";
    }
}
