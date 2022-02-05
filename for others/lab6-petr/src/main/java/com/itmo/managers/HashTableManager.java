package com.itmo.managers;

import com.itmo.product.Person;
import com.itmo.product.Product;
import com.itmo.product.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс оболочка для работы с коллекцией
 */
public class HashTableManager {
    private Map<Integer, Product> products = new Hashtable<>();
    private Date date = null;

    public synchronized Map<Integer, Product> getProducts() {
        return products;
    }
    private DatabaseManager db;

    public DatabaseManager getDb() {
        return db;
    }

    public HashTableManager() {
        System.out.println("HashTableManager constructor");
        db = new DatabaseManager();
    }

    public synchronized void insertToDb(int key, Product product) {
        if (date == null) {
            date = new Date();
        }
        product.setId(key);
        db.addElement(product);
        products.put(key, product);
    }

    public synchronized void insert(int key, Product product) {
        if (date == null) {
            date = new Date();
        }
        product.setId(key);
        products.put(key, product);
    }


    public synchronized Long sumOfPrice() {
        Long price = 0L;
        for (Product p : products.values()) {
            if (p.getPrice() != null){
                price += p.getPrice();
            }

        }
        return price;
    }

    public synchronized Product minByCreationDate() {
        Product product = null;
        boolean first = true;
        for (Product p : products.values()) {
            if (first) {
                first = false;
                product = p;
            } else {
                if (p.getCreationDate().compareTo(product.getCreationDate()) > 0) {
                    product = p;
                }
            }
        }
        return product;
    }

    public synchronized int countByOwner(Person owner) {
        int cnt = 0;
        for (Product p : products.values()) {
            if (p.getOwner().equals(owner)) {
                cnt++;
            }
        }
        return cnt;
    }

    public synchronized void clear(User user) {
        Map<Integer, Product> p = filterByUser(user);
        for(Integer i : p.keySet()) {
            products.remove(i);
        }
    }

    public synchronized boolean findKey(Integer key, User u) {
        Map<Integer, Product> p = filterByUser(u);
        return p.containsKey(key);
    }

    public synchronized void removeGreaterKey(Integer key, User user) {
        Map<Integer, Product> p = filterByUser(user);
        for (Integer i : p.keySet()) {
            if (i > key) {
                products.remove(i);
                db.remove(i);
            }
        }
    }

    public synchronized Map<Integer, Product> filterByUser(User user){
        return products.entrySet().stream()
                .filter(e -> e.getValue().getUserOwner()
                        .getName()
                        .equals(user.getName()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public synchronized void removeLowerKey(Integer key, User user) {
        Map<Integer, Product> p = filterByUser(user);
        for (Integer i : p.keySet()) {
            if (i < key) {
                products.remove(i);
                db.remove(i);
            }
        }
    }

    public synchronized boolean replaceIfLowe(Integer key, Product product, User user) {
        Map<Integer, Product> p = filterByUser(user);
        if (findKey(key, user)) {
            if (p.get(key).compareTo(product) > 0) {
                db.addElement(product);
                products.put(key, product);
            }
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean findId(int id, User user) {
        Map<Integer, Product> p = filterByUser(user);
        for (Product pp : p.values()) {
            if (pp.getId() == id)
                return true;
        }
        return false;
    }

    public synchronized boolean removeByKey(Integer key, User user) {
        Map<Integer, Product> p = filterByUser(user);
        if (!p.containsKey(key)){
            return false;
        }
        int size = size();
        db.remove(key);
        products.remove(key);
        if (size() - size == 0) {
            return false;
        }
        return true;
    }


    public synchronized Integer removeById(int id, User user) {
        Product product;
        Integer key = 0;
        Map<Integer, Product> p = filterByUser(user);
        for (int i : p.keySet()) {
            if (products.get(i).getId() == id) {
                key = i;
                product = products.get(i);
                break;
            }
        }
        removeByKey(key, user);
        return key;
    }

    public Date getDate() {
        return date;
    }

    public synchronized int size() {
        return products.size();
    }

    public synchronized String toStr() {
        StringBuilder el = new StringBuilder();
        for (Product p : products.values()) {
            el.append("\n").append(p.toString());
        }
        return el.toString();
    }

    public synchronized void write(FileOutputStream writer) throws IOException {
        System.out.println(products.size());
        for (Product product : products.values()) {
            String line = product.toCsv() + "\n";
            writer.write(line.getBytes(StandardCharsets.UTF_8));
        }
    }
}
