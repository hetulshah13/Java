import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ArrayListDemo2 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(0);
        for (int i=1;i<=10;i++)
            list.add(i*100);

        list.forEach(System.out::println);

        System.out.println("---------------------------");

        for (int e : list) System.out.println(e);

        Iterator<Integer> it = list.iterator();
        while (it.hasNext())
            System.out.println(it.next());
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        ListIterator<Integer> lit = list.listIterator();
        int cnt = 0;
        boolean traverse;
        do {
            traverse = cnt > list.size()/2 ? lit.hasPrevious() : lit.hasNext();
            if (!traverse) break;
            int element = cnt > list.size()/2 ? lit.previous() : lit.next();
            System.out.println(element);
            cnt++;
        } while (true);

        while (lit.hasNext())
            lit.set(lit.next()+5);

        System.out.println(list);
    }
}
