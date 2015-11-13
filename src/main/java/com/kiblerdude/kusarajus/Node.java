package com.kiblerdude.kusarajus;

import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;

public class Node {
	public Integer value = -1;
	public Set<Integer> incoming = Sets.newTreeSet(new NodeComparator());
	public Set<Integer> outgoing = Sets.newTreeSet(new NodeComparator());
	
	public boolean explored = false;
	public Integer leader = -1;
	public Integer time = -1;
	
	public Node(Integer value) {
		this.value = value;
	}
	
	public Node(Integer value, Set<Integer> incoming, Set<Integer> outgoing, Boolean explored, Integer leader, Integer time) {
		this.value = value;
		this.incoming = incoming;
		this.outgoing = outgoing;
		this.explored = explored;
		this.leader = leader;
		this.time = time;
	}
	
	public void reverse() {
		Set<Integer> temp = Sets.newTreeSet(incoming);
		incoming = outgoing;
		outgoing = temp;
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o == this) return true;
		if (o instanceof Node) {
			Node that = (Node)o;
			return value.equals(that.value);
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(value).append(" (").append(Joiner.on(",").join(explored, time, leader)).append(")").append(" -> ");
		builder.append(Joiner.on(",").join(outgoing));
		builder.append(" : <- ");
		builder.append(Joiner.on(",").join(incoming));
		return builder.toString();
	}
}
