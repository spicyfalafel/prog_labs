package Commands;

import Collection.Product;
import Collection.UnitOfMeasure;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;


public class FilterGreaterThanUnitOfMeasureCommand extends Command {

    public FilterGreaterThanUnitOfMeasureCommand(CommandReceiver receiver) {
        super(receiver);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 1;
    }

    @Override
    public void execute(String[] cmdArgs) {
        try{
            InputHelper helper = new InputHelper(new Scanner(System.in));
            UnitOfMeasure uom = (UnitOfMeasure )helper.parseEnum(cmdArgs[0], false, UnitOfMeasure.class);
            if(uom == null){
                System.out.println("Введите правильный аргумент");
                return;
            }
            List<Product> res = receiver.getCollection()
                    .filterUnitMoreThan(uom);
            if(res.size()!=0){
                System.out.println("Элементов в коллекции имена которых начинаются" +
                        "со строки " + cmdArgs[0] + ": " + res.size());
                for (Product product : res) {
                    System.out.println(product.toString());
                }
            }else{
                System.out.println("Элементов с именами начинающихся с " + cmdArgs[0] + " нет.");
            }
        }catch (Exception e){
            System.out.println("капут");
        }
    }

    @Override
    public String getDescription() {
        return "вывести элементы, " +
                "значение поля unitOfMeasure которых больше заданного";
    }
}
