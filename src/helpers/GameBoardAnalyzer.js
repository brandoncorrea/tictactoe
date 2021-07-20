import Cell from "../models/Cell";

const cloneArray = (orig) =>
  Array.from(orig, i => i);

export default class GameBoardAnalyzer {

  // Returns the next best cell to play
  getOptimalCell(game, token, opponentToken) {
    if (game.isOver())
      return null;

    var winningPaths = this
      .getAllPaths(game, token, opponentToken)
      .filter(i => i.length === 1);

    if (winningPaths.length)
      return winningPaths[0][0];

    return null;
  }

  // Returns all possible paths given a table
  getAllPaths(game, playerToken, opponentToken, currentPath) {
    currentPath = currentPath || [];
    var paths = [];
    
    if (game.isOver())
      return [currentPath];
    
    var size = game.getSize();
    for (var r = 0; r < size; r++)
      for (var c = 0; c < size; c++) 
        paths = paths.concat(
          this.getRemainingPathsFromCell(
            game.clone(), 
            playerToken, 
            opponentToken,
            cloneArray(currentPath), 
            new Cell(r, c)));

    return paths;
  }

  // Places a token in a cell and gets all possible paths after that move
  getRemainingPathsFromCell(game, playerToken, opponentToken, currentPath, cell) {
    if (game.getToken(cell))
      return [];

    game.placeToken(playerToken, cell);
    currentPath.push(cell);
    return this.getAllPaths(
        game,
        opponentToken,
        playerToken,
        currentPath);
  }
}