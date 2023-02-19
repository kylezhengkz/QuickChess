import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

class ChessBoard extends JPanel implements MouseListener {


	//DECLARE VARIABLES

	//BufferedImage variable storing the pieces, game board, and winner images
	BufferedImage boardImage, bKing, bQueen, bRook, bBishop, bKnight, bPawn1, bPawn2, bPawn3, bPawn4, bPawn5;
	BufferedImage wKing, wQueen, wRook, wBishop, wKnight, wPawn1, wPawn2, wPawn3, wPawn4, wPawn5;
	BufferedImage bWinsImage, wWinsImage, tieGameImage;

	Rectangle selectedBox; //stores the user's selected rectangle, which holds the piece's image
	Piece currentPiece = null; //stores the user's selected piece
	Piece promotedPiece = null; //stores the piece being promoted

	Rectangle[] pieceRect = new Rectangle[20]; //array storing a rectangle for each piece
	Coordinate[] coordinate = new Coordinate[20]; //array storing the coordinates for each image, and rectangle

	Piece[][] tempBoard = new Piece[6][5]; //copy of the chess board

	Color green = new Color(153, 255, 51, 100); //a transparent colour green to indicate the piece selected
	Color yellow = new Color(225, 225, 0, 80); //a transparent colour yellow to indicate when the king is in check

	ArrayList<Move> allPossible = new ArrayList<Move>(); //an array list storing all the possible moves for the current side

	int clickNum = 0; //stores the number of clicks on the board
	int moveNum;// = 0; //stores the number of moves made

	int xPos = 0; //stores the x position of the mouse click
	int yPos = 0; //stores the y position of the mouse click
	int prevX; //stores the previous x position of the mouse click (for storing the piece selected)
	int prevY; //stores the previous y position of the mouse click (for storing the piece selected)
	int selectedIndex = 0; //stores the index selected, from the piece selected- useful for the piece and coordinate array
	int pendingIndex = 0; //stores the index user wants to move their piece to
	int actionlessMoves = 0; //stores the number of moves where no capture or promotion has taken place
	int winner = -1; //stores the winner of the game; 0 = black, 1 = white, -1 indicates tie game (default)
	int kingRow = 0; //stores the king's row on the board, for determining where to draw the yellow block indicated the king's in check
	int kingCol = 0; //stores the king's column on the board

	boolean pieceSelected = false; //stores whether a valid piece has been selected
	boolean canMove = false; //stores whether the pending move is valid/can be made
	boolean pawnPromoted = false; //stores whether a pawn has been promoted
	boolean pieceCaptured = false; //stores whether a piece has been captured
	boolean kingLocated = false; //stores whether the king has been located
	boolean wInCheck = false; //stores whether the white king is in check
	boolean bInCheck = false; //stores whether the black king is in check
	boolean winnerFound = false; //stores whether a winner has been determined

	JFrame frame;

