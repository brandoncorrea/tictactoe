import { GameResult } from "../enums/GameResult";
import Cell from "../models/Cell";
import CellPath from "../models/CellPath";

// Groups a collection by a specified key
const groupBy = (collection, keySelector, keyComparer) => {
  var groups = [];
  collection.forEach(value => {
    var key = keySelector(value);
    var groupIndex = groups.findIndex(
      group => keyComparer(group.key, key));
    
    if (groupIndex < 0)
      groups.push({ key: key, values: [value] });
    else
      groups[groupIndex].values.push(value);
  })
  return groups;
}

// Returns the number of times a predicate is satisfied in a collection
const count = (collection, predicate) =>
  collection.filter(predicate).length;

// Returns the item in the collection where the selector returns the greatest value
const max = (collection, selector) => {
  var maxValue = null;
  var maxItem = null;

  collection.forEach(item => {
    var value = selector(item);
    if(maxValue && maxValue >= value) 
      return;
    maxValue = value;
    maxItem = item;
  });
  return maxItem;
}

const cellsEqual = (cell1, cell2) =>
  cell1.row === cell2.row &&
  cell1.col === cell2.col;

export default class GameBoardAnalyzer {

  // Returns the next best cell to play
  getOptimalCell(game, token, opponentToken) {
    // Check if game is over
    if (game.getGameResult(token) !== GameResult.None)
      return null;

    var bestGroup = max(
      groupBy(
        this.getAllPaths(game, token, opponentToken),
        path => path.path[0],
        cellsEqual),
      group => count(
        group.values,
        path => path.gameResult === GameResult.Win))

    return bestGroup && bestGroup.key || null;
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