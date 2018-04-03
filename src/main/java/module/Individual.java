package module;

import java.util.LinkedList;
import java.util.Random;

public class Individual <T extends Integer> extends LinkedList<Integer> implements Comparable<Individual<Integer>> 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int penalty;
	private int monitors;
	
	
	public void setMonitors(int monitors) {
		this.monitors = monitors;
	}

	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}

	public int getMonitors() {
		return monitors;
	}

	public Individual<T> randomInit(int len)
	{
		Random r = new Random();
		this.clear();
		for(int i=0; i<len; i++ )
		{
			this.add(r.nextInt(2));
		}
		return this;
	}
	
	public int getFitness()
	{
		return this.monitors + this.penalty;
	}

	public int compareTo(Individual<Integer> int1) 
	{
		//System.out.println("##### Compertion Call #####");
		//System.out.println("##### individual Fitness is" + this.getFitness());
		//System.out.println("##### individual Fitness is" + int1.getFitness());
		if(this.getFitness() == int1.getFitness()){
			return 0;
		} else if(this.getFitness() > int1.getFitness()) {
			return 1;
		} else {
			return -1;
		}
	}

	
}
