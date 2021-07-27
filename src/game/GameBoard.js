import { GameResult } from '../enums/GameResult';
import { Players } from '../enums/Players';
import { 
  hasCol, 
  hasRow, 
  hasBottomLeftDiagonal, 
  hasTopLeftDiagonal, 
  newTable,
  isTableFull
} from '../helpers/TableHelper';

// Validate cell is empty
const verifyCellEmpty = (board, cell) => {
  if (board[cell.row][cell.col] !== Players.None)
    throw Error("Cannot move to an occupied cell.");
}

const verifySize = size => {
  if (size < 1)
    throw new Error("Size cannot be less than 1.");
  if (isNaN(size))
    throw new Error("Size must be numeric.");
}

export default class GameBoard {
  // Places the player 1 flag in the cell
  movePlayer1 = cell => {
    verifyCellEmpty(this.board, cell);
    this.board[cell.row][cell.col] = Players.Player1;
  }

  // Places the player 2 flag in the cell
  movePlayer2 = cell => {
    verifyCellEmpty(this.board, cell);
    this.board[cell.row][cell.col] = Players.Player2;
  }

  // Resets the game board
  reset(size) {
    if (size === null || size === undefined)
      size = this.board.length;
    verifySize(size);

    while (this.board.length)
      this.board.pop();
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

  // True if the token has a winning set of cells
  playerWon(player) {
    for (var i = 0; i < this.board.length; i++)
      if (hasRow(player, this.board, i) || 
        hasCol(player, this.board, i))
        return true;

    return hasTopLeftDiagonal(player, this.board) || 
      hasBottomLeftDiagonal(player, this.board);
  }

  // Returns the game result for the given token
  getGameResult = () => 
    this.playerWon(Players.Player1) ? GameResult.Win
    : this.playerWon(Players.Player2) ? GameResult.Loss
    : !isTableFull(this.board) ? GameResult.None
    : GameResult.Draw;

  // Initializes the board with a size
  constructor(size) {
    if (size === undefined || size === null)
      size = 3;
    verifySize(size);
    this.board = newTable(size);
  }
}