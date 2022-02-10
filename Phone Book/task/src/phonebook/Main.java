package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        ConcurrentSkipListSet<String> personsToFind = getPersonsToFind("/Users/dmitrijlanin/Documents/ReadingFiles/JB_Ac_PhoneBook/find.txt");
        ArrayList<String> unsortedData = getUnsortedData("/Users/dmitrijlanin/Documents/ReadingFiles/JB_Ac_PhoneBook/directory.txt");

        long start = System.currentTimeMillis();
        System.out.println("Start searching (linear search)...");
        SortingAlgs.lineSearch(unsortedData, personsToFind);
        Servises.printTime("Time taken: %s \n\n", System.currentTimeMillis()-start);

        SortingAlgs.jumpSearch(unsortedData, personsToFind);
        SortingAlgs.quickSortBinarySearch(unsortedData, personsToFind);
        SortingAlgs.hashSearch(unsortedData, personsToFind);
    }



    public static ArrayList<String> getUnsortedData(String filePath) throws FileNotFoundException{
        ArrayList<String> unsortedData = new ArrayList<>();
        String str;
        try(Scanner scanner = new Scanner(new File(filePath))){
            while(scanner.hasNext()){
                unsortedData.add(str = scanner.nextLine());
            }
        }
        return unsortedData;
    }

    public static ConcurrentSkipListSet<String> getPersonsToFind(String fileName) throws FileNotFoundException{
        ConcurrentSkipListSet<String> personsToFind = new ConcurrentSkipListSet<>();
        try(Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNext()) {
                personsToFind.add(scanner.nextLine());
            }
        }
        return personsToFind;
    }
}