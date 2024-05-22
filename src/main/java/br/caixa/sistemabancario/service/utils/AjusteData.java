package br.caixa.sistemabancario.service.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AjusteData {
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static public LocalDate convertTextToLocalDate(String data){

        LocalDate dataLocalDate = LocalDate.parse(data.trim(),FORMATTER);
        return dataLocalDate;
    }
}