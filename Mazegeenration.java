import java.util.Stack;

class Position {
    int first;
    int second;

    Position(int first, int second) {
        this.first = first;
        this.second = second;
    }
}

public class MazeSearch {
    private static final char PACMAN = 's';
    private static final char GHOST = 'g';
    private static final char BLOCK = '+';
    private static final char PATH = ' ';
    private static final char VISITED = 'e';

    private static final int firstIndex = 12;
    private static final int secondIndex = 21;

    private int finalSize;
    private Position mazeEntrance;
    private Position present;
    private Position[] correctPath;

    // private char[][] copiedMaze = new char[firstIndex][secondIndex];
    private char[][] copiedMaze = new char[firstIndex][];

    private Stack < Position > path = new Stack < > ();

    private char[][] maze = {
        "++++++++++++++++++++".toCharArray(),
        "+                  +".toCharArray(),
        "+  s+ ++++ + +++++ +".toCharArray(),
        "+ + +++ ++ + ++ ++ +".toCharArray(),
        "+ +   +    + ++    +".toCharArray(),
        "+ +++ ++++++ +++++++".toCharArray(),
        "+   +     ++ + +   +".toCharArray(),
        "+ +++++++ ++ + + +g+".toCharArray(),
        "+ +       ++ +   + +".toCharArray(),
        "+ ++++++++++ +++++ +".toCharArray(),
        "+                  +".toCharArray(),
        "++++++++++++++++++++".toCharArray()
    };

    public static void main(String[] args) {
        MazeSearch mazeGeneration = new MazeSearch();

        long start, stop;
        double totalTime;

        mazeGeneration.enterMaze();
        mazeGeneration.copyMaze();

        System.out.println("Initial Maze: ");
        mazeGeneration.printMaze();

        start = System.currentTimeMillis();
        mazeGeneration.ImazeSearch();
        mazeGeneration.printPath();

        System.out.println("\nMaze Traversal: ");
        mazeGeneration.printMaze();

        stop = System.currentTimeMillis();
        totalTime = (stop - start);
        System.out.println("Time taken to Iterative Maze: " + totalTime + " ms ");
    }

    private void ImazeSearch() {
        present = mazeEntrance;

        while (true) {
            if (copiedMaze[present.first][present.second] == GHOST)
                break;

            char top = present.first - 1 >= 0 ? copiedMaze[present.first - 1][present.second] : BLOCK;
            char right = present.second + 1 < secondIndex ? copiedMaze[present.first][present.second + 1] : BLOCK;
            char down = present.first + 1 < firstIndex ? copiedMaze[present.first + 1][present.second] : BLOCK;
            char left = present.second - 1 >= 0 ? copiedMaze[present.first][present.second - 1] : BLOCK;

            if ((top == PATH || top == GHOST)) {
                path.push(present);
                copiedMaze[present.first][present.second] = VISITED;
                present = new Position(present.first - 1, present.second);
            } else if ((right == PATH || right == GHOST)) {
                path.push(present);
                copiedMaze[present.first][present.second] = VISITED;
                present = new Position(present.first, present.second + 1);
            } else if ((down == PATH || down == GHOST)) {
                path.push(present);
                copiedMaze[present.first][present.second] = VISITED;
                present = new Position(present.first + 1, present.second);
            } else if ((left == PATH || left == GHOST)) {
                path.push(present);
                copiedMaze[present.first][present.second] = VISITED;
                present = new Position(present.first, present.second - 1);
            } else {
                copiedMaze[present.first][present.second] = BLOCK;
                present = path.pop();
            }
        }

        finalSize = path.size();
        correctPath = new Position[firstIndex * secondIndex];

        for (int i = finalSize - 1; i >= 0; i--) {
            correctPath[i] = path.pop();
        }
    }
    private void copyMaze() {
        for (int i = 0; i < firstIndex; i++) {
            copiedMaze[i] = new char[maze[i].length];
            System.arraycopy(maze[i], 0, copiedMaze[i], 0, maze[i].length);
        }
    }



    private void enterMaze() {
        for (int a = 0; a < firstIndex; a++) {
            for (int b = 0; b < maze[a].length; b++) {
                if (maze[a][b] == PACMAN) {
                    mazeEntrance = new Position(a, b);
                    return;
                }
            }
        }
    }



    private void printMaze() {
        for (int a = 0; a < firstIndex; a++) {
            for (int b = 0; b < maze[a].length; b++) {
                System.out.print(maze[a][b]);
            }
            System.out.println();
        }
    }



    private void printPath() {
        for (int i = 0; i < finalSize; i++) {
            Position temp = correctPath[i];
            for (int a = 0; a < firstIndex; a++) {
                for (int b = 0; b < secondIndex; b++) {
                    if (maze[temp.first][temp.second] == PACMAN || maze[temp.first][temp.second] == GHOST) {
                        continue;
                    } else {
                        maze[temp.first][temp.second] = '.';
                    }
                }
            }
        }
    }
}