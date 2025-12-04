package br.edu.ifpr.gep.model.repository;

import java.util.Objects;

//Classe imutável que representa a suposta "chave primária"
//no nosso programa
class PortariaPK {
   //Código do emissor da portaria
   private Integer emissor;
   //Número da portaria
   private Long número;
   //Ano de publicação da portaria
   private Integer ano;

   public PortariaPK(Integer emissor, Long número, Integer ano) {
	   //Armazena os valores da chave composta que identifica unicamente uma portaria
	   this.emissor = emissor;
	   this.número  = número;
	   this.ano     = ano;
   }

   //Retorna o emissor associado à portaria
   public Integer getEmissor() { return emissor; }
   //Retorna o número da portaria
   public Long getNúmero() { return número; }
   //Retorna o ano da publicação da portaria
   public Integer getAno() { return ano; }

   @Override
   public String toString() {
	   //Retorna uma representação textual da "chave primária"
	   return "PortariaPK [emissor=" + emissor + ", número=" + número +
			  ", ano=" + ano + "]";
   }

   @Override
   public int hashCode() {
	   //Gera um código hash baseado nos campos que compõem a chave
	   return Objects.hash(emissor,número,ano);
   }

   @Override
   public boolean equals(Object obj) {
	//Verifica igualdade entre duas chaves comparando seus atributos
	if (this == obj) return true;
	if (obj == null) return false;
	if (getClass() != obj.getClass()) return false;

	PortariaPK other = (PortariaPK) obj;
	return Objects.equals(ano,other.ano) &&
		   Objects.equals(emissor,other.emissor) &&
		   Objects.equals(número,other.número);
   }
}
