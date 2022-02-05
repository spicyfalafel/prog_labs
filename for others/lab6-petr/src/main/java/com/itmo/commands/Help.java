package com.itmo.commands;

import com.itmo.managers.HashTableManager;
import com.itmo.managers.StringManager;

import static com.itmo.managers.StringManager.*;

public class Help extends AbstractCommand{

    private final String HELP = "help : вывести справку по доступным командам\n" +
            "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
            "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
            "insert : добавить новый элемент с заданным ключом\n" +
            "update : обновить значение элемента коллекции, id которого равен заданному\n" +
            "remove_key : удалить элемент из коллекции по его ключу\n" +
            "clear : очистить коллекцию\n" +
            "execute_script : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
            "exit : завершить программу (без сохранения в файл)\n" +
            "replace_if_lowe : заменить значение по ключу, если новое значение меньше старого\n" +
            "remove_greater_key : удалить из коллекции все элементы, ключ которых превышает заданный\n" +
            "remove_lower_key : удалить из коллекции все элементы, ключ которых меньше, чем заданный\n" +
            "sum_of_price : вывести сумму значений поля price для всех элементов коллекции\n" +
            "min_by_creation_date : вывести любой объект из коллекции, значение поля creationDate которого является минимальным\n" +
            "count_by_owner  : вывести количество элементов, значение поля owner которых равно заданному";

    public Help(String name, HashTableManager products, StringManager stringManager) {
        super(name, products, stringManager);
    }

    @Override
    public String execute() {
        return HELP;
    }
}
