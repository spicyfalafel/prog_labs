package Main;

import Commands.*;
import Collection.MyProductCollection;
import Exceptions.NoSuchCommandException;
import Exceptions.NoSuchProductException;
import Exceptions.WrongArgumentsNumberException;
import Json.JsonStaff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    private static CommandsInvoker invoker;
    private static String defaultFileName = "input.json";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MyProductCollection collection;

        try {
            File file;
            if (args.length == 1) {
                String fileName = args[0];
                file = new File(fileName);
                if (file.exists() && !file.isDirectory()) {
                    System.out.println("Файл существует, попытаемся считать коллекцию");
                } else {
                    System.out.println("json файла по пути " + file.getAbsolutePath() + " не нашлось, использую input.json");
                    file = new File(defaultFileName);
                }
            } else {
                System.out.println("использую input.json");
                file = new File(defaultFileName);
            }
            collection = new MyProductCollection(JsonStaff.fromJsonToProductList(file));
        } catch (FileNotFoundException |
                NullPointerException e) {
            System.out.println("файл не нашелся или нет прав на чтение.");
            collection = tryToGetFromDefaultFile();
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла");
            e.printStackTrace();
            collection = tryToGetFromDefaultFile();
        }
        System.out.println("коллекция инициализована. show - показать коллекцию");
        System.out.println("help - помощь");
        // клиент
        //вызывающий объект и несколько объектов команд принадлежат объекту клиента
        // Клиент решает, какие команды выполнить и когда.
        // Чтобы выполнить команду он передает объект команды объекту invoker.

        invoker = CommandsInvoker.getInstance();
        CommandReceiver mainReceiver = new CommandReceiver(invoker.getMapOfRegisteredCommands(),
                collection);

        registerCommands(mainReceiver);

        while (!mainReceiver.userWantsToExit()) {
            try {
                try {
                    String userInput = scanner.nextLine().trim();
                    executeCommand(userInput);
                } catch (NoSuchElementException e) {
                    if (!scanner.hasNext()) {
                        executeCommand("exit");
                    }
                }
            } catch (Exception e) {
                scanner = new Scanner(System.in);
            }
        }
    }

    private static void registerCommands(CommandReceiver mainReceiver) {
        invoker.register("info", new InfoCommand(mainReceiver));
        invoker.register("help", new HelpCommand(mainReceiver));
        invoker.register("exit", new ExitCommand(mainReceiver));
        invoker.register("clear", new ClearCommand(mainReceiver));
        invoker.register("remove_by_id", new RemoveByIdCommand(mainReceiver));
        invoker.register("add", new AddElementCommand(mainReceiver));
        invoker.register("show", new ShowCommand(mainReceiver));
        invoker.register("update", new UpdateIdCommand(mainReceiver));
        invoker.register("add_if_max", new AddIfMaxCommand(mainReceiver));
        invoker.register("add_if_min", new AddIfMinCommand(mainReceiver));
        invoker.register("remove_head", new RemoveHeadCommand(mainReceiver));
        invoker.register("print_field_descending_owner", new PrintOwnerDescendingCommand(mainReceiver));
        invoker.register("save", new SaveCommand(mainReceiver));
        invoker.register("execute_script", new ExecuteScriptCommand(mainReceiver));
        invoker.register("filter_greater_than_unit_of_measure", new FilterGreaterThanUnitOfMeasureCommand(mainReceiver));
        invoker.register("average_of_price", new AveragePriceCommand(mainReceiver));
    }

    public static void executeCommand(String userInput) {
        String[] splitted = userInput.split(" ");
        String commandName = splitted[0];
        String[] arguments = new String[splitted.length - 1];
        System.arraycopy(splitted, 1, arguments, 0, splitted.length - 1);
        try {
            invoker.execute(commandName, arguments);
        } catch (WrongArgumentsNumberException e) {
            System.out.println("введите команду с правильным количеством аргументов");
        } catch (NoSuchCommandException e) {
            System.out.println("help покажет доступные команды. Осторожно, не опечатайтесь!");
        } catch (NoSuchElementException e) {
            System.out.println("^D в моей программе не работает");
        } catch (StackOverflowError e) {
            System.out.println("стэк сломан. поздравляю.");
        } catch (NoSuchProductException e) {
            System.out.println("возможно, стоит изучить доступных драконов по команде show?");
        }
    }

    public static MyProductCollection tryToGetFromDefaultFile() {
        System.out.println("Попытаемся получить коллекцию из файла " + defaultFileName);
        try {
            return new MyProductCollection(JsonStaff.fromJsonToProductList(new File(defaultFileName)));
        } catch (FileNotFoundException e) {
            System.out.println("Не удалось найти файл " + defaultFileName + ". Проверьте, существует ли файл по" +
                    " пути " + (new File(defaultFileName).getAbsolutePath()) + " и ваши на него права.");
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла");
        }
        return new MyProductCollection();
    }
}
