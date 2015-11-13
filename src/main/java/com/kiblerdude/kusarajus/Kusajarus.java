package com.kiblerdude.kusarajus;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.google.common.base.Splitter;

/**
 * Kusajaru's Algorithm
 * <p>
 * Computes strongly connected components in a directed graph.
 */
public class Kusajarus 
{	
	private Integer time = 0;
	private Integer leader = 0;
	
	public void computeStronglyConnectedComponents(Graph graph) {
		
		System.out.println("Original Graph");
    	System.out.println(graph);    	
		Graph gRev = graph.reverse();
		System.out.println("Reversed Graph");
		System.out.println(gRev);


		gRev.nodes.forEach((k,node) -> {
			if (!node.explored) {
				DFSTimes(gRev, node);
			}
		});
		System.out.println("After Calculating Times");
		System.out.println(gRev);
			
		Graph g = gRev.reverse().swapValueAndTime();
		g.resetExplored();
		// TODO need to swap node value with magic time
		System.out.println("After Reverse And Swap");
		System.out.println(g);
		
		g.nodes.forEach((k, node) -> {
			leader = node.value;
			if (!node.explored) {
				DFSLeaders(g, node);
			}
		});
		
		System.out.println("After Calculating Leaders");
		System.out.println(g);
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
	
    public static void main( String[] args ) throws IOException
    {
    	BufferedReader reader = Files.newBufferedReader(Paths.get("SCCTest.txt"));
    	
    	Graph g = new Graph();
    	
    	reader.lines().forEach(line -> {
    		//System.out.println(line);
    		List<String> split = Splitter.on(" ").splitToList(line);
    		g.addEdge(Integer.parseInt(split.get(0)), Integer.parseInt(split.get(1)));
    	});
    	
    	Kusajarus algorithm = new Kusajarus();    	
    	algorithm.computeStronglyConnectedComponents(g);
    }
}