	ChessBoard(JFrame runFrame) {

		this.frame = runFrame;
		this.addMouseListener(this); //adds a mouse listener

		if (QuickChess.mySide == 0) {
			moveNum = 1;
		} else {
			moveNum = 0;
		}

		//adds new coordinates on the board
		coordinate[0] = new Coordinate(0, 0);
		coordinate[1]= new Coordinate(106, 0);
		coordinate[2] = new Coordinate(2*106, 0);
		coordinate[3] = new Coordinate(3*106, 0);
		coordinate[4] = new Coordinate(4*106, 0);

		coordinate[5] = new Coordinate(0, 88);
		coordinate[6] = new Coordinate(106, 88);
		coordinate[7] = new Coordinate(2*106, 88);
		coordinate[8] = new Coordinate(3*106, 88);
		coordinate[9] = new Coordinate(4*106, 88);

		coordinate[10] = new Coordinate(0, 4*88);
		coordinate[11] = new Coordinate(106, 4*88);
		coordinate[12] = new Coordinate(2*106, 4*88);
		coordinate[13] = new Coordinate(3*106, 4*88);
		coordinate[14] = new Coordinate(4*106, 4*88);

		coordinate[15] = new Coordinate(0, 5*88);
		coordinate[16] = new Coordinate(106, 5*88);
		coordinate[17] = new Coordinate(2*106, 5*88);
		coordinate[18] = new Coordinate(3*106, 5*88);
		coordinate[19] = new Coordinate(4*106, 5*88);


		try { //reads the files at the beginning of game

			//image of chess board as background
			boardImage = ImageIO.read(new File("ChessBoard.png"));

			//image of black pieces
			bKing = ImageIO.read(new File("BlackKing.png"));
			bQueen = ImageIO.read(new File("BlackQueen.png"));
			bRook = ImageIO.read(new File("BlackRook.png"));
			bBishop = ImageIO.read(new File("BlackBishop.png"));
			bKnight = ImageIO.read(new File("BlackKnight.png"));
			bPawn1 = ImageIO.read(new File("BlackPawn.png"));
			bPawn2 = ImageIO.read(new File("BlackPawn.png"));
			bPawn3 = ImageIO.read(new File("BlackPawn.png"));
			bPawn4 = ImageIO.read(new File("BlackPawn.png"));
			bPawn5 = ImageIO.read(new File("BlackPawn.png"));

			//image of white pieces
			wKing = ImageIO.read(new File("WhiteKing.png"));
			wQueen = ImageIO.read(new File("WhiteQueen.png"));
			wRook = ImageIO.read(new File("WhiteRook.png"));
			wBishop = ImageIO.read(new File("WhiteBishop.png"));
			wKnight = ImageIO.read(new File("WhiteKnight.png"));
			wPawn1 = ImageIO.read(new File("WhitePawn.png"));
			wPawn2 = ImageIO.read(new File("WhitePawn.png"));
			wPawn3 = ImageIO.read(new File("WhitePawn.png"));
			wPawn4 = ImageIO.read(new File("WhitePawn.png"));
			wPawn5 = ImageIO.read(new File("WhitePawn.png"));

			//winner images
			bWinsImage = ImageIO.read(new File("BlackWins.png"));
			wWinsImage = ImageIO.read(new File("WhiteWins.png"));
			tieGameImage = ImageIO.read(new File("TieGame.png"));


		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error loading image");
		}


	}


	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(boardImage, 0, 0, this); //draw on the board

		//Draw on black pieces
		g.drawImage(bRook, coordinate[0].getX() + 13, coordinate[0].getY() + 5, 80, 80, this);
		g.drawImage(bBishop, coordinate[1].getX() + 14, coordinate[1].getY() + 2, 78, 78, this);
		g.drawImage(bKing, coordinate[2].getX() + 19, coordinate[2].getY() + 8, 68, 68, this);
		g.drawImage(bQueen, coordinate[3].getX() + 14, coordinate[3].getY() + 3, 78, 78, this);
		g.drawImage(bKnight, coordinate[4].getX() + 16, coordinate[4].getY() + 5, 75, 75, this);        

		g.drawImage(bPawn1, coordinate[5].getX() + 14, coordinate[5].getY() + 3, 77, 77, this);
		g.drawImage(bPawn2, coordinate[6].getX() + 14, coordinate[6].getY() + 3, 77, 77, this);
		g.drawImage(bPawn3, coordinate[7].getX() + 16, coordinate[7].getY() + 3, 77, 77, this);
		g.drawImage(bPawn4, coordinate[8].getX() + 15, coordinate[8].getY() + 3, 77, 77, this);
		g.drawImage(bPawn5, coordinate[9].getX() + 16, coordinate[9].getY() + 3, 77, 77, this);

		//Draw on white pieces
		g.drawImage(wPawn1, coordinate[10].getX() + 13, coordinate[10].getY() + 6, 76, 76, this);
		g.drawImage(wPawn2, coordinate[11].getX() + 14, coordinate[11].getY() + 6, 76, 76, this);
		g.drawImage(wPawn3, coordinate[12].getX() + 16, coordinate[12].getY() + 6, 76, 76, this);
		g.drawImage(wPawn4, coordinate[13].getX() + 15, coordinate[13].getY() + 6, 76, 76, this);
		g.drawImage(wPawn5, coordinate[14].getX() + 16, coordinate[14].getY() + 6, 76, 76, this);

