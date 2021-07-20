// Clones a 2d array
const copyArray_2d = arr =>
  Array.from(arr, i => Array.from(i));


// True if the token has the row index
const hasRow = (token, table, row) => {
  for (var col = 0; col < table.length; col++)
    if (table[row][col] !== token)
      return false;
  return true;
}

// True if the token has the specified column index
const hasCol = (token, table, col) => {
  for (var row = 0; row < table.length; row++)
    if (table[row][col] !== token)
      return false;
  return true;
}

// True if the token has the top-left diagonal
const hasTopLeftDiagonal = (token, table) => {
  for (var i = 0; i < table.length; i++)
    if (table[i][i] !== token)
      return false;
  return true;
}

// True if the token has the bottom-left diagonal
const hasBottomLeftDiagonal = (token, table) => {
  var r = 0;
  var c = table.length - 1;

  for ( ; r < table.length && c >= 0; r++, c--)
    if (table[r][c] !== token)
      return false;

  return true;
}

export default class GameBoard {
  // Updates the cell with the token
  placeToken = (token, cell) =>
    this.board[cell.row][cell.col] = token;

  // Returns the token from a given cell
  getToken = cell => 
    this.board[cell.row][cell.col];

  // Returns the size of the game board
  getSize = () => this.board.length;
  
  // Returns the game board table
  getTable = () => copyArray_2d(this.board);

  // Creates a clone of the GameBoard object
  clone = () => {
    var clone = new GameBoard();
    clone.setBoard(this.board);
    return clone;
  }

  // True if the token has a winning set of cells
  tokenWon(token) {
    for (var i = 0; i < this.board.length; i++)
      if (hasRow(token, this.board, i) || 
        hasCol(token, this.board, i))
        return true;

    return hasTopLeftDiagonal(token, this.board) || 
      hasBottomLeftDiagonal(token, this.board);
  }

  // Assigns a table to the board property
  setBoard = board => {
    if (!board)
      throw "Game board must have a value."
    if (board.length < 1)
      throw "Game board cannot be empty.";

    // Remove object reference on board
    var newBoard = copyArray_2d(board);

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

  // Initializes the board with a size
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