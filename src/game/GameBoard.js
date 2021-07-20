export default class GameBoard {
  placeToken = (token, cell) =>
    this.board[cell.row][cell.col] = token;

  getToken = cell => 
    this.board[cell.row][cell.col];

  getSize = () => this.board.length;

  setBoard = board => {
    if (!board)
      throw "Game board must have a value."
    if (board.length < 1)
      throw "Game board cannot be empty.";

    // Remove object reference on board
    var newBoard = Array.from(board, x => Array.from(x));

    // Validate new board
    for (var r = 0; r < newBoard.length; r++) {
      if (newBoard[r].length !== newBoard.length)
        throw "Game board must have equally sized rows and columns.";

      // Initialize missing values with an empty token
      for (var c = 0; c < newBoard[r].length; c++) {
        if (!newBoard[r][c])
          newBoard[r][c] = '';
        // Accept only string values
        else if (typeof newBoard[r][c] !== "string")
          throw "Game board must only contain string values.";
      }
    }
    
    this.board = newBoard;
  }

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