package Search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Find {
    public  File f;
    List<String> cities= new ArrayList<>();
    Find() throws FileNotFoundException {
        f=new File("C:\\Users\\Hetul Shah\\Downloads\\simplemaps_worldcities_basicv1.75\\worldcities.csv");
        Scanner sc= new Scanner(f);
        while(sc.hasNext()){
            String s1=sc.nextLine();
                cities.add(s1);
        }
        sc.close();
    }
    public  List<String> main(String s) throws FileNotFoundException {

        List<String> l=new ArrayList<>();
        for (String city:cities)

        {
            String s1=city;
            String s2=s1;
            s1=s1.toLowerCase(Locale.ROOT);
            if(s1.contains(s))
            l.add(s2);
        }
        return l;
    }
}
