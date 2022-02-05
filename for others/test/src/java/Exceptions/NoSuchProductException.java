package Exceptions;

public class NoSuchProductException extends Exception {
    /**
     * ошибка, которая выбрасывается, если не найден элемент
     * @param id id элемента, которого не нашли
     */
    public NoSuchProductException(long id){
        System.out.println("Элемент с id " + id + " не найден.");
    }

}
