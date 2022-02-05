package Commands;

import Exceptions.NoSuchProductException;

public class AveragePriceCommand extends Command {
    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }

    public AveragePriceCommand(CommandReceiver mainReceiver) {
        super(mainReceiver);
    }

    @Override
    public void execute(String[] cmdArgs) throws NoSuchProductException {
        System.out.println("Среднее значение всех price: " + receiver.getCollection().averageOfPrice());
    }
}
