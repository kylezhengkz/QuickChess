import java.util.ArrayList;

class Pawn extends Piece {

	Pawn(int side, int weight, char letter, int index) {

		super(side, weight, letter, index);

	}


	/**
	 * possibleMoves
	 * This method determines all the possible moves for the pawn, at the given position on the given board
	 * The pawn can only move forward one square (up for white, down for black). Only when capturing a piece, the pawn is allowed to move diagonally in their respective direction.
	 */
	public ArrayList<Move> possibleMoves(int r, int c, Piece[][] chessBoard) {


		//DECALRE LOCAL VARIABLES
		ArrayList<Move> allPossible = new ArrayList<Move>(); //array list storing all the pawn's possible moves on the given board at the given position
		int valueOfMove = 0; //stores the value of the move

		//For white pawns:
		if (chessBoard[r][c].getSide() == 1) {

			//searches vertically downwards
			if (r-1 >= 0) { //ensures the position is within boundaries

				if (chessBoard[r-1][c] == null) { //if the space is empty
					
					//value of move is slightly higher than a neutral move, because the pawn is closer to reaching its promotion
					Move position = new Move(r, c, r-1, c, 0.3); //move is valid- create a new move sending the pawn's current position, and position to move to. Adds 0.5 as the value
					allPossible.add(position); //adds this move to the array of possible moves

				} 
			} 

			//search diagonally towards the lower left corner- only for capturing
			if (r-1 >= 0 && c-1 >= 0  && chessBoard[r-1][c-1] != null) {

				if ((chessBoard[r][c].getSide() != chessBoard[r-1][c-1].getSide())) {

					valueOfMove = chessBoard[r-1][c-1].getWeight();
					Move position = new Move(r, c, r-1, c-1, valueOfMove);
					allPossible.add(position);
				}

			}

			valueOfMove = 0; //reset to default

			//repeat search for moving diagonally towards the lower right corner- only for capturing
			if (r-1 >= 0 && c+1 < 5 && chessBoard[r-1][c+1] != null) {

				if (chessBoard[r][c].getSide() != chessBoard[r-1][c+1].getSide()) { //if the position is occupied by another piece of the opposite side

					valueOfMove = chessBoard[r-1][c+1].getWeight(); //obtain the weight of the piece the pawn is capturing
					Move position = new Move(r, c, r-1, c+1, valueOfMove); //create a new move sending the pawn's current position, and the position to move to along with the value of the move
					allPossible.add(position);
				}
			}

			valueOfMove = 0; //reset to default


		} else if (chessBoard[r][c].getSide() == 0) { //if piece selected is a black piece

			//repeat all searches, however moving downwards for a black pawn
			
			if (r+1 <= 5) {

				if (chessBoard[r+1][c] == null) {

					Move position = new Move(r, c, r+1, c, 0.3);
					allPossible.add(position);

				} 
			} 

			valueOfMove = 0;

			if (r+1 <= 5 && c-1 >= 0) {

				if (chessBoard[r+1][c-1] != null) {

					if (chessBoard[r][c].getSide() != chessBoard[r+1][c-1].getSide()) {

						valueOfMove = chessBoard[r+1][c-1].getWeight();
						Move position = new Move(r, c, r+1, c-1, valueOfMove);
						allPossible.add(position);

					}
				}

			}

			valueOfMove = 0;

			if (r+1 <= 5 && c+1 < 5) {
				if (chessBoard[r+1][c+1] != null) {

					if (chessBoard[r][c].getSide() != chessBoard[r+1][c+1].getSide()) {

						valueOfMove = chessBoard[r+1][c+1].getWeight();
						Move position = new Move(r, c, r+1, c+1, valueOfMove);
						allPossible.add(position);
					}

				}

				valueOfMove = 0;

			}
		}


		return allPossible; //returns all possible moves for the selected pawn
	}

	/**
	 * getPromotion
	 * This method finds the best captured piece for the respective side to set as the pawn's promotion
	 * @param side: Integer value storing which side the pawn is on (0 = black, 1 = white)
	 * @return bestPiece: Pieces object that has the greatest value of all the captured pieces on the respective side
	 */
	public static Piece getPromotion(int side) {

		Piece bestPiece = null; //Piece object to store the best captured piece
		int greatestValue = 0; //variable to hold the current best piece of the highest value

		//loop through all captured pieces and get the piece of highest value, to replace current piece
		for (int i = 0; i < QuickChess.capturedPieces.size(); i++) {

			if (QuickChess.capturedPieces.get(i).getSide() == side && QuickChess.capturedPieces.get(i).getWeight() > greatestValue) { //if current piece is on the same side, and has the greatest value thus far

				bestPiece = QuickChess.capturedPieces.get(i); //set this piece as the best piece after checking conditions
				greatestValue = QuickChess.capturedPieces.get(i).getWeight(); //obtains the value of the current best piece being promoted

			}

		}

		return bestPiece; //returns the piece of highest value

	} //end of getPromotion



} //end of Pawn class
