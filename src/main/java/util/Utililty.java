package util;

import com.github.javafaker.Faker;
import org.apache.poi.hpsf.Date;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Utililty {

    Random random = new Random();


    private static final String[] SAMPLE_TITLES = {
            "The Silent Sky", "Echoes of Time", "Crimson Dawn", "The Lost Path", "Shadows of Fate"
    };
    private static final String[] SAMPLE_AUTHORS = {
            "Alice Brown", "John Smith", "Karen Lee", "Michael Davis", "Sophie Turner"
    };

    // Generates a random 12-letter capital ISBN string
    public static String generateRandomISBN() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder isbn = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            isbn.append(characters.charAt(random.nextInt(characters.length())));
        }
        return isbn.toString();
    }

    // Generates a random release date between 1950 and today
    public static String generateRandomDate() {
        LocalDate startDate = LocalDate.of(1950, 1, 1);
        LocalDate endDate = LocalDate.now();

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        long randomDays = ThreadLocalRandom.current().nextLong(daysBetween + 1);

        LocalDate randomDate = startDate.plusDays(randomDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        return randomDate.format(formatter);
    }

    // Returns a full JSON string with random data
    public static String generateRandomBookJson() {
        Random random = new Random();
        String title = SAMPLE_TITLES[random.nextInt(SAMPLE_TITLES.length)];
        String author = SAMPLE_AUTHORS[random.nextInt(SAMPLE_AUTHORS.length)];
        String isbn = generateRandomISBN();
        String releaseDate = generateRandomDate();

        return "{\n" +
                "  \"title\": \"" + title + "\",\n" +
                "  \"author\": \"" + author + "\",\n" +
                "  \"isbn\": \"" + isbn + "\",\n" +
                "  \"releaseDate\": \"" + releaseDate + "\"\n" +
                "}";
    }

    public static void main(String[] args) {
        String randomBookJson = generateRandomBookJson();
        System.out.println(randomBookJson);
    }

    public static String getSingleJsonData(String jsonFilePath, String jsonKey) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = new FileReader(jsonFilePath);
        Object obj = jsonParser.parse(fileReader);

        JSONObject jsonObject = (JSONObject) obj;
        return jsonObject.get(jsonKey).toString();
    }

    public static String getExcelData(int RowNum, int ColNum, String SheetName) {
        XSSFWorkbook workBook;
        XSSFSheet sheet;
        String projectPath = System.getProperty("user.dir");
        String cellData = null;
        try {
            workBook = new XSSFWorkbook(projectPath + "src/test/resources/data/data.xlsx");
            sheet = workBook.getSheet(SheetName);
            cellData = sheet.getRow(RowNum).getCell(ColNum).getStringCellValue();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }
        return cellData;
    }

    public static String getRandomFirstName() {
        String[] firstNames = {"Liam", "Emma", "Noah", "Olivia", "Ava", "Elijah", "Sophia", "James", "Isabella", "Lucas"};
        int index = (int) (Math.random() * firstNames.length);
        return firstNames[index];

    }

    public static String generateRandomLastName() {
        String[] lastNames = {"Alice", "Bob", "Charlie", "David", "Emily", "Frank", "Grace", "Henry", "Isabella", "Jack","Amin"};
        Random random = new Random();
        int index = random.nextInt(lastNames.length);
        return lastNames[index];

    }

    public static String generateRandomFullName() {
        return getRandomFirstName() + " " + generateRandomLastName();
    }

    public static String generateRandomEmail(String domain, String separator) {
        String firstName = getRandomFirstName().toLowerCase();
        String lastName = generateRandomLastName().toLowerCase();
        int randomNum = (int) (Math.random() * 10000); // You can make it more unique with timestamp if needed
        return firstName + separator + lastName + randomNum + "@" + domain;
    }

    public static String generateRandomTitle() {
        return "Book-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public static String generateRandomAuthor() {
        return "Author-" + UUID.randomUUID().toString().substring(0, 5);
    }

    public static String generateRandomIsbn() {
        return String.valueOf(ThreadLocalRandom.current().nextLong(1000000000000L, 9999999999999L));
    }

    public static String generateRandomPastDate() {
        return LocalDate.now().minusDays(ThreadLocalRandom.current().nextInt(1, 1000)).toString();
    }

    // TODO: delete screenshots
    public static void deleteFilesInFolder(String folderPath) {
        File folder = new File(folderPath);
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        boolean isDeleted = file.delete();
                        if (isDeleted) {
                            System.out.println("Deleted: " + file.getName());
                        } else {
                            System.out.println("Failed to delete: " + file.getName());
                        }
                    }
                }
            } else {
                System.out.println("The specified folder is empty or an error occurred.");
            }
        } else {
            System.out.println("The specified path is not a folder.");
        }
    }
}



