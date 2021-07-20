import Cell from "../models/Cell";

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
}