package br.edu.ifpr.gep.model.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ParseValores {
    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final ValidarEntradaUsuario vali = new ValidarEntradaUsuario();

	public LocalDate ParseData(String dataStr) {
		LocalDate data = null;
		try {
		    data = LocalDate.parse(dataStr, formato);
		    System.out.println(data);
		} catch (DateTimeParseException e) {
		    System.out.println("Erro ao converter a data. A string não corresponde ao padrão 'dd/MM/yyyy'.");
		    e.printStackTrace();
		}
		return data;
	}
	public Long ParseNumero(String numeroStr) {
		Long numero = 0L;
        try {
            numero = Long.parseLong(numeroStr);
        } catch (NumberFormatException e) {
            vali.exibirValorInvalido();
        }
        return numero;
	}
	
}
