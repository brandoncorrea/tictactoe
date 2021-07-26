import { GameResult } from '../enums/GameResult';
import { Players } from '../enums/Players';
import { copyArray_2d } from '../helpers/ArrayHelper';
import { hasCol, hasRow, hasBottomLeftDiagonal, hasTopLeftDiagonal, newTable } from '../helpers/TableHelper';

// Validate cell is empty
const verifyCellEmpty = (board, cell) => {
  if (board[cell.row][cell.col] !== Players.None)
      throw Error("Cannot move to an occupied cell.");
}

// Validate new board lengths and types
const verifyBoard = board => {
  if (!board)
    throw new Error("Game board must have a value.");
  if (board.length < 1)
    throw new Error("Game board cannot be empty.");
  
  for (var r = 0; r < board.length; r++) {
    if (board[r].length !== board.length)
      throw new Error("Game board must have equally sized rows and columns.");
    for (var c = 0; c < board[r].length; c++)
      if (board[r][c] && typeof board[r][c] !== "number")
        throw new Error("Game board must only contain numeric values.");
  }
}

const verifySize = size => {
  if (size < 1)
    throw new Error("Size cannot be less than 1.");
  if (isNaN(size))
    throw new Error("Size must be numeric.");
}

export default class GameBoard {
  movePlayer1 = cell => {
    verifyCellEmpty(this.board, cell);
    this.board[cell.row][cell.col] = Players.Player1;
  }
  movePlayer2 = cell => {
    verifyCellEmpty(this.board, cell);
    this.board[cell.row][cell.col] = Players.Player2;
  }

  reset(size) {
    if (size === null || size === undefined)
      size = this.board.length;
    verifySize(size);
    this.board = [];
    for (var r = 0; r < size; r++) {
      var row = [];
      for (var c = 0; c < size; c++)
        row.push(Players.None);
      this.board.push(row);
    }
  }

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
    verifyBoard(board);
    this.board = copyArray_2d(board);
    
    // Set missing values
    for (var r = 0; r < this.board.length; r++)
      for (var c = 0; c < this.board[r].length; c++)
        if (!this.board[r][c])
          this.board[r][c] = Players.None;
  }

  // Initializes the board with a size
  constructor(size) {
    if (size === undefined 
      || size === null)
      size = 3;
    verifySize(size);

    // Create 2D array based on the given size
    this.board = newTable(size);
  }
}