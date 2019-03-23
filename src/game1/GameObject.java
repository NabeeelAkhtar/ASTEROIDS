package game1;

import Utils.Vector2D;

import java.awt.*;


public abstract class GameObject {

    public Vector2D position;
    public Vector2D velocity;
    public Vector2D direction;
    public boolean dead;
    public double radius;

    public GameObject(Vector2D position, Vector2D velocity, double radius){

        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
        this.dead = false;
    }

    public boolean overlap(GameObject other){
        double diffx = this.position.x - other.position.x;
        double diffy = this.position.y - other.position.y;
        double detect = diffx*diffx + diffy*diffy ;
        return detect < (this.radius + other.radius)*(this.radius + other.radius);
    }

    public boolean abouttooverlap(GameObject other){
        double diffx = (this.position.x) - (other.position.x);
        double diffy = (this.position.x) - (other.position.y);
        double detect = diffx*diffx + diffy*diffy ;
        return detect < (this.radius + other.radius)*(this.radius + other.radius);
    }

    public void collisionHandling(GameObject other) {
    }

    protected GameObject() {
    }

    public void hit(){

        this.dead = true;
    }


    public void update(){


    }

    public abstract void draw(Graphics2D g);


}
