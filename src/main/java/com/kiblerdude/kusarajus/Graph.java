package com.kiblerdude.kusarajus;

import java.util.Map;


import java.util.Set;

import com.google.common.collect.Maps;import com.google.common.collect.Sets;


public class Graph {
	
	public Map<Integer, Node> nodes = Maps.newTreeMap(new NodeComparator()); // use descending order
	
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
	
	public Graph swapValueAndTime() {
		Graph gSwapped = new Graph();
		Map<Integer, Integer> nodeToTime = Maps.newHashMap();
		
		// generate a mapping of node to magic time so we can easily lookup
		// values to change for the incoming and outgoing edges
		nodes.forEach((k,v) -> {
			nodeToTime.put(k, v.time);
		});
		
		nodes.forEach((k,v) -> {
			// swap the incoming
			Set<Integer> swappedIncoming = Sets.newTreeSet();
			v.incoming.forEach(e -> {
				swappedIncoming.add(nodeToTime.get(e));
			});
			
			// swap the outgoing
			Set<Integer> swappedOutgoing = Sets.newTreeSet();
			v.outgoing.forEach(e -> {
				swappedOutgoing.add(nodeToTime.get(e));
			});
			
			// swap the value with the time
			Node nSwap = new Node(v.time, swappedIncoming, swappedOutgoing, v.explored, v.leader, k);
			gSwapped.addNode(nSwap);
		});
		return gSwapped;
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
