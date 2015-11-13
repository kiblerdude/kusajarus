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
	Integer time = 0;
	Node leader = null;
	
	public void DFSLoop(Graph g) {
		g.nodes.forEach((k,node) -> {
			if (!node.explored) {
				leader = node;
				DFS(g, node);
			}
		});
	}
	
	private void DFS(Graph g, Node n) {
		n.explored = true;
		
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
    	
    	//System.out.println(g);
    	
    	Graph gRev = g.reverse();
    	
    	//System.out.println(gRev);
    }
}
