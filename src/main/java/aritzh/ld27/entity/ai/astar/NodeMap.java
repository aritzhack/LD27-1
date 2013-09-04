package aritzh.ld27.entity.ai.astar;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class NodeMap {

    Node start;
    Node end;
    int width, height;
    private Node[][] map;
    private Node[][] backCheck;
    private boolean pathFound;
    private PriorityQueue<Node> openList;
    private HashSet<Node> closedList;
    private LinkedList<Node> path;
    private boolean orthogonal;
    private boolean recalculate = false;

    public NodeMap(Node[][] map, int startX, int startY, int endX, int endY, boolean orthogonal) {
        this(map, map[startX][startY], map[endX][endY], orthogonal);
    }

    public NodeMap(Node[][] map, Node start, Node end, boolean orthogonal) {
        this.orthogonal = orthogonal;
        this.map = map;
        this.backCheck = Arrays.copyOf(this.map, this.map.length);
        this.start = start;
        this.end = end;
        this.width = map.length;
        if (this.width == 0) throw new IllegalArgumentException("Map width must be bigger than 0!");
        this.height = map[0].length;
        if (this.height == 0) throw new IllegalArgumentException("Map height must be bigger than 0!");

        this.openList = new PriorityQueue<Node>();
        this.closedList = new HashSet<Node>();

        for (Node[] row : this.map) {
            for (Node n : row) {
                n.setMap(this);
            }
        }
    }

    public static NodeMap toNodeMap(int[][] map, int startX, int startY, int endX, int endY, boolean orthogonal) {
        int width = map.length;
        if (width == 0) throw new IllegalArgumentException("Map width must be bigger than 0!");
        int height = map[0].length;
        if (height == 0) throw new IllegalArgumentException("Map height must be bigger than 0!");

        Node[][] nodeMatrix = toNodes(map, width, height);
        return new NodeMap(nodeMatrix, startX, startY, endX, endY, orthogonal);
    }

    public static Node[][] toNodes(int[][] map, int width, int height) {
        Node[][] nodeMatrix = new Node[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                nodeMatrix[x][y] = new Node(x, y, map[x][y] != 0);
            }
        }
        return nodeMatrix;
    }

    public LinkedList<Node> calculatePath() {

        if (!this.recalculate && this.pathFound && Arrays.deepEquals(this.map, this.backCheck)) return this.path;

        this.closedList.clear();
        this.openList.clear();

        this.openList.add(this.start);

        while (!this.openList.contains(this.end)) {

            if (this.openList.isEmpty()) return null;

            Node curr = this.openList.poll();

            if (curr == null || this.closedList.contains(curr) || this.openList.contains(curr)) continue;


            for (Node neighbor : curr.getNeighbors(this.orthogonal)) {
                if (neighbor == null || curr.getParent() == neighbor || neighbor.getParent() == curr) continue;

                if (neighbor.isSolid()) {
                    this.closedList.add(neighbor);
                    continue;
                }

                if (this.closedList.contains(neighbor)) continue;

                if (this.openList.contains(neighbor) && neighbor.getHForParent(curr) < neighbor.getH()) {
                    neighbor.setParent(curr);
                    this.closedList.remove(neighbor);
                    if (!this.openList.contains(neighbor)) this.openList.add(neighbor);
                } else if (!this.openList.contains(neighbor)) {
                    neighbor.setParent(curr);
                    this.openList.add(neighbor);
                }
            }

            this.closedList.add(curr);
        }

        this.pathFound = false;
        LinkedList<Node> ret = new LinkedList<Node>();
        Node n = this.end;
        while (n != null) {
            ret.addFirst(n);
            n = n.getParent();
        }
        this.pathFound = ret.contains(this.end) && ret.contains(this.start);
        return this.path = ret;
    }

    public void printPath(PrintStream out, boolean printLists) {

        boolean checkLists = printLists && this.openList != null && this.closedList != null;
        Node node;

        for (int i = 0; i <= this.width + 1; i++) out.print("**");
        out.println();

        for (int y = 0; y < this.height; y++) {
            out.print("* ");
            for (int x = 0; x < this.width; x++) {
                node = this.map[x][y];
                if (node.isSolid()) {
                    out.print("XX");
                } else if (this.start == node) {
                    out.print("SS");
                } else if (this.end == node) {
                    out.print("GG");
                } else if (this.path != null && this.path.contains(node)) {
                    out.print("--");
                } else if (checkLists && this.openList.contains(node)) {
                    out.print("OO");
                } else if (checkLists && this.closedList.contains(node)) {
                    out.print("CC");
                } else {
                    out.print("  ");
                }
            }
            out.print(" *");
            out.println();
        }
        for (int i = 0; i <= this.width + 1; i++) out.print("**");
        out.println();
        out.println();
        out.println();
    }

    public LinkedList<Node> getPath() {
        return this.path;
    }

    public Node getNode(int x, int y) {
        if (x < 0 || y < 0 || x >= this.width || y >= this.height) return null;
        return this.map[x][y];
    }

    public int getVisitedNodeAmount() {
        return this.pathFound ? this.closedList.size() : -1;
    }

    public void update(Node[][] nodes, int startX, int startY, int endX, int endY) {
        this.map = nodes;
        Node start = this.map[startX][startY];
        Node end = this.map[endX][endY];
        this.recalculate = Arrays.deepEquals(this.map, this.backCheck) && this.end.equals(end) && this.start.equals(start);
        this.start = start;
        this.end = end;
    }
}
