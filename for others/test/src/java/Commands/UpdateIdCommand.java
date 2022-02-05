package Commands;

import Collection.*;
import java.util.Scanner;


public class UpdateIdCommand extends Command{

    public UpdateIdCommand(CommandReceiver receiver) {
        super(receiver);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 1;
    }


    /**
     * апдейтит элемент по указанному id.
     * @param cmdArgs id дракона для обновления
     */
    @Override
    public void execute(String[] cmdArgs) {
        try{
            Scanner sc = new Scanner(System.in);
            InputHelper inputHelper = new InputHelper(sc);
            Integer id = Integer.parseInt(cmdArgs[0].trim());
            if(receiver.getCollection().removeById(id)){
                Product pr = inputHelper.scanProduct();
                pr.changeId(id);
                System.out.println("Элемент добавлен успешно!");
                receiver.getCollection().add(pr);
            }else{
                System.out.println("Элемента с id " + id + " в коллекции не нашлось.");
            }
        }catch (NumberFormatException e){
            System.out.println("ID - это число");
        }
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }
}