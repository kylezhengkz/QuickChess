import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;

public class QuickChess extends JFrame {

	ChessBoard board;

	//Create and initialize objects for each piece which corresponds to each of their subclass 
	static Rook bRook = new Rook(0, 30, 'r', 0);
	static Rook wRook = new Rook(1, 30, 'R', 15);

	//create bishop objects
	static Bishop bBishop = new Bishop(0, 13, 'b', 1);
	static Bishop wBishop = new Bishop(1, 13, 'B', 16);

	//create king objects
	static King bKing = new King(0, 90, 'k', 2);
	static King wKing = new King(1, 90, 'K', 17);

	//create queen objects
	static Queen bQueen = new Queen(0, 90, 'q', 3);
	static Queen wQueen = new Queen(1, 90, 'Q', 18);

	//create knight objects
	static Knight bKnight = new Knight(0, 13, 'n', 4);
	static Knight wKnight = new Knight(1, 13, 'N', 19);

	//create pawn objects
	static Pawn bPawn1 = new Pawn(0, 1, 'p', 5);
	static Pawn bPawn2 = new Pawn(0, 1, 'p', 6);
	static Pawn bPawn3 = new Pawn(0, 1, 'p', 7);
	static Pawn bPawn4 = new Pawn(0, 1, 'p', 8);
	static Pawn bPawn5 = new Pawn(0, 1, 'p', 9);

	static Pawn wPawn1 = new Pawn(1, 1, 'P', 10);
	static Pawn wPawn2 = new Pawn(1, 1, 'P', 11);
	static Pawn wPawn3 = new Pawn(1, 1, 'P', 12);
	static Pawn wPawn4 = new Pawn(1, 1, 'P', 13);
	static Pawn wPawn5 = new Pawn(1, 1, 'P', 14);

	//create and initialize 2d array of Piece objects
	static Piece[][] chessBoard = {
		{bRook, bBishop, bKing, bQueen, bKnight},
		{bPawn1, bPawn2, bPawn3, bPawn4, bPawn5},
		{null, null, null, null, null},
		{null, null, null, null, null},
		{wPawn1, wPawn2, wPawn3, wPawn4, wPawn5},
		{wRook, wBishop, wKing, wQueen, wKnight}};

	static Piece[] captured = new Piece[20]; //array for captured pieces

	static ArrayList<Piece> capturedPieces = new ArrayList<Piece>(); //stores the captured pieces- important for pawn promotions


	boolean myTurn = false; //myTurn corresponds with computer's turn

