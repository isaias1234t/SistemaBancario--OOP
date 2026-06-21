package banco_api.conta_service.exception;

public class ContaNaoEncontradaException extends RuntimeException{
    public ContaNaoEncontradaException(String message) {
        super(message);
    }
}
