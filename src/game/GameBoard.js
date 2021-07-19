export default class GameBoard {
  placeToken = (token, cell) =>
    this.board[cell.row][cell.col] = token;

  getToken = cell => 
    this.board[cell.row][cell.col];

  getSize = () => this.board.length;

  constructor(size) {
    if (size === 0)
      throw "Size cannot be less than 1.";
    if (!size)
      size = 3;
    if (isNaN(size))
      throw "Size must be numeric.";

    // Create 2D array based on the given size
    this.board = [];
    for (var r = 0; r < size; r++) {
      var row = [];
      for (var c = 0; c < size; c++)
        row.push('');
      this.board.push(row);
    }
  }
}