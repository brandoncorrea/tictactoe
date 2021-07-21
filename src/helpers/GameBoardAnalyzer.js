import { GameResult } from "../enums/GameResult";
import Cell from "../models/Cell";
import CellPath from "../models/CellPath";

export default class GameBoardAnalyzer {

  // Returns the next best cell to play
  getOptimalCell(game, token, opponentToken) {
    if (game.getGameResult(token) !== GameResult.None)
      return null;

    var winningPaths = this
      .getAllPaths(game, token, opponentToken)
      .filter(i => i.path.length === 1);

    if (winningPaths.length)
      return winningPaths[winningPaths.length - 1].path[0];

    return null;
  }

  // Returns all possible paths given a table
  getAllPaths(game, playerToken, opponentToken, currentPath) {
    currentPath = currentPath || new CellPath(playerToken);
    var paths = [];
    
    currentPath.gameResult = game.getGameResult(currentPath.token);
    if (currentPath.gameResult !== GameResult.None)
      return [currentPath];
    
    var size = game.getSize();
    for (var r = 0; r < size; r++)
      for (var c = 0; c < size; c++) 
        paths = paths.concat(
          this.getRemainingPathsFromCell(
            game.clone(), 
            playerToken, 
            opponentToken,
            currentPath.clone(), 
            new Cell(r, c)));

    return paths;
  }

  // Places a token in a cell and gets all possible paths after that move
  getRemainingPathsFromCell(game, playerToken, opponentToken, currentPath, cell) {
    if (game.getToken(cell))
      return [];

    game.placeToken(playerToken, cell);
    if (currentPath.token === playerToken)
      currentPath.path.push(cell);
      
    return this.getAllPaths(
      game,
      opponentToken,
      playerToken,
      currentPath);
  }
}