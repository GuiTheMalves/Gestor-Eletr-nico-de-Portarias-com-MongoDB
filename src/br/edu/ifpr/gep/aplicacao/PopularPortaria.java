package br.edu.ifpr.gep.aplicacao;

import java.util.List;

import br.edu.ifpr.gep.model.Portaria;
import br.edu.ifpr.gep.model.enums.PortariaEmissor;
import br.edu.ifpr.gep.model.repository.MongoPortariaRepository;
//Classe responsável por popular o gestor eletrônico de portarias (GEP)
public class PopularPortaria {
    private MongoPortariaRepository repo = MongoPortariaRepository.INSTANCE;
    //Método responsável por popular o gestor eletrônico de portarias (GEP)
    public void popular() {
        repo.insert(new Portaria(PortariaEmissor.REITORIA, 234L, "30/05/2000", List.of("Alana Beatriz Pereira")));
        repo.insert(new Portaria(PortariaEmissor.PROENS, 74L, "11/07/2015", List.of("Pietra Maya Souza")));
        repo.insert(new Portaria(PortariaEmissor.PROAD, 112L, "27/03/2001", List.of("Sueli Lúcia Gabriela dos Santos")));
        repo.insert(new Portaria(PortariaEmissor.PROEPPI, 234L, "10/11/2020", List.of("Eduardo Cauã Martins")));
        repo.insert(new Portaria(PortariaEmissor.PROGEPE, 3L, "12/09/2005", List.of("Hugo Fernando Melo")));
        repo.insert(new Portaria(PortariaEmissor.PROPLAN, 14L, "05/08/2009", List.of("Joana Pereira Souza")));
        repo.insert(new Portaria(PortariaEmissor.ARAPONGAS_DG, 1001L, "20/02/2008", List.of("Hadassa Isabella Esther Campos")));
        repo.insert(new Portaria(PortariaEmissor.ASSIS_CHATEAUBRIAND_DG, 79L, "01/12/2011", List.of("Juan Raul Danilo de Paula")));
        repo.insert(new Portaria(PortariaEmissor.ASTORGA_DG, 33L, "30/06/2000", List.of("Débora Joana Farias")));
        repo.insert(new Portaria(PortariaEmissor.BARRACÃO_DG, 79L, "03/05/2019", List.of("Marcos Pedro Bryan Vieira")));
        repo.insert(new Portaria(PortariaEmissor.CAMPO_LARGO_DG, 98L, "19/04/2002", List.of("Murilo Enzo Pedro Araújo")));
        repo.insert(new Portaria(PortariaEmissor.CAPANEMA_DG, 101L, "21/10/2010", List.of("Davi Thales Teixeira")));
        repo.insert(new Portaria(PortariaEmissor.CASCAVEL_DG, 234L, "02/12/2018", List.of("Anderson Thomas Miguel Lima")));
        repo.insert(new Portaria(PortariaEmissor.COLOMBO_DG, 7L, "10/01/2010", List.of("Juliana Adriana Mariah Jesus")));
        repo.insert(new Portaria(PortariaEmissor.CORONEL_VIVIDA_DG, 234L, "15/06/2022", List.of("Juliana Adriana Mariah Jesus")));
        repo.insert(new Portaria(PortariaEmissor.CURITIBA_DG, 11L, "30/12/2009", List.of("Louise Aurora Sophia da Conceição")));
        repo.insert(new Portaria(PortariaEmissor.FOZ_IGUAÇU_DG, 259L, "27/06/2000", List.of("Joaquim Azevedo")));
        repo.insert(new Portaria(PortariaEmissor.GOIOERÊ_DG, 263L, "20/11/2001", List.of("Pedro Caxias")));
        repo.insert(new Portaria(PortariaEmissor.IRATI_DG, 295L, "09/02/2013", List.of("Armando de Souza")));
        repo.insert(new Portaria(PortariaEmissor.IVAIPORÃ_DG, 131L, "14/08/2005", List.of("Joaquim Peixoto")));
        repo.insert(new Portaria(PortariaEmissor.JACAREZINHO_DG, 39L, "24/05/2000", List.of("Ester Lima Andrade")));
        repo.insert(new Portaria(PortariaEmissor.JAGUARIAÍVA_DG, 132L, "12/05/2003", List.of("João Oliveira Santos")));        
        repo.insert(new Portaria(PortariaEmissor.LONDRINA_DG, 262L, "15/08/2010", List.of("Jonas Azevedo")));
        repo.insert(new Portaria(PortariaEmissor.PALMAS_DG, 59L, "04/01/2002", List.of("Maria Antônia Conceição")));
        repo.insert(new Portaria(PortariaEmissor.PARANAGUÁ_DG, 99L, "30/05/2008", List.of("Pablo Alves")));
        repo.insert(new Portaria(PortariaEmissor.PARANAVAÍ_DG, 209L, "30/05/2010", List.of("Stefany Rodrigues Oliveira")));
        repo.insert(new Portaria(PortariaEmissor.PINHAIS_DG, 116L, "26/06/2019", List.of("Roberto Júnior")));
        repo.insert(new Portaria(PortariaEmissor.PITANGA_DG, 115L, "21/05/2004", List.of("Marcos Schmidt")));
        repo.insert(new Portaria(PortariaEmissor.PONTA_GROSSA_DG, 147L, "04/08/2005", List.of("Mateus Abreu Santos")));
        repo.insert(new Portaria(PortariaEmissor.QUEDAS_IGUAÇU_DG, 38L, "19/01/2009", List.of("Lucas Oliveira Paixão")));
        repo.insert(new Portaria(PortariaEmissor.TELÊMACO_BORGA_DG, 27L, "21/03/2010", List.of("Tiago Almeida Amaral")));
        repo.insert(new Portaria(PortariaEmissor.TOLEDO_DG, 294L, "23/04/2014", List.of("Nathalie Carvalho Azevedo")));
        repo.insert(new Portaria(PortariaEmissor.UMUARAMA_DG, 221L, "07/11/2016", List.of("Jonas Costa")));
        repo.insert(new Portaria(PortariaEmissor.UNIÃO_VITÓRIA_DG, 184L, "28/12/2001", List.of("Mariana Coelho Dias")));
        repo.insert(new Portaria(PortariaEmissor.LONDRINA_DG, 262L, "12/05/2019", List.of("Jonas Azevedo")));
        repo.insert(new Portaria(PortariaEmissor.LONDRINA_DG, 218L, "13/08/2017", List.of("Jonas Azevedo")));
        repo.insert(new Portaria(PortariaEmissor.GOIOERÊ_DG, 100L, "23/02/2004", List.of("Pedro Caxias")));
    }
}
