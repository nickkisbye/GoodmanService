import java.util.Scanner;
public class Arrangement {

    private String name;
    private String type;
    private String additionalInfo;
    private int id;
    private int eventDuration;
    private Attendee attendee;

    public Arrangement() {

    }

    public Arrangement(String name, String type, String additionalInfo, int id, int eventDuration) {

        this.name = name;
        this.type = type;
        this.additionalInfo = additionalInfo;
        this.id = id;
        this.eventDuration = eventDuration;
        this.attendee = attendee;

    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
    public String getAdditionalInfo() {
        return additionalInfo;
    }
    public int getId() {
        return id;
    }
    public int getEventDuration() {
        return eventDuration;
    }

    public static Arrangement makeArrangement() {

        Arrangement arrangement = new Arrangement();
        return arrangement;
    }
}
