import java.util.Arrays;
import java.util.List;

public class MyNullPointerException extends NullPointerException {
    //unchecked
    //мне кажется этот класс не особо полезный, но по заданию надо сделать два
    //класса исключения, DifferentLocsException хорош, а второй придумать сложно

    public MyNullPointerException(String s) {
        super(s);
        //бесполезный класс!
        //зато локальный!
        class MyLocalClass{
            public void doSmth() {
                Runnable r = () -> System.out.print("КАПУТ");
                for (int i = 0; i<5;i++){
                    r.run();
                }
                System.out.println();
            }
        }
        MyLocalClass a = new MyLocalClass();
        a.doSmth();
        System.out.println(getClass().getName());
    }
}