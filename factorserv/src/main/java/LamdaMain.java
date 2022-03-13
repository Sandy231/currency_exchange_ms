import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class LamdaMain {
    public static void main(String[] args) {

        Runnable r = () -> {
            System.out.println("Inside Runnable");
        };
        r.run();

        Supplier<Integer> sp = () -> {
            return 10;
        };
        System.out.println(sp.get());

        Consumer c = (i) -> {
            System.out.println(i);
        };
        c.accept(new Object());

        Function<Integer, Integer> f = (val) -> val * 2;
        System.out.println(f.apply(4));

    }
}