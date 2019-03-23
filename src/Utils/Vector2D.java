package Utils;

public final class Vector2D {
    public double x, y;

    // constructor for zero vector
    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    // constructor for vector with given coordinates
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // constructor that copies the argument vector
    public Vector2D(Vector2D v) {

        this.x = v.x;
        this.y = v.y;

    }

    // set coordinates
    public Vector2D set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    // set coordinates based on argument vector
    public Vector2D set(Vector2D v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    // compare for equality (note Object type argument)
    public boolean equals(Object o) {

        Vector2D veco = (Vector2D) o;
        return (this.x == veco.x)&&(this.y == veco.y);
    }

    // String for displaying vector as text
    public String toString() {
        return "X = " + this.x + " Y = " + this.y;
    }

    //  magnitude (= "length") of this vector
    public double mag() {
        return Math.sqrt((x*x)+(y*y));
    }

    // angle between vector and horizontal axis in radians in range [-PI,PI]
    // can be calculated using Math.atan2
    public double angle() {

        return Math.atan2(this.y,this.x);
    }

    // angle between this vector and another vector in range [-PI,PI]
    public double angle(Vector2D other) {

        return Math.atan2( this.x*other.y - this.y*other.x, this.x*other.x + this.y*other.y );

    }

    // add argument vector
    public Vector2D add(Vector2D v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    // add values to coordinates
    public Vector2D add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    // weighted add - surprisingly useful
    public Vector2D addScaled(Vector2D v, double fac) {
        this.x += v.x*fac;
        this.y += v.y*fac;
        return this;
    }

    // subtract argument vector
    public Vector2D subtract(Vector2D v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    // subtract values from coordinates
    public Vector2D subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    // multiply with factor
    public Vector2D mult(double fac) {
        this.x *= fac;
        this.y *= fac;
        return this;
    }

    // rotate by angle given in radians
    public Vector2D rotate(double angle) {

        double px,py;
        double cosine = Math.cos(angle);
        double sine = Math.sin(angle);

        px = x * cosine - y * sine;
        py = x * sine + y * cosine;

        this.x = px;
        this.y = py;

        return this;
    }

    // "dot product" ("scalar product") with argument vector
    public double dot(Vector2D v) {

        return (this.x*v.x) + (this.y*v.y);

    }

    // distance to argument vector
    public double dist(Vector2D v) {
        double u = this.x - v.x;
        double g = this.y - v.y;

        return Math.sqrt((u*u)+(g*g));

    }

    // normalise vector so that magnitude becomes 1
    public Vector2D normalise() {
        double px,py ;
        px = this.x/this.mag();
        py = this.y/this.mag();
        this.x = px;
        this.y = py;
        return this;
    }

    // wrap-around operation, assumes w> 0 and h>0
    // remember to manage negative values of the coordinates
    public Vector2D wrap(double w, double h) {

        double px = this.x%(w), py=this.y%(h);

        if (px < 0) px+= w;
        if (py < 0) py+= h;

        this.set(px,py);
        return this;
    }

    // construct vector with given polar coordinates
    public static Vector2D polar(double angle, double mag) {

        double nx = mag*Math.cos(angle);
        double ny = mag*Math.sin(angle);
        return new Vector2D(nx,ny);

    }
}