package Commands;

import Collection.Product;

import java.util.Scanner;


public class RemoveHeadCommand extends Command {

    public RemoveHeadCommand(CommandReceiver receiver) {
        super(receiver);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }

    @Override
    public void execute(String[] cmdArgs) {
        receiver.getCollection().removeHead();
    }

    @Override
    public String getDescription() {
        return "вывести первый элемент коллекции и удалить его";
    }
}
