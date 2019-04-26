
public class ValueCount {
    private int count;
    private int x;
    private int y;

    public ValueCount() {
        this.count = 0;
    }
    
    public void update(int x, int y) {
        this.count++;
        this.x = x;
        this.y = y;
    }
    
    public void overCount() {
        this.count+=10;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getCount() {
        return this.count;
    }
}
