import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count = 0;
        boolean invalidCount = false;
        String message = "Некорректное число!";
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество людей: ");
        try {
            count = scanner.nextInt();
        } catch (InputMismatchException e) {
            invalidCount = true;
        }
        if (invalidCount || count < 1) {
            throw new IllegalArgumentException(message);
        }
        for (int i = 0; i < count; i++) {
            System.out.print("Введите данные: ");
            String inputString = new Scanner(System.in).nextLine();
            String[] fields = inputString.split(" ");
            if (fields.length != 6) {
                message = String.format("Введено параметров: %d, необходимо: 6", fields.length);
                throw new IllegalArgumentException(message);
            }
            try {
                LocalDate.parse(fields[3], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } catch (DateTimeParseException e) {
                message = "Некорректная дата: " + fields[3];
                throw new IllegalArgumentException(message);
            }
            try {
                Long.parseLong(fields[4]);
            } catch (NumberFormatException e) {
                message = "Некорректный номер телефона: " + fields[4];
                throw new IllegalArgumentException(message);
            }
            if (!(fields[5].equals("m") || fields[5].equals("f"))) {
                message = "Некорректный пол: " + fields[5];
                throw new IllegalArgumentException(message);
            }
            try {
                FileWriter fileWriter = new FileWriter(fields[0] + ".txt", true);
                for (String field : fields) {
                    fileWriter.write("<" + field + ">");
                }
                fileWriter.write("\n");
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}