package xyz.Dot.ui.obj;

import net.minecraft.client.Minecraft;

public class Obj {
    private double speed;
    private double acceleration;
    private double x;
    private double y;
    private double basex;
    private double basey;
    private double goalx;
    private double goaly;
    private double drawx;
    private double drawy;
    Minecraft mc = Minecraft.getMinecraft();

    public Obj(double basex, double basey, double x, double y, double speed, double acceleration) {
        this.basex = basex;
        this.basey = basey;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.acceleration = acceleration;
    }

    public Obj(double basex, double basey, double x, double y, double acceleration) {
        this.basex = basex;
        this.basey = basey;
        this.x = x;
        this.y = y;
        this.speed = 0;
        this.acceleration = acceleration;
    }

    public double getGoalx() {
        return goalx;
    }

    public double getGoaly() {
        return goaly;
    }

    public double getDrawx() {
        return drawx;
    }

    public double getDrawy() {
        return drawy;
    }

    public void setGoalPos(double goalx, double goaly) {
        this.goalx = goalx;
        this.goaly = goaly;
    }

    public void setBasePos(double basex, double basey) {
        this.basex = basex;
        this.basey = basey;
    }

    public double getBasex() {
        return basex;
    }

    public double getBasey() {
        return basey;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }


    public void draw() {
        drawx = this.getBasex() + getX();
        drawy = this.getBasey() + getY();
    }

    public void doAnim() {
        if (x != goalx || y != goalx) {
            double range = getTwoPointRange(goalx, goaly, x, y);
            double speedLowRange = displacement(speed, -acceleration);
            double beterspeedinfps = 120.0d / Minecraft.getDebugFPS();
            System.out.println(range);

            if(range > speedLowRange){
                speed += acceleration;
            }else{
                speed -= acceleration;
            }

            double xp = (goalx - x) * (speed / range);
            double yp = (goaly - y) * (speed / range);
            if(Math.abs(xp) > Math.abs(goalx - x)){
                x = goalx;
            }else{
                x += xp * beterspeedinfps;
            }

            if(Math.abs(yp) > Math.abs(goaly - y)){
                y = goaly;
            }else{
                y += yp * beterspeedinfps;
            }

        }else{
            speed = 0;
        }
    }

    private double getTwoPointRange(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    public static double displacement(double speed, double deceleration) {
        // 计算速度减到0的位移
        double s = Math.pow(speed, 2) / (2 * deceleration);
        // 返回位移
        return s;
    }

    public void setPos(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
