package Commands;

import Collection.*;
import java.util.Scanner;


public class AddElementCommand extends Command {
    private Scanner sc = new Scanner(System.in);

    public AddElementCommand(CommandReceiver receiver) {
        super(receiver);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }


    public void execute(String[] cmdArgs){
        InputHelper inputHelper = new InputHelper(sc);
        Product dr = inputHelper.scanProduct();
        receiver.getCollection().add(dr);
        System.out.println("Элемент добавлен успешно!");
    }

    /**
     *
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }
}
