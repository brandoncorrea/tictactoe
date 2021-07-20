import Cell from "../models/Cell";

const cloneArray = (orig) =>
  Array.from(orig, i => i);

export default class GameBoardAnalyzer {

  // Returns all possible paths given a table
  getAllPaths(game, playerToken, opponentToken, currentPath) {
    if (!currentPath)
      currentPath = [];
    var paths = [];
    var size = game.getSize();

    if (game.isOver())
      return [currentPath];

    for (var r = 0; r < size; r++) {
      for (var c = 0; c < size; c++) {
        var cell = new Cell(r, c);
        if (!game.getToken(cell)) {
          var clone = game.clone();
          clone.placeToken(playerToken, cell);
          currentPath.push(cell);

          paths = paths.concat(
            this.getAllPaths(
              clone,
              opponentToken,
              playerToken, 
              cloneArray(currentPath)));

          currentPath.pop();
        }
      }
    }

    return paths;
  }
}