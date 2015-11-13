package com.kiblerdude.kusarajus;

import java.util.Comparator;

public class NodeComparator implements Comparator<Integer> {
	@Override
	public int compare(Integer a, Integer b) {
		return b.compareTo(a);
	}
}
