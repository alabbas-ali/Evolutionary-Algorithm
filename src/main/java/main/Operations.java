package main;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import module.Individual;

public class Operations 
{
	public static Random random = new Random();
	
	public static void bitFlipMutation (Individual<Integer> ind, double prob)
	{
		for(int i=0; i < ind.size(); i++)
		{
			if(random.nextDouble() <= prob)
			{
				ind.set(i, ind.get(i) == 0 ? 1 : 0);
			}
		}
	}
	
	public static void shuffleMutation(Individual<Integer> ind, double prob)
	{
		for(int i = 0 ; i < random.nextInt(ind.size()) ; i++)
		{
			if(random.nextDouble() <= prob)
			{
				int bit1 = random.nextInt(ind.size());
				int bit2 = random.nextInt(ind.size());
				int bit2Value = ind.get(bit2);
				ind.set(bit2, ind.get(bit1));
				ind.set(bit1, bit2Value);
			}
		}
	}
	
	public static void onePointCrossover( Individual<Integer> ind1, Individual<Integer> ind2)
	{
		int splitPoint = random.nextInt(ind1.size());
		for(int i= splitPoint; i < ind1.size(); i++)
		{
			int swap = ind1.get(i);
			ind1.set(i, ind2.get(i));
			ind2.set(i, swap);
		}
	}
	
	public static void towPointCrossover( Individual<Integer> ind1, Individual<Integer> ind2)
	{
		int startPoint = random.nextInt(ind1.size());
		int endPoint = startPoint + random.nextInt(ind1.size() - startPoint);
		for(int i= startPoint; i < endPoint; i++)
		{
			int swap = ind1.get(i);
			ind1.set(i, ind2.get(i));
			ind2.set(i, swap);
		}
	}
	
	public static List<Individual<Integer>> selectBest(List<Individual<Integer>> population, int len)
	{
		List<Individual<Integer>> best = new LinkedList<Individual<Integer>>();
		Collections.sort(population);
		for(int i=0; i<len; i++)
		{
			best.add(population.get(i));
		}
	
		return best;
	}
	
	public static Individual<Integer> selectParent(List<Individual<Integer>>population){
		return population.get(random.nextInt(population.size()));	
	}
	
	public static void printIndividual(String beginString , Individual<Integer> ind, int edgeCount){
		System.out.println(beginString + " with,Monitors : " + ind.getMonitors() + ", Penalty :"
				+ ind.getPenalty() + " and Fitness :" + ind.getFitness() + " has been Generate , Coverd Edges : "
				+ (edgeCount - (ind.getPenalty() / 2)) + " Uncovered Edges : "
				+ ind.getPenalty() / 2);
	}
}
