package Commands;

import Exceptions.NoSuchProductException;

/**
 * The type Remove by id command.
 */
public class RemoveByIdCommand extends Command {
    /**
     * Instantiates a new Remove by id command.
     *
     * @param receiver the receiver
     */
    public RemoveByIdCommand(CommandReceiver receiver) {
        super(receiver);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 1;
    }


    @Override
    public void execute(String[] cmdArgs) throws NoSuchProductException {
        try{
            Integer id = Integer.parseInt(cmdArgs[0]);
            if(receiver.getCollection().removeById(id)){
                System.out.println("Dragon with id " + cmdArgs[0] + " removed");
            }else{
                throw new NoSuchProductException(id);
            }
        }catch (NumberFormatException e){
            System.out.println("id - это число большее нуля");
        }
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его id";
    }
}
