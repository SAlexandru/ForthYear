package eightPuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
	private int N_;
	private int sX_;
	private int sY_;
	private int[][] board_;
	
	private static int[] dx = {-1, 0, 0, 1};
	private static int[] dy = {0, -1, 1, 0};

	private int[][] copyOfBoard(int[][] board) {
		int[][] b = new int[board.length][];
		
		for (int i = 0; i < board.length; ++i) {
			b[i] = Arrays.copyOf(board[i], board[i].length);
			
		}
		
		return b;
	}
	
	public Board(int[][] board) {
		if (null == board || 0 == board.length) {
			throw new IllegalArgumentException();
		}
		for (int[] row: board) {
			if (board.length != row.length) {
				throw new IllegalArgumentException();
			}
		}
		N_ = board.length;
		board_ = copyOfBoard(board);
		
		for (sX_ = 0; sX_ < N_; ++sX_) {
			for (sY_ = 0; sY_ < N_; ++sY_) {
				if (0 == board_[sX_][sY_]) {
					break;
				}
			}
			if (sY_ < N_) {
				break;
			}
		}
	}

	public int getSize() {return N_;}
	public int[][] getBoard()  {return copyOfBoard(board_);}
	
	/*
	 *  the number of misplaced tiles w.r.t goal
	 */
	public int manhattanDistance(Board goal) {
		if (null == goal || N_ != goal.N_) {
			throw new IllegalArgumentException();
		}
		
		int count = 0;
		for (int i = 0; i < N_; ++i) {
			for (int j = 0; j < N_; ++j) {
				if (board_[i][j] != goal.board_[i][j]) {
					++count;
				}
			}
		}
		
		return count;
	}
	
	public List<Board> getNeighbors() {
		List<Board> neighbors = new ArrayList<>();
	
		for (int i = 0; i < dx.length; ++i) {
			int newSX = sX_ + dx[i];
			int newSY = sY_ + dy[i];
			
			if (newSX < 0 || newSY < 0 || newSX >= N_ || newSY >= N_) {
				continue;
			}
			
			int[][] board = getBoard();
			
			int aux = board[sX_][sY_];
			board[sX_][sY_] = board[newSX][newSY];
			board[newSX][newSY] = aux;
			
			neighbors.add(new Board(board));
			
		}
		
		return neighbors;
	}
	
	
	@Override
	public boolean equals(Object board) {
		return null != board &&
				board instanceof Board &&
				Arrays.deepEquals(board_, ((Board)board).board_);
	}
	
	@Override
	public String toString() {
		return Arrays.deepToString(board_);
	}
}
