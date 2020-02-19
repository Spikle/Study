public class Individual
{
    public Double[] signs;
    public Double[] distances;
    public Double distance;

    public  Individual(Double[] signs)
    {
        this.signs = signs;
        distances = new Double[signs.length];
    }

    public void calculateDistances()
    {
        for(int i = 0; i< signs.length; i++)
        {
            Double x = i * 0.1;
            Double origY = MathAlgorithm.Function(x);
            distances[i] = Math.sqrt(Math.pow(x-x,2)+Math.pow(signs[i]-origY,2));
        }

        distance = 0d;
        for(int i = 0; i< distances.length; i++)
        {
            distance+=Math.abs(distances[i]);
        }
    }
}