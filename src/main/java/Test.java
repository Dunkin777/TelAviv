import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws ParseException {
        Ticket ticket = new Ticket();
        ticket.departure_date = "12.05.18";
        ticket.departure_time = "6:10";
        ticket.arrival_date = "12.05.18";
        ticket.arrival_time = "16:15";
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy H:mm");
        String strDaparture = ticket.departure_date + " " + ticket.departure_time;
        String strArrival = ticket.arrival_date + " " + ticket.arrival_time;
        Date departure = formatter.parse(strDaparture);
        Date arrival = formatter.parse(strArrival);
        System.out.println(arrival.getTime() - departure.getTime());
    }
}
