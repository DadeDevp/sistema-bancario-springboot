package br.caixa.sistemabancario.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MensagemCampo {
    private String fieldName;
    private String message;
}
