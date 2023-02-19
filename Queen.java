import java.util.ArrayList;

class Queen extends Piece{


	Queen(int side, int weight, char letter, int index) {

		super(side, weight, letter, index);

	}
	
	/**
	 * possibleMoves
	 * This method determines all the possible moves for the queen, at the given position on the given board
	 * The queen can move diagonally, vertically and horizontally
	 */
	public ArrayList<Move> possibleMoves(int r, int c, Piece[][] chessBoard) {

		
		//DECLARE LOCAL VARIABLES
		ArrayList<Move> allPossible = new ArrayList<Move>(); //array list storing all the moves the queen can make on the given board at the given position
		int valueOfMove = 0; //stores the value of the move
		boolean pieceEaten = false; //whether a piece has been eaten
		boolean pieceBlocking = false; //whether a piece of the same side is blocking the queen
		boolean oppositeSide = false; //whether the piece is on the opposite side

		//Searches for moves horizontally left
		for (int i = 1; i < 5 && !pieceEaten && !pieceBlocking; i++) { //continue searching as long as no pieces are blocking the queen

			if (c-i >= 0) { //ensures the current position isn't out of the board's boundaries

				if (chessBoard[r][c-i] != null) { //if the position is occupied by the piece
					if (chessBoard[r][c].getSide() == chessBoard[r][c-i].getSide()) { //if your own piece is blocking pending move
						pieceBlocking = true; //indicate a piece is blocking
					} else { //the piece must be on the opposite side
						oppositeSide = true; //indicate that a piece of the opposite side is blocking the queen
					}
				}

				if (chessBoard[r][c-i] == null || oppositeSide && !pieceBlocking) { //if the position is open, or has a pice of the opposite side (capture), and no piece of its own is blocking

					if (chessBoard[r][c-i] != null) { //if queen eats another piece

						valueOfMove = chessBoard[r][c-i].getWeight(); //get the weight of the piece being captured
						pieceEaten = true; //indicates that a piece has been captured

					} 

					Move position = new Move(r, c, r, c-i, valueOfMove); //create a new move, sending the bishop's current position, and position to move to
					allPossible.add(position); //adds this move to the array of possible moves

				}

			}
		}

		valueOfMove = 0; //reset to default
		pieceEaten = false; //reset to default
		pieceBlocking = false; //reset to default
		oppositeSide = false; //reset to default

		//repeat search for moves horizontally right
		for (int i = 1; i < 5 &&!pieceEaten && !pieceBlocking; i++) {


			if (c+i < 5) {

				if (chessBoard[r][c +i] != null) {
					if (chessBoard[r][c].getSide() == chessBoard[r][c+i].getSide()) { //if your own piece is blocking pending move
						pieceBlocking = true;
					} else {
						oppositeSide = true;
					}
				}

				if (chessBoard[r][c+i] == null || oppositeSide && !pieceBlocking) {

					if (chessBoard[r][c+i] != null) { //if queen eats another piece

						valueOfMove = chessBoard[r][c+i].getWeight();
						pieceEaten = true;

					} 

					Move position = new Move(r, c, r, c+i, valueOfMove);
					allPossible.add(position);

				}
			}
		}

		valueOfMove = 0; //reset to default
		pieceEaten = false; //reset to default
		pieceBlocking = false; //reset to default;
		oppositeSide = false; //reset to default;

		//repeat search for vertically up
		for (int i = 1; i < 6 && !pieceEaten && !pieceBlocking; i++) {

			if (r-i >= 0) {

				if (chessBoard[r-i][c] != null) {

					if (chessBoard[r][c].getSide() == chessBoard[r-i][c].getSide()) { //if your own piece is blocking pending move
						pieceBlocking = true;
					} else {
						oppositeSide = true;
					}
				}

				if (chessBoard[r-i][c] == null || oppositeSide && !pieceBlocking) {

					if (chessBoard[r-i][c] != null) { //if queen eats another piece

						valueOfMove = chessBoard[r-i][c].getWeight();
						pieceEaten = true;

					} 

					Move position = new Move(r, c, r-i, c, valueOfMove);
					allPossible.add(position);
				}
			}
		}

		valueOfMove = 0; //reset to default
		pieceEaten = false; //reset to default
		pieceBlocking = false; //reset to default
		oppositeSide = false; //reset to default
		
		//repeat search for vertically down
		for (int i = 1; i < 6 && !pieceEaten && !pieceBlocking; i++) {

			if (r+i < 6) {

				if (chessBoard[r+i][c] != null) {
					if (chessBoard[r][c].getSide() == chessBoard[r+i][c].getSide()) { //if your own piece is blocking pending move
						pieceBlocking = true;
					} else {
						oppositeSide = true;
					}
				}

				if (chessBoard[r+i][c] == null || oppositeSide && !pieceBlocking) {

					if (chessBoard[r+i][c] != null) { //if queen eats another piece

						valueOfMove = chessBoard[r+i][c].getWeight();
						pieceEaten = true;

					} 

					Move position = new Move(r, c, r+i, c, valueOfMove);
					allPossible.add(position);
				}
			}
		}

		valueOfMove = 0; //reset to default
		pieceEaten = false; //reset to default
		pieceBlocking = false; //reset to default
		oppositeSide = false;

		//repeat search diagonally, towards the upper left direction
		for (int i = 1; i < 5 && !pieceEaten && !pieceBlocking; i++) {

			if (r-i >= 0 && c-i >= 0) {

				if (chessBoard[r-i][c-i] != null) {
					if (chessBoard[r][c].getSide() == chessBoard[r-i][c-i].getSide()) { //if your own piece is blocking pending move
						pieceBlocking = true;
					} else {
						oppositeSide = true;
					}
				}


				if (chessBoard[r-i][c-i] == null || oppositeSide && !pieceBlocking) {
					
					if (chessBoard[r-i][c-i] != null) { //if queen eats another piece

						valueOfMove = chessBoard[r-i][c-i].getWeight();
						pieceEaten = true;

					} 

					Move position = new Move(r, c, r-i, c-i, valueOfMove);
					allPossible.add(position);
				}
			}
		} 

		valueOfMove = 0; //resets to default
		pieceEaten = false; //resets to default
		pieceBlocking = false; //resets to default
		oppositeSide = false; //resets to default

		//repeat search diagonally, towards the upper right direction
		for (int i = 1; i < 5 && !pieceEaten && !pieceBlocking; i++) {

			if (r-i >= 0 && c+i < 5) {

				if (chessBoard[r-i][c+i] != null) {
					if (chessBoard[r][c].getSide() == chessBoard[r-i][c+i].getSide()) { //if your own piece is blocking pending move
						pieceBlocking = true;
					} else {
						oppositeSide = true;
					}
				}

				if (chessBoard[r-i][c+i] == null || oppositeSide && !pieceBlocking) {

					if (chessBoard[r-i][c+i] != null) { //if queen eats another piece

						valueOfMove = chessBoard[r-i][c+i].getWeight();
						pieceEaten = true;

					} 

					Move position = new Move(r, c, r-i, c+i, valueOfMove);
					allPossible.add(position);
				}
			}			
		}

		valueOfMove = 0; //resets to default
		pieceEaten = false; //resets to default
		pieceBlocking = false; //resets to default
		oppositeSide = false; //resets to default

		//repeat search diagonally, towards the lower left direction
		for (int i = 1; i < 5 && !pieceEaten && !pieceBlocking; i++) {

			if (r+i < 6 && c-i >= 0) {

				if (chessBoard[r+i][c-i] != null) {
					if (chessBoard[r][c].getSide() == chessBoard[r+i][c-i].getSide()) { //if your own piece is blocking pending move
						pieceBlocking = true;
					} else {
						oppositeSide = true;
					}
				}

				if (chessBoard[r+i][c-i] == null || oppositeSide && !pieceBlocking) {

					if (chessBoard[r+i][c-i] != null) { //if queen eats another piece

						valueOfMove = chessBoard[r+i][c-i].getWeight();
						pieceEaten = true;

					} 

					Move position = new Move(r, c, r+i, c-i, valueOfMove);
					allPossible.add(position);
				}
			}
		}

		valueOfMove = 0; //resets to default
		pieceEaten = false; //resets to default
		pieceBlocking = false; //resets to default
		oppositeSide = false; //resets to default

		//repeat search diagonally, towards the lower right direction
		for (int i = 1; i < 5 && !pieceEaten && !pieceBlocking; i++) {
			if (r+i < 6 && c+i < 5) {

				if (chessBoard[r+i][c+i] != null) {
					if (chessBoard[r][c].getSide() == chessBoard[r+i][c+i].getSide()) { //if your own piece is blocking pending move
						pieceBlocking = true;
					} else {
						oppositeSide = true;
					}
				}

				if (chessBoard[r+i][c+i] == null || oppositeSide && !pieceBlocking) {

					if (chessBoard[r+i][c+i] != null) { //if queen eats another piece

						valueOfMove = chessBoard[r+i][c+i].getWeight();
						pieceEaten = true;

					} 

					Move position = new Move(r, c, r+i, c+i, valueOfMove);
					allPossible.add(position);

				}
			}
		}


		return allPossible; //return all possible moves for the queen

	}

}
