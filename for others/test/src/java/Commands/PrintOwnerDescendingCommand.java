package Commands;


public class PrintOwnerDescendingCommand extends Command {

    public PrintOwnerDescendingCommand(CommandReceiver receiver) {
        super(receiver);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }

    @Override
    public void execute(String[] cmdArgs) {
        receiver.getCollection().printOwnerDescending();
    }

    @Override
    public String getDescription() {
        return "вывести значения поля owner всех элементов в порядке убывания";
    }
}
