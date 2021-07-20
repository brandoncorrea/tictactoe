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
      [new Cell(1, 2), new Cell(2, 0)],
      [new Cell(2, 0), new Cell(1, 2)]
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
      [A, C],
      [A, B, C],
      [B, C],
      [B, A, C],
      [C, B, A],
      [C, A, B],
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
      [A, B, C, D],
      [A, B, D],
      [A, C, B, D],
      [A, C, D],
      [A, D, B, C],
      [A, D, C, B],
      
      // Start on B
      [B, A],
      [B, C, A, D],
      [B, C, D, A],
      [B, D, A, C],
      [B, D, C],
      
      // Start on C
      [C, A],
      [C, B, A, D],
      [C, B, D, A],
      [C, D, A, B],
      [C, D, B],
      
      // Start on D
      [D, A],
      [D, B, A],
      [D, B, C, A],
      [D, C, A],
      [D, C, B, A]
    ])
})
