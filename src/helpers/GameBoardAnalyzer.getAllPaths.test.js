import { GameResult } from '../enums/GameResult';
import GameBoard from '../game/GameBoard';
import Cell from '../models/Cell';
import GameBoardAnalyzer from './GameBoardAnalyzer';

// Returns true if cell1 and cell2 have the same property values
const cellsMatch = (cell1, cell2) =>
  cell1.row === cell2.row &&
  cell1.col === cell2.col;

// Returns true if path1 and path2 have the same cell values
const pathsMatch = (path1, path2) => {
  // Compare length of the paths
  if (path1.length !== path2.length)
    return false;

  // Compare values of each cell
  for (var i = 0; i < path1.length; i++)
    if (!cellsMatch(path1[i], path2[i]))
      return false;

  return true;
}

// Expects the expectedPaths to be returned from the GameBoardAnalyzer with a given board.
const assertPathsReturned = (board, expectedPaths) => {
  var game = new GameBoard();
  var analyzer = new GameBoardAnalyzer();
  game.setBoard(board);
  var cellPaths = analyzer.getAllPaths(game, 'X', 'O');

  expect(cellPaths.length).toBe(expectedPaths.length);
  expectedPaths.forEach(path1 =>
    expect(
      cellPaths.some(cellPath => pathsMatch(path1, cellPath.path))
    ).toBeTruthy());
    
  cellPaths.forEach(cellPath =>
    expect(cellPath.gameResult).not.toBe(GameResult.None))
}

test('Returns one path of one cell when there is one cell left for a draw', () =>
  assertPathsReturned(
    [['X', 'O', 'X'],
      ['', 'O', 'O'],
      ['O', 'X', 'X']],
    [
      [new Cell(1, 0)]
    ]))

test('Returns one path of one cell when there is one cell left for a win', () => 
  assertPathsReturned(
    [['X', 'O', 'X'],
      ['O', 'O', ''],
      ['O', 'X', 'X']],
    [
      [new Cell(1, 2)]
    ]))

test('Returns paths with board of two empty cells', () => 
  assertPathsReturned(
    [['X', 'X', 'O'],
      ['O', 'O', ''],
      ['', 'O', 'X']],
    [
      [new Cell(1, 2)],
      [new Cell(2, 0)]
    ]))

test('Returns paths with board of three empty cells', () => {
  var A = new Cell(1, 0);
  var B = new Cell(1, 2);
  var C = new Cell(2, 0);

  assertPathsReturned(
    [['X', 'X', 'O'],
    ['', 'O', ''],
    ['', 'O', 'X']],
    [
      [A],
      [A, C],
      [B],
      [B, C],
      [C, A],
      [C, B],
    ])
})

test('Returns paths with board of four empty cells', () =>  {

  var A = new Cell(0, 2);
  var B = new Cell(1, 0);
  var C = new Cell(1, 2);
  var D = new Cell(2, 0);

  assertPathsReturned(
    [['O', 'O', ''],
    ['', 'X', ''],
    ['', 'X', 'O']],
    [
      // Start on A
      [A, C],
      [A, D],
      [A, B],
      [A, D],
      [A, B],
      [A, C],
      
      // Start on B
      [B],
      [B, A],
      [B, D],
      [B, A],
      [B, C],
      
      // Start on C
      [C],
      [C, A],
      [C, D],
      [C, A],
      [C, B],
      
      // Start on D
      [D],
      [D, A],
      [D, C],
      [D, A],
      [D, B]
    ])
})
