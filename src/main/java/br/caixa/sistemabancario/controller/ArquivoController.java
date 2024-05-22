package br.caixa.sistemabancario.controller;

import br.caixa.sistemabancario.service.ArquivoService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/arquivos")
public class ArquivoController {
    final private ArquivoService arquivoService;

    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }


    @PostMapping("/csv")
    @Schema(title = "Criacao de contas via arquivos csv", description = "Deve ser anexado um arquivo csv no formato padrão")
    public ResponseEntity<byte[]> processCSV(@RequestParam("file") MultipartFile file) {

        return arquivoService.processarArquivoCsv(file);
    }
}
