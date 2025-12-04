package br.edu.ifpr.gep.model.utils.comparators;

import java.time.LocalDate;
import java.util.Comparator;

import br.edu.ifpr.gep.model.Portaria;
import br.edu.ifpr.gep.model.utils.ParseValores;

//Comparator que ordena Portarias seguindo a hierarquia: emissor, número, ano
public class EmissorNúmeroAnoComparator implements Comparator<Portaria> {
    private final ParseValores pars = new ParseValores();
	@Override
	//Compara duas portarias pela ordem estabelecida
    public int compare(Portaria o1, Portaria o2) {	
		//Primeiro compara pelo emissor
		int comp = o1.getEmissor().compareTo(o2.getEmissor());
		if (comp != 0)
			return comp;

		//Se os emissores forem iguais, compara os números
		comp = o1.getNúmero().compareTo(o2.getNúmero());
		if (comp != 0)
			return comp;
		LocalDate data1 = pars.ParseData(o1.getPublicação());
		LocalDate data2 = pars.ParseData(o1.getPublicação());

		//Se emissor e número forem iguais, compara o ano da publicação
		return Integer.compare(data1.getYear(),data2.getYear());
	}
}