		g.drawImage(wRook, coordinate[15].getX() + 13, coordinate[15].getY() + 3, 77, 77, this);
		g.drawImage(wBishop, coordinate[16].getX() + 14, coordinate[16].getY() + 3, 80, 80, this);
		g.drawImage(wKing, coordinate[17].getX() + 19, coordinate[17].getY() + 7, 68, 68, this);
		g.drawImage(wQueen, coordinate[18].getX() + 19, coordinate[18].getY() + 8, 68, 68, this);
		g.drawImage(wKnight, coordinate[19].getX() + 16, coordinate[19].getY() + 3, 80, 80, this);


		//create a new rectangle around each piece's image
		for (int index = 0; index < 20; index++) {

			pieceRect[index] = new Rectangle(coordinate[index].getX(), coordinate[index].getY(), 106, 88);

		}


		if (pieceSelected) { //if a piece was selected for the first time, fill the grid with green to clearly indicate piece user chose

			g.drawRect((xPos/106)*106, (yPos/88)*88, 106, 88); //draws a rectangle around the piece selected
			g.setColor(green); //sets the colour to green with transparency
			g.fillRect((xPos/106)*106, (yPos/88)*88, 106, 88); //fills the box with green

		} else if (!pieceSelected) { //if a piece was deselected

			g.fillRect(0,0,0,0); //hide the rectangle temporary by giving it no colour

		}

		
		if (moveNum%2 == 1) { //black's turn

			bInCheck = QuickChess.isCheck(0, QuickChess.chessBoard); //check if black is in check


			//CHECK IF BLACK IS IN CHECK

			if (bInCheck) {

				//loop through chess board to find the location of the king in check
				for (int r = 0; r < 6 && !kingLocated; r++) {
					for (int c = 0; c < 5 && !kingLocated; c++) {

						if (QuickChess.chessBoard[r][c] instanceof King && QuickChess.chessBoard[r][c].getSide() == 0) {

							//if the piece being looped through is a king and is a black piece
							kingRow = r;
							kingCol = c;

							kingLocated = true; //set boolean to true, to break loop

						}
					}
				}

				//draw a yellow rectangle around the king in check to inform the user
				g.drawRect(kingCol * 106, kingRow * 88, 106, 88);
				g.setColor(yellow);
				g.fillRect(kingCol * 106, kingRow * 88, 106, 88);


			} else { //if not in check, remove the yellow rectangle
				g.fillRect(0, 0, 0, 0);
			}


			Move currMove = QuickChess.myMove(); //call the myMove method to get the computer's best move

			if (currMove == null) { //if no move was found, it indicates that the computer is in check mate

				winner = 1; //set winner to be white
				winnerFound = true; //winner is found

			} else { //if the computer is not in check mate, update the chess board accordingly;

				currentPiece = QuickChess.chessBoard[currMove.getPrevRow()][currMove.getPrevCol()]; //retrieves the current piece wanting to be moved
				selectedIndex = QuickChess.chessBoard[currMove.getPrevRow()][currMove.getPrevCol()].getIndex(); //stores the index of the piece to be moved


				if (QuickChess.chessBoard[currMove.getNextRow()][currMove.getNextCol()] == null) { //if the piece is moving to an empty spot

					coordinate[selectedIndex].setX(currMove.getNextCol() * 106); //sets the new  X coordinate of the index of the selected piece
					coordinate[selectedIndex].setY(currMove.getNextRow() * 88); //sets the new Y coordinate of the index of the selected piece

					QuickChess.chessBoard[currMove.getPrevRow()][currMove.getPrevCol()] = null; //sets the previous position of where the piece came from to null on the chess board
					QuickChess.chessBoard[currMove.getNextRow()][currMove.getNextCol()] = currentPiece; //sets the new chosen position on the board to the current piece selected


				} else { //piece is capturing another piece

					coordinate[selectedIndex].setX(currMove.getNextCol() * 106); //sets the new  X coordinate of the index of the selected piece
					coordinate[selectedIndex].setY(currMove.getNextRow() * 88); //sets the new Y coordinate of the index of the selected piece

					pendingIndex = QuickChess.chessBoard[currMove.getNextRow()][currMove.getNextCol()].getIndex(); //holds the index of the piece about to be captured

					coordinate[pendingIndex].setX(636 + pendingIndex*106); //moves the piece off the screen, available for pawn promotion later
					coordinate[pendingIndex].setY(0);

					QuickChess.captured[pendingIndex] = QuickChess.chessBoard[currMove.getNextRow()][currMove.getNextCol()]; //adds the captured piece to its index in the captured array
					QuickChess.capturedPieces.add(QuickChess.chessBoard[currMove.getNextRow()][currMove.getNextCol()]); //adds the captured piece to the captured pieces array list

					QuickChess.chessBoard[currMove.getPrevRow()][currMove.getPrevCol()] = null; //sets the previous position of where the piece came from to null on the chess board
					QuickChess.chessBoard[currMove.getNextRow()][currMove.getNextCol()] = currentPiece; //sets the new chosen position on the board to the current piece selected

					pieceCaptured = true; //set piece captured to be true in this move

				}

				//check for pawn promotions
				if (QuickChess.chessBoard[currMove.getNextRow()][currMove.getNextCol()] instanceof Pawn && QuickChess.chessBoard[currMove.getNextRow()][currMove.getNextCol()].getSide() == 0 && currMove.getNextRow() == 5) { //if a black piece reaches the bottom

					promotedPiece = Pawn.getPromotion(0); //retrieves the best piece to be promoted; calls getPromotion method with 0 to look through the computer's captured pieces

					if (promotedPiece != null) { //a piece can be promoted (piece was found after calling method)

						QuickChess.chessBoard[currMove.getNextRow()][currMove.getNextCol()] = promotedPiece; //updates the chess board with the promoted piece (replaces pawn)

						coordinate[selectedIndex].setX(636 + selectedIndex*106); //makes pawn not visible
						coordinate[selectedIndex].setY(0);
						coordinate[promotedPiece.getIndex()].setX(currMove.getNextCol() * 106); //changes the X coordinate of the promoted piece, which updates the image and rectangle
						coordinate[promotedPiece.getIndex()].setY(currMove.getNextRow() * 88); //changes the Y coordinate of the promoted piece, which updates the image and rectangle

						QuickChess.capturedPieces.remove(QuickChess.capturedPieces.indexOf(promotedPiece)); //removes the promoted piece from captured pieces arrayList
						QuickChess.captured[promotedPiece.getIndex()] = null; //sets the index of the promoted pieces array to null

						pawnPromoted = true; //sets pawn promoted boolean to be true this move

					} 


				}

				moveNum++; //adds one to the total move numbers after move has been successfully completed



				if (!pawnPromoted && !pieceCaptured) { //if no pawn was promoted or no piece was captured

					actionlessMoves++; //add a count to actionlessMoves

				} else {

					actionlessMoves = 0; //restart count to determine end of game

				}


				pieceCaptured = false; //reset for next move
				pawnPromoted = false; //reset for next move

				wInCheck = QuickChess.isCheck(1, QuickChess.chessBoard); //checks if the opponent's king is in check after the computer finishes its move




			}
			

			kingLocated = false; //reset to default

			if (QuickChess.isCheckMate(1, QuickChess.chessBoard)) { //check if white is in check mate after black's move
				winner = 0; //black wins
				winnerFound = true; //indicate that a winner has been found
			}


			//check if 20 moves have been made without a promotion or capture
			if (actionlessMoves >= 20) {

				winner = QuickChess.findWinnerByPoints(); //find winner by calling method
				winnerFound = true; //indicate that a winner has been determined

			}


		} //end of black's turn
		
