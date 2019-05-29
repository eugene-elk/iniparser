package IniParser;

public class IniException extends Exception {
    private Exception innerException;

    public IniException(String message) {
        super(message);
    }

    public IniException(String message, Exception innerException) {
        this(message);
        this.innerException = innerException;
    }

    public Exception getInnerException() {
        return innerException;
    }
}