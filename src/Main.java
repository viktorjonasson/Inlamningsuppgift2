import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    Main() {
        String filePath = "src/clientdata.txt";

        List<Client> allClients = IOUtil.readFileToList(filePath);

        while (true) {
            String userInput = JOptionPane.showInputDialog(null,
                    "Skriv in kundens förnamn och efternamn eller personnummer.");
            if (userInput == null) {
                System.exit(0);
            }
            else if (userInput.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Sökfältet är tomt, försök igen.");
            }
            else {
                JOptionPane.showMessageDialog(null, IOUtil.validateInputAndCheckClient(allClients, userInput));

                int choice = JOptionPane.showConfirmDialog(null,
                        "Vill du söka efter en annan kund?",
                        "Sök igen",
                        JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.NO_OPTION || choice == JOptionPane.CLOSED_OPTION) {
                    System.exit(0);
                }
            }
        }
    }

    public static void main(String[] args) {
        Main m = new Main();
    }
}