// Java implementation of the approach
import java.util.Arrays;
class GFG
{
  static int ans = 0;
 
  // Function for dfs.
  // i, j ==> Current cell indexes
  // vis ==> To mark visited cells
  // ans ==> Result
  // z ==> Current count 0s visited
  // z_count ==> Total 0s present
  static void dfs(int i, int j, int[][] grid,
                  boolean[][] vis, int z, int z_count)
  {
    int n = grid.length, m = grid[0].length;
 
    // Mark the block as visited
    vis[i][j] = true;
    if (grid[i][j] == 0)
 
      // update the count
      z++;
 
    // If end block reached
    if (grid[i][j] == 2)
    {
 
      // If path covered all the non-
      // obstacle blocks
      if (z == z_count)
        ans++;
      vis[i][j] = false;
      return;
    }
 
    // Up
    if (i >= 1 && !vis[i - 1][j] && grid[i - 1][j] != -1)
      dfs(i - 1, j, grid, vis, z, z_count);
 
    // Down
    if (i < n - 1 && !vis[i + 1][j] && grid[i + 1][j] != -1)
      dfs(i + 1, j, grid, vis, z, z_count);
 
    // Left
    if (j >= 1 && !vis[i][j - 1] && grid[i][j - 1] != -1)
      dfs(i, j - 1, grid, vis, z, z_count);
 
    // Right
    if (j < m - 1 && !vis[i][j + 1] && grid[i][j + 1] != -1)
      dfs(i, j + 1, grid, vis, z, z_count);
 
    // Unmark the block (unvisited)
    vis[i][j] = false;
  }
 
  // Function to return the count of the unique paths
  static int uniquePaths(int[][] grid)
  {
    int z_count = 0; // Total 0s present
    int n = grid.length, m = grid[0].length;
 
    boolean[][] vis = new boolean[n][m];
    for (int i = 0; i < n; i++)
    {
      Arrays.fill(vis[i], false);
    }
    int x = 0, y = 0;
    for (int i = 0; i < n; ++i)
    {
      for (int j = 0; j < m; ++j)
      {
 
        // Count non-obstacle blocks
        if (grid[i][j] == 0)
          z_count++;
        else if (grid[i][j] == 1)
        {
 
          // Starting position
          x = i;
          y = j;
        }
      }
    }
    dfs(x, y, grid, vis, 0, z_count);
    return ans;
  }
 
  // Driver code
  public static void main(String[] args)
  {
 
    int[][] grid = { { 1, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 2, -1 } };
    System.out.println(uniquePaths(grid));
  }
}