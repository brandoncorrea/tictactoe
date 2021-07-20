import Cell from "../models/Cell";

// True if the token has the row index
const hasRow = (token, game, row) => {
  var size = game.getSize()
  for (var col = 0; col < size; col++)
    if (game.getToken(new Cell(row, col)) !== token)
      return false;
  return true;
}

// True if the token has the specified column index
const hasCol = (token, game, col) => {
  var size = game.getSize()
  for (var row = 0; row < size; row++)
    if (game.getToken(new Cell(row, col)) !== token)
      return false;
  return true;
}

// True if the token has the top-left diagonal
const hasTopLeftDiagonal = (token, game) => {
  var size = game.getSize();
  for (var i = 0; i < size; i++)
    if (game.getToken(new Cell(i, i)) !== token)
      return false;
  return true;
}

// True if the token has the bottom-left diagonal
const hasBottomLeftDiagonal = (token, game) => {
  var size = game.getSize();
  for (var r = 0, c = size - 1; r < size && c >= 0; r++, c--)
    if (game.getToken(new Cell(r, c)) !== token)
      return false;
  return true;
}

export default class GameBoardAnalyzer {

  // Returns all possible paths given a table
  getAllPaths(game) {
    var paths = [];
    var size = game.getSize();

    // Adds a single cell array to paths for all empty cells
    for (var r = 0; r < size; r++) {
      for (var c = 0; c < size; c++) {
        var cell = new Cell(r, c);
        if (game.getToken(cell) === '')
          paths.push([cell]);
      }
    }

    return paths;
  }

  // True if the token has a winning set of cells
  tokenWon(token, game) {
    var size = game.getSize();
    for (var i = 0; i < size; i++)
      if (hasRow(token, game, i) || hasCol(token, game, i))
        return true;

    return hasTopLeftDiagonal(token, game) || hasBottomLeftDiagonal(token, game);
  }
}