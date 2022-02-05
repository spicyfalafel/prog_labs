package Collection;

import java.util.*;
import java.util.stream.Collectors;

/*
    Класс для хранения и манипуляции коллекцией
 */

public class MyProductCollection {

    private LinkedList<Product> products;
    private Date creationDate;

    /**
     * Конструктор для пустой коллекции
     */
    public MyProductCollection() {
        creationDate = new Date();
        products = new LinkedList<Product>();
    }

    /**
     * конструктор, который использую в чтении из файла
     */
    public MyProductCollection(LinkedList<Product> products) {
        creationDate = new Date();
        this.products = products;
    }

    public void show() {
        if (products.size() == 0) System.out.println("Коллекция пуста. Добавьте продукты.");
        for (Product p : products) {
            System.out.println(p.toString());
            System.out.println(p.toString());
        }
    }

    /**
     * очистить коллекцию
     */

    public void clear() {
        products.clear();
    }

    public void add(Product product) {
        this.products.add(product);
    }

    public void addIfMax(Product product) {
        if (findMaxValue() < product.getPrice()) {
            add(product);
            System.out.println("Продукт добавлен");
        }
    }

    public void addIfMin(Product product) {
        if (products.stream().anyMatch(d -> (d.getPrice() < product.getPrice()))) {
            System.out.println("Не добавлен, т.к. не меньший");
        } else {
            add(product);
            System.out.println("Продукт добавлен");
        }
    }

    private LinkedList<Product> sorted() {
        LinkedList<Product> pr = new LinkedList<Product>(products);
        Collections.sort(pr);
        return pr;
    }

    /**
     * простой метод для вывода полей owner коллекции в обратном порядке
     */
    public void printOwnerDescending() {
        LinkedList<Product> pr = sorted();
        pr.stream().map(p -> p.getOwner()).forEach(p -> System.out.print(p + " "));
    }

    /**
     * метод для того, чтобы найти удалить продукт по его id
     * @return удален или нет
     */

    public boolean removeById(Integer id) {
        Product product = findById(id);
        if (product != null) {
            this.products.remove(product);
            return true;
        }
        return false;
    }

    /**
     * метод для того, чтобы найти максимальное значение цены
     * @return максимальная цена продукта
     */


    public Double findMaxValue() {
        Product pp = products.stream().max(Comparator.comparing(p -> p.getPrice())).orElse(null);
        if (pp == null) return 0.0;
        else return pp.getPrice();
    }

    /**
     * метод для того, чтобы найти продукт по id
     * @return продукт
     */

    public Product findById(Integer id) {
        return products.stream().filter(p -> Objects.equals(p.getId(), id))
                .findFirst().orElse(null);
    }

    public void printCollectionInfo() {
        System.out.println("Тип коллекции: Person");
        System.out.println("Дата инициализации: " + creationDate);
        System.out.println("Количество элементов: " + products.size());
    }



    public void removeHead(){
        Product p = this.products.get(0);
        System.out.println(p.toString());
        this.products.remove(0);
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Product> filterUnitMoreThan(UnitOfMeasure uom) {
        return products.stream().filter(el -> el.getUnitOfMeasure().ordinal() > uom.ordinal())
                .collect(Collectors.toList());
    }

    public double averageOfPrice() {
        return products.stream().mapToDouble(p -> p.getPrice()).average().orElse(0);
    }
}
