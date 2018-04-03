package main;

import java.util.ArrayList;
import java.util.List;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import module.Individual;

public class GraphOpertions {
	
	private int vertixCount = 0;
	private Graph g;
	private int edgeCount = 0;
	
	public GraphOpertions(Graph g) 
	{
		this.g = g; 
		this.doGraphStatistics();
	}
	
	public int getVertixCount() 
	{
		return vertixCount;
	}
	
	public int getEdgeCount() 
	{
		return edgeCount;
	}
	
	@SuppressWarnings("unused")
	private void doGraphStatistics() 
	{
		System.out.println("Vertices of " + g);
		int postion = 0;
		for (Vertex vertex : g.getVertices()) 
		{
			postion ++;
			vertixCount ++;
			vertex.setProperty("postion", postion);
		}
		System.out.println("Edges of " + g);
		for (Edge edge : g.getEdges())
		{
			edgeCount ++;
		}
	}
	
	public int calculateIndividualPanelty(Individual<Integer> ind)
	{
		int countOfUnCoveredEdges = edgeCount;
		List<Integer> coverdEdgesIds = new ArrayList<Integer>();
		int postion = 0;
		int totalCoverdEdgeCount = 0;
		for(int i:ind)
		{
			postion ++;
			if(i==1)
			{
				Iterable<Vertex> vertixes = g.getVertices("postion", postion);
				Vertex vertex = vertixes.iterator().next();
				for (Edge e : vertex.getEdges(Direction.BOTH)) {
					//System.out.println(e.getId().toString());
					if(!coverdEdgesIds.contains(Integer.parseInt(e.getId().toString())))
					{
						coverdEdgesIds.add(Integer.parseInt(e.getId().toString()));
						totalCoverdEdgeCount ++;
					}
				}
			}
		}
		//System.out.println("Number of CoveredEdges : " + totalCoverdEdgeCount);
		//System.out.println("Total edge count is : " + totalTesteddEdgeCount);
		return (countOfUnCoveredEdges - totalCoverdEdgeCount ) * 2;
	}
	
	public int calculateIndividualMonitors(Individual<Integer> ind)
	{
		int monitors = 0;
		for(int i:ind)
		{
			monitors += i;
		}
		return monitors;
	}
	
}
