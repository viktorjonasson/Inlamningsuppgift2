import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IOUtilTest {

    IOUtil ioUtil = new IOUtil();

    //Kollar så att datan som kommer in i mina objekt och ArrayListan motsvarar vad som förväntas.
    String testFile = "test/clientdatatest.txt";

    //Att listan får rätt mängd objekt.
    @Test
    public final void readFileToListTest() {
        List<Client> allClients = IOUtil.readFileToList(testFile);
        assertTrue(allClients.size() == 3);
        assertFalse(allClients.size() == 5);
    }

    //Att datan är rätt och på rätt plats.
    @Test
    public final void readFileToListEntriesTest() {
        List<Client> allClients = IOUtil.readFileToList(testFile);

        assertTrue(allClients.get(1).getID().equals("8204021234"));
        assertFalse(allClients.get(1).getID().equals("7703021234"));

        assertTrue(allClients.get(0).getName().equals("Alhambra Aromes"));
        assertFalse(allClients.get(0).getName().equals("Bear Belle"));

        assertTrue(allClients.get(2).getDatePaid().isEqual(LocalDate.of(2018, 3, 12)));
        assertFalse(allClients.get(2).getDatePaid().isEqual(LocalDate.of(2019, 12, 2)));
    }

    //Testar att datumkonverteraren funkar.
    @Test
    public final void convStringToDateTest() {
        String testDateString = "2020-10-12";

        LocalDate testDate = IOUtil.convStringToDate(testDateString);
        assertTrue(testDate.isEqual(LocalDate.of(2020, 10, 12)));
        assertFalse(testDate.isEqual(LocalDate.of(2021, 10, 12)));
    }

    //Testar funktionen att räkna ut om en kund är aktiv.
    @Test
    public final void calcActiveMembershipTest() {
        LocalDate testDateActive = LocalDate.now().minusMonths(11);
        LocalDate testDateNotActive = LocalDate.now().minusMonths(12);

        Boolean resultTrue = IOUtil.calcActiveMembership(testDateActive);
        assertTrue(resultTrue);
        Boolean resultFalse = IOUtil.calcActiveMembership(testDateNotActive);
        assertFalse(resultFalse);
    }

    //Testar valideringsmetod för lista
    @Test
    public final void validateListTest() {
        List<Client> allClientsEmpty = new ArrayList<>();
        Throwable exception = assertThrows(NullPointerException.class, () -> IOUtil.validateList(allClientsEmpty));

        List<Client> allClients = IOUtil.readFileToList(testFile);
        assertDoesNotThrow(() -> IOUtil.validateList(allClients));
    }

    //Test att rätt meddelande kommer tillbaka för olika scenarion.
    @Test
    public final void checkClientTest() {
        List<Client> allClients = IOUtil.readFileToList(testFile);
        String activeClientMessage = "Kunden är en nuvarande medlem.";
        String idleClientMessage = "Kunden är en före detta kund.";
        String notAClientMessage = "Personen finns inte i registret och är obehörig.";

        assertTrue(IOUtil.checkClient(allClients, "Alhambra Aromes").equals(activeClientMessage));
        assertFalse(IOUtil.checkClient(allClients, "Bear Belle").equals(activeClientMessage));
        assertTrue(IOUtil.checkClient(allClients, "8204021234").equals(idleClientMessage));
        assertFalse(IOUtil.checkClient(allClients, "7703021234").equals(idleClientMessage));
        assertTrue(IOUtil.checkClient(allClients, "not in file client").equals(notAClientMessage));
        assertFalse(IOUtil.checkClient(allClients, "Alhambra Aromes").equals(notAClientMessage));
    }

    //Test att rätt meddelande kommer tillbaka för olika scenarion.
    //Kanske överflödig då meddelandet bara åker rakt igenom denna metod utan att den gör något.
    @Test
    public final void validateAndCheckClientTest() {
        List<Client> allClients = IOUtil.readFileToList(testFile);
        String activeClientMessage = "Kunden är en nuvarande medlem.";
        String idleClientMessage = "Kunden är en före detta kund.";
        String notAClientMessage = "Personen finns inte i registret och är obehörig.";

        assertTrue(IOUtil.validateInputAndCheckClient(allClients, "Alhambra Aromes").equals(activeClientMessage));
        assertFalse(IOUtil.validateInputAndCheckClient(allClients, "Bear Belle").equals(activeClientMessage));
        assertTrue(IOUtil.validateInputAndCheckClient(allClients, "8204021234").equals(idleClientMessage));
        assertFalse(IOUtil.validateInputAndCheckClient(allClients, "7703021234").equals(idleClientMessage));
        assertTrue(IOUtil.validateInputAndCheckClient(allClients, "not in file client").equals(notAClientMessage));
        assertFalse(IOUtil.validateInputAndCheckClient(allClients, "Alhambra Aromes").equals(notAClientMessage));
    }

    //Testar skrivning till fil
    @Test
    public final void saveWorkoutSessionToFile() {
        List<Client> allClients = IOUtil.readFileToList(testFile);
        String outFilePath = "test/workoutstatisticstest.txt";

        IOUtil.saveWorkoutSessionToFile(outFilePath, allClients.get(0));
        IOUtil.saveWorkoutSessionToFile(outFilePath, allClients.get(1));

        try(BufferedReader reader = new BufferedReader(new FileReader(outFilePath))){
            String string = reader.readLine();
            String date = String.valueOf(LocalDate.now());
            assertTrue(string.equalsIgnoreCase("Alhambra Aromes, 7703021234, tränade den " + date));
            string = reader.readLine();
            assertTrue(string.equalsIgnoreCase("Bear Belle, 8204021234, tränade den " + date));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
