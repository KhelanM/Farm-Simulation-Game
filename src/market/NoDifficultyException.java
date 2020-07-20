package market;

/**
 * Exception Class that extends runtimeException
 * the main purpose is to keep the code running when any unexpected error has occured
 */
public class NoDifficultyException extends RuntimeException {
    public NoDifficultyException(String s) {
        super(s);
    }
}
