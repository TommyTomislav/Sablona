package sablona;

import java.util.*;

public class Templater {
    public static void main(String[] args)  throws java.io.IOException {

        HashMap<String,String> parameterMap = new HashMap<String, String>();

        for (int i = 0; i < args.length; i++)
        {
            if (args[i].startsWith("--var=")) {
                String defParameter = args[i].substring(6);
                int poziceRovnaSe = defParameter.indexOf('=');
                if (poziceRovnaSe >= 0) {
                    String nazevParam = defParameter.substring(0, poziceRovnaSe);
                    String hodnotaParam = defParameter.substring(poziceRovnaSe + 1);
                    parameterMap.put(nazevParam,hodnotaParam);
                }
            }
        }

        String sablona = "";
        Scanner s = new Scanner(System.in).useDelimiter("\n");
        try {
            do {
                String radek = s.nextLine();
                sablona = sablona + radek;
                sablona = sablona + "\n";
            } while (true);
        }
        catch (NoSuchElementException e) {    
        }                


        for (String hashK : parameterMap.keySet()) {
            sablona.replaceAll("\\{\\{\\s" + hashK + "\\s\\}\\}", parameterMap.get(hashK));
            int pozice = -1;
            do {
                pozice = sablona.indexOf("{{ " + hashK + " }}");
                if (pozice >= 0) {
                    sablona = sablona.substring(0, pozice) + parameterMap.get(hashK) + sablona.substring(pozice + hashK.length() + 6);
                }
            } while (pozice >= 0);
        }
        System.out.print(sablona);
    }
}