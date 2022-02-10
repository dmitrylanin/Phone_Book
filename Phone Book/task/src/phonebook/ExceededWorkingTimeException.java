package phonebook;

public class ExceededWorkingTimeException extends Exception{
    long timeDelta;

    public ExceededWorkingTimeException(long timeDelta){
        super();
        this.timeDelta = timeDelta;
    }

}
