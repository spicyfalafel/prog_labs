package Commands;

import Exceptions.NoSuchProductException;

/**
 * шаблон команда
 */
public interface Executable {
    /**
     * Execute.
     *
     * @param cmdArgs the cmd args
     * @throws NoSuchProductException the no such dragon exception
     */
    void execute(String[] cmdArgs) throws NoSuchProductException;
}
