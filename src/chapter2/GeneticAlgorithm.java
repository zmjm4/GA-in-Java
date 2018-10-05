package chapter2;

/**
 * The GeneticAlgorithm class is our main abstraction for managing the
 * operations of the genetic algorithm. This class is meant to be
 * problem-specific, meaning that (for instance) the "calcFitness" method may
 * need to change from problem to problem.
 * 
 * GeneticAlgorithm类是我们管理遗传算法操作的主要抽象类。 这个类是特定于问题存在的，
 * 这意味着（例如）“calcFitness”方法会根据问题的不同而改变具体实现。
 * 
 * This class concerns itself mostly with population-level operations, but also
 * problem-specific operations such as calculating fitness, testing for
 * termination criteria, and managing mutation and crossover operations (which
 * generally need to be problem-specific as well).
 * 该类主要关注种群级操作，但也涉及特定于问题的操作，例如计算适应性，判断是否达到终止条件以及管理变异和交叉操作 （通常也需要根据特定问题进行改变）。
 * 
 * Generally, GeneticAlgorithm might be better suited as an abstract class or an
 * interface, rather than a concrete class as below. A GeneticAlgorithm
 * interface would require implementation of methods such as
 * "isTerminationConditionMet", "calcFitness", "mutatePopulation", etc, and a
 * concrete class would be defined to solve a particular problem domain. For
 * instance, the concrete class "TravelingSalesmanGeneticAlgorithm" would
 * implement the "GeneticAlgorithm" interface. This is not the approach we've
 * chosen, however, so that we can keep each chapter's examples as simple and
 * concrete as possible. 所以GeneticAlgorithm可能更适合作为一个虚类或者是一个接口，而不是一个具体的类。
 * GeneticAlgorithm接口需要实现诸如“isTerminationConditionMet”，“calcFitness”，“mutatePopulation”等方法，
 * 并且将定义具体类来解决特定问题域。例如，具体类“TravelingSalesmanGeneticAlgorithm”将实现“GeneticAlgorithm”接口。
 * 显然这不是我们应该选择的方法，但是这样做可以使每章的实例尽可能的简单和具体
 * 
 * @author bkanber
 *
 */
public class GeneticAlgorithm {
	private int populationSize;// 种群数量

	/**
	 * Mutation rate is the fractional probability than an individual gene will
	 * mutate randomly in a given generation. The range is 0.0-1.0, but is generally
	 * small (on the order of 0.1 or less).
	 * 变异率是单个基因在给定世代中随机突变的概率分数。范围是0-1之间，但是通常设置很小一般设置为0.1以下的小数
	 */
	private double mutationRate;// 变异率

	/**
	 * Crossover rate is the fractional probability that two individuals will "mate"
	 * with each other, sharing genetic information, and creating offspring with
	 * traits of each of the parents. Like mutation rate the rance is 0.0-1.0 but
	 * small. 交叉率是两个个体会相互交叉的概率分数，分享基因信息，并创造具有每个父母特征的后代。和突变率一样，范围在0-1之间，是一个很小的小数。
	 */
	private double crossoverRate; // 交叉率

	/**
	 * Elitism is the concept that the strongest members of the population should be
	 * preserved from generation to generation. If an individual is one of the
	 * elite, it will not be mutated or crossover. 精英主义是种群中最强大的成员应该代代相传的概念。
	 * 如果一个人是精英之一，它将不会发生变异或交叉
	 */
	private int elitismCount; // 精英数

