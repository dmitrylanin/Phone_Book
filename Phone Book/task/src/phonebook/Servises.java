package phonebook;

import java.util.Arrays;

public class Servises {

    public static void printResults(int entriesPlan, int entriesFact) {
        System.out.printf("Found %d / %d entries. ", entriesPlan, entriesFact);
    }

    public static void printTime(String str, long time) {
        System.out.printf(str, Servises.getTimeStatistic(time));
    }


    public static String getTimeStatistic(long delta){

        int minutes = (int) (delta/ 1000)/ 60;
        int seconds = (int)((delta / 1000) % 60);
        int mls = (int) (delta - (long) minutes*60*1000 - seconds*1000);

        return String.format(" %d min. %d sec. %d ms.", minutes, seconds, mls);
    }

    public static boolean compareStrings(String s1, String s2) {
        int comparedResult = s1.compareTo(s2);

        if (comparedResult > 0) {
            return true;
        }
        return false;
    }

    public static String getFIO(String names[]){
        String [] fioArr = Arrays.copyOfRange(names, 1, names.length);

        String fio = "";
        for(int j = 0; j<fioArr.length; j++){
            fio = fio + fioArr[j] + " ";
        }
        return fio.trim();
    }
}
