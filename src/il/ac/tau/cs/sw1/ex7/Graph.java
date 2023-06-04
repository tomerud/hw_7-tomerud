package il.ac.tau.cs.sw1.ex7;
import java.util.*;


public class Graph implements Greedy<Graph.Edge> {
    List<Edge> lst; //Graph is represented in Edge-List. It is undirected. Assumed to be connected.
    int n; //nodes are in [0,..., n]

    Graph(int n1, List<Edge> lst1) {
        lst = lst1;
        n = n1;
    }

    public static class Edge {
        int node1, node2;
        double weight;

        Edge(int n1, int n2, double w) {
            node1 = n1;
            node2 = n2;
            weight = w;
        }

        public int getNode1() {
            return node1;
        }

        public int getNode2() {
            return node2;
        }

        public double getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return "{" + "(" + node1 + "," + node2 + "), weight=" + weight + '}';
        }
    }

    @Override
    public Iterator<Edge> selection() {
        List<Edge> sortedEdges = new ArrayList<>(lst);
        sortedEdges.sort(Comparator.comparingDouble(Edge::getWeight).thenComparingInt(Edge::getNode1).thenComparingInt(Edge::getNode2));
        return sortedEdges.iterator();
    }

    @Override
    public boolean feasibility(List<Edge> candidates_lst, Edge element) {
        int[][] adjacentMatrix = new int[n + 1][n + 1];
        for (int i = 0; i < candidates_lst.size(); i++) {
            int firstNode = candidates_lst.get(i).node1;
            int secondNode = candidates_lst.get(i).node2;
            adjacentMatrix[firstNode][secondNode] = 1;
            adjacentMatrix[secondNode][firstNode] = 1;
            if (isPath(adjacentMatrix, element.node1, element.node2)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPath(int[][] matrix, int node1, int node2) {
        if (node1 == node2) {
            return true;
        }

        int m = matrix.length;
        int[][] matrixCopy = new int[m][m];
        for (int i = 0; i < m; i++) {
            System.arraycopy(matrix[i], 0, matrixCopy[i], 0, m);
        }
        for (int i = 0; i < m; i++) {
            if (matrixCopy[node1][i] == 1) {
                matrixCopy[node1][i] = 0;
                if (isPath(matrixCopy, i, node2)) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void assign(List<Edge> candidates_lst, Edge element) {
        candidates_lst.add(element);
    }


    @Override
    public boolean solution(List<Edge> candidates_lst) {
        if (candidates_lst.size() != n) {
            return false;
        }
        int[][] adjacentMatrix = new int[n + 1][n + 1];
        for (int i = 0; i < candidates_lst.size(); i++) {
            int firstNode = candidates_lst.get(i).node1;
            int secondNode = candidates_lst.get(i).node2;
            adjacentMatrix[firstNode][secondNode] = 1;
            adjacentMatrix[secondNode][firstNode] = 1;
        }
        int countPaths = 0;
        for (int i = 0; i < n+1; i++) {
            for (int j = 0; j < n+1; j++) {
                if (isPath(adjacentMatrix, i, j)) {
                    countPaths++;
                }
            }
        }
        if (countPaths == (n+1) * (n+1)) {
            return true;
        }
        return false;
    }
}




