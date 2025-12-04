package br.edu.ifpr.gep.model.utils;

import java.text.Normalizer;

import br.edu.ifpr.gep.model.enums.PortariaEmissor;

//Classe utilitária responsável por converter e exibir valores relacionados ao emissor das portarias.
//Fornece métodos para transformar dados entre String e Integer, além de tratar variações de escrita.
public class EmissorConverter {

    //Retorna o nome do emissor correspondente ao código inteiro informado
	//a fim de poder exibir o emissor de maneira agradável:
	//LONDRINA_DG ====> Campus Londrina DG
    public String exibirEmissor(Integer temp) {   
        String emissorStr = null;
        //Percorre todos os valores possíveis do enum PortariaEmissor
        for (PortariaEmissor type : PortariaEmissor.values() ) {
            //Quando o índice do enum coincide com o valor informado, recupera o nome correspondente
        	//em forma de string
            if (type.index().equals(temp))
                emissorStr = type.nome();
        }
        return emissorStr;
    }

    //Converte uma String que representa o nome de um emissor em seu respectivo código
    public Integer converterEmissorParaInteger(String emissorStr) {
        String checagem = emissorStr.trim();

        //Caso a entrada vazia, retorna nulo
        if (checagem.isEmpty()) {
            return null;
        }

        //Normaliza a entrada (remove acentos, coloca em maiúsculas)
        String entradaNormal = normalize(checagem).toUpperCase();
        String entradaSEMDG = tirarDg(entradaNormal);

        //Percorre todos os emissores possíveis
        for (PortariaEmissor tipo : PortariaEmissor.values()) {
            //Normaliza o nome da constante enum
            String enumName = normalize(tipo.name()).toUpperCase();

            //Se o nome normalizado do enum for igual à entrada, retorna seu índice
            if (enumName.equals(entradaNormal)) {
                return tipo.index();
            }

            //Normaliza o campo nome() do enum, caso haja diferença do name()
            //campo nome() = com espaços, letras maiúsculas e minúsuclas como Londrina
            //campo name() = exatamente como está no enum, como LONDRINA_DG
            String nomeCampo = tipo.nome() != null ? tipo.nome() : tipo.name();
            String nomeNorm = normalize(nomeCampo).toUpperCase();

            //Caso o nome normalizado seja igual, retorna o índice
            if (nomeNorm.equals(entradaNormal)) return tipo.index();

            //Trata nomes com e sem o sufixo "DG" (aceita variações como LONDRINA, LONDRINADG, LONDRINA_DG)
            String emissorSemDG = tirarDg(nomeNorm);
            if (emissorSemDG.equals(entradaSEMDG)) {
                return tipo.index();
            }

            //Permite buscas parciais (entrada e nome contendo um ao outro)
            if (!emissorSemDG.isEmpty() && (emissorSemDG.contains(entradaSEMDG) || entradaSEMDG.contains(emissorSemDG))) {
                return tipo.index();
            }
        }
        //Caso nada seja encontrado, retorna null
        return null;
    }

    //Normaliza uma String, removendo acentuação e caracteres especiais
    private String normalize(String s) {
        if (s == null) {
            return "";
        }
        // NFD separa letras de seus acentos e remove as marcas diacríticas
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    }

    //Remove o sufixo DG da string
    private String tirarDg(String s) {
        if (s == null) {
            return "";
        }
        //Remove DG do final, caso exista
        if (s.endsWith("DG")) s = s.substring(0, s.length() - 2);
        //Remove underlines, traços ou caracteres extras ao final
        s = s.replaceAll("[_\\-]+$", "");
        return s;
    }
}
