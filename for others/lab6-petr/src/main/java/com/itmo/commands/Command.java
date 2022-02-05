package com.itmo.commands;
/**
 * Интерфейс команды
 */
public interface Command {
    String execute();
    boolean isExit();
}
