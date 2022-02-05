package client;

import com.data.*;

import java.util.Date;
import java.util.Scanner;

public class ConsoleManager {
    Scanner scanner = new Scanner(System.in);

    public City ScanCity(){
        ConsoleDataManager consoleDataManager = new ConsoleDataManager();

        Long id = consoleDataManager.getCityId();
        String cityName = consoleDataManager.getCityName();
        Coordinates coordinates = consoleDataManager.getCoordinates();
        Date birthDate = consoleDataManager.getDate();
        Long area = consoleDataManager.getArea();
        Integer population = consoleDataManager.getPopulation();
        Long metersAboveSeaLevel = consoleDataManager.getMetersAboveSeaLevel();
        Date establishmentDate = consoleDataManager.getDate();
        Double agglomiration = consoleDataManager.getAgglomiration();
        Climate climate = consoleDataManager.getClimate();
        Human governor = consoleDataManager.getGovernor();

        City city = new City(id, cityName, coordinates, birthDate, area, population, metersAboveSeaLevel, establishmentDate, agglomiration, climate, governor);

        return city;
    }


    public Commands getCommand(){
        String scanCommand = scanner.nextLine();
        String[] split = scanCommand.split(" ");
        String nextCommand = split[0];

        ConsoleDataManager consoleDataManager = new ConsoleDataManager();



        if ("exit".equals(nextCommand)) {

        }
        if ("help".equals(nextCommand)) {
            return new Commands("help", null);

        } else if("show".equals(nextCommand)){
            Commands command = new Commands("show", null;
        } else if("info".equals(nextCommand)) {
            Commands command = new Commands("info", null);
        }else if("insert".equals(nextCommand)) {
            Commands command = new Commands("insert", ScanCity());
        }else if("updateid".equals(nextCommand)) {
            UdateIdPayload udateIdPayload = new UdateIdPayload(consoleDataManager.getCityName(), consoleDataManager.getCityId()));
            Commands command = new Commands("updateid", udateIdPayload);
        }else if("removekey".equals(nextCommand)) {
            Long id = Long.valueOf(scanner.nextLine());
            Commands command = new Commands("removekey", id);
        }else if("clear".equals(nextCommand)) {
            Commands command = new Commands("clear", null);
        }else if("save".equals(nextCommand)) {
            Commands command = new Commands("save", null);
        }else if("execute_script".equals(nextCommand)) {
            Commands command = new Commands("execute_script", null);
        }else if("remove_greater".equals(nextCommand)) {
            Commands command = new Commands("remove_greater", null);
        }else if("remove_greater_key".equals(nextCommand)) {
            Commands command = new Commands("remove_greater_key", null);
        }else if("remove_lower_key".equals(nextCommand)) {
            Commands command = new Commands("remove_lower_key", null);
        }else if("remove_all_by_governor".equals(nextCommand)) {
            Commands command = new Commands("remove_all_by_governor", null);
        }else if("print_ascending".equals(nextCommand)) {
            Commands command = new Commands("print_ascending", null);
        }else if("print_field_descending_agglomeration".equals(nextCommand)) {
            Commands command = new Commands("print_field_descending_agglomeration", null);
        }
    }

}
