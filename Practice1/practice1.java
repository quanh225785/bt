public class practice1 {
    public static void main(String[] args){
        Rectangle rect=new Rectangle(2, 3);
        Circle cir= new Circle(5);
        rect.display();
        System.out.println("------------------");
        cir.display();
    }
}
class Shape{
    protected double width;

    protected double height;
    public Shape(double width, double height) {
        this.width = width;
        this.height = height;
    }
}
 class Rectangle extends Shape{
    public Rectangle(double width, double height) {
        super(width, height);
    }
    
    public double getArea(){
        return width * height;
    }
    public double getCircumference(){
        return 2 * (width + height);
    }
    public void display(){
        System.out.println("Rectangle Area: " + getArea());
        System.out.println("Rectangle Circumference: " + getCircumference());
    }
 }   
 class Circle extends Shape{
    public Circle(double radius) {
        super(radius, 0); }
    
    public double getArea(){
        return Math.PI * width * width; }
    public double getCircumference(){
        return 2 * Math.PI * width; 
    }
    public void display(){
        System.out.println("Circle Area: " + getArea());
        System.out.println("Circle Circumference: " + getCircumference());
    }
}