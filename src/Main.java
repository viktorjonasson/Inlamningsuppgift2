import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    Main() {
        String filePath = "src/clientdata.txt";

        List<Client> allClients = IOUtil.readFileToList(filePath);

        boolean dialogInput = true;

        while (dialogInput) {
            String userInput = JOptionPane.showInputDialog(null,
                    "Skriv in kundens förnamn och efternamn eller personnummer.");
            if (userInput == null) {
                dialogInput = false;
                continue;
            }
            if (userInput.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Sökfältet är tomt, försök igen.");
                continue;
            }
            JOptionPane.showMessageDialog(null, IOUtil.validateInputAndCheckClient(allClients, userInput));
        }



//        System.out.println(allClients.toString());


    }

    public static void main(String[] args) {
        Main m = new Main();
    }
}