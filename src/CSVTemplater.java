package sablona;

import java.util.*;
import java.io.*;

public class CSVTemplater {

    public static void main(String[] args)  throws java.io.IOException {

        String templateName = "";
        String csvName = "";
        String sablona = "";
        String outFile = "templater-out-%05d.txt";
        Scanner s;

        for (int i = 0; i < args.length; i++)
        {
            if (args[i].startsWith("--template=")) {
                templateName = args[i].substring(11);
                System.out.println("Soubor s sablonou je " + templateName);
            } else if (args[i].startsWith("--csv=")) {
                csvName = args[i].substring(6);
                System.out.println("CSV soubor je " + csvName);
            } else if (args[i].startsWith("--out")) {
                outFile = args[i].substring(6);
                System.out.println("Out soubor je " + csvName);
            }
        }

        if (templateName.equals("")){
            System.out.println("Nezadan parametr --template");
            return;
        }

        if (csvName.equals("")){
            s = new Scanner(  System.in ).useDelimiter("\n");
        } else {
            s = new Scanner(  new FileReader(csvName) ).useDelimiter("\n");
        }

        ArrayList csvLines = new ArrayList( );
        try {
            String radek = s.nextLine();
            csvLines.add(radek);
            do {
                radek = s.nextLine();
                csvLines.add(radek);
            } while (true);
        }
        catch (NoSuchElementException e)
        {
            
        }

        s = new Scanner(   new FileReader(templateName) ).useDelimiter("\n");
        try {
            do {
                String radek = s.nextLine();
                sablona = sablona + radek;
                sablona = sablona + "\n";
            } while (true);
        }
        catch (NoSuchElementException e)
        {
            
        }

        String sN = (String)csvLines.get(0);
        String[] pNames = sN.split(",");
        for (int nCisloOsoby = 1; nCisloOsoby < csvLines.size(); nCisloOsoby++) {

            String[] pValues = ((String) csvLines.get(nCisloOsoby)).split(",");
            String vystup = sablona;

            for (int i = 0; i < pNames.length; i++) {
                int pozice = -1;
                do {
                    pozice = vystup.indexOf("{{ " + pNames[i].trim() + " }}");
                    if (pozice >= 0) {
                        vystup = vystup.substring(0, pozice) + pValues[i].trim() + vystup.substring(pozice + pNames[i].trim().length() + 6);
                    }
                } while (pozice >= 0);
            }

            String nazevFile = String.format(outFile, nCisloOsoby);

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(nazevFile))) {
                bw.write(vystup);
                bw.flush();
            } catch (Exception e) {
                System.err.println("Do souboru se nepovedlo zapsat.");
            }
        }
    }
}
