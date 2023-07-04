public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public static final double G = 6.67e-11;
    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        double dx = p.xxPos - xxPos;
        double dy = p.yyPos - yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double calcForceExertedBy(Planet p) {
        double distance = calcDistance(p);
        return (G * p.mass * mass) / (distance * distance);
    }

    public double calcForceExertedByX(Planet p) {
        double dx = p.xxPos - xxPos;
        double r = calcDistance(p);
        return calcForceExertedBy(p) * dx / r;
    }

    public double calcForceExertedByY(Planet p) {
        double dy = p.yyPos - yyPos;
        double r = calcDistance(p);
        return calcForceExertedBy(p) * dy / r;
    }

    public void update(double time, double xForce, double yForce) {
        double a_x = xForce / mass;
        double a_y = yForce / mass;
        xxVel += time * a_x;
        yyVel += time * a_y;
        xxPos += time * xxVel;
        yyPos += time * yyVel;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
















