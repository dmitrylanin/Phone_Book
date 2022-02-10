package phonebook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class SortingAlgs{
    public static long lineSearchTime = 0;

    public static void hashSearch(ArrayList<String> unsortedData, ConcurrentSkipListSet<String> personsToFind){
        HashMap<String, String> sortedData = new HashMap<>();
        int countHahSearch = 0;

        System.out.println();
        System.out.println("Start searching (hash table)...");
        long hashCreateStart = System.currentTimeMillis();
        for (int j=0; j<unsortedData.size(); j++){
            sortedData.put(Servises.getFIO(unsortedData.get(j).split(" ")), unsortedData.get(j).split(" ")[0]);
        }
        long deltaHashCreate = System.currentTimeMillis() - hashCreateStart;
        long hashSearchStart = System.currentTimeMillis();
        for (String str : personsToFind) {
            if (sortedData.containsKey(str)) {
                countHahSearch++;
                if (countHahSearch == personsToFind.size()) {
                    break;
                }
            }
        }
        long deltaHashSearch = System.currentTimeMillis() - hashSearchStart;

        System.out.printf("Found %d / %d entries. Time taken:%s\n", countHahSearch, personsToFind.size(), Servises.getTimeStatistic(deltaHashCreate+deltaHashSearch));
        System.out.printf("Creating time:%s \n", Servises.getTimeStatistic(deltaHashCreate));
        System.out.printf("Searching time:%s \n", Servises.getTimeStatistic(deltaHashSearch));;
    }


    public static void quickSortBinarySearch(ArrayList<String> unsortedData, ConcurrentSkipListSet<String> personsToFind) throws InterruptedException {
        int countBinarySearch = 0;

        System.out.println();
        System.out.println("Start searching (quick sort + binary search)...");
        long quickSortStart = System.currentTimeMillis();
        ArrayList<String> quickSortedData = unsortedData;
        Collections.sort(quickSortedData);
        Thread.currentThread().sleep(50000);
        long deltaQuickStart = System.currentTimeMillis() - quickSortStart;
        long binarySearchStart = System.currentTimeMillis();
        for (String str : personsToFind) {
            if (Collections.binarySearch(quickSortedData, str) != -1) {
                countBinarySearch++;
                if (countBinarySearch == personsToFind.size()) {
                    break;
                }
            }
        }
        long deltaBinarySearch = System.currentTimeMillis() - binarySearchStart;
        System.out.printf("Found %d / %d entries. Time taken:%s\n", countBinarySearch, personsToFind.size(), Servises.getTimeStatistic(deltaQuickStart+deltaBinarySearch));
        System.out.printf("Sorting time:%s \n", Servises.getTimeStatistic(deltaQuickStart));
        System.out.printf("Searching time:%s \n", Servises.getTimeStatistic(deltaBinarySearch));;
    }

    public static ArrayList<String> quickSort(ArrayList<String> unsortedData){
        if (unsortedData.size() == 0) return unsortedData;
        String pivot = unsortedData.get(0);
        ArrayList<String> lLower = new ArrayList<>();
        ArrayList<String> lHigher = new ArrayList<>();
        for (String current : unsortedData) {
            if (Servises.compareStrings(current, pivot)) {
                lLower.add(current);
            }
            if (!Servises.compareStrings(current, pivot)) {
                lHigher.add(current);
            }
        }
        ArrayList<String> sorted = new ArrayList<>();
        sorted.addAll(quickSort(lLower));
        sorted.add(pivot);
        sorted.addAll(quickSort(lHigher));
        return sorted;
    }


    public static void jumpSearch(ArrayList<String> unsortedData, ConcurrentSkipListSet<String> personsToFind){
        int countJumpSearch = 0;
        long startSearch;

        System.out.println("Start searching (bubble sort + jump search)...");
        long start = System.currentTimeMillis();
        ArrayList<String> sortedData = getBubbleSortedData(unsortedData, start);
        long deltaBubbleSorting = System.currentTimeMillis() - start;

        if(sortedData.isEmpty()){
            long lineSearchStart = System.currentTimeMillis();
            lineSearch(unsortedData, personsToFind);
            long deltaSearchFinish = System.currentTimeMillis()-lineSearchStart;
            Servises.printTime("Time taken: %s \n", deltaBubbleSorting+deltaSearchFinish);
            Servises.printTime("Sorting time:%s - STOPPED, moved to linear search\n", deltaBubbleSorting);
            Servises.printTime("Searching time:%s \n", deltaSearchFinish);
        }else{
            startSearch = System.currentTimeMillis();
            for (String str: personsToFind){
                if(SortingAlgs.jumpSearchEngine(sortedData, str) != -1){
                    countJumpSearch++;
                    if (countJumpSearch == personsToFind.size()){
                        break;
                    }
                }
            }
            long finishSearch = System.currentTimeMillis() - startSearch;
            System.out.printf("Found %d / %d entries. Time taken:%s\n", countJumpSearch, personsToFind.size(), Servises.getTimeStatistic(deltaBubbleSorting));
            System.out.printf("Sorting time:%s \n", Servises.getTimeStatistic(deltaBubbleSorting));
            System.out.printf("Searching time:%s \n", Servises.getTimeStatistic(finishSearch));;
        }
    }


    public static ArrayList<String> getBubbleSortedData(ArrayList<String> unsortedData, long start){
        ArrayList<String> sortedData = unsortedData;
        boolean isSorted = false;
        String buf;

        while(!isSorted) {
            isSorted = true;
            for (int i = 0; i < unsortedData.size()-1; i++) {
                if(Servises.compareStrings(unsortedData.get(i), unsortedData.get(i+1))){
                    isSorted = false;
                    buf = unsortedData.get(i);
                    unsortedData.set(i, unsortedData.get(i+1));
                    unsortedData.set(i+1, buf);
                }
                if(System.currentTimeMillis() - start>(lineSearchTime*1000)){
                    return new ArrayList<String>();
                }
            }
        }
        return sortedData;
    }



    public static void lineSearch(ArrayList<String> unsortedData, ConcurrentSkipListSet<String> personsToFind){
        int countSimpleSearch = 0;
        String fio = "";
        for(int j = 0 ; j<unsortedData.size(); j++){
            fio = Servises.getFIO(unsortedData.get(j).split(" "));
            if(personsToFind.contains(fio)){
                countSimpleSearch++;
                if (countSimpleSearch == personsToFind.size()){
                    Servises.printResults(countSimpleSearch, personsToFind.size());
                    break;
                }
            }
        }
    }

    public static int jumpSearchEngine(ArrayList<String> arrayToSearch, String element){
        int blockSize = (int) Math.floor(Math.sqrt(arrayToSearch.size()));
        int currentLastIndex = blockSize-1;

        while (currentLastIndex < arrayToSearch.size() && Servises.compareStrings(element, Servises.getFIO(arrayToSearch.get(currentLastIndex).split(" ")))) {
            currentLastIndex += blockSize;
        }
        for (int currentSearchIndex = currentLastIndex - blockSize + 1;
             currentSearchIndex <= currentLastIndex && currentSearchIndex < arrayToSearch.size(); currentSearchIndex++) {
            if (element == arrayToSearch.get(currentSearchIndex)){
                return currentSearchIndex;
            }
        }
        return -1;
    }
}