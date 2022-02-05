package Commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import Main.Main;

/**
 * The type Execute script command.
 */
public class ExecuteScriptCommand extends Command {
    /**
     * Instantiates a new Execute script command.
     *
     * @param receiver the receiver
     */
    public ExecuteScriptCommand(CommandReceiver receiver) {
        super(receiver);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 1;
    }


    /**
     * скрипт выполняется путём вызова метода выполнения команды из Main.
     * это немного нарушает концепцию шаблона Команда, но это крайне удобно
     * @param cmdArgs имя скрипта
     */
    @Override
    public void execute(String[] cmdArgs) {
        try(BufferedReader reader = new BufferedReader(new FileReader(cmdArgs[0]))){
            String line;
            System.out.println("Запуск скрипта " + cmdArgs[0]);
            while((line = reader.readLine()) != null){
                Main.executeCommand(line);
            }
        }catch(FileNotFoundException e){
            System.out.println("file not found");
        } catch (IOException e) {
            System.out.println("капут");
        }
    }

    @Override
    public String getDescription() {
        return "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }
}
