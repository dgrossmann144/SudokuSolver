import java.util.HashSet;
import java.util.Set;

public class PossibleValues {
    private Set<Integer> values;
    
    public PossibleValues() {
        this.values = new HashSet<Integer>();
        this.values.add(1);
        this.values.add(2);
        this.values.add(3);
        this.values.add(4);
        this.values.add(5);
        this.values.add(6);
        this.values.add(7);
        this.values.add(8);
        this.values.add(9);
    }
    
    public boolean canBe(int value) {
        return this.values.contains(value);
    }
    
    public boolean addPossibility(int value) {
        return this.values.add(value);
    }
    
    public boolean removePossibility(int value) {
        return this.values.remove(value);
    }
    
    public void clearPossibilities() {
        this.values.clear();
    }
    
    public int isOnlyOption() {
        return this.values.size() == 1 ? (int) this.values.toArray()[0] : 0; 
    }
    
    public void printPossibleValues() {
        Object[] possibilities = values.toArray();
        for(int i = 0; i < possibilities.length; i++) {
            System.out.print(possibilities[i]);
        }
    }
}
