/**
 * Created by macbook on 2/6/17.
 */
public class Parameter {
    public static void main(String[] args) {
        C c = new C();
        Parameter p = new Parameter();
        String b = "test";
        b = c.method(b);
        System.out.println(b);
//System.out.println(c.x);
    //    c.method("foo ");
  //      System.out.println(c.x);
    }
}

class C {
    public String x;
    String method(String a) {
        //String b="efg"
        a = a + "addendum";
        {
            String b = "abc" +a;
            System.out.println(b);
        }
        for (int i=0;i<10;i++){
            System.out.println(i);
        }

        System.out.println(a);
        //System.out.println(a);
        return a;
    }
}

