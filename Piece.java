import java.util.ArrayList;

public abstract class Piece {

	private int side; // 0 = computer, 1 = user
	private int weight;
	private char letter;
	private int index;
	
	public Piece (Piece p) {
		this.side = p.side;
		this.weight = p.weight;
		this.letter = p.letter;
		this.index = p.index;
	}

	Piece(int side, int weight, char letter, int index) {

		this.side = side;
		this.weight = weight;
		this.letter = letter;
		this.index = index;

	}


	/**
	 * setSide
	 * This method sets the piece's side (opponent or computer)
	 * @param side: Integer containing the piece's side, 0=computer, 1=opponent
	 */
	public void setSide(int side) {

		this.side = side;

	}

	/**
	 * setWeight
	 * This method sets the piece's weight
	 * @param weight: Integer containing the piece's weight
	 */
	public void setWeight(int weight) {

		this.weight = weight;

	}

	/**
	 * setLetter
	 * This method sets the piece's letter
	 * @param letter: Character containing the piece's letter
	 */
	public void setLetter(char letter) {

		this.letter = letter;

	}
	
	/**
	 * setIndex
	 * This method sets the piece's index
	 * @param index: Integer containing the piece's index
	 */
	public void setIndex(int index) {

		this.index = index;

	}



	/**
	 * getSide
	 * This method retrieves the piece's side (opponent or computer)
	 * @return side: Returns the piece's side, 0=computer, 1=opponent
	 */
	public int getSide(){

		return this.side;

	}


	/**
	 * getWeight
	 * This method retrieves the piece's weight
	 * @return weight: Returns the piece's weight
	 */
	public int getWeight(){

		return this.weight;

	}

	/**
	 * getLetter
	 * This method retrieves the piece's letter
	 * @return letter: Returns the piece's letter
	 */
	public char getLetter(){

		return this.letter;

	}
	
	/**
	 * getIndex
	 * This method retrieves the piece's index
	 * @return index: Returns the piece's index
	 */
	public int getIndex(){

		return this.index;

	}
	
	abstract public ArrayList<Move> possibleMoves(int r, int c, Piece[][] chessboard);



}