	static int mySide;
	static int opponentSide;
	static String response;

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in); 

		//Determines which user goes first
		System.out.println("Who Goes First? (b/w)");
		response = input.nextLine();

		if (response.equalsIgnoreCase("b")) {
			mySide = 0;
			opponentSide = 1;
		}  else if (response.equalsIgnoreCase("w")) {
			mySide = 1;
			opponentSide = 0;
		}

		QuickChess myChess = new QuickChess();
		myChess.run(); //call run method which launches game



	}

	QuickChess() {

		super("Quick Chess");
		board = new ChessBoard(this); //creates a new ChessBoard object

	}


	void run() {

		this.setSize(530,551); //sets size of frame
		this.setResizable(false); //User won't be allowed to adjust the frame so the format is consistent
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exits when "X" is hit
		add(board); //add jpanel to frame

		this.setVisible(true); //set board to be visible

	}



	/**
	 * possibleMovesBySide
	 * @param side: Integer variable storing which side's moves are being searched
	 * @param chessBoard: 2d array of piece objects reflecting the current board in game
	 * @return moves: array list of all possible moves for indicated side
	 */
	public static ArrayList<Move> possibleMovesBySide(int side, Piece[][] chessBoard) {
		ArrayList<Move> moves = new ArrayList<Move>(); //array list storing all the possible moves for the side can make

		//loop through each position on the game board
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 5; c++){

				if (chessBoard[r][c] != null && chessBoard[r][c].getSide() == side) { //if the position has a piece and is on the indicated side
					ArrayList<Move> currMoves = chessBoard[r][c].possibleMoves(r, c, chessBoard); //find the possible moves for that piece by calling its respective possible moves method
					moves.addAll(currMoves); //add the array list for the piece to the master array list of all possible moves
				}
			}

		}

		return moves; //return array list of all possible moves	
	}

	/**
	 * myMove
	 * This is method is called when it's the computer's turn in the game
	 * This method finds the best move for the computer, by looping through each of its possible moves and finding the value of each move
	 * It then finds the possible moves of the opponent's side for each possible move the computer can take
	 * It then finds the computer's next next move, for each of the opponent's possible next move
	 * It weighs each possibility, and calculates the best possible current move the computer can take
	 * @return myBestMove: Move object storing the computer's best move
	 */
	public static Move myMove() {

		ArrayList<Move> allPossibleMoves = possibleMovesBySide(0, QuickChess.chessBoard); //finds all the possible moves by calling the possibleMovesBySide method
		ArrayList<Move> myPossibleMoves = new ArrayList<Move>(); //valid possible moves without moves putting king in check

		Move myBestMove = null;	// best move so far when loop through my all possible moves
		Move currMove = null; //temporary move to store the current move

		Piece[][] tempBoard;

		boolean inCheck = false; //boolean storing whether the move puts the king in check or not
		double myBestScore = -1000; // so far my best score when loop through my all possible moves

		for (int i = 0; i < allPossibleMoves.size(); i++) { //loops through the computer's possible moves

			currMove = allPossibleMoves.get(i); //stores the current move being looped through
			inCheck = false; //reset each iteration
			tempBoard = cloneChessBoard(chessBoard); //clone a new chess board each time

			//updates the cloned chess board with the current move being looped through
			tempBoard[currMove.getNextRow()][currMove.getNextCol()] = tempBoard[currMove.getPrevRow()][currMove.getPrevCol()];
			tempBoard[currMove.getPrevRow()][currMove.getPrevCol()] = null;

			inCheck = isCheck(0, tempBoard); //checks if the move puts the king in check by passing the cloned chess board to the inCheck method

			if (!inCheck) { 
				myPossibleMoves.add(currMove); //if the piece is not in check after making the move, add it to the updated possible moves array list
			}


		}


		//loop through each element in the myPossibleMoves array
		//Calculate the score you can get from each move
		//Save a best score so far for each loop 
		for (int i = 0; i < myPossibleMoves.size(); i++) {

			Move move = myPossibleMoves.get(i);

			int prevRow = move.getPrevRow();
			int prevCol = move.getPrevCol();
			int nextRow = move.getNextRow();
			int nextCol = move.getNextCol();
			double score = move.getValue();

			//copy existing chessboard to a new board
			Piece[][] newBoard = cloneChessBoard(chessBoard);

			//change board to reflect this move 
			newBoard[nextRow][nextCol] = newBoard[prevRow][prevCol];
			newBoard[prevRow][prevCol] = null;

			//Call opponentPossibleMoves array list to get all possible moves by the opponent
			ArrayList<Move> opponentPossibleMoves = possibleMovesBySide(1, newBoard);
			double opponentBestScore = 0;

			//loop through each possible move to get highest value that opponent can get after my move 
			for (int n = 0; n < opponentPossibleMoves.size(); n++) {

				if (opponentPossibleMoves.get(n).getValue() > opponentBestScore) {

					opponentBestScore = opponentPossibleMoves.get(n).getValue();

				}

			}

			//Call myNextPossibleMoves to find out what is the next best move computer can make 
			ArrayList<Move> myNextPossibleMoves = possibleMovesBySide(0, newBoard);

			double myNextBestScore = 0;

			for (int n = 0; n < myNextPossibleMoves.size(); n++) {
				if (myNextPossibleMoves.get(n).getValue() > myNextBestScore) {

					myNextBestScore = myNextPossibleMoves.get(n).getValue();

				}
			}

			//calculate total score from computer's move 
			score = 1.25*score - 0.5 * opponentBestScore + 0.25 * myNextBestScore;

			//if this score is greater than my existing bestscore, save this 
			if (score > myBestScore) {
				myBestScore = score;
				myBestMove = move;
			}

		}

		if (myBestMove != null)	{ //if the best move involves a captured piece, add this to the array list of captured pieces

			if (QuickChess.chessBoard[myBestMove.getNextRow()][myBestMove.getNextCol()] != null) {

				QuickChess.capturedPieces.add(QuickChess.chessBoard[myBestMove.getNextRow()][myBestMove.getNextCol()]);

			}

		}

		return myBestMove; //returns the move of the highest score



	}



	/**
	 * isAttacked
	 * This method determines if the king is in check by comparing it with the opponent's possible moves
	 * @param r: the row of the position
	 * @param c: the column of the position
	 * @param moves: the array list which keeps track of moves 
	 * @return true or false if the piece is in attack 
	 */
	public static boolean isAttacked(int r, int c, ArrayList<Move> moves) {

		if (moves == null) { //if the opponent has no moves, return false indicating the king is not in check
			return false;
		}

		for (int i = 0; i < moves.size(); i ++) { //loop through the opponent's possible moves
			Move move = moves.get(i);
			if ((move.getNextRow() == r) && (move.getNextCol() == c)) { //if position is matched, meaning the king can be captured, return true indicating the king is in check
				return true;
			}
		}

		return false; //return false if no position is matched, indicating the king is safe (not in check)
	}


	/**
	 * isCheck 
	 * @param side: the side (0 = opponent/1 = computer) 
	 * @param chessBoard: the current chess board
	 * @return true or false whether or not the king is in check 
	 */
	public static boolean isCheck(int side, Piece[][]chessBoard) {

		int otherSide; //the number representing the opposite of the side being checked
		boolean kingLocated = false; //whether the king has been located on the board

		//assign the other side based on the side passed into the method
		if (side == 0) {
			otherSide = 1;
		} else {
			otherSide = 0;
		}

		int kingRow = 0;
		int kingCol = 0; 

		ArrayList<Move> otherPossibleMoves = possibleMovesBySide(otherSide, chessBoard); //find all the possible moves for the other side by passing it into the possibleMovesBySide method

		//loop through chess board to find the location of the king in check
		//call in check to see if any of the possible king spots will be attacked 
		for (int r = 0; r < 6 && !kingLocated; r++) {
			for (int c = 0; c< 5 && !kingLocated; c++) {

				if (chessBoard[r][c] instanceof King && chessBoard[r][c] != null && chessBoard[r][c].getSide() == side) {

					kingRow = r;
					kingCol = c;

					kingLocated = true; //to break out of loop once the king has been found

				}

			}
		}


		//if that spot will be in attack 
		if (isAttacked(kingRow, kingCol, otherPossibleMoves) == true) {

			return true; //as long as one spot is found, then it is in check 

		} else {

			return false; //if not spot was found, return false

		}

	}



	/**
	 * isCheckMate
	 * @param side: the side (opponent = 1/computer = 0) 
	 * @return true or false whether or not the king is in check mate 
	 */
	public static boolean isCheckMate(int side, Piece[][] board) {

		ArrayList<Move> possibleMoves = possibleMovesBySide(side, board); //find all the possible moves for the computer

		//loop through all possible moves 
		for (int i = 0; i < possibleMoves.size(); i++) {

			Move move = possibleMoves.get(i);

			int prevRow = move.getPrevRow();
			int prevCol = move.getPrevCol();
			int newRow = move.getNextRow();
			int newCol = move.getNextCol();

			//create new board 
			Piece[][] newBoard = cloneChessBoard(board);

			//update the board by setting new position and old position to empty
			newBoard[newRow][newCol] = newBoard[prevRow][prevCol];
			newBoard[prevRow][prevCol ] = null;

			//call isCheck method to see if king is in check
			if (isCheck(side, newBoard) == false) {
				return false;
			}

		}

		return true;

	}


	/**
	 * findWinnerByPoints
	 * This method is called when 20 moves have taken place without a capture or promotion
	 * This method finds the winner by finding the player with the most 'points' on the board
	 * @return winner: Integer storing the winner of the game; 0 = black, 1 = white, -1 indicates tie
	 */
	public static int findWinnerByPoints() {

		int bPoints = 0; //total black piece's points
		int wPoints = 0; //total white piece's points

		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 5; c++) {

				if (chessBoard[r][c] != null) { //if there is a piece at the index

					if (chessBoard[r][c].getSide() == 0) { //if the piece is a black piece
						bPoints += chessBoard[r][c].getWeight(); //add the weighting of the piece to the total black side's points
					} else { //if the piece is white
						wPoints += chessBoard[r][c].getWeight(); //add the weighting of the piece to the total white side's points
					}

				}

			}
		}

		if (bPoints > wPoints) { //if black has more points than white
			return 0; //return black as the winner
		} else if (wPoints > bPoints) { //if white has more points than black
			return 1; //return white as the winner
		}


		return -1; //if this statement is reached, a tie has taken place- indicate with -1

	}

	/**
	 * cloneChessBoard
	 * This method clones the original chess board by assigning each value to its duplicated index
	 * @param chessBoard: 2d array of Pieces objects of the original chess board
	 * @return tempBoard: duplicated 2d array of Pieces objects
	 */
	public static Piece[][] cloneChessBoard(Piece[][] chessBoard) {

		Piece[][] tempBoard = new Piece[6][5]; //create a new 2d array of pieces objects to be used for duplication

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {

				tempBoard[i][j] = chessBoard[i][j]; //assign piece at the original index to the duplicated index

			}
		}


		return tempBoard; //return the duplicated chess board

	} //end of cloneChessBoard method


}
