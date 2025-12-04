package br.edu.ifpr.gep.model.utils.comparators;

import java.util.Comparator;

import br.edu.ifpr.gep.model.Portaria;
//Comparator que ordena objetos Portaria com base no código do emissor
public class EmissorComparator implements Comparator<Portaria> {
	@Override
	//Compara duas portarias retornando a ordem baseada no valor do emissor
	public int compare(Portaria o1, Portaria o2) {
		return o1.getEmissor().compareTo(o2.getEmissor());
	}
}
