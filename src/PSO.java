import java.util.ArrayList;
import static java.lang.Math.*;

public class PSO {
    private static int dim ;
    private int num_p;
    private double gbest_val = Double.MAX_VALUE;
    private Vector gbest;
    private final int MAX_ITERS = 5000;
    private static int function;
    private int approach;

    public PSO(int function, int approach) {
        this.function = function;
        this.approach = approach;
        this.dim = 30;
        this.num_p = 30;
        gbest  = new Vector(randomArray());
    }


    public double[] randomArray(){
        double[] arr = new double[dim];
        for (int i = 0; i < dim; i++) {
            arr[i] = Math.random();
        }
        return arr;
    }

    public void doPSO () {
        // INIT
        gbest_val = Double.MAX_VALUE;
        ArrayList<Particle> particles = new ArrayList<>();
        for (int i = 0; i < num_p; i++) {
            particles.add(new Particle(randomArray(), approach)) ;
        }

        int count = 0;
        double mean, stddev;

        //STEP
        while (count < MAX_ITERS) {
            for (Particle p: particles) {
                p.updateVelocity(gbest);
                p.updatePosition();
                p.updatePbest();
                updateGbest(p);
            }
            mean = meanPbest(particles);
            stddev = stddevPbest(particles, mean);
            if (count % 50 == 0) {
                System.out.println(count + "," + gbest_val + "," + mean + "," + (mean-stddev) +","+ (mean+stddev));
            }
            count++;
        }

    }

    public double meanPbest(ArrayList<Particle> particles) {
        double mean = 0;
        for (Particle p:particles) {
            mean += p.pbest_val;
        }
        return mean/num_p;
    }

    public double stddevPbest(ArrayList<Particle> particles, double mean) {
        double stddev = 0;
        for (Particle p:particles) {
            stddev += Math.pow(p.pbest_val - mean, 2);
        }
        return Math.sqrt(stddev / (num_p-1));
    }

    public void updateGbest(Particle p){
        if (gbest_val > p.pbest_val) {
            gbest_val = p.pbest_val;
            gbest = Vector.copyVector(p.pbest);
        }
    }


    public static double evalFunc(Vector p) {
        switch (function) {
            case 1:
                return exp(p, dim);
            case 2:
                return brown(p,dim);
            case 3:
                return ripple(p,dim);
            case 4:
                return rosenbrock(p,dim);
            case 5:
                return qing(p,dim);
        }
        return 2;
    }


    public static double ripple(Vector p, int dim) {
        double func_val = 0;
        for (int i = 0; i < dim; i++) {
            func_val += -1 * Math.exp(-2* Math.log(2) * pow(((p.get(i) - 0.1)/(0.8)), 2) ) * pow(Math.sin(5*Math.PI*p.get(i)), 6) ;
        }
        return func_val;
    }

    public static double eggCrate(Vector p, int dim) {
        double func_val = 0;
        for (int i = 0; i < dim; i++) {
            func_val += Math.pow(p.get(i),2) + 24* Math.sin(Math.pow(p.get(i),2)) ;
        }
        return func_val;
    }

    public static double exp(Vector p, int dim) {
        double sum = 0;
        for (int i = 0; i < dim; i++) {
            sum += Math.pow(p.get(i),2);
        }
        return -1 * Math.exp(-0.5 * sum);
    }

    public static double brown(Vector p, int dim) {
        double sum = 0;
        for (int i = 0; i < dim-1; i++) {
            sum += Math.pow(Math.pow(p.get(i),2), Math.pow(p.get(i+1), 2) + 1) + Math.pow(Math.pow(p.get(i+1), 2), Math.pow(p.get(i+1), 2) + 1);
        }
        return sum;
    }

    public static double rosenbrock(Vector p,int dim) {
        double sum = 0;
        for (int i = 0; i < dim-1; i++) {
            sum += 100 * Math.pow(p.get(i+1) - Math.pow(p.get(i),2), 2) + Math.pow(p.get(i) - 1,2);
        }
        return sum;
    }

    public static double qing(Vector p, int dim){
        double sum = 0;
        for (int i = 1; i < dim+1; i++) {
            sum += Math.pow(Math.pow(p.get(i-1),2) - i, 2);
        }
        return sum;
    }


    public static void main(String[] args) {
        long startTime = System.nanoTime();
        int function = Integer.parseInt(args[0]);
        int approach = Integer.parseInt(args[1]);
        int rep = Integer.parseInt(args[2]);
        String[] functions = {"exponential", "brown", "ripple", "rosenbrock", "qing"};
        PSO swarm = new PSO(function, approach);
        switch (approach) {
            case 1:
                System.out.println("Using suggested approach on "+ functions[function-1] + " with " + rep +" runs.");
                break;
            case 2:
                System.out.println("Using iterative sampling on "+ functions[function-1] + " with " + rep +" runs.");
                break;
            case 3:
                System.out.println("Using stagnation sampling on "+ functions[function-1] + " with " + rep +" runs.");
                break;
            case 4:
                System.out.println("Using biased iteration sampling on "+ functions[function-1] + " with " + rep+ " runs.");
                break;
            case 5:
                System.out.println("Using biased stagnation sampling on "+ functions[function-1] + " with " + rep +" runs.");
                break;
        }
        System.out.println("iter,gbest,mean,stdev-mean,stddev+mean");
        for (int i = 0; i < rep; i++) {
            swarm.doPSO();
        }
        long endTime   = System.nanoTime();
        double totalTime = (endTime - startTime) ;
        System.out.println(totalTime/ 1_000_000_000);
    }
}
