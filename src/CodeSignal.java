import java.util.Arrays;

public class CodeSignal {

    /*
    Problem (inferred): Laser rows and columns

    Goal:
    Given an R x C board, the current position (curRow, curCol), and two lasers at positions
    lasers[0] and lasers[1], where each laser blocks its entire row and entire column,
    compute the maximum number of squares the piece can move from its current position in
    a single straight cardinal direction (up, down, left or right) before hitting a blocked
    cell or the board edge. The starting square is not counted.

    Input (method signature):
    - numOfRows, numOfCol : board dimensions (R, C)
    - curRow, curCol      : 0-based coordinates of current position
    - lasers               : int[2][2], each entry is {row, col} for a laser

    Output: an int — the maximum reachable squares in one direction.

    Constraints / assumptions:
    - 1 <= R, C
    - 0 <= curRow < R, 0 <= curCol < C
    - lasers contains exactly two coordinates inside bounds

    Example:
    R=8, C=8, cur=(3,4), lasers=[[0,7],[1,7]]
    - lasers block rows 0 and 1 and column 7
    - Right: cols 5,6 available -> 2
    - Left: cols 3,2,1,0 -> 4
    - Up: row 2 only -> 1
    - Down: rows 4,5,6,7 -> 4
    Answer: 4

    Implementation note: this class marks blocked rows/columns on a board and scans each
    direction; consider an O(1) solution that computes distances from current position
    to the nearest blocking row/column for larger boards.
    */

    public static void main(String[] args) {
        // original sample
        int[][] lasers1 = {{0,7},{1,7}};
        int result1 = solution(8,8,3,4,lasers1);
        System.out.println("returned: " + result1);

        // test 1: current row blocked by lasers
        int[][] lasers2 = {{2,0},{2,4}}; // row 2 blocked
        int result2 = solution(5,5,2,2,lasers2);
        System.out.println("test row-blocked returned: " + result2 + " (expected 2)");

        // test 2: non-square board
        int[][] lasers3 = {{0,6},{2,6}}; // block rows 0 and 2 and column 6
        int result3 = solution(3,7,1,3,lasers3);
        System.out.println("test non-square returned: " + result3 + " (expected 3)");

        // test 3: laser located at current position
        int[][] lasers4 = {{2,2},{0,0}}; // laser at current cell
        int result4 = solution(5,5,2,2,lasers4);
        System.out.println("test laser-on-current returned: " + result4 + " (expected 0)");
    }

    private static int solution(int numOfRows, int numOfCol, int curRow, int curCol, int[][] lasers) {

        int[][] board = new int[numOfRows][numOfCol];
        // ...existing code...

        int laser1Row = lasers[0][0];
        int laser1Col = lasers[0][1];

        int laser2Row = lasers[1][0];
        int laser2Col = lasers[1][1];

        // mark laser rows
        Arrays.fill(board[laser1Row], -1);
        Arrays.fill(board[laser2Row], -1);

        // mark laser columns (use numOfRows for rows iteration)
        for (int i = 0; i < numOfRows; i++) {
            board[i][laser1Col] = -1;
            board[i][laser2Col] = -1;
        }

        // now mark the current position (so it isn't overwritten by lasers earlier)
        board[curRow][curCol] = 99;

        System.out.println(Arrays.deepToString(board));

        int result;
        int right = 0;
        // iterate columns using numOfCol (not board.length)
        for (int i = curCol + 1; i < numOfCol ; i++) {
            if (board[curRow][i] == -1){
                break;
            } else{
                right++;
            }
        }
        result = right;

        int left = 0;
        // include column 0
        for (int i = curCol - 1; i >= 0 ; i--) {
            if (board[curRow][i] == -1){
                break;
            } else{
                left++;
            }
        }
        result = Math.max(result, left);
        int up = 0;
        // include row 0
        for (int i = curRow - 1; i >= 0 ; i--) {
            if (board[i][curCol] == -1){
                break;
            } else{
                up++;
            }
        }

        result = Math.max(result, up);


        int down = 0;
        // iterate rows using numOfRows
        for (int i = curRow + 1; i < numOfRows ; i++) {
            if (board[i][curCol] == -1){
                break;
            } else{
                down++;
            }
        }

        result = Math.max(result, down);
        System.out.println(result);
        return result;
    }
}
