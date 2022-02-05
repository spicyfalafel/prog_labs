package Commands;

/**
 * The type Exit command.
 */
public class ExitCommand extends Command {

    /**
     * Instantiates a new Exit command.
     *
     * @param receiver the receiver
     */
    public ExitCommand(CommandReceiver receiver) {
        super(receiver);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }


    @Override
    public String getDescription() {
        return "завершить программу (без сохранения в файл)";
    }

    @Override
    public void execute(String[] cmdArgs) {
        System.out.println("byebye");
        receiver.exit();
    }
}
