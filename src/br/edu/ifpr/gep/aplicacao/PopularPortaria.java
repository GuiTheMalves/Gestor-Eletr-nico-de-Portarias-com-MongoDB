package br.edu.ifpr.gep.aplicacao;

import java.time.LocalDate;
import java.util.List;

import br.edu.ifpr.gep.model.Portaria;
import br.edu.ifpr.gep.model.enums.PortariaEmissor;
import br.edu.ifpr.gep.model.repository.MongoPortariaRepository;
//Classe responsável por popular o gestor eletrônico de portarias (GEP)
public class PopularPortaria {
    private MongoPortariaRepository repo = MongoPortariaRepository.INSTANCE;
    //Método responsável por popular o gestor eletrônico de portarias (GEP)
    public void popular() {
    	repo.insert(new Portaria(PortariaEmissor.REITORIA,234L,LocalDate.of(2000,5,30),List.of("Alana Beatriz Pereira")));
		repo.insert(new Portaria(PortariaEmissor.PROENS,74L,LocalDate.of(2015,7,11),List.of("Pietra Maya Souza")));
		repo.insert(new Portaria(PortariaEmissor.PROAD,112L,LocalDate.of(2001,3,27),List.of("Sueli Lúcia Gabriela dos Santos")));
		repo.insert(new Portaria(PortariaEmissor.PROEPPI,234L,LocalDate.of(2020,11,10),List.of("Eduardo Cauã Martins")));
		repo.insert(new Portaria(PortariaEmissor.PROGEPE,3L,LocalDate.of(2005,9,12),List.of("Hugo Fernando Melo")));
		repo.insert(new Portaria(PortariaEmissor.PROPLAN,14L,LocalDate.of(2009,8,5),List.of("Joana Pereira Souza")));
		repo.insert(new Portaria(PortariaEmissor.ARAPONGAS_DG,1001L,LocalDate.of(2008,2,20),List.of("Hadassa Isabella Esther Campos")));
		repo.insert(new Portaria(PortariaEmissor.ASSIS_CHATEAUBRIAND_DG,79L,LocalDate.of(2011,12,1),List.of("Juan Raul Danilo de Paula")));
		repo.insert(new Portaria(PortariaEmissor.ASTORGA_DG,33L,LocalDate.of(2000,6,30),List.of("Débora Joana Farias")));
		repo.insert(new Portaria(PortariaEmissor.BARRACÃO_DG,79L,LocalDate.of(2019,5,3),List.of("Marcos Pedro Bryan Vieira")));
		repo.insert(new Portaria(PortariaEmissor.CAMPO_LARGO_DG,98L,LocalDate.of(2002,4,19),List.of("Murilo Enzo Pedro Araújo")));
		repo.insert(new Portaria(PortariaEmissor.CAPANEMA_DG,101L,LocalDate.of(2010,10,21),List.of("Davi Thales Teixeira")));
		repo.insert(new Portaria(PortariaEmissor.CASCAVEL_DG,234L,LocalDate.of(2018,12,2),List.of("Anderson Thomas Miguel Lima")));
		repo.insert(new Portaria(PortariaEmissor.COLOMBO_DG,7L,LocalDate.of(2010,1,10),List.of("Juliana Adriana Mariah Jesus")));
		repo.insert(new Portaria(PortariaEmissor.CORONEL_VIVIDA_DG,234L,LocalDate.of(2022,6,15),List.of("Juliana Adriana Mariah Jesus")));
		repo.insert(new Portaria(PortariaEmissor.CURITIBA_DG,11L,LocalDate.of(2009,12,30),List.of("Louise Aurora Sophia da Conceição")));
		repo.insert(new Portaria(PortariaEmissor.FOZ_IGUAÇU_DG,259L,LocalDate.of(2000,6,27),List.of("Joaquim Azevedo")));
		repo.insert(new Portaria(PortariaEmissor.GOIOERÊ_DG,263L,LocalDate.of(2001,11,20),List.of("Pedro Caxias")));
		repo.insert(new Portaria(PortariaEmissor.IRATI_DG,295L,LocalDate.of(2013,2,9),List.of("Armando de Souza")));
		repo.insert(new Portaria(PortariaEmissor.IVAIPORÃ_DG,131L,LocalDate.of(2005,8,14),List.of("Joaquim Peixoto")));
		repo.insert(new Portaria(PortariaEmissor.JACAREZINHO_DG,39L,LocalDate.of(2000,5,24),List.of("Ester Lima Andrade")));
		repo.insert(new Portaria(PortariaEmissor.JAGUARIAÍVA_DG,132L,LocalDate.of(2003,5,12),List.of("João Oliveira Santos")));		
		repo.insert(new Portaria(PortariaEmissor.LONDRINA_DG,262L,LocalDate.of(2010,8,15),List.of("Jonas Azevedo")));
		repo.insert(new Portaria(PortariaEmissor.PALMAS_DG,59L,LocalDate.of(2002,1,4),List.of("Maria Antônia Conceição")));
		repo.insert(new Portaria(PortariaEmissor.PARANAGUÁ_DG,99L,LocalDate.of(2008,5,30),List.of("Pablo Alves")));
		repo.insert(new Portaria(PortariaEmissor.PARANAVAÍ_DG,209L,LocalDate.of(2010,5,30),List.of("Stefany Rodrigues Oliveira")));
		repo.insert(new Portaria(PortariaEmissor.PINHAIS_DG,116L,LocalDate.of(2019,6,26),List.of("Roberto Júnior")));
		repo.insert(new Portaria(PortariaEmissor.PITANGA_DG,115L,LocalDate.of(2004,5,21),List.of("Marcos Schmidt")));
		repo.insert(new Portaria(PortariaEmissor.PONTA_GROSSA_DG,147L,LocalDate.of(2005,8,4),List.of("Mateus Abreu Santos")));
		repo.insert(new Portaria(PortariaEmissor.QUEDAS_IGUAÇU_DG,38L,LocalDate.of(2009,1,19),List.of("Lucas Oliveira Paixão")));
		repo.insert(new Portaria(PortariaEmissor.TELÊMACO_BORGA_DG,27L,LocalDate.of(2010,3,21),List.of("Tiago Almeida Amaral")));
		repo.insert(new Portaria(PortariaEmissor.TOLEDO_DG,294L,LocalDate.of(2014,4,23),List.of("Nathalie Carvalho Azevedo")));
		repo.insert(new Portaria(PortariaEmissor.UMUARAMA_DG,221L,LocalDate.of(2016,11,7),List.of("Jonas Costa")));
		repo.insert(new Portaria(PortariaEmissor.UNIÃO_VITÓRIA_DG,184L,LocalDate.of(2001,12,28),List.of("Mariana Coelho Dias")));
		repo.insert(new Portaria(PortariaEmissor.LONDRINA_DG,262L,LocalDate.of(2019,5,12),List.of("Jonas Azevedo")));
		repo.insert(new Portaria(PortariaEmissor.LONDRINA_DG,218L,LocalDate.of(2017,8,13),List.of("Jonas Azevedo")));
		repo.insert(new Portaria(PortariaEmissor.GOIOERÊ_DG,100L,LocalDate.of(2004,2,23),List.of("Pedro Caxias")));
    }
}
