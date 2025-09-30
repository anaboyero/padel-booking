package ana.learning.padel.padelBooking.exceptions;

public class PastDateException extends RuntimeException {

    // Constructor que acepta solo el mensaje
    public PastDateException(String message) {
        // Llama al constructor de la clase padre (RuntimeException) con el mensaje
        super(message);
    }

}