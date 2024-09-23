import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

public class teste {
    public static void main(String[] args) {
        // ArrayList<String> lista = new ArrayList<>();
        // lista.add("03"); 
        // lista.add("01"); 
        // lista.add("02"); 
        // lista.add("05"); 
        // System.out.println(lista);
        // Collections.sort(lista);
        // System.out.println(lista);
        // if (Pattern.matches( "^\\d{4}$", "00dsd0")){
        //     System.out.println("Sim");
        // } else{
        //     System.out.println("nao");
        // }

        String str = "a,b,c,d,e";
        String[] array = str.split(",");
        System.out.println(array[0]);
        String str2 = " " ;
        System.out.println(str2.isEmpty());

    }   
}
