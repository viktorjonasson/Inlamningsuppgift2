import java.util.ArrayList;
import java.util.List;

public class Main {

    Main() {
        String filePath = "src/clientdata.txt";

        List<Client> allClients = IOUtil.readFileToList(filePath);

//        System.out.println(allClients.toString());


    }

    public static void main(String[] args) {
        Main m = new Main();
    }
}