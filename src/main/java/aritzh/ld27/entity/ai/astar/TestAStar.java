package aritzh.ld27.entity.ai.astar;

import java.math.BigDecimal;

public class TestAStar {

    private static int mapWith = 20;
    private static int mapHeight = 20;
    private static int[][] obstacleMap =
            {
                    {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                    {0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0},
                    {0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                    {0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
                    {0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                    {1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
            };
    private static int startX = 0;
    private static int startY = 1;
    private static int goalX = 19;
    private static int goalY = 15;

    public static void main(String[] args) {

        System.out.println("Orthogonal map initializing...");
        NodeMap map = NodeMap.toNodeMap(obstacleMap, startX, startY, goalX, goalY, true);

        System.out.println("Calculating shortest orthogonal path...");

        long before = System.nanoTime();
        map.calculatePath();
        long time = System.nanoTime() - before;

        System.out.println("Visited node amount: " + map.getVisitedNodeAmount());
        System.out.println("Time to calculate path in nanoseconds: " + time);
        System.out.println("Time to calculate path in seconds: " + BigDecimal.valueOf(time).divide(BigDecimal.TEN.pow(9)));

        System.out.println("Printing map of shortest orthogonal path...");
        map.printPath(System.out, false);

        System.out.println();
        System.out.println();


        System.out.println("Shortest map initializing...");
        map = NodeMap.toNodeMap(obstacleMap, startX, startY, goalX, goalY, false);

        System.out.println("Calculating shortest path...");

        before = System.nanoTime();
        map.calculatePath();
        time = System.nanoTime() - before;

        System.out.println("Visited node amount: " + map.getVisitedNodeAmount());
        System.out.println("Time to calculate path in nanoseconds: " + time);
        System.out.println("Time to calculate path in seconds: " + BigDecimal.valueOf(time).divide(BigDecimal.TEN.pow(9)));

        System.out.println("Printing map of shortest path...");
        map.printPath(System.out, false);
    }

}