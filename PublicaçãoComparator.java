package br.edu.ifpr.gep.model.utils.comparators;

import java.util.Comparator;

import br.edu.ifpr.gep.model.Portaria;
//Comparator que ordena Portarias pela data de publicação
public class PublicaçãoComparator implements Comparator<Portaria> {
	@Override
	//Compara a data de publicação de duas Portarias
	public int compare(Portaria o1, Portaria o2) {
		return o1.getPublicação().compareTo(o2.getPublicação());
	}
}
