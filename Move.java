
public class Move {


	private int prevRow, prevCol;
	private int nextRow, nextCol;
	private double value;

	public Move(int row1, int col1, int row2, int col2, double value) {
		this.prevRow = row1;
		this.prevCol = col1;
		this.nextRow = row2;
		this.nextCol = col2;
		this.value = value;
	}

	
	/**
	 * getPrevRow
	 * This method retrieves the position's previous row
	 * @return prevRow: Returns the position's previous row
	 */
	public int getPrevRow(){

		return this.prevRow;

	}
	
	/**
	 * getPrevCol
	 * This method retrieves the position's previous column
	 * @return prevCol: Returns the position's previous column
	 */
	public int getPrevCol(){

		return this.prevCol;

	}
	
	/**
	 * getNextRow
	 * This method retrieves the position's next row
	 * @return nextRow: Returns the position's next row
	 */
	public int getNextRow(){

		return this.nextRow;

	}
	
	/**
	 * getNextCol
	 * This method retrieves the position's next column
	 * @return nextCol: Returns the position's next column
	 */
	public int getNextCol(){

		return this.nextCol;

	}
	
	
	
	/**
	 * getValue
	 * This method retrieves the position's value
	 * @return value: Returns a double value of the position's value
	 */
	public double getValue(){

		return this.value;

	}
	
	/**
	 * setValue
	 * This method sets the value of the current move
	 * @param value: double storing the value of the move
	 */
	public void setValue(double value) {
		
		this.value = value;
		
	}



}




