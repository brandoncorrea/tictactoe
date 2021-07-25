import { GameResult } from '../enums/GameResult';
import { Players } from '../enums/Players';
import { copyArray_2d } from '../helpers/ArrayHelper';
import { hasCol, hasRow, hasBottomLeftDiagonal, hasTopLeftDiagonal } from '../helpers/TableHelper';

export default class GameBoard {
  movePlayer1 = cell => 
    this.board[cell.row][cell.col] = Players.Player1;
  movePlayer2 = cell => 
    this.board[cell.row][cell.col] = Players.Player2;

  // Returns the token from a given cell
  getToken = cell => 
    this.board[cell.row][cell.col];

  // Returns the game board table
  getTable = () => copyArray_2d(this.board);

  // Creates a clone of the GameBoard object
  clone = () => {
    var clone = new GameBoard();
    clone.setBoard(this.board);
    return clone;
  }

  // True if the token has a winning set of cells
  playerWon(player) {
    for (var i = 0; i < this.board.length; i++)
      if (hasRow(player, this.board, i) || 
        hasCol(player, this.board, i))
        return true;

    return hasTopLeftDiagonal(player, this.board) || 
      hasBottomLeftDiagonal(player, this.board);
  }

  // True if the board has any empty cells
  hasEmptyCells = () =>
    this.board.some(row => row.some(token => token === Players.None));
  
  // Returns the game result for the given token
  getGameResult = () => {
    if (this.playerWon(Players.Player1))
      return GameResult.Win;
    else if (this.playerWon(Players.Player2))
      return GameResult.Loss;
    else if (this.hasEmptyCells())
      return GameResult.None;
    else
      return GameResult.Draw;
  }
  
  // Assigns a table to the board property
  setBoard = board => {
    if (!board)
      throw new Error("Game board must have a value.");
    if (board.length < 1)
      throw new Error("Game board cannot be empty.");

    // Remove object reference on board
    var newBoard = copyArray_2d(board);

    // Validate new board
    for (var r = 0; r < newBoard.length; r++) {
      if (newBoard[r].length !== newBoard.length)
        throw new Error("Game board must have equally sized rows and columns.");

      // Initialize missing values with an empty token
      for (var c = 0; c < newBoard[r].length; c++) {
        if (!newBoard[r][c])
          newBoard[r][c] = Players.None;
        // Accept only string values
        else if (typeof newBoard[r][c] !== "number")
          throw new Error("Game board must only contain numeric values.");
      }
    }
    
    this.board = newBoard;
  }

  // Initializes the board with a size
  constructor(size, playerToken1, playerToken2) {
    this.playerToken1 = playerToken1;
    this.playerToken2 = playerToken2;

    if (size === 0)
      throw new Error("Size cannot be less than 1.");
    if (!size)
      size = 3;
    if (isNaN(size))
      throw new Error("Size must be numeric.");

    // Create 2D array based on the given size
    this.board = [];
    for (var r = 0; r < size; r++) {
      var row = [];
      for (var c = 0; c < size; c++)
        row.push(Players.None);
      this.board.push(row);
    }
  }
}