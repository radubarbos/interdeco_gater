package ro.barbos.gater.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by radu on 3/10/2016.
 */
public class ProductCombinator {

    private List<int[]> combinationIndex = new ArrayList<>();

    public List<List<Product>> combineProducts(List<Product> products, int k, boolean seq) {
       List<List<Product>> result = new ArrayList<>();
       if(!seq) {
           combine(products.size(), k);
           result.addAll(convert(products, combinationIndex));
       } else {
           for(int i=1;i<=k;i++) {
               combine(products.size(), i);
               result.addAll(convert(products, combinationIndex));
               reset();
           }
       }

        return result;
    }

    private void reset() {
        combinationIndex.clear();
    }

    private List<List<Product>> convert(List<Product> products, List<int[]> combinations) {
        List<List<Product>> result = new ArrayList<>(combinations.size());
        for(int[] combine: combinations) {
            result.add(convertProductCombination(products, combine));
        }
        return result;
    }

    private List<Product> convertProductCombination(List<Product> products, int[] combination) {
        List<Product> result = new ArrayList<>();
        for(int index = combination.length-1; index>=0; index--) {
            if(combination[index] == 0) {
                break;
            }
            result.add(products.get(combination[index]-1));
        }
        return result;
    }

    private void combine(int n, int k) {
        int[] a = new int[n];

        for (int i = 0; i < a.length; i++) {
            a[i] = i + 1;
        }
        doCombination(a, k, 0, new int[n]);
    }

    private  void doCombination(int[] arr, int len, int startPosition, int[] result) {
        if (len == 0) {
            //System.out.println(Arrays.toString(result));
            combinationIndex.add(result);
            return;
        }
        for (int i = startPosition; i <= arr.length - len; i++) {
            result[result.length - len] = arr[i];
            doCombination(arr, len - 1, i + 1, result);
        }
    }

    private void setCombinationIndex() {

    }

    /*public static void main(String[] args) {
        int N = 3, K = 2;
        int[] a = new int[N];

        for (int i = 0; i < a.length; i++) {
            a[i] = i + 1;
        }

        long start = System.currentTimeMillis();
        combinations2(a, K, 0, new int[N]);

        System.out.println("Counter: " + counter);
        System.out.println("Time: " + (System.currentTimeMillis() - start));
    }

    static void combinations2(int[] arr, int len, int startPosition, int[] result) {
        if (len == 0) {
            counter++;
            System.out.println(Arrays.toString(result));
            return;
        }
        for (int i = startPosition; i <= arr.length - len; i++) {
            result[result.length - len] = arr[i];
            combinations2(arr, len - 1, i + 1, result);
        }
    }
*/
}
