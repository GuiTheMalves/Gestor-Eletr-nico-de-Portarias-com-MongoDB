package br.edu.ifpr.gep.model.utils.comparators;

import java.util.Comparator;

import br.edu.ifpr.gep.model.Portaria;

//Comparator que ordena Portarias seguindo a hierarquia: emissor, número, ano
public class EmissorNúmeroAnoComparator implements Comparator<Portaria> {
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

		//Se emissor e número forem iguais, compara o ano da publicação
		return Integer.compare(o1.getPublicação().getYear(),o2.getPublicação().getYear());
	}
}
