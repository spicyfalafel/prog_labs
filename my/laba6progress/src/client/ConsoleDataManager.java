package client;

import com.data.Climate;
import com.data.Coordinates;
import com.data.Human;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ConsoleDataManager {
    private Scanner scanner = new Scanner(System.in);
    public String getCityName() {
        String cityName = scanner.nextLine();

        return cityName;
    }
    public Long getCityId() {
        Long id = Long.valueOf(scanner.nextLine());

        return id;
    }
    public Coordinates getCoordinates() {
        Coordinates c;
        Integer x;
        double y;
        List<String> coords = Arrays.asList(scanner.nextLine().split("\\s+"));
        x = Integer.valueOf(coords.get(0));
        y = Double.valueOf(coords.get(1));

        return new Coordinates(x, y);
    }

    public Date getDate(){
        String dateStr = scanner.nextLine();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);

        try {
            return formatter.parse(dateStr); // "7-01-2013"
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("incorrect date format");
        }
    }

    public Long getArea(){
        Long area = Long.valueOf(scanner.nextLine());

        return area;
    }

    public Integer getPopulation(){
        Integer population = Integer.valueOf(scanner.nextLine());

        return population;
    }

    public Long getMetersAboveSeaLevel(){
        Long metersAboveSeaLevel = Long.valueOf(scanner.nextLine());

        return metersAboveSeaLevel;
    }


    public Double getAgglomiration(){
        double agglomiration = Double.valueOf(scanner.nextLine());

        return agglomiration;
    }

    public Climate getClimate(){
        Climate climate;

        climate = Climate.valueOf(scanner.nextLine());

        return climate;
    }

    public Human getGovernor(){
        Human governor = null;
        governor.setName(scanner.nextLine());
        governor.setHeight(Long.valueOf(scanner.nextLine()));
        governor.setBirthday(scanner.nextLine());

        return governor;
    }

}
