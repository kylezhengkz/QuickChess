import java.util.ArrayList;

class Bishop extends Piece {


	Bishop(int side, int weight, char letter, int index) {

		super(side, weight, letter, index);

	}
	

	/**
	 * possibleMoves
	 * This method determines all the possible moves for the bishop, at the given position on the given board
	 * The bishop can move diagonally
	 */
	public ArrayList<Move> possibleMoves(int r, int c, Piece[][] chessBoard) {

		//DECLARE LOCAL VARIABLES
		ArrayList<Move> allPossible = new ArrayList<Move>(); //array list storing all the moves the bishop can make on the given board at the given position
		
		int valueOfMove = 0; //stores the value of the move
		
		boolean pieceEaten = false; //whether a piece has been eaten
		boolean pieceBlocking = false; //whether a piece of the same side is blocking the bishop
		boolean oppositeSide = false; //whether the piece is on the opposite side


		//searches for diagonal moves towards the upper left corner
		for (int i = 1; i < 5 && !pieceEaten && !pieceBlocking; i++) { //continue searching as long as no pieces are blocking the rook

			if (r-i >= 0 && c-i >= 0) { //ensures the current position isn't off the board

				if (chessBoard[r-i][c-i] != null) { //if the position is occupied by a piece
					
					if (chessBoard[r][c].getSide() == chessBoard[r-i][c-i].getSide()) { //if your own piece is blocking pending move
						pieceBlocking = true;
					} else { //the piece must be on the opposite side
						oppositeSide = true;
					}
				}


				if (chessBoard[r-i][c-i] == null || oppositeSide && !pieceBlocking) { //if the position is open, or has a piece of the opposite side (capture), and no piece of its own is blocking

					if (chessBoard[r-i][c-i] != null) { //if bishop captures another piece

						valueOfMove = chessBoard[r-i][c-i].getWeight(); //get the weight of the piece being captured
						pieceEaten = true; //indicates that a piece has been captured

					} 

					Move position = new Move(r, c, r-i, c-i, valueOfMove); //create a new move, sending the bishop's current position, and position to move to
					allPossible.add(position); //adds this move to the array of possible moves

				}

			}
		} 

		valueOfMove = 0; //resets to default
		pieceEaten = false; //resets to default
		pieceBlocking = false; //resets to default
		oppositeSide = false; //resets to default
		

		//repeat steps while searching for diagonal moves towards the upper left corner
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

					if (chessBoard[r-i][c+i] != null) { //if bishop eats another piece

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
		pieceBlocking = false; // reset to default
		oppositeSide = false; //resets to default

		//repeat steps while searching for diagonal moves towards the lower right corner
		for (int i = 1; i < 5 && !pieceEaten && !pieceBlocking; i++) {

			if (r+i < 6 && c-i >= 0) {

				if (chessBoard[r+i][c-i] != null) {
					if (chessBoard[r][c].getSide() == chessBoard[r+i][c-i].getSide()) { //if your own piece is blocking pending move
						pieceBlocking = true;
					} else {
						oppositeSide = true;
					}
				}

				if ((chessBoard[r+i][c-i] == null || oppositeSide) && !pieceBlocking) {

					if (chessBoard[r+i][c-i] != null) { //if bishop eats another piece

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

		//repeat steps while searching for moves towards the lower left corner
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

					if (chessBoard[r+i][c+i] != null) { //if bishop eats another piece

						valueOfMove = chessBoard[r+i][c+i].getWeight();
						pieceEaten = true;

					} 
					Move position = new Move(r, c, r+i, c+i, valueOfMove);
					allPossible.add(position);

				}

			}



		}

		return allPossible; //returns all the possible moves for the bishop

	}



}
