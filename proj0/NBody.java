public class NBody {
    public static double readRadius(String fileName) {
        In in = new In(fileName);
        in.readInt();
        double res = in.readDouble();
        return res;
    }

    public static Planet[] readPlanets(String fileName) {
        In in = new In(fileName);
        int count = in.readInt();
        in.readDouble();
        Planet[] res = new Planet[count];
        for (int i = 1; i <= count; ++i) {
            res[i - 1] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), 
                                in.readDouble(), in.readDouble(), in.readString());
        }
        return res;
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.print("args.length should be 3");
            return;
        }
        T = Double.valueOf(args[0]);
        dt = Double.valueOf(args[1]);
        fileName = args[2];
        double radius = readRadius(fileName);
        Planet[] planets = readPlanets(fileName);
        
        double[] xForces = new double[planets.length];
        double[] yForces = new double[planets.length];

        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);
        double time = 0.0;
        while (time < T) {
            for (int i = 0; i < planets.length; ++i) {
                xForces[i] = yForces[i] = 0;
            }
            for (int i = 0; i < planets.length; ++i) {
                for (int j = 0; j < planets.length; ++j) {
                    if (j != i) {
                        xForces[i] += planets[i].calcForceExertedByX(planets[j]);
                        yForces[i] += planets[i].calcForceExertedByY(planets[j]);
                    }
                }
            }
            for (int i = 0; i < planets.length; ++i) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Planet p : planets) {
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }
        
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                  planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }
    }


    public static double T;
    public static double dt;
    public static String fileName;
}
