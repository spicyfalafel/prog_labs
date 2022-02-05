package Commands;

/**
 * The type Help command.
 */
public class HelpCommand extends Command {

    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }

    /**
     * Instantiates a new Help command.
     *
     * @param receiver the receiver
     */
    public HelpCommand(CommandReceiver receiver){
        super(receiver);
    }
    @Override
    public void execute(String[] cmdArgs) {
        receiver.printHelp();
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }
}
