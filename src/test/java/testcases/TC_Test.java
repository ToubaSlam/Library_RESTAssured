package testcases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.CreateBook;

import static io.restassured.RestAssured.given;
import static model.CreateBookBody.getCreateBookBody;
import static util.Utililty.*;

public class TC_Test extends TestBase {

    static JSONObject createBookBody;
    String isbn = generateRandomISBN();
    String releaseDate = "2015-03-21T00:00:00.000Z";
    String title = getRandomFirstName();
    String author = getRandomFirstName() + generateRandomLastName();
//    String author = getSingleJsonData(System.getProperty("user.dir","src/test/resources/data/testdata.json"));
//    String author1 = getExcelData(0,0,"sheet1");

    @Test(priority = 1, description = "Create new book with valid data")
    public void createNewBook() {
        //createBookBody.re("","")
        //Precondition (param, auth, headers, body)
        Response response =
                //param("name,"value")
                //.auth().basic("admin","admin")
                //.auth().digest("","")
                //header("Autherization","bearer token")
                given().log().all().header("Content-Type", "application/json")
                        .header("g-token", "ROM831ESV")
                        .body(getCreateBookBody(title, author, isbn, releaseDate))
                        .when().post("/books")
                        .then().log().all().assertThat().statusCode(201).extract().response();

        System.out.println("title" + "author" + "isbn" + "releaseDate");
        System.out.println(response.getTime());
        Assert.assertTrue(response.getTime() < 5000);
        bookID = response.jsonPath().getInt("id");
        String title = response.jsonPath().get("title");
        System.out.println("book ID: " + bookID);

    }


    @Test(priority = 2, description = "Create new book with invalid auth")
    public void createBookWithInvalidAuth_N() throws JsonProcessingException {
          //JSONObject
//        createBookBody=new JSONObject(); //this initialization have to be inside the test case
//        createBookBody.put("title",title);
//        createBookBody.put("author",author);
//        createBookBody.put("isbn",isbn);
//        createBookBody.put("releaseDate",releaseDate);
//
//        JSONArray arr=new JSONArray();
//
//        JSONObject adressess1=new JSONObject();
//        adressess1.put("adress","adress 1");
//        arr.add(adressess1);
//
//        JSONObject adressess2=new JSONObject();
//        adressess2.put("adress","adress 2");
//        arr.add(adressess2);
//
//        createBookBody.put("adressess",arr);

        //Jackson data bind
        CreateBook createBookBody = new CreateBook();
        createBookBody.setTitle(getRandomFirstName()).setAuthor(generateRandomLastName()).
                setIsbn(generateRandomISBN()).setReleaseDate(generateRandomDate());
        ObjectMapper mapper=new ObjectMapper();


        Response response=
                given().log().all().header("Content-Type", "application/json")
                        .header("g-token", "ROM831ESV")
                        .body(mapper.writeValueAsString(createBookBody))
                        .when().post("/books")
                        .then().log().all().assertThat().statusCode(201).extract().response();

        System.out.println("title" + "author" + "isbn" + "releaseDate");
        System.out.println(response.getTime());
        Assert.assertTrue(response.getTime() < 5000);
        bookID = response.jsonPath().getInt("id");
        String title = response.jsonPath().get("title");
        System.out.println("book ID: " + bookID);

    }
}
