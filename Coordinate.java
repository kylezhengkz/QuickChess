
class Coordinate {
	
	private int x, y;

	Coordinate(int xCoordinate, int yCoordinate) {
		this.x = xCoordinate;
		this.y = yCoordinate;
	}
	
	
	/**
	 * getX
	 * This method retrieves the piece's X coordinate
	 * @return X: Returns the piece's X coordinate
	 */
	public int getX(){

		return this.x;

	}
	
	/**
	 * getY
	 * This method retrieves the pieces's Y coordinate
	 * @return Y: Returns the pieces's Y coordinate
	 */
	public int getY(){

		return this.y;

	}
	
	/**
	 * setY
	 * This method sets the piece's Y coordinate
	 * @param Y: Integer containing the piece's Y coordinate
	 */
	public void setY(int yPos) {

		this.y = yPos;

	}
	
	/**
	 * setX
	 * This method sets the piece's X coordinate
	 * @param X: Integer containing the piece's X coordinate
	 */
	public void setX(int xPos) {

		this.x = xPos;

	}
	
}
