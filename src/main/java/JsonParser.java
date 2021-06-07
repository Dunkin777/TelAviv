import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class JsonParser {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        try (FileReader reader = new FileReader("/home/kullare/Рабочий стол/tickets.json");
             FileWriter writer = new FileWriter("/home/kullare/Рабочий стол/text.txt")) {
            Root root = mapper.readValue(reader, Root.class);
            List<Long> longList = new ArrayList<>();
            for (Ticket ticket :
                    root.tickets) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy H:mm");
                Date departure = formatter.parse(ticket.departure_date + " " + ticket.departure_time);
                Date arrival = formatter.parse(ticket.arrival_date + " " + ticket.arrival_time);
                longList.add(arrival.getTime() - departure.getTime());
            }
            double avarage = longList.stream().mapToDouble(d -> d).average().orElse(0.0);
            Long ninetyPercentile = percentile(longList,90);
            long avarageTime = (long) avarage;
            String avg = formateLongTime(avarageTime);
            String _90Persentile = formateLongTime(ninetyPercentile);
            System.out.println("Avarage time From Vladivostok to TelAviv: " + avg);
            System.out.println("NinetyPercentile: " + _90Persentile);
            writer.write("Avarage time From Vladivostok to TelAviv: " + avg);
            writer.write("\n");
            writer.write("NinetyPercentile: " + _90Persentile);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static Long percentile(List<Long> collection, double percentile) {
        Collections.sort(collection);
        int index = (int) Math.ceil(percentile / 100.0 * collection.size());
        return collection.get(index-1);
    }

    public static String formateLongTime(Long time){
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)),
                TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
    }
}

class Ticket{
    public String origin;
    public String origin_name;
    public String destination;
    public String destination_name;
    public String departure_date;
    public String departure_time;
    public String arrival_date;
    public String arrival_time;
    public String carrier;
    public int stops;
    public int price;
}

class Root{
    public List<Ticket> tickets;
}