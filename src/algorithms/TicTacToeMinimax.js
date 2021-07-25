import { GameResult } from "../enums/GameResult";
import { Players } from "../enums/Players";
import GameBoard from "../game/GameBoard";
import { copyArray_2d } from '../helpers/ArrayHelper';
import Cell from "../models/Cell";

const min = (left, right) =>
  left > right ? right : left;
const max = (left, right) =>
  left > right ? left : right;

export default class TicTacToeMinimax {

  constructor(maximizingToken, minimizingToken) {
    this.maximizingToken = maximizingToken;
    this.minimizingToken = minimizingToken;
  }

  getChildren(table) {
    var cells = [];
    for (var r = 0; r < table.length; r++) 
      for (var c = 0; c < table.length; c++) 
        if (table[r][c] === Players.None) 
          cells.push(new Cell(r, c));
    return cells;
  }

  evaluatePosition(table) {
    var game = new GameBoard();
    game.setBoard(table);

    var result = game.getGameResult();
    if (result === GameResult.Win)
      return 1;
    else if (result === GameResult.Draw)
      return 0;
    else
      return -1;
  }

  isGameOver(table) {
    var game = new GameBoard();
    game.setBoard(table);
    return game.getGameResult() !== GameResult.None;
  }

  getNextBestCell = table => {
    var maxEval = Number.NEGATIVE_INFINITY;
    var bestCell = null;

    this.getChildren(table).forEach(cell => {
      var evaluation = this.minimax(table, cell, Number.NEGATIVE_INFINITY, Number.POSITIVE_INFINITY, true);
      if (evaluation > maxEval) {
        maxEval = evaluation;
        bestCell = cell;
      }
    })

    return bestCell;
  }

  minimax(table, cell, alpha, beta, maximizingPlayer) {
    var cloneTable = copyArray_2d(table);
    cloneTable[cell.row][cell.col] = maximizingPlayer 
      ? this.maximizingToken 
      : this.minimizingToken;

    if (this.isGameOver(cloneTable))
      return this.evaluatePosition(cloneTable);
    
    var children = this.getChildren(cloneTable);
    
    if (maximizingPlayer) {
      var maxEval = Number.POSITIVE_INFINITY;
      
      children.forEach(child => {
        var minimax = this.minimax(cloneTable, child, alpha, beta, false);
        maxEval = min(maxEval, minimax);
        beta = min(beta, minimax);
        if (beta <= alpha) return maxEval;
      })

      return maxEval;
    } else {
      var minEval = Number.NEGATIVE_INFINITY;

      children.forEach(child => {
        var minimax = this.minimax(cloneTable, child, alpha, beta, true);
        minEval = max(minEval, minimax);
        alpha = max(alpha, minimax)
        if (beta <= alpha) return maxEval;
      })

      return minEval
    }
  }
}