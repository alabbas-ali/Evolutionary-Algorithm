package main;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLReader;
import module.Individual;


public class Main {

	static final int POPSIZE = 50;
	static final int COUNTOFINJECTEDINDIVIDUAL = 10;
	static final int INJECTAFTER = 4;
	static final int GENERATIONS = 100;
	static final int PARENTS = 10;
	static final double MUTATION_RATE = 0.05;
	static final int ROUND_PARENT_NUM = 2;
	static final String XML_FILE = "tech-routers-rf.graphml";

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream in = classloader.getResourceAsStream(XML_FILE);

		Graph graph = new TinkerGraph();
		GraphMLReader.inputGraph(graph, in);

		GraphOpertions gOpertions = new GraphOpertions(graph);
		int ind_len = gOpertions.getVertixCount();
		List<Individual<Integer>> population = new ArrayList<Individual<Integer>>();
		
		ExcelOperation eOperation = new ExcelOperation();
		eOperation.creatHeadLine();
		
		// System.out.println("######### Create Random Population ##########");
		for (int i = 0; i < POPSIZE; i++) {
			Individual<Integer> ind = new Individual<Integer>().randomInit(ind_len);
			ind.setPenalty(gOpertions.calculateIndividualPanelty(ind));
			ind.setMonitors(gOpertions.calculateIndividualMonitors(ind));
			//Operations.printIndividual(" -- Random Individual ", ind, gOpertions.getEdgeCount());
			population.add(ind);
		}
		
		population = Operations.selectBest(population, POPSIZE);
		Individual<Integer> bestInd = population.get(0);
		int numberOfGenerationWithTheSameFitness = 0;
		int previousFitness = bestInd.getFitness();

		System.out.println("########### Start The Algorthem #########");
		for (int i = 1; i < GENERATIONS + 1; i++) {
			List<Individual<Integer>> children = new ArrayList<Individual<Integer>>();
			for (int j = 0; j < PARENTS; j++) {
				Individual<Integer> parent1 = Operations.selectParent(population);
				Individual<Integer> parent2 = Operations.selectParent(population);
				Individual<Integer> child1 = (Individual<Integer>) parent1.clone();
				Individual<Integer> child2 = (Individual<Integer>) parent2.clone();
				//Operations.onePointCrossover(child1, child2);
				//Operations.bitFlipMutation(child1, MUTATION_RATE);
				//Operations.bitFlipMutation(child2, MUTATION_RATE);
				Operations.towPointCrossover(child1, child2);
				Operations.shuffleMutation(child1, MUTATION_RATE);
				Operations.shuffleMutation(child2, MUTATION_RATE);
				child1.setPenalty(gOpertions.calculateIndividualPanelty(child1));
				child1.setMonitors(gOpertions.calculateIndividualMonitors(child1));
				child2.setPenalty(gOpertions.calculateIndividualPanelty(child2));
				child2.setMonitors(gOpertions.calculateIndividualMonitors(child2));
				//Operations.printIndividual(" -- Adding Child Individual ", child1, gOpertions.getEdgeCount());
				//Operations.printIndividual(" -- Adding Child Individual ", child2, gOpertions.getEdgeCount());
				children.add(child1);
				children.add(child2);
			}
			
			if(numberOfGenerationWithTheSameFitness == INJECTAFTER)
			{
				System.out.println("########### Start Injecting Individuals #########");
				for (int k = 0; k < COUNTOFINJECTEDINDIVIDUAL; k++) {
					Individual<Integer> ind = new Individual<Integer>().randomInit(ind_len);
					ind.setPenalty(gOpertions.calculateIndividualPanelty(ind));
					ind.setMonitors(gOpertions.calculateIndividualMonitors(ind));
					Operations.printIndividual(" -- Random Individual Injected ", ind, gOpertions.getEdgeCount());
					population.add(ind);
				}
				System.out.println("########### End Injecting Individuals #########");
				numberOfGenerationWithTheSameFitness = 0;
			}
			
			population.addAll(children);
			population = Operations.selectBest(population, POPSIZE);
			bestInd = population.get(0);
			
			System.out.println("###### The Bist Individual for Generation " + i + " ######");
			Operations.printIndividual(" -- Best Generation Individual ", bestInd, gOpertions.getEdgeCount());
			eOperation.addIndividualInfo(bestInd, i, gOpertions.getEdgeCount());
			int bestFitness = bestInd.getFitness();
			if(previousFitness == bestFitness)
			{
				numberOfGenerationWithTheSameFitness++;
			}else{
				numberOfGenerationWithTheSameFitness = 0;
				previousFitness = bestFitness;
			}
			
		}
		
		eOperation.save();
	}
	
	
}
