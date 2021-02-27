package data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TextIOFile {

    private static File myFile = new File("record.txt");

    public static void writeRecord(Data data) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(myFile, true));
        writer.print(data.toString());
        writer.flush();
        writer.close();
    }

    public static ArrayList<Data> findAll() throws IOException {
        Scanner scanner = new Scanner(myFile);
        ArrayList<Data> list = new ArrayList<Data>();
        while (scanner.hasNext()) {
            Data data = new Data(scanner.nextLine());
            list.add(data);
        }
        scanner.close();
        return list;
    }

    public static ArrayList<Data> findForCity(String city) throws IOException {
        Scanner scanner = new Scanner(myFile);
        ArrayList<Data> list = new ArrayList<Data>();
        while (scanner.hasNext()) {
            Data data = new Data(scanner.nextLine());
            if(data.getCity().equals(city)){
                list.add(data);
            }
        }
        scanner.close();
        return list;
    }

    public static ArrayList<Data> findForDate(String date) throws IOException {
        Scanner scanner = new Scanner(myFile);
        ArrayList<Data> list = new ArrayList<Data>();
        while (scanner.hasNext()) {
            Data data = new Data(scanner.nextLine());
            if(data.getDate().equals(date)){
                list.add(data);
            }
        }
        scanner.close();
        return list;
    }


}


