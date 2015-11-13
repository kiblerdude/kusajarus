package com.kiblerdude.kusarajus;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

/**
 * Kusajaru's Algorithm
 * <p>
 * Computes strongly connected components in a directed graph.
 */
public class Kusajarus {
	private Integer time = 0;
	private Integer leader = 0;

	public void computeStronglyConnectedComponents(Graph graph) {

		// System.out.println("Original Graph");
		// System.out.println(graph);
		Graph gRev = graph.reverse();
		// System.out.println("Reversed Graph");
		// System.out.println(gRev);

		System.out.println("Running part 1");
		gRev.nodes.forEach((k, node) -> {
			if (!node.explored) {
				DFSTimes(gRev, node);
			}
		});
		// System.out.println("After Calculating Times");
		// System.out.println(gRev);

		System.out.println("Swapping");
		Graph g = gRev.reverse().swapValueAndTime();
		g.resetExplored();
		// System.out.println("After Reverse And Swap");
		// System.out.println(g);

		System.out.println("Running part 2");
		g.nodes.forEach((k, node) -> {
			leader = node.value;
			if (!node.explored) {
				DFSLeaders(g, node);
			}
		});

		// System.out.println("After Calculating Leaders");
		// System.out.println(g);

		System.out.println("Grouping by leader");
		Map<Integer, List<Node>> grouped = g.nodes.values().stream()
				.collect(Collectors.groupingBy(Node::getLeader));
		Map<Integer, Integer> sized = Maps.newHashMap(); 
				grouped.forEach((leader, nodes) -> {
			sized.put(leader, nodes.size());
		});

		System.out.println("Sorting results");
		entriesSortedByValues(sized).stream().limit(5).forEach(e -> System.out.println(e));
	}

	private void DFSTimes(Graph g, Node n) {
		n.explored = true;
		n.outgoing.forEach(value -> {
			Node arc = g.nodes.get(value);
			if (!arc.explored) {
				DFSTimes(g, arc);
			}
		});
		time++;
		n.time = time;
	}

	private void DFSLeaders(Graph g, Node n) {
		n.explored = true;
		n.leader = leader;
		n.outgoing.forEach(value -> {
			Node arc = g.nodes.get(value);
			if (!arc.explored) {
				DFSLeaders(g, arc);
			}
		});
	}

	static <K, V extends Comparable<? super V>> List<Entry<K, V>> entriesSortedByValues(Map<K, V> map) {

		List<Entry<K, V>> sortedEntries = new ArrayList<Entry<K, V>>(
				map.entrySet());

		Collections.sort(sortedEntries, new Comparator<Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> e1, Entry<K, V> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}
		});

		return sortedEntries;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader reader = Files.newBufferedReader(Paths
				.get("SCCTest5.txt"));

		Graph g = new Graph();

		reader.lines().forEach(
				line -> {
					List<String> split = Splitter.on(" ").splitToList(line);
					g.addEdge(Integer.parseInt(split.get(0)),
							Integer.parseInt(split.get(1)));
				});

		Kusajarus algorithm = new Kusajarus();
		algorithm.computeStronglyConnectedComponents(g);
	}
}
