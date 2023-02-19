import java.util.ArrayList;

class Knight extends Piece{


	public Knight(int side, int weight, char letter, int index) {

		super(side, weight, letter, index);

	}


	/**
	 * possibleMoves
	 * This method determines all the possible moves for the knight, at the given position on the given board
	 * The knight can move 2 squares in any direction, then 1 perpendicular to the 2 squares in either direction
	 */
	public ArrayList<Move> possibleMoves(int r, int c, Piece[][] chessBoard) {

			//DECLARE LOCAL VARIABLES
			ArrayList<Move> allPossible = new ArrayList<Move>(); //array list storing all the knight's possible moves on the given board at the given position
			int valueOfMove = 0; //stores the value of the move
			boolean switchNum = false; //whether the 1 and 2 square movement of the knight should be reflected
			int i = 1;
			int j = 2;
			

			for (int count = 0; count < 2; count++) { //repeat search process twice, totaling 8 possible knight moves after the reflection

				if (switchNum) { //if the 1 and 2 square movements are to be switched (switches horizontal and vertical search)
					i = 2; //flip them
					j = 1;
				} 

				//searches vertically upwards 2, and 1 to the left
				if (r-i >= 0 && c-j >= 0) { //ensures the position is within boundaries

					if (chessBoard[r-i][c-j] == null || (chessBoard[r][c].getSide() != chessBoard[r-i][c-j].getSide())) { //ensures the piece is either moving to an empty space, or capturing an opponent's piece

						if (chessBoard[r-i][c-j] != null) { //if the knight captures another piece

							valueOfMove = chessBoard[r-i][c-j].getWeight(); //obtain the weight of the piece being captured

						}

						Move position = new Move(r, c, r-i, c-j, valueOfMove); //create a new move sending the knight's current position, pending position, and value of piece eaten if applicable 
						allPossible.add(position); //adds this move to the array of possible moves

					} 
				}

				valueOfMove = 0; //reset to default

				//repeat search vertically upwards 2, and 1 to the right
				if (r-i >= 0 && c+j < 5) {

					if (chessBoard[r-i][c+j] == null || (chessBoard[r][c].getSide() != chessBoard[r-i][c+j].getSide())) {			

						if (chessBoard[r-i][c+j] != null) { //if knight captures another piece

							valueOfMove = chessBoard[r-i][c+j].getWeight();

						}

						Move position = new Move(r, c, r-i, c+j, valueOfMove);
						allPossible.add(position);

					} 
				}

				valueOfMove = 0; //reset to default

				//repeat search vertically downwards 2, and 1 to the left
				if (r+i < 6 && c-j >= 0) {

					if (chessBoard[r+i][c-j] == null || (chessBoard[r][c].getSide() != chessBoard[r+i][c-j].getSide())) {			

						if (chessBoard[r+i][c-j] != null) { //if knight eats another piece

							valueOfMove = chessBoard[r+i][c-j].getWeight();//valueOfMove = QuickChess.getValue(r+i,c-j);

						}

						Move position = new Move(r, c, r+i, c-j, valueOfMove);
						allPossible.add(position);

					} 
				}

				valueOfMove = 0; //reset to default

				//repeat search vertically downwards 2, and 1 to the right
				if (r+i < 6 && c+j < 5) {

					if (chessBoard[r+i][c+j] == null || (chessBoard[r][c].getSide() != chessBoard[r+i][c+j].getSide())) {			

						if (chessBoard[r+i][c+j] != null) { //if knight eats another piece

							valueOfMove = chessBoard[r+i][c+j].getWeight();

						}

						Move position = new Move(r, c, r+i, c+j, valueOfMove);
						allPossible.add(position);

					} 
				}

				valueOfMove = 0; //reset to default
				switchNum = true; //indicate that the horizontal and vertical search once first iteration is done

			}


			return allPossible; //returns all the knight's possible moves

		}

	}
