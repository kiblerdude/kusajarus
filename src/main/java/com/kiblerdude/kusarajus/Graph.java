package com.kiblerdude.kusarajus;

import java.util.Map;

import com.google.common.collect.Maps;

public class Graph {
	
	public Map<Integer, Node> nodes = Maps.newTreeMap(new NodeComparator());
	
	public void addNode(Node node) {
		nodes.put(node.value, node);
	}
	
	public void addEdge(Integer from, Integer to) {
		if (nodes.containsKey(from)) {
			Node node = nodes.get(from);
			node.outgoing.add(to);
		} else {
			Node node = new Node(from);
			node.outgoing.add(to);
			nodes.put(from, node);
		}
		
		if (nodes.containsKey(to)) {
			Node node = nodes.get(to);
			node.incoming.add(from);
		} else {
			Node node = new Node(to);
			node.incoming.add(from);
			nodes.put(to, node);
		}
	}
	
	public Graph reverse() {
		Graph gRev = new Graph();
		nodes.forEach((k,v) -> {
			// swap the incoming and outgoing nodes
			// Node(Integer value, Set<Integer> incoming, Set<Integer> outgoing)
			Node nRev = new Node(k, v.outgoing, v.incoming, v.explored, v.leader, v.time);
			gRev.addNode(nRev);
		});
		return gRev;
	}
	
	public void resetExplored() {
		nodes.forEach((k,v) -> {
			v.explored = false;
		});
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		nodes.forEach( (k,v) -> {
			builder.append(v).append("\n");
		});
		return builder.toString();
	}
}
