import static java.lang.Integer.parseInt;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.Paths.get;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import java.io.IOException;
import java.util.List;
public class Application {

  /**
   *  If we could enter the cell or not
   */
  public static boolean canEnterCell(int[][] matrix, boolean[][] isVisited,
    int curRow, int curCol) {
    int nRows = matrix.length;
    int nCols = matrix[0].length;

    /*If we are outside the bounds of the matrix or
    if the cell is already visited or if the value in cell is 0
    then we shouldn't enter the cell */
    if (curRow < 0 || curRow >= nRows
      || curCol < 0 || curCol >= nCols
      || isVisited[curRow][curCol]
      || matrix[curRow][curCol] == 0) {
      return false;
    }

    return true;
  }


  /* Helper function to count the number of islands of 1's
  matrix: 2d matrix consisting of 0's and 1's
  isVisited: if cell (i, j) has been visited, isVisited[i][j] is set to true
  curRow: row of the current cell being processed
  curCol: column of the current cell being processed
  */
  public static void expandSearch(int[][] matrix, boolean[][] isVisited, int curRow, int curCol) {
    int nRows = matrix.length;
    int nCols = matrix[0].length;

    isVisited[curRow][curCol] = true;

    /*For the current cell, find out if we can continue the island of 1's
    with its neighbors. Each cell has 9 neighbors. The rows
    of neighbors will vary from curRow - 1 to curRow + 1
    The columns of the neighbors will vary from curCol - 1
    to curCol + 1*/
    for (int i = -1; i <= 1; ++i) {
      for (int j = -1; j <= 1; ++j) {
        boolean isSafeCell = canEnterCell(matrix, isVisited, curRow+i,
          curCol+j);

        if (isSafeCell) {
          expandSearch(matrix, isVisited, curRow+i, curCol+j);
        }
      }
    }
  }

  /* Main function to find the number of islands of 1's
  matrix: 2d matrix consisting of 0's and 1's. Should not be empty
  */
  public static int findIslands(int[][] matrix) {
    int nRows = matrix.length;
    int nCols = matrix[0].length;
    boolean[][] isVisited = new boolean[nRows][nCols];

    /*Initially all cells are not yet visited*/
    int i, j;
    for (i = 0; i < nRows; ++i)
      for (j = 0; j < nCols; ++j)
        isVisited[i][j] = false;

    /*Search all the cells in matrix that are not yet visited*/
    int count = 0;
    for (i = 0; i < nRows; ++i) {
      for (j = 0; j < nCols; ++j) {
        if (matrix[i][j] == 1 && !isVisited[i][j]) {
                /*We have found an island. Now expand the island
                in all directions*/
          expandSearch(matrix, isVisited, i, j);
          ++count;
        }
      }
    }
    return count;
  }

  public static void main(String[] args) {
    System.out.println("Hello in Finding Islands Application");
      List<int[]> list = readFile(args[0]).stream().map(line -> line.split("\\s*")).map(c -> stream(c).mapToInt(i -> parseInt(i)).toArray()).collect(toList());

    int[][] array = new int[list.size()][];

    int i = 0;
    for (int[] nestedList : list) {
      array[i++] = nestedList;
    }

    System.out.println(findIslands(array));


  }

  public static List<String> readFile(String path) {
    try {
      return readAllLines(get(path));
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return emptyList();
  }


}