		if (wInCheck) { //if the opponent's in check

			//loop through chess board to find the location of the king in check
			for (int r = 0; r < 6 && !kingLocated; r++) {
				for (int c = 0; c < 5 && !kingLocated; c++) {

					//if the piece being looped through is a king, and is on the opponent's side
					if (QuickChess.chessBoard[r][c] instanceof King && QuickChess.chessBoard[r][c].getSide() == 1) {
						kingRow = r;
						kingCol = c;

						kingLocated = true; //indicate that the king was found, to break out of loop

					}

				}
			}

			//draw a yellow rectangle around the king in check to inform the user
			g.drawRect(kingCol * 106, kingRow * 88, 106, 88);
			g.setColor(yellow);
			g.fillRect(kingCol * 106, kingRow * 88, 106, 88);

		} else { //if not in check, remove the yellow rectangle
			g.fillRect(0, 0, 0, 0);
		}
		
		if (winnerFound) { //if a winner is found this turn, draw on the image corresponding to the winner

			if (winner == 0) { //if black won
				g.drawImage(bWinsImage, 70, 220, this); 
			} else if (winner == 1) { //if white won
				g.drawImage(wWinsImage, 70, 220, this);
			} else { //if tie game
				g.drawImage(tieGameImage, 70, 220, this);
			}

		}
		repaint(); //repaint jPanel to update changes

	}


	public void mousePressed(MouseEvent e) {}


	public void mouseReleased(MouseEvent e) {	
		
		xPos = e.getX(); //x position of mouse click
		yPos = e.getY(); //y position of mouse click

		//clickNum variable used to track which one of the 2 functions are executed;
		//If it's the user's first click on their turn, only execute code related to selecting a piece
		//If it's the user's second click on their turn, execute the code related to moving the piece previously selected on user's first click


		//SECOND CLICK---
		//if user's click is made after selected a piece, tracked by an odd move number
		if (pieceSelected && clickNum%2 != 0) { //space chosen does not have an already existing piece there


			allPossible = (currentPiece).possibleMoves(prevY, prevX, QuickChess.chessBoard); //find all possible moves for the current selected piece
			tempBoard = QuickChess.cloneChessBoard(QuickChess.chessBoard);

			//check if piece is able to make a move to the position chosen by calling the pieces' possibleMoves() method
			for (int i = 0; i < allPossible.size() && !canMove; i++) { 

				//loops through the x and y positions of the possible moves, and matches it with the user's pending move
				if ((allPossible.get(i).getNextCol() == xPos/106) && (allPossible.get(i).getNextRow() == yPos/88)) { //if matched

					if (tempBoard[yPos/88][xPos/106] == null) {

						tempBoard[prevY][prevX] = null; //sets the previous position of where the piece came from to null on the chess board
						tempBoard[yPos/88][xPos/106] = currentPiece; //sets the new chosen position on the board to the current piece selected

					} else if (tempBoard[prevY][prevX].getSide() != tempBoard[yPos/88][xPos/106].getSide()) {

						tempBoard[prevY][prevX] = null; //sets the previous position of where the piece came from to null on the chess board
						tempBoard[yPos/88][xPos/106] = currentPiece; //sets the new chosen position on the board to the current piece selected

					} 

					wInCheck = QuickChess.isCheck(1, tempBoard);

					if (!wInCheck) {

						canMove = true; //set boolean to true
						//moveNum++; //increase the total count for number of moves by 1 if the move can be made

					}

				}

			}




			if (canMove && QuickChess.chessBoard[yPos/88][xPos/106] == null) { //if move can be made into an empty position after checking previously

				coordinate[selectedIndex].setX(xPos/106 * 106); //sets the new  X coordinate of the index of the selected piece
				coordinate[selectedIndex].setY(yPos/88 * 88); //sets the new Y coordinate of the index of the selected piece

				QuickChess.chessBoard[prevY][prevX] = null; //sets the previous position of where the piece came from to null on the chess board
				QuickChess.chessBoard[yPos/88][xPos/106] = currentPiece; //sets the new chosen position on the board to the current piece selected

				canMove = false; //reset to default
				pieceSelected = false; //reset to default
				moveNum++;


			}  else if (canMove && QuickChess.chessBoard[prevY][prevX].getSide() != QuickChess.chessBoard[yPos/88][xPos/106].getSide()) { //if user is capturing a piece


				coordinate[selectedIndex].setX(xPos/106 * 106); //sets the new  X coordinate of the index of the previously selected piece
				coordinate[selectedIndex].setY(yPos/88 * 88); //sets the new Y coordinate of the index of the previously selected piece

				//loop through the rectangles drawn around each piece, and match it with the user's selected
				for (int i = 0; i < 20; i++) {

					if (pieceRect[i].contains(e.getPoint())) {
						pendingIndex = i; //set the index of piece to be captured to the index of the corresponding rectangle
					}

				}

				coordinate[pendingIndex].setX(636 + pendingIndex*106);
				coordinate[pendingIndex].setY(0);

				QuickChess.captured[pendingIndex] = QuickChess.chessBoard[yPos/88][xPos/106]; //adds the captured piece to its index in the captured array
				QuickChess.capturedPieces.add(QuickChess.chessBoard[yPos/88][xPos/106]); //adds the captured piece to the captured pieces array list
				QuickChess.chessBoard[prevY][prevX] = null; //sets the previous position of where the piece came from to null on the chess board
				QuickChess.chessBoard[yPos/88][xPos/106] = currentPiece; //sets the new chosen position on the board to the current piece selected

				canMove = false; //reset to default
				pieceSelected = false; //reset to default
				pieceCaptured = true;
				moveNum++;

			} else if (!canMove && clickNum%2 != 0) { //second click, however no valid move was selected; user wants to reset their choice of piece to be moved

				canMove = false; //reset to default
				pieceSelected = false; //reset to default
				clickNum--;

			} else if (QuickChess.chessBoard[yPos/88][xPos/106] == QuickChess.chessBoard[prevY][prevX]) { //if same spot is clicked; user wants to reset their choice of piece to be moved

				canMove = false; //reset to default
				pieceSelected = false; //reset to default
				clickNum--;
			}


			if (QuickChess.chessBoard[yPos/88][xPos/106] instanceof Pawn && QuickChess.chessBoard[yPos/88][xPos/106].getSide() == 1 && coordinate[selectedIndex].getY()/88 == 0) { //if a white piece reaches the top

				promotedPiece = Pawn.getPromotion(1); //call it with 1 to look through the captured white pieces

				if (promotedPiece != null) { //a piece can be promoted

					QuickChess.chessBoard[yPos/88][xPos/106] = promotedPiece; //change the pawn to its promoted piece on the chess board

					coordinate[promotedPiece.getIndex()].setX(xPos/106 * 106 ); //change the Y position of the promoted piece to replace the pawn
					coordinate[promotedPiece.getIndex()].setY(yPos/88 * 88); //change the X position of the promoted piece to replace the pawn

					coordinate[selectedIndex].setX(636 + selectedIndex*106); //remove pawn from the visible chess board
					coordinate[selectedIndex].setY(0);

					QuickChess.capturedPieces.remove(QuickChess.capturedPieces.indexOf(promotedPiece)); //remove the promoted piece from captured pieces arrayList
					QuickChess.captured[promotedPiece.getIndex()] = null; //remove the promoted piece from the captured pieces array

					pawnPromoted = true; //change boolean storing whether a pawn has been promoted to true

				} 

			}


			if (!pawnPromoted && !pieceCaptured && clickNum%2 != 0) { //if no pawn was promoted or no piece was captured

				actionlessMoves++;

			} else {

				actionlessMoves = 0; //restart count until end of game

			}

			pieceCaptured = false; //reset for next move
			pawnPromoted = false; //reset for next move
			repaint(1000); //repaint jPanel

		} //end of SECOND click functions


		//FIRST CLICK---
		//On the user's first click, a piece should be selected to be moved

		//determines which piece was selected, and sets the selected box to the selected piece
		if (QuickChess.chessBoard[yPos/88][xPos/106] != null  && !pieceSelected && clickNum%2 == 0) { //if there is a piece at the selected grid

			if (moveNum%2 != QuickChess.chessBoard[yPos/88][xPos/106].getSide()) {

				prevX = xPos/106; //stores the index for the column in the chess board to be used for the next click- moving this piece selected
				prevY = yPos/88; //stores the index for the row in the chess board

				//sets the selected rectangle around the piece selected
				for (int i = 0; i < 19; i++) {

					if (pieceRect[i].contains(e.getPoint())) {
						selectedBox = pieceRect[i];
					}

				}

				currentPiece = QuickChess.chessBoard[yPos/88][xPos/106];//find the current piece, by seeing which piece is on the position clicked in the chess board
				selectedIndex = QuickChess.chessBoard[yPos/88][xPos/106].getIndex(); //finds the index of the selected piece
				pieceSelected = true; //indicates that a piece has been selected

			} else { //if a piece of the opposite side was selected

				pieceSelected = false; //set to default
				clickNum--; //subtract one from click number, to repeat this process again for user's next click

			}


		} else if (QuickChess.chessBoard[yPos/88][xPos/106] == null && !pieceSelected && clickNum%2 == 0){ //if no piece was clicked on
			pieceSelected = false; //set to default
			clickNum--; //ensures this process is repeated until a valid piece has been selected to be moved
		} //end of FIRST click functions

		clickNum++; //increases the variable counting the number of clicks by 1; tracked by the number of times this listener was called

	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {}
	

} //end of ChessBoard class