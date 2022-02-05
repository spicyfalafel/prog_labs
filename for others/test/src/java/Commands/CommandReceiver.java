package Commands;

import Collection.MyProductCollection;
import Collection.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * шаблон Команда
 * Получатель ничего не знает о командах. ему дают задание и коллекцию - он выполняет.
 */
public class CommandReceiver {
    private HashMap<String, Command> registeredCommands;

    private MyProductCollection collection;

    /**
     * Gets registered commands.
     *
     * @return зарегистрированные команды
     */
    public HashMap<String, Command> getRegisteredCommands() {
        return registeredCommands;
    }

    /**
     * Gets collection.
     *
     * @return the collection
     */
    public MyProductCollection getCollection() {
        return collection;
    }

    /**
     * Instantiates a new Command receiver.
     *
     * @param registeredCommands the registered commands
     * @param collection         the collection
     */
    public CommandReceiver(HashMap<String, Command> registeredCommands, MyProductCollection collection){
        this.registeredCommands = registeredCommands;
        this.collection = collection;
    }


    private boolean userWantsToExit = false;

    /**
     * User wants to exit boolean.
     *
     * @return the boolean
     */
    public boolean userWantsToExit() {
        return userWantsToExit;
    }

    /**
     * Print help.
     */
    public void printHelp(){
        //для сортировки по ключу (алфавиту)
        Map<String, Command> treeMap = new TreeMap<String, Command>(registeredCommands);
        for(Map.Entry<String, Command> entry : treeMap.entrySet()){
            System.out.println(entry.getKey() + " : " + entry.getValue().getDescription());
        }
    }


    /**
     * Exit.
     */
    public void exit(){
        userWantsToExit = true;
    }
}