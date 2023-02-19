import java.util.ArrayList;

class King extends Piece {

	King(int side, int weight, char letter, int index) {

		super(side, weight, letter, index);

	}

	/**
	 * possibleMoves
	 * This method determines all the possible moves for the king, at the given position on the given board
	 * The king can move one square in any direction from its current position
	 */
	public ArrayList<Move> possibleMoves(int r, int c, Piece[][] chessBoard) {
		
		
		//DECLARE LOCAL VARIABLES
		ArrayList<Move> allPossible = new ArrayList<Move>(); //array list storing all the moves the king can make on the given board at the given position
		int valueOfMove = 0; //stores the value of the move


		//searches diagonally towards the upper left corner
		if (r-1 >= 0 && c-1 >= 0) { //ensures position is within boundaries
			if (chessBoard[r-1][c-1] == null || (chessBoard[r][c].getSide() != chessBoard[r-1][c-1].getSide())) { //makes sure the piece can move there by checking for an empty space, or a piece of the opponent


				if (chessBoard[r-1][c-1] != null) { //if king captures another piece

					valueOfMove = chessBoard[r-1][c-1].getWeight(); //obtain the weight of the piece being captured

				}

				Move position = new Move(r, c, r-1, c-1, valueOfMove); //create a new move, sending the king's current position, and position to move to
				allPossible.add(position); //adds this move to the array of possible moves

			} 
		}

		valueOfMove = 0; //resets to default

		//repeat search for the position above
		if (r-1 >= 0) { //ensures position is within boundaries

			if (chessBoard[r-1][c] == null || (chessBoard[r][c].getSide() != chessBoard[r-1][c].getSide())) {

				if (chessBoard[r-1][c] != null) { //if king eats another piece

					valueOfMove = chessBoard[r-1][c].getWeight();

				} 

				Move position = new Move(r, c, r-1, c, valueOfMove);
				allPossible.add(position);

			} 
		}

		valueOfMove = 0; //resets to default

		//repeat search for upper right corner
		if (r-1 >= 0 && c+1 < 5) {

			if (chessBoard[r-1][c+1] == null || (chessBoard[r][c].getSide() != chessBoard[r-1][c+1].getSide())) {

				if (chessBoard[r-1][c+1] != null) { //if king eats another piece

					valueOfMove = chessBoard[r-1][c+1].getWeight();

				}

				Move position = new Move(r, c, r-1, c+1, valueOfMove);
				allPossible.add(position);

			} 
		}

		valueOfMove = 0; //resets to default

		//repeat search to the left
		if (c-1 >= 0) {

			if (chessBoard[r][c-1] == null || (chessBoard[r][c].getSide() != chessBoard[r][c-1].getSide())) {

				if (chessBoard[r][c-1] != null) { //if king eats another piece

					valueOfMove = chessBoard[r][c-1].getWeight();
				}

				Move position = new Move(r, c, r, c-1, valueOfMove);
				allPossible.add(position);

			} 
		}

		valueOfMove = 0; //resets to default

		//repeat search to the right
		if (c+1 < 5) {

			if (chessBoard[r][c+1] == null || (chessBoard[r][c].getSide() != chessBoard[r][c+1].getSide())) {

				if (chessBoard[r][c+1] != null) { //if king eats another piece

					valueOfMove = chessBoard[r][c+1].getWeight();

				} 

				Move position = new Move(r, c, r, c+1, valueOfMove);
				allPossible.add(position);

			} 
		}

		valueOfMove = 0; //resets to default

		//repeat search for lower left corner
		if (r+1 < 6 && c-1 >= 0) {

			if (chessBoard[r+1][c-1] == null || (chessBoard[r][c].getSide() != chessBoard[r+1][c-1].getSide())) {

				if (chessBoard[r+1][c-1] != null) { //if king eats another piece

					valueOfMove = chessBoard[r+1][c-1].getWeight();

				} 

				Move position = new Move(r, c, r+1, c-1, valueOfMove);
				allPossible.add(position);

			} 
		}

		valueOfMove = 0; //resets to default

		//repeat search for below
		if (r+1 < 6) {

			if (chessBoard[r+1][c] == null|| (chessBoard[r][c].getSide() != chessBoard[r+1][c].getSide())) {

				if (chessBoard[r+1][c] != null) { //if king eats another piece

					valueOfMove = chessBoard[r+1][c].getWeight();

				} 

				Move position = new Move(r, c, r+1, c, valueOfMove);
				allPossible.add(position);

			} 
		}

		valueOfMove = 0; //resets to default

		//repeat search for lower left corner
		if (r+1 < 6 && c+1 < 5) {

			if (chessBoard[r+1][c+1] == null || (chessBoard[r][c].getSide() != chessBoard[r+1][c+1].getSide())) {

				if (chessBoard[r+1][c+1] != null) { //if king eats another piece

					valueOfMove = chessBoard[r+1][c+1].getWeight();

				} 

				Move position = new Move(r, c, r+1, c+1, valueOfMove);
				allPossible.add(position);

			}
		}

		return allPossible; //returns all the king's possible moves

	}


}
