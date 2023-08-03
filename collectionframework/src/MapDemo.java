import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapDemo {
    public static void main(String[] args) {
        Map<String, Double> itemMap = new TreeMap<>();
        itemMap.put("Milk", 30.25);
        itemMap.put("Chocolate", 3d);
        itemMap.put("Toy plane", 2000.75);
        itemMap.put("Hair oil", 60.15);
        System.out.println(itemMap);
        System.out.println(itemMap.keySet());
        System.out.println(itemMap.values());
        for (Map.Entry<String, Double> entry : itemMap.entrySet()) {
            entry.setValue(entry.getValue() + 10);
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        System.out.println(itemMap);
        System.out.println("----------------------------------");
        System.out.println(itemMap.put("Milk", 99d));
        System.out.println(itemMap);
        System.out.println(itemMap.putIfAbsent("Milk", 77d));
        System.out.println(itemMap.putIfAbsent("Ghee", 77d));
        System.out.println(itemMap);
        System.out.println(itemMap.replace("Milk", 66d));
        System.out.println(itemMap.replace("Milk", 66d, 605d));
        System.out.println(itemMap.replace("Cow Milk", 55d));
        System.out.println(itemMap);
        System.out.println("---------------------------------------");
        itemMap.forEach((k,v) -> System.out.println(k + " => " + v));
        double milkPrice =  itemMap.getOrDefault("Milk", -192.45);
        double pufPrice =  itemMap.getOrDefault("Puff", -192.45);
        System.out.println("Cow milk price: " + milkPrice + "\tPuff price" + pufPrice);
        System.out.println("Item map is " + (itemMap.isEmpty() ? " empty" : "not empty") + " and its size = " + itemMap.size());
    }
}

