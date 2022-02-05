package Commands;

/**
 * The type Clear command.
 */
public class ClearCommand extends Command {

    /**
     * Instantiates a new Clear command.
     *
     * @param receiver the receiver
     */
    public ClearCommand(CommandReceiver receiver) {
        super(receiver);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }

    @Override
    public void execute(String[] cmdArgs) {
        receiver.getCollection().clear();
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }
}
