package com.itmo.commands;

import com.itmo.Exceptions.NotYourPropertyException;
import com.itmo.app.Application;
import com.itmo.client.User;
import com.itmo.collection.Dragon;
import com.itmo.utils.FieldsScanner;

/**
 * The type Update id command.
 */
public class UpdateIdCommand extends Command{

    /**
     * Instantiates a new Command.
     */
    public UpdateIdCommand(String[] args) {
        super(args);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 1;
    }
    private Dragon dr;

    @Override
    public void clientInsertion() {
        FieldsScanner fieldsScanner = FieldsScanner.getInstance();
        dr = fieldsScanner.scanDragon();
    }

    /**
     * апдейтит дракона по указанному id. реализован так: сначала удаляет элемент,
     * потом создает новый и присваивает ему id прошлого.
     * @return
     */
    @Override
    public String execute(Application application, User user) {
        try{
            long id = Long.parseLong(args[0].trim());
            Dragon prev = application.getCollection().findById(id);
            if(prev!=null){
                application.getCollection().remove(prev, user);
                dr.setId(id);
                application.getCollection().add(dr);
                return "Дракон добавлен успешно!";
            }else{
                return "Дракона с id " + id + " в коллекции не нашлось.";
            }
        }catch (NumberFormatException e){
            return "ID - это число";
        }catch (NotYourPropertyException e){
            return "этот дракон принадлежит " + e.getMessage();
        }
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }
}