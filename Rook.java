import java.util.ArrayList;

class Rook extends Piece {


	public Rook(int side, int weight, char letter, int index) {

		super(side, weight, letter, index);

	}


	/**
	 * possibleMoves
	 * This method determines all the possible moves for the rook, at the given position on the given board
	 * The rook can move horizontally and vertically
	 */
	public ArrayList<Move> possibleMoves(int r, int c, Piece[][] chessBoard) {
		
		//DECLARE LOCAL VARIABLES
		ArrayList<Move> allPossible = new ArrayList<Move>(); //array list storing all the moves the rook can make on the given board at the given position

		int valueOfMove = 0; //stores the value of the move
		int sideSelect = chessBoard[r][c].getSide(); //stores which side this method looks through
		
		boolean pieceEaten = false; //whether a piece has been eaten
		boolean pieceBlocking = false; //whether a piece of the same side is blocking the rook
		boolean oppositeSide = false; //whether the piece is on the opposite side

		//searches for moves horizontally left
		for (int i = 1; i < 5 && !pieceEaten && !pieceBlocking; i++) { //continue searching as long as no pieces are blocking the rook

			if (c-i >= 0) { //ensures the current position isn't off the board
				
				if (chessBoard[r][c-i] != null) { //if the position is occupied piece
					
					if (chessBoard[r][c-i].getSide() != sideSelect) { //if the piece is on the opposite side
						oppositeSide = true;
					} else { //the piece must be on the same side
						pieceBlocking = true;
					}
					
				}

				if (chessBoard[r][c-i] == null || oppositeSide && !pieceBlocking) { //if the position is open, or has a piece of the opposite side (capture), and no piece of its own is blocking
					

					if (chessBoard[r][c-i] != null) { //if rook captures another piece

						valueOfMove = chessBoard[r][c-i].getWeight(); //get the weight of the piece being captured
						pieceEaten = true; //indicates that a piece has been captured

					} 

					Move position = new Move(r, c, r, c-i, valueOfMove); //create a new move, sending the rook's current location and location to move to
					allPossible.add(position); //adds this move to the array list of possible moves


				}

			}
		}

		valueOfMove = 0; //reset to default
		pieceEaten = false; //reset to default
		oppositeSide = false; //reset to default
		pieceBlocking = false; //reset to default

		
		//repeats steps while searching for moves horizontally right
		for (int i = 1; i < 5 && !pieceEaten && !pieceBlocking; i++) {

			if (c+i < 5) {
				
				if (chessBoard[r][c+i] != null) {
					
					if (chessBoard[r][c+i].getSide() != sideSelect) {
						oppositeSide = true;
					} else {
						pieceBlocking = true;
					}
				}

				if (chessBoard[r][c+i] == null || oppositeSide && !pieceBlocking) {

					if (chessBoard[r][c+i] != null) {

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
		oppositeSide = false; //reset to default
		pieceBlocking = false; //reset to default

		//repeats steps while searching for moves vertically down
		for (int i = 1; i < 6 && !pieceEaten && !pieceBlocking; i++) {

			if (r-i >= 0) {

								
				if (chessBoard[r-i][c] != null) {
					
					if (chessBoard[r-i][c].getSide() != sideSelect) {
						oppositeSide = true;
					} else {
						pieceBlocking = true;
					}
					
				}
				
				if (chessBoard[r-i][c] == null || oppositeSide && !pieceBlocking) {

					if (chessBoard[r-i][c] != null) { 

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
		oppositeSide = false; //reset to default
		pieceBlocking = false; //reset to default
		
		//repeats steps while searching for moves vertically up
		for (int i = 1; i < 6 && !pieceEaten && !pieceBlocking; i++) {

			if (r+i < 6) {

				if (chessBoard[r+i][c] != null) {
					
					if (chessBoard[r+i][c].getSide() != sideSelect) {
						oppositeSide = true;
					} else {
						pieceBlocking = true;
					}
					
				}

				if (chessBoard[r+i][c] == null || oppositeSide && !pieceBlocking) {

					if (chessBoard[r+i][c] != null) { 

						valueOfMove = chessBoard[r+i][c].getWeight();
						pieceEaten = true;

					} 

					Move position = new Move(r, c, r+i, c, valueOfMove);
					allPossible.add(position);
				}

			}
		}


		return allPossible; //returns all the possible moves for the rook
		
	}


} //end of Rook class