	public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount) {
		this.populationSize = populationSize; // 种群数量
		this.mutationRate = mutationRate;// 变异率
		this.crossoverRate = crossoverRate;// 交叉率
		this.elitismCount = elitismCount;// 精英数量
	}

	/**
	 * Initialize population //初始化种群
	 * 
	 * @param chromosomeLength
	 *            //染色体长度 The length of the individuals chromosome //单个染色体的长度
	 * @return population The initial population generated //初始种群
	 */

	/**
	 * 当构造完种群类和个体类之后，就可以在GeneticAlgorithm类中将它们示例化。即只需创建一个名为“initPopulation”方法，
	 * 放在GeneticAlgorithm类中的任意位置
	 * 
	 * @param chromosomeLength
	 * @author cloud
	 */
	public Population initPopulation(int chromosomeLength) {
		// Initialize population
		Population population = new Population(this.populationSize, chromosomeLength);
		return population;
	}

	/**
	 * Calculate fitness for an individual.
	 * 
	 * In this case, the fitness score is very simple: it's the number of ones in
	 * the chromosome. Don't forget that this method, and this whole
	 * GeneticAlgorithm class, is meant to solve the problem in the "AllOnesGA"
	 * class and example. For different problems, you'll need to create a different
	 * version of this method to appropriately calculate the fitness of an
	 * individual.
	 * 
	 * @param individual
	 *            the individual to evaluate
	 * @return double The fitness value for individual
	 */

	/**
	 * 为每个个体计算适应度函数值
	 * 
	 * 在这个例子中，适应度函数很简单，是染色体中1的个数，在这个例子中需要解决的是染色体中全一的问题。
	 * 对于不同的问题，你需要生成不同版本的这个方法来合理计算个体的适应度函数值
	 * 在评估阶段，种群的每个个体都要计算器适应度值，并存储以便将来使用，为了计算个体适应度，使用适应度函数进行评价
	 * 
	 * @author cloud
	 */
	public double calcFitness(Individual individual) {

		// Track number of correct genes(跟踪正确基因的数量)
		int correctGenes = 0;

		// Loop over individual's genes(循环个体的基因)
		for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
			// Add one fitness point for each "1" found
			// 对于一个个体，只要找到其一个基因是1，就将correctGenes的值增加1
			if (individual.getGene(geneIndex) == 1) {
				correctGenes += 1;
			}
		}

		// Calculate fitness
		// 计算适应度，将为1的基因的个数除以染色体中基因的长度
		double fitness = (double) correctGenes / individual.getChromosomeLength();

		// Store fitness
		// 保存适应度函数值
		individual.setFitness(fitness);

		return fitness;
	}

	/**
	 * Evaluate the whole population
	 * 
	 * Essentially, loop over the individuals in the population, calculate the
	 * fitness for each, and then calculate the entire population's fitness. The
	 * population's fitness may or may not be important, but what is important here
	 * is making sure that each individual gets evaluated.
	 * 
	 * @param population
	 *            the population to evaluate
	 */
	public void evalPopulation(Population population) {
		double populationFitness = 0;

		// Loop over population evaluating individuals and suming population
		// fitness
		for (Individual individual : population.getIndividuals()) {
			populationFitness += calcFitness(individual);
		}

		population.setPopulationFitness(populationFitness);
	}

	/**
	 * Check if population has met termination condition
	 * 
	 * For this simple problem, we know what a perfect solution looks like, so we
	 * can simply stop evolving once we've reached a fitness of one.
	 * 
	 * @param population
	 * @return boolean True if termination condition met, otherwise, false
	 */
	public boolean isTerminationConditionMet(Population population) {
		for (Individual individual : population.getIndividuals()) {
			if (individual.getFitness() == 1) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Select parent for crossover
	 * 
	 * @param population
	 *            The population to select parent from
	 * @return The individual selected as a parent
	 */
	public Individual selectParent(Population population) {
		// Get individuals
		Individual individuals[] = population.getIndividuals();

		// Spin roulette wheel
		double populationFitness = population.getPopulationFitness();
		double rouletteWheelPosition = Math.random() * populationFitness;

		// Find parent
		double spinWheel = 0;
		for (Individual individual : individuals) {
			spinWheel += individual.getFitness();
			if (spinWheel >= rouletteWheelPosition) {
				return individual;
			}
		}
		return individuals[population.size() - 1];
	}

	/**
	 * Apply crossover to population
	 * 
	 * Crossover, more colloquially considered "mating", takes the population and
	 * blends individuals to create new offspring. It is hoped that when two
	 * individuals crossover that their offspring will have the strongest qualities
	 * of each of the parents. Of course, it's possible that an offspring will end
	 * up with the weakest qualities of each parent.
	 * 
	 * This method considers both the GeneticAlgorithm instance's crossoverRate and
	 * the elitismCount.
	 * 
	 * The type of crossover we perform depends on the problem domain. We don't want
	 * to create invalid solutions with crossover, so this method will need to be
	 * changed for different types of problems.
	 * 
	 * This particular crossover method selects random genes from each parent.
	 * 
	 * @param population
	 *            The population to apply crossover to
	 * @return The new population
	 */
	public Population crossoverPopulation(Population population) {
		// Create new population
		Population newPopulation = new Population(population.size());

		// Loop over current population by fitness
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual parent1 = population.getFittest(populationIndex);

			// Apply crossover to this individual?
			if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount) {
				// Initialize offspring
				Individual offspring = new Individual(parent1.getChromosomeLength());

				// Find second parent
				Individual parent2 = selectParent(population);

				// Loop over genome
				for (int geneIndex = 0; geneIndex < parent1.getChromosomeLength(); geneIndex++) {
					// Use half of parent1's genes and half of parent2's genes
					if (0.5 > Math.random()) {
						offspring.setGene(geneIndex, parent1.getGene(geneIndex));
					} else {
						offspring.setGene(geneIndex, parent2.getGene(geneIndex));
					}
				}

				// Add offspring to new population
				newPopulation.setIndividual(populationIndex, offspring);
			} else {
				// Add individual to new population without applying crossover
				newPopulation.setIndividual(populationIndex, parent1);
			}
		}

		return newPopulation;
	}

	/**
	 * Apply mutation to population
	 * 
	 * Mutation affects individuals rather than the population. We look at each
	 * individual in the population, and if they're lucky enough (or unlucky, as it
	 * were), apply some randomness to their chromosome. Like crossover, the type of
	 * mutation applied depends on the specific problem we're solving. In this case,
	 * we simply randomly flip 0s to 1s and vice versa.
	 * 
	 * This method will consider the GeneticAlgorithm instance's mutationRate and
	 * elitismCount
	 * 
	 * @param population
	 *            The population to apply mutation to
	 * @return The mutated population
	 */
	public Population mutatePopulation(Population population) {
		// Initialize new population
		Population newPopulation = new Population(this.populationSize);

		// Loop over current population by fitness
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual individual = population.getFittest(populationIndex);

			// Loop over individual's genes
			for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
				// Skip mutation if this is an elite individual
				if (populationIndex > this.elitismCount) {
					// Does this gene need mutation?
					if (this.mutationRate > Math.random()) {
						// Get new gene
						int newGene = 1;
						if (individual.getGene(geneIndex) == 1) {
							newGene = 0;
						}
						// Mutate gene
						individual.setGene(geneIndex, newGene);
					}
				}
			}

			// Add individual to population
			newPopulation.setIndividual(populationIndex, individual);
		}

		// Return mutated population
		return newPopulation;
	}

}
