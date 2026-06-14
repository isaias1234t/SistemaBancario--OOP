package banco_api.handler;

import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import banco_api.exception.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<String> handleClienteNaoEncontrado(ClienteNaoEncontradoException ex){
        log.error("O cliente número {} não foi encontrado", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());

    }
    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<String> handleCredenciaisInvalidasException(CredenciaisInvalidasException ex){
        log.warn("Tentativa de login com credenciais inválidas: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ex.getMessage());

    }
    @ExceptionHandler(ContaNaoEncontradaException.class)
    public ResponseEntity<String> handleContaNaoEncontrada(ContaNaoEncontradaException ex){
        log.error("A conta número {} não foi encontrada.", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<String> handleSaldoInsuficiente(SaldoInsuficienteException ex){
        log.warn("Tentativa de transação com saldo insuficiente: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }
    @ExceptionHandler(ValorInvalidoException.class)
    public ResponseEntity<String> handleValorInvalido(ValorInvalidoException ex){
        log.warn("Tentativa de transação com valor inválido: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex){
        String erro = ex
                .getBindingResult()
                .getFieldError()
                .getDefaultMessage();
        log.warn("Dados errôneos: {}", erro);
        return ResponseEntity.badRequest().body(erro);
    }

}
