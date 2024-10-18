import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalDate;
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
    String testDateString = "2020-10-12";

    @Test
    public final void convStringToDateTest() {
        LocalDate testDate = IOUtil.convStringToDate(testDateString);
        assertTrue(testDate.isEqual(LocalDate.of(2020, 10, 12)));
        assertFalse(testDate.isEqual(LocalDate.of(2021, 10, 12)));
    }

    //Testar funktionen att räkna ut om en kund är aktiv.
    LocalDate testDateActive = LocalDate.now().minusMonths(11);
    LocalDate testDateNotActive = LocalDate.now().minusMonths(12);

    @Test
    public final void calcActiveMembershipTest() {
        Boolean resultTrue = IOUtil.calcActiveMembership(testDateActive);
        assertTrue(resultTrue);
        Boolean resultFalse = IOUtil.calcActiveMembership(testDateNotActive);
        assertFalse(resultFalse);
    }
}
