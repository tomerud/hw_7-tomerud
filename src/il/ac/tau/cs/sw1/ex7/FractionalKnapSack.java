package il.ac.tau.cs.sw1.ex7;
import java.util.*;

public class FractionalKnapSack implements Greedy<FractionalKnapSack.Item> {
    int capacity;
    List<Item> lst;

    FractionalKnapSack(int c, List<Item> lst1) {
        capacity = c;
        lst = lst1;
    }

    public static class Item {
        double weight, value;

        Item(double w, double v) {
            weight = w;
            value = v;
        }

        @Override
        public String toString() {
            return "{" + "weight=" + weight + ", value=" + value + '}';
        }
    }

    @Override
    public Iterator<Item> selection() {
        Collections.sort(lst, (a, b) -> Double.compare(b.value / b.weight, a.value / a.weight));
        return lst.iterator();
    }

    @Override
    public boolean feasibility(List<Item> candidates_lst, Item element) {
        if(this.capacity > 0){
            return true;
        }
        return false;
    }

    @Override
    public void assign(List<Item> candidates_lst, Item element) {
        if(element.weight <= capacity){
            candidates_lst.add(element);
            capacity -= element.weight;
        }
        else {
            double fraction = capacity / element.weight;
            double fractionWeight = fraction * element.weight;
            double fractionValue = fraction * element.value;
            Item fractionalItem = new Item(fractionWeight, fractionValue);
            candidates_lst.add(fractionalItem);
            capacity = 0;
        }
    }

    @Override
    public boolean solution(List<Item> candidates_lst) {
        return capacity == 0 || !selection().hasNext();
    }
}
