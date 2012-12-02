package main;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import commands.CalculateWmPrecisionCommand;
import commands.ConstructCommand;
import commands.ConstructWmCommand;
import commands.PrepareTrainCommand;

import classifier.ClassifierBuilder;
import classifier.IClassifier;
import classifier.Invoker;
import classifier.Item;
import classifier.WeightedMajority;
import classifier.WekaClassifier;
import util.Options;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.VotedPerceptron;
import weka.classifiers.trees.J48;
import wlsvm.WLSVM;

public class Main {
	
	public static void main(String[] args) throws Exception {

		List<IClassifier> classifiers = new LinkedList<IClassifier>();
		ClassifierBuilder cb = new ClassifierBuilder();
		
		System.out.println("Initializing Naive Bayes ...");
		WekaClassifier wc1 = cb.retrieveClassifier("weka.classifiers.bayes.NaiveBayes");
		System.out.println("Initializing J48 ...");
		WekaClassifier wc2 = cb.retrieveClassifier("weka.classifiers.trees.J48");
		System.out.println("Initializing VotedPerceptron ...");
		WekaClassifier wc3 = cb.retrieveClassifier("weka.classifiers.functions.VotedPerceptron");
		classifiers.add(wc1);
		classifiers.add(wc2);
		classifiers.add(wc3);
		
		WeightedMajority wm  = new WeightedMajority(classifiers);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String read;
		
		while(true) {
			System.out.print("Please provide a text to classify (or type \"quit\"): ");
			read = br.readLine();
			if(read.equals("quit"))
				break;
			
			Item item = wm.weightedClassify(read);
			
			System.out.println("Naive Bayes:     "+item.getCl2pol().get(1));  // weight: wm.get_cl2weight().get(1)
			System.out.println("J48:             "+item.getCl2pol().get(2));  // weight: wm.get_cl2weight().get(2)
			System.out.println("VotedPerceptron: "+item.getCl2pol().get(3));  // weight: wm.get_cl2weight().get(3)
			
			System.out.println("Voted Result: "+item.getPolarity());
		
		}
	}
}
