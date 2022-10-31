package conwaygame;
import java.util.ArrayList;
 
import javax.print.attribute.IntegerSyntax;
 
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.
 
 * @author Seth Kelley
 * @author Maxwell Goldberg
 */
public class GameOfLife {
 
    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;
 
    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)
 
    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }
 
    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file)
    {
        // WRITE YOUR CODE HERE
        StdIn.setFile(file);
 
        int r = StdIn.readInt();
        int c = StdIn.readInt();
        grid = new boolean[r][c];
        totalAliveCells = 0;

        String[] s = StdIn.readAllStrings();
 
        int y = 0;

        if (s.length == (r * c + 1))
        {
            y = 1;
        }

        for (int row = 0; row < r; row++)
        {
            for (int col = 0; col < c; col++)
            {
                grid[row][col] = Boolean.parseBoolean(s[y]);

                if (grid[row][col])
                {
                    totalAliveCells += 1;
                }

                y += 1;
            }
        }
    }
 
    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
   
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }
 
    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col)
    {
        // WRITE YOUR CODE HERE
        return grid[row][col];  // update this line, provided so that code compiles

    }
 
    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive ()
    {
        // WRITE YOUR CODE HERE
 
        if (getTotalAliveCells() > 0)
        {
            return true;
        }
        else
        {
            return false; // update this line, provided so that code compiles
        }
    }
 
    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are
     * horizontally, vertically, or diagonally adjacent.
     *
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col)
    {
        // WRITE YOUR CODE HERE
        int neighbors = 0;

        for (int r = row - 1; r <= row + 1; r++)
        {
            int ro = r;
 
            if (r < 0)
            {
                ro = grid.length - 1;
            }
            else if (r == grid.length)
            {
                ro = 0;
            }
 
            for (int c = col - 1; c <= col + 1; c++)
            {
                int co = c;
 
                if (c < 0)
                {
                    co = grid[0].length - 1;
                }
                else if (c == grid[0].length)
                {
                    co = 0;
                }
 
                if (co == col && ro == row)
                {
                    continue;
                }
                else if (grid[ro][co] == true)
                {
                    neighbors += 1;
                }
 
                if (c == grid[0].length - 1)
                {
                    co = c;
                }
            }
 
            if (r == grid.length - 1)
            {
                ro = r;
            }
        }
 
        return neighbors; // update this line, provided so that code compiles
    }

    /**
     * Creates a new grid with the next generation of the current grid using
     * the rules for Conway's Game of Life.
     *
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid ()
    {
        // WRITE YOUR CODE HERE
        boolean[][] newGrid = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[0].length; j++)
            {
                newGrid[i][j] = grid[i][j];
            }
        }
 
        for (int r = 0; r < grid.length; r++)
        {
            for (int c = 0; c < grid[0].length; c++)
            {
                int n = numOfAliveNeighbors(r, c);
           
             // rule 2
                if ((getCellState(r, c) == false) && (n == 3))
                {
                    newGrid[r][c] = true;
                }
 
            // rule 1
                else if ((getCellState(r, c) == true) && (n == 0 || n == 1))
                {
                    newGrid[r][c] = false;
                }
 
             // rule 4
                else if ((getCellState(r, c) == true) && (n >= 4))
                {
                    newGrid[r][c] = false;
                }
 
             // rule 3
                else if ((getCellState(r, c) == true) && (n == 2 || n == 3))
                {
                    newGrid[r][c] = true;
                }
            }
        }
 
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[0].length; j++)
            {
                grid[i][j] = newGrid[i][j];
            }
        }

            return grid;// update this line, provided so that code compiles
    }
 
    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     *
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration ()
    {
        // WRITE YOUR CODE HERE
        computeNewGrid();

        totalAliveCells = 0;

        for (int r = 0; r < grid.length; r++)
        {
            for (int c = 0; c < grid[0].length; c++)
            {
                if (grid[r][c] == true)
                {
                    totalAliveCells++;
                }
            }
        }
    }
 
    /**
     * Updates the current grid with the grid computed after multiple (n) generations.
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n)
    {
        // WRITE YOUR CODE HERE
        for (int i = 0; i < n; i++)
        {
            nextGeneration();
        }
    }
 
    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities()
    {
        // WRITE YOUR CODE HERE
        WeightedQuickUnionUF q = new WeightedQuickUnionUF(grid.length, grid[0].length);
        int[][] ID = new int[grid.length][grid[0].length];
        ArrayList<Integer> changedIDs = new ArrayList<Integer>();

        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[0].length; j++)
            {
                ID[i][j] = -1;
            }
        }

        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[0].length; j++)
            {
                if (grid[i][j] == true)
                {
                    for (int r = i - 1; r <= i + 1; r++)
                    {
                        int ro = r;

                        if (r < 0)
                        {
                            ro = grid.length - 1;
                        }
                        if (r == grid.length)
                        {
                            ro = 0;
                        }

                        for (int c = j - 1; c <= j + 1; c++)
                        {
                            int co = c;

                            if (c < 0)
                            {
                                co = grid[0].length - 1;
                            }
                            if (c == grid[0].length)
                            {
                                co = 0;
                            }

                            if (grid[ro][co] == true)
                            {
                                q.union(ro, co, i, j);
                                ID[ro][co] = q.find(i, j);
                            }

                            if (j == grid[0].length - 1)
                            {
                                co = j;
                            }
                        }

                        if (i == grid.length - 1)
                        {
                            ro = i;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[0].length; j++)
            {
                if (grid[i][j] == true)
                {
                    boolean b = false;

                    if (ID[i][j] == q.find(i, j))
                    {
                        for (int x = 0; x < changedIDs.size(); x++)
                        {
                            if (changedIDs.size() == 0)
                            {
                                break;
                            }

                            if (q.find(i, j) == changedIDs.get(x))
                            {
                                b = true;
                                break;
                            }
                        }
                    }

                    if (ID[i][j] == q.find(i, j) && b == false)
                    {
                        changedIDs.add(ID[i][j]);
                    }
                }
            }
        }

        return changedIDs.size();   // update this line, provided so that code compiles
    }
}