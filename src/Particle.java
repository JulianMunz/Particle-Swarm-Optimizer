import java.util.Arrays;

public class Particle {
    public Vector position;
    private Vector velocity;
    public Vector pbest;
    public double prev_pbest;
    public double pbest_val;
    public int same;
    double w ;
    double c1;
    double c2;
    int approach;

    public Particle (double[] start, int approach) {
        this.approach = approach;
        updateParameters();
        position = new Vector(start);
        pbest = new Vector(start);
        pbest_val = PSO.evalFunc(pbest);
        prev_pbest = pbest_val;
        same = 0;
        double[] zeroes = new double[position.length()];
        Arrays.fill(zeroes, 0);
        velocity = new Vector(zeroes);
    }

    public void updateParameters () {
        double i;
        double bound;
        switch (approach) {
            case 1 :
                w = 0.7;
                c1 = 1.4;
                c2= 1.4;
                break;
            case 2:
            case 3:
                w = Math.random();
                i = Math.random() * 0.2 + 0.4;
                bound = 24 * (1 - Math.pow(w,2)) / (7 - 5 * w);
                c1 = bound*i;
                c2 = bound*(1-i);
                break;
            case 4:
            case 5:
                w = Math.random();
                i = Math.random() * 0.2 + 0.4;
                double upper_bound = 24 * (1 - Math.pow(w,2)) / (7 - 5 * w) ;
                double lower_bound = (22 - 30*Math.pow(w,2)) / (7 - 5 * w) ;
                bound = 0.9*((Math.random() * (upper_bound - lower_bound)) + lower_bound);
                c1 = bound*i;
                c2 = bound*(1-i);
        }
    }

    public double[] randomArray(){
        double[] arr = new double[position.length()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Math.random();
        }
        return arr;
    }

    public void updateVelocity(Vector gbest) {
        Vector r1 = new Vector(randomArray());
        Vector r2 = new Vector(randomArray());
        if (approach == 2 || approach == 4) {
            updateParameters();
        }
        velocity.scalarMult(w);
        r1.scalarMult(c1);
        r2.scalarMult(c2);

        Vector cognitive = Vector.minus(pbest, position);
        cognitive.product(r1);
        Vector social = Vector.minus(gbest, position);
        social.product(r2);

        velocity = Vector.add(velocity, cognitive);
        velocity = Vector.add(velocity, social);
    }

    public void updatePbest() {
        double temp = PSO.evalFunc(position);
        if (pbest_val > temp) {
            pbest = Vector.copyVector(position);
            pbest_val = temp;
        }
        if (approach == 3 || approach == 5) {
            if (prev_pbest == pbest_val) {
                same++;
                if (same >= 3) {
                    updateParameters();
                    same = 0;
                }
            } else {
                same = 0;
            }
            prev_pbest = pbest_val;
        }
    }

    public void updatePosition() {
        position = Vector.add(position, velocity);
    }
}
