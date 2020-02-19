import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.*;

public class Main extends Application {

    private static final int countSigns = 100;
    private static final int countPop = 20;

    private static ArrayList<Individual> populations;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        createPopulation(); //создание популяции

        Individual min = getMinIndividual();
        System.out.println(min.distance);

        int step = 0;
        while (min.distance >= 0.5)
        {
            reproduction(); //скрешивание
            extinction(); //вымирание

            min = getMinIndividual();
            System.out.println(step+" "+min.distance);
            step++;
        }

        showChart(primaryStage, min);
    }

    public static void createPopulation()
    {
        populations = new ArrayList<Individual>();
        for(int i = 0; i < countPop; i++)
        {
            Double[] signs = new Double[countSigns];
            for(int k = 0; k < countSigns; k++)
            {
                DecimalFormat df = new DecimalFormat("#.##");
                double start = new Random().nextInt(10)*0.1;

                signs[k] = start;
            }

            Individual individ = new Individual(signs);
            individ.calculateDistances();

            populations.add(individ);
        }
    }

    public static  Individual getMinIndividual()
    {
        return  Collections.min(populations, Comparator.comparing(s->s.distance));
    }

    public static  void reproduction()
    {
        ArrayList<Individual> parentOne = new ArrayList<Individual>();
        ArrayList<Individual> parentTwo = new ArrayList<Individual>();
        ArrayList<Individual> children = new ArrayList<Individual>();

        for(int i = 0 ; i< populations.size(); i++)
        {
            Individual parent = populations.get(i);
            if(i % 2 == 0)
                parentOne.add(parent);
            else
                parentTwo.add(parent);
        }

        for(int i = 0 ; i< parentOne.size(); i++)
        {
            ArrayList<Individual> child = Crossbreeding(parentOne.get(i), parentTwo.get(i));
            children.addAll(child);
        }

        ArrayList<Individual> childrenMutate = mutate(children);

        for(Individual child:childrenMutate)
            child.calculateDistances();

        populations.addAll(childrenMutate);
    }

    private static  ArrayList<Individual> Crossbreeding(Individual parentOne, Individual parentTwo)
    {
        Double[] parentOneSings = parentOne.signs;
        Double[] parentTwoSings = parentTwo.signs;

        Double[] childOneSings = new Double[countSigns];
        Double[] childTwoSings = new Double[countSigns];

        for(int i = 0; i < countSigns; i++)
        {
            String binaryParentOne = Long.toBinaryString(Double.doubleToRawLongBits(parentOneSings[i]));
            String binaryParentTwo = Long.toBinaryString(Double.doubleToRawLongBits(parentTwoSings[i]));

            String binaryChildOne = binaryParentOne.substring(0, binaryParentOne.length()/2) +
                    binaryParentTwo.substring(binaryParentTwo.length()/2, binaryParentTwo.length());

            String binaryChildTwo = binaryParentTwo.substring(0, binaryParentTwo.length() / 2) +
                    binaryParentOne.substring(binaryParentOne.length()/2, binaryParentOne.length());

            childOneSings[i] = Double.longBitsToDouble(Long.parseLong(binaryChildOne, 2));
            childTwoSings[i] = Double.longBitsToDouble(Long.parseLong(binaryChildTwo, 2));
        }

        Individual childOne = new Individual(childOneSings);
        Individual childTwo = new Individual(childTwoSings);

        ArrayList<Individual> childs = new ArrayList<Individual>();
        childs.add(childOne);
        childs.add(childTwo);

        return  childs;
    }

    public static ArrayList<Individual> mutate(ArrayList<Individual> childrens)
    {
        ArrayList<Individual> mutateChildrens = new ArrayList<Individual>(childrens);
        for(Individual mutate:mutateChildrens)
        {
            for(int i = 0; i < countSigns; i++)
            {
                Double x = i * 0.1;
                Double y = MathAlgorithm.Function(x);

                if(mutate.signs[i] > y && (mutate.signs[i] - y > 0.005))
                {
                    mutate.signs[i] -= 0.005;
                }

                if(mutate.signs[i] < y && (y - mutate.signs[i] > 0.005))
                    mutate.signs[i] += 0.005;
            }
        }

        return mutateChildrens;
    }

    private static void extinction()
    {
        int count = populations.size()/2;
        ArrayList<Individual> survivors = new ArrayList<Individual>();
        for(int i = 0; i < count; i++)
        {
            Individual survivor = getMinIndividual();
            survivors.add(survivor);
            populations.remove(survivor);
        }
        populations.clear();
        populations.addAll(survivors);
    }

    private void showChart(Stage stage, Individual min)
    {
        Double[] functionY = new Double[countSigns];
        Double[] populationY = new Double[countSigns];
        for(int i = 0; i < countSigns; i++)
        {
            Double x = i * 0.1;
            functionY[i] = MathAlgorithm.Function(x);
            populationY[i] = min.signs[i];

            System.out.println(populationY[i]);
        }


        stage.setTitle("Line Chart Population");

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);


        XYChart.Series funSeries = new XYChart.Series();
        XYChart.Series popSeries = new XYChart.Series();

        for(int i = 0; i < countSigns; i++) {
            Double x = i * 0.1;
            funSeries.getData().add(new XYChart.Data(x, functionY[i]));
            popSeries.getData().add(new XYChart.Data(x, populationY[i]));
        }
        funSeries.setName("x ^ 2");
        popSeries.setName("Individ");


        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(funSeries);
        lineChart.getData().add(popSeries);

        stage.setScene(scene);
        stage.show();

    }
}
