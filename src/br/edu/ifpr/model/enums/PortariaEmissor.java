package br.edu.ifpr.gep.model.enums;

import br.edu.ifpr.gep.model.enums.PortariaEmissor;

//Enum que representa todos os emissores possíveis de uma portaria
public enum PortariaEmissor {
   //Cada constante possui um índice numérico e um nome amigável para exibição
   REITORIA(1,"Reitoria"),
   PROENS(2,"Pró-Reitoria de Ensino"),
   PROAD(3,"Pró-Reitoria de Administração"),
   PROEPPI(4,"Pró-Reitoria de Extensão, Pesquisa, Pós-Graduação e Inovação"),
   PROGEPE(5,"Pró-Reitoria de Pessoas"),
   PROPLAN(6,"Pró-Reitoria de Planejamento e Desenvolvimento Institucional"),
   ARAPONGAS_DG(7,"Campus Arapongas (DG)"),
   ASSIS_CHATEAUBRIAND_DG(8,"Campus Assis Cheteaubriand (DG)"),
   ASTORGA_DG(9,"Campus Astorga (DG)"),
   BARRACÃO_DG(10,"Campus Barracão (DG)"),
   CAMPO_LARGO_DG(11,"Campus Campo Largo (DG)"),
   CAPANEMA_DG(12,"Campus Capanema (DG)"),
   CASCAVEL_DG(13,"Campus Cascavel (DG)"),
   COLOMBO_DG(14,"Campus Colombo (DG)"),
   CORONEL_VIVIDA_DG(15,"Campus Coronel Vivida (DG)"),
   CURITIBA_DG(16,"Campus Curitiba (DG)"),
   FOZ_IGUAÇU_DG(17,"Campus Foz do Iguaçu (DG)"),
   GOIOERÊ_DG(18,"Campus Goioerê (DG)"),
   IRATI_DG(19,"Campus Irati (DG)"),
   IVAIPORÃ_DG(20,"Campus Ivaiporã (DG)"),
   JACAREZINHO_DG(21,"Campus Jacarezinho (DG)"),
   JAGUARIAÍVA_DG(22,"Campus Jaguariaíva (DG)"),
   LONDRINA_DG(23,"Campus Londrina (DG)"),
   PALMAS_DG(24,"Campus Palmas (DG)"),
   PARANAGUÁ_DG(25,"Campus Paranaguá (DG)"),
   PARANAVAÍ_DG(26,"Campus Paranavaí (DG)"),
   PINHAIS_DG(27,"Campus Pinhais (DG)"),
   PITANGA_DG(28,"Campus Pitanga (DG)"),
   PONTA_GROSSA_DG(29,"Campus Ponta Grossa (DG)"),
   QUEDAS_IGUAÇU_DG(30,"Campus Quedas do Iguaçu (DG)"),
   TELÊMACO_BORGA_DG(31,"Campus Telêmaco Borba (DG)"),
   TOLEDO_DG(32,"Campus Toledo (DG)"),
   UMUARAMA_DG(33,"Campus Umuarama (DG)"),
   UNIÃO_VITÓRIA_DG(34,"Campus União da Vitória (DG)");

   //Índice numérico usado para identificar o emissor
   private Integer index;
   //Nome completo que será exibido ao usuário
   private String nome;

   //Construtor que atribui índice e nome para cada constante
   PortariaEmissor(Integer index, String nome) {
      this.index = index;
      this.nome  = nome;
   }

   //Retorna o código numérico do emissor
   public Integer index() { return index; }
   //Retorna o nome "amigável" do emissor
   public String nome() { return nome; }

   //Retorna a constante correspondente ao índice informado
   public static PortariaEmissor fromValue(Integer index) {
      for (PortariaEmissor type : values())
         if (type.index == index)
            return type;
      //Caso não exista um emitente válido para o índice fornecido
      //há uma exceção
      throw new IllegalArgumentException("Nenhum emissor para " + index);
   }
}
