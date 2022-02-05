package Commands;

import java.util.Scanner;

/**
 * The type Add if max command.
 */
public class AddIfMaxCommand extends Command{
    /**
     * Instantiates a new Add if max command.
     *
     * @param receiver the receiver
     */
    public AddIfMaxCommand(CommandReceiver receiver) {
        super(receiver);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }

    /**
     *
     * @param cmdArgs ничего, с новых строк - поля дракона
     */
    @Override
    public void execute(String[] cmdArgs) {
        Scanner sc = new Scanner(System.in);
        InputHelper helper = new InputHelper(sc);
        receiver.getCollection().
                addIfMax(helper.
                        scanProduct());
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }
}
