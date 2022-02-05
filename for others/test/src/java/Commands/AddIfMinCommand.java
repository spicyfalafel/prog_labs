package Commands;

import java.util.Scanner;

/**
 * The type Add if min command.
 */
public class AddIfMinCommand extends Command {
    /**
     * Instantiates a new Add if min command.
     *
     * @param receiver the receiver
     */
    public AddIfMinCommand(CommandReceiver receiver) {
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
                addIfMin(helper.
                        scanProduct());
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }
}
