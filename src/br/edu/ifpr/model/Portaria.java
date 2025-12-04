package br.edu.ifpr.gep.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.edu.ifpr.gep.model.enums.PortariaEmissor;

//Classe responsável por representar uma Portaria, contendo informações
//como emissor, número, data de publicação e membros
public class Portaria {
   //Identificador inteiro que representa o emissor da portaria
   private Integer emissor;

   //Número único da portaria
   private Long número;

   //Data de publicação da portaria
   private String publicação;

   //Lista que contém os membros associados à portaria
   private List<String> membros = new ArrayList<>();

   //Construtor padrão, para criar objetos vazios
   public Portaria() {}

   //Construtor que recebe todos os atributos da classe
   public Portaria(Integer emissor, Long número,
                   String publicação, List<String> membros) {
       this.emissor = emissor;
       this.número = número;
       this.publicação = publicação;
       this.membros = membros;
   }

   //Construtor que utiliza um enum PortariaEmissor como parâmetro para "emissor"
   //Ele converte o enum em seu índice numérico e delega ao outro construtor
   public Portaria(PortariaEmissor emissor, Long número,
                   String publicação, List<String> membros) {
       this(emissor.index(), número, publicação, membros);
   }

   //Retorna o emissor da portaria
   public Integer getEmissor() {
       return emissor;
   }

   //Define o emissor da portaria
   public void setEmissor(Integer emissor) {
       this.emissor = emissor;
   }

   //Retorna o número da portaria
   public Long getNúmero() {
       return número;
   }

   //Define o número da portaria
   public void setNúmero(Long número) {
       this.número = número;
   }

   //Retorna a data de publicação
   public String getPublicação() {
       return publicação;
   }

   //Define a data de publicação
   public void setPublicação(String string) {
       this.publicação = string;
   }

   //Retorna a lista completa de membros
   public List<String> getMembro() {
       return membros;
   }

   //Define a lista de membros da portaria
   public void setMembro(List<String> membro) {
       this.membros = membro;
   }

   //Método que converte o código numérico do emissor para seu nome correspondente
   //percorrendo os valores do enum PortariaEmissor e retornando a String
   //correspondente ao emissor
   String emissorCorreto(Integer temp) {
       String emissorStr = null;
       for (PortariaEmissor type : PortariaEmissor.values()) {
           if (type.index().equals(temp))
               emissorStr = type.nome();
       }
       return emissorStr;
   }

   //Representação textual da Portaria
   @Override
   public String toString() {
       return "Portaria[emissor=" + emissorCorreto(emissor) + ", número=" +
              número + ", publicação=" + publicação +
              ", membro=" + membros + "]";
   }

   //Verifica se duas portarias são iguais comparando seus atributos
   @Override
   public boolean equals(Object obj) {
       if (obj == null) return false;
       if (this.getClass() != obj.getClass()) return false;
       if (this == obj) return true;

       Portaria p = (Portaria) obj;
       if (!this.emissor.equals(p.emissor)) return false;
       if (!this.número.equals(p.número)) return false;
       if (!this.publicação.equals(p.publicação)) return false;
       if (!this.membros.equals(p.membros)) return false;

       return true;
   }

   //Gera um código hash baseado nos atributos da portaria
   @Override
   public int hashCode() {
       return Objects.hash(emissor, membros, número, publicação);
   }
}
