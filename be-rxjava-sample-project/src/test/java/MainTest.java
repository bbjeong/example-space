import io.reactivex.Observable;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MainTest {

    @Test
    public void test() {
        char a = '1';
        char b = a;


        System.out.println(a + " " + b + " " + (a == b));
    }

    @Test
    public void stringRepeat() {
        String str = "bbtest";

        System.out.println(str.repeat(5));
    }

    @Test
    public void mapTest() {
        Map<String, String> map = new HashMap<>();

        String s = (String) map.get("test");
        System.out.println(s + " " + (s == null) + " " +("null" == null));
    }

    @Test
    public void compareTest() {

        BigDecimal x = new BigDecimal(1);
        BigDecimal y = new BigDecimal(2);
        System.out.println(x.compareTo(y));
    }

    @Test
    public void assertValueTest() {
        Observable.just("apple", "banana", "strawberry")
                .filter(item -> item.equals("banana"))
                .take(1)
                .test()
                .assertValue("banana");
    }

}
