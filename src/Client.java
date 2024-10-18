import java.time.LocalDate;

public class Client {

    private String ID;
    private String name;
    private LocalDate datePaid;
    private Boolean activeClient;
    private LocalDate dateOfWorkout;

    public Client(String ID, String name, LocalDate datePaid, Boolean activeClient) {
        this.ID = ID;
        this.name = name;
        this.datePaid = datePaid;
        this.activeClient = activeClient;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(LocalDate datePaid) {
        this.datePaid = datePaid;
    }

    public LocalDate getDateOfWorkout() {
        return dateOfWorkout;
    }

    public void setDateOfWorkout(LocalDate dateOfWorkout) {
        this.dateOfWorkout = dateOfWorkout;
    }

    public Boolean getActiveClient() {
        return activeClient;
    }

    public void setActiveClient(Boolean activeClient) {
        this.activeClient = activeClient;
    }

    @Override
    public String toString() {
        return "Client{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", datePaid=" + datePaid +
                ", activeClient=" + activeClient +
                ", dateOfWorkout=" + dateOfWorkout +
                '}';
    }
}
