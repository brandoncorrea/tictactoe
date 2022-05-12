import { Players } from "../enums/Players";
import { 
  hasBottomLeftDiagonal, 
  hasCol, 
  hasRow, 
  hasTopLeftDiagonal, 
  isTableEmpty, 
  isTableFull,
  enumerateEmptyCells } from "../helpers/TableHelper";
import Cell from "../models/Cell";

const MAX_EVALUATION = 1;
const MIN_EVALUATION = -1;

export default class TicTacToeMinimax {

  constructor(maximizingToken, minimizingToken) {
    this.maximizingToken = maximizingToken;
    this.minimizingToken = minimizingToken;
  }

  // Win = 1, Draw = 0, Loss = -1, Game Not Over = undefined
  evaluatePosition(table) {
    // Check rows and columns
    for (var i = 0; i < table.length; i++) {
      // If the cell is empty, then neither player owns the row or column
      if (table[i][i] === Players.None)
        continue;
      if (hasRow(this.minimizingToken, table, i) || 
          hasCol(this.minimizingToken, table, i))
        return MIN_EVALUATION;
      if (hasRow(this.maximizingToken, table, i) || 
          hasCol(this.maximizingToken, table, i))
        return MAX_EVALUATION;
    }

    // Check diagonals
    if (hasTopLeftDiagonal(this.minimizingToken, table) ||
        hasBottomLeftDiagonal(this.minimizingToken, table))
      return MIN_EVALUATION;
    if (hasTopLeftDiagonal(this.maximizingToken, table) ||
        hasBottomLeftDiagonal(this.maximizingToken, table))
      return MAX_EVALUATION;
    
    if (isTableFull(table))
      return 0; // Draw
  
    // Game is incomplete
    return undefined;
  }

  // Returns the next best cell to play for the maximizing player given a table
  getNextBestCell = table => {
    // When board is empty, don't search the board - always return a corner
    if (isTableEmpty(table))
      return new Cell(0, 0);

    var maxEval = MIN_EVALUATION;
    var children = enumerateEmptyCells(table);
    var next = children.next();
    var bestCell = next.value;

    while(!next.done) {
      var evaluation = this.minimax(table, next.value, maxEval, MAX_EVALUATION, true);

      // This move will win - Return the cell
      if (evaluation === MAX_EVALUATION) 
        return next.value;
      if (evaluation > maxEval) {
        maxEval = evaluation;
        bestCell = next.value;
      }
      
      next = children.next();
    }

    return bestCell;
  }

  // Evaluates the numeric value of a given play
  minimax(table, cell, alpha, beta, maximizingPlayer) {
    table[cell.row][cell.col] = maximizingPlayer 
      ? this.maximizingToken 
      : this.minimizingToken;
    var minimax = this.recurse(table, alpha, beta, maximizingPlayer);
    table[cell.row][cell.col] = Players.None;
    return minimax;
  }

  // Returns the greatest value of all the next plays in a given table
  recurse(table, alpha, beta, maximizingPlayer) {
    // If the game is over, return the evaluation of the current position
    var evaluation = this.evaluatePosition(table);
    if (evaluation !== undefined)
      return evaluation;

    // Iterators
    var children = enumerateEmptyCells(table);
    var next = children.next();

    if (maximizingPlayer) {
      // Find min evaluation for each play
      while(!next.done) {
        var minEval = this.minimax(table, next.value, alpha, beta, false);
        if (minEval < beta)
          beta = minEval;

        // Exit if caller has a better value
        if (beta <= alpha) break;
        next = children.next();
      }
      
      return beta
    } else {
      // Find max evaluation for each play
      while(!next.done) {
        var maxEval = this.minimax(table, next.value, alpha, beta, true);
        if (maxEval > alpha)
          alpha = maxEval;

        // Exit if caller has a better value
        if (beta <= alpha) break;
        next = children.next();
      }

      return alpha
    }
  }
}
