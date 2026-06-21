package banco_api.conta_service.exception;

public class ValorInvalidoException extends RuntimeException{
    public ValorInvalidoException(String message) {
        super(message);
    }
}
