import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ArrayListDemo {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("Delhi");
        list.add("Jaipur");
        list.add("Noida");
        list.add(1, "Udaipur");
        System.out.println(list.size());
        System.out.println("LIST: " + list);
        String[] cities = new String[list.size()];
        list.toArray(cities);
        System.out.println("ARRAY: " + Arrays.toString(cities));
        List<String> list2 = new ArrayList<>();
        list2.add("Manipur");
        list2.add("Goa");
        list2.add("Patna");
        list.addAll(0, list2);
        System.out.println(list.size() + "\t:\t" + list);
        System.out.println("Check for Ahmedabad : " + list.contains("Ahmedabad"));
        System.out.println("Check for Jaipur : " + list.contains("Jaipur"));
        System.out.println("Check for delhi : " + list.contains("delhi"));
        System.out.println("Contains all : " + list.containsAll(list2));
        list2.add("Kolkata");
        System.out.println("Now, contains all : " + list.containsAll(list2));
        System.out.println("------------------");
        System.out.println("Equals: " + list.equals(list2));
        list.retainAll(list2);
        list2.remove("Kolkata");
        list2.remove("Manipur");
        list2.add("Manipur");
        System.out.println("After retain list: " + list + "\t\t" + list2);
        System.out.println("Now, Equals: " + list.equals(list2));
        System.out.println("------------------");
        list.add("Mumbai");
        list.add("Pune");
        list.add("Jaipur");
        list.add("Jaipur");
        list.add("Surat");
        System.out.println("Current list: " + list);
        System.out.println("First element: " + list.get(0));
        System.out.println("Last element: " + list.get(list.size() - 1));
        System.out.println("Index of Jaipur: " + list.indexOf("Jaipur"));
        System.out.println("Index of Jaipur: " + list.lastIndexOf("Jaipur"));
        System.out.println("Index of Meghalaya: " + list.indexOf("Meghalaya"));
        list.remove(0);
        list.set(4, "Ajmer");
        List<String> subList = list.subList(0,3);
        System.out.println("List: " + list + "\tSubList: "+subList);

        list.clear();
        System.out.println("Now empty list: " + list + "\t\t" + list.isEmpty());

    }
}
