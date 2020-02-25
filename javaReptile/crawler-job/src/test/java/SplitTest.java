import org.junit.Test;

public class SplitTest {
    @Test
    public void test1() {
        String v="sdfdsf/sdfsdf/sdfdsf";
        String[] strings = v.split("/");
        for (int i=0;i<strings.length;i++){
            System.out.println(strings[i]);
        }
        System.out.println(strings.length);
    }
}
