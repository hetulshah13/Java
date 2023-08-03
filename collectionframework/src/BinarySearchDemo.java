import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BinarySearchDemo {
    public static void main(String[] args) {
        final int MAX = 100;
        Random random = new Random();
        List<Integer> list = new ArrayList<>(0);
        for (int i=0;i<10;i++)
            list.add(random.nextInt(MAX));
        Collections.sort(list);
        System.out.println("LIST : " + list);
        Scanner sc = new Scanner(System.in);
        System.out.print("ENTER NUMBER TO FIND: ");
        int find = sc.nextInt();

        /*
        int low = 0;
        int high = list.size()-1;
        do {
            int mid = (low + high) / 2;
            int val = list.get(mid);
            if (find == val) {
                System.out.println("NUMBER FOUND ON " + mid + " INDEX");
                return;
            }
            else if (find < val) {
                high = mid-1;
            }
            else if (find > val) {
                low = mid+1;
            }
        } while (low <= high);
        System.out.println("NUMBER NOT FOUND");
        */
        int i=list.indexOf(find);
        if(i>-1)System.out.println("NUMBER FOUND ON " + i + " INDEX");
        else System.out.println("NUMBER NOT FOUND");

    }
}

 class UnsafeArrayListExample {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> unsafeArrayList = new ArrayList<>();
        unsafeArrayList.add(1);
        unsafeArrayList.add(2);
        unsafeArrayList.add(3);

        // Create a thread pool of size 10
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        // Create a Runnable task that increments each element of the ArrayList by one
        Runnable task = () -> {
            incrementArrayList(unsafeArrayList);
        };

        // Submit the task to the executor service 100 times.
        // All the tasks will modify the ArrayList concurrently
        for(int i = 0; i < 100; i++) {
            executorService.submit(task);
        }

        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);

        System.out.println(unsafeArrayList);
    }

    // Increment all the values in the ArrayList by one
    private static synchronized void incrementArrayList(List<Integer> unsafeArrayList) {
        for(int i = 0; i < unsafeArrayList.size(); i++) {
            Integer value = unsafeArrayList.get(i);
            unsafeArrayList.set(i, value + 1);
        }
    }
}