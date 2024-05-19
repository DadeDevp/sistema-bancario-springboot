package br.caixa.sistemabancario.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErroValidacao  extends ErroPadrao{
    private List<MensagemCampo> erros = new ArrayList<>();

    public ErroValidacao(Instant timestamp, String status, String message, String path) {
        super(timestamp, status, message, path);
    }

    public void addError(String fieldName, String message) {
        this.erros.add(new MensagemCampo(fieldName,message));
    }
}
