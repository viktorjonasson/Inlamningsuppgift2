import javax.swing.*;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IOUtil {

    public static List<Client> readFileToList(String readFromFile) {

        List<Client> allClients = new ArrayList<>();
        String[] firstLineData = new String[2];
        String firstLine;
        String secondLine = "";
        LocalDate datePaid;
        Boolean clientStatus;

        Path inFilePath = Paths.get(readFromFile);

        try (Scanner fileScanner = new Scanner(inFilePath)) {
            while (fileScanner.hasNext()) {
                //Dela upp, läs in, spara till objekt:
                firstLine = fileScanner.nextLine();
                firstLineData = firstLine.split(",");
                if (fileScanner.hasNext()) {
                    secondLine = fileScanner.nextLine().trim();
                }
                datePaid = convStringToDate(secondLine);
                clientStatus = calcActiveMembership(datePaid);
                Client c = new Client(firstLineData[0].trim(), firstLineData[1].trim(), datePaid, clientStatus);
                allClients.add(c);
            }
        } catch (NoSuchFileException e) {
            JOptionPane.showMessageDialog(null, "Filen med kunddata kunde inte hittas.");
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Fel vid inläsning av fil med kunddata.");
            e.printStackTrace();
        }

        return allClients;

    }

    public static LocalDate convStringToDate(String dateString) {
        LocalDate date = null;
        try {
            date = LocalDate.parse(dateString);
        }
        catch (DateTimeParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Boolean calcActiveMembership(LocalDate date) {
        LocalDate dateOneYearAgo = LocalDate.now().minusYears(1);
        if (date.isAfter(dateOneYearAgo)) {
            return true;
        }
        else{
            return false;
        }
    }

    public static String validateInputAndCheckClient(List<Client> list, String userInput) {
        String userMessage = null;
        try {
            validateList(list);
            userMessage = checkClient(list, userInput.trim());
        }
        catch (NullPointerException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ett oväntat fel inträffade: " + e.getMessage());
            e.printStackTrace();
        }
        return userMessage;
    }

    public static void validateList(List<Client> list) throws NullPointerException {
        if (list.isEmpty()) {
            throw new NullPointerException("Listan med kunder är tom.");
        }
    }

    public static String checkClient(List<Client> list, String userInput) {
        String userMessage = "Personen finns inte i registret och är obehörig.";

        for (Client client : list) {
            if (userInput.equalsIgnoreCase(client.getName()) || userInput.equals(client.getID())) {
                if (client.getActiveClient()) {
                    userMessage = "Kunden är en nuvarande medlem.";
                } else {
                    userMessage = "Kunden är en före detta kund.";
                }
                break;
            }
        }
        return userMessage;
    }
}
