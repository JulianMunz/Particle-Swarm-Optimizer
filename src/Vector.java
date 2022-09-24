public class Vector {

    public double[] point;

    public Vector(double ... entries) {
        this.point = new double[entries.length];
        for (int i = 0; i < entries.length; i++) {
            this.point[i] = entries[i];
        }
    }

    public int length() {
        return this.point.length;
    }

    public static Vector copyVector(Vector cpied){
        double[] cp = new double[cpied.length()];
        for (int i = 0; i < cpied.length(); i++) {
            cp[i] = cpied.get(i);
        }
        return new Vector(cp);
    }


    public double get(int i) {
        return this.point[i];
    }

    public static Vector add(Vector one, Vector two) {
        double[] sums = new double[one.length()];
        for (int i = 0; i < sums.length; i++) {
            sums[i] = one.get(i) + two.get(i);
        }

        return new Vector(sums);
    }

    public static Vector minus(Vector one, Vector two) {
        double[] diffs = new double[one.length()];

        for (int i = 0; i < diffs.length; i++) {
            diffs[i] = one.get(i) - two.get(i);
        }

        return new Vector(diffs);
    }


    public void scalarMult(double scalar) {
        double[] points = this.point;

        for (int i = 0; i < points.length; i++) {
            points[i] = scalar * points[i];
        }
    }

    public void product(Vector second) {
        double[] product = this.point;
        double[] mult = second.point;

        for (int i = 0; i < product.length; i++) {
            product[i] = mult[i] * product[i];
        }
    }



    @Override
    public String toString(){
        String out = "{";
        for(int i=0; i<this.point.length; i++) {
            out = out + String.valueOf(this.point[i]);
            if (i == this.point.length - 1) {
                out = out + "}";
            }else {
                out = out + ",";
            }
        }
        return out;
    }

    public static void main(String args[]){
        double[] arr1 = {0.0,1.5,2.0};
        Vector one = new Vector(arr1);
        double[] arr2 = {1.0,2.0,6.0};
        Vector two = new Vector(arr2);
        //Vector added = Vector.add(one, two);
        //Vector added = Vector.minus(one, two);
        //one.scalarMult(123);
        System.out.println(one.toString());
    }
}
