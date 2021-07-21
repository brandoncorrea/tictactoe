import GameBoard from '../game/GameBoard';
import Cell from '../models/Cell';
import GameBoardAnalyzer from './GameBoardAnalyzer';

const assertReturnsNull = (board) => {
  var game = new GameBoard();
  var analyzer = new GameBoardAnalyzer();
  game.setBoard(board);
  var cell = analyzer.getOptimalCell(game, 'X', 'O');
  expect(cell).toBe(null);
}

const assertReturnsCell = (board, expected) => {
  var game = new GameBoard();
  var analyzer = new GameBoardAnalyzer();
  game.setBoard(board);
  var cell = analyzer.getOptimalCell(game, 'X', 'O');
  //expect(cell).toBe(expected);
  expect(cell.row).toBe(expected.row);
  expect(cell.col).toBe(expected.col);
}

test('Returns null if board is full', () =>
  assertReturnsNull([
    ['X', 'O', 'X'],
    ['X', 'O', 'O'],
    ['O', 'X', 'X']]))

test('Returns null if player won', () => 
  assertReturnsNull([
    ['X', 'X', 'X'],
    ['O', 'O', ''],
    ['', '', '']]))

test('Returns null if opponent won', () => 
  assertReturnsNull([
    ['X', 'X', ''],
    ['O', 'O', 'O'],
    ['', '', '']]))

test('Returns cell when only one cell is empty', () => 
  assertReturnsCell([
    ['X', 'O', 'X'],
    ['', 'O', 'O'],
    ['O', 'X', 'X']
  ],
  new Cell(1, 0)))

test('Returns the winning cell when two cells remain', () =>
  assertReturnsCell([
    ['O', 'O', 'X'],
    ['O', 'O', 'X'],
    ['X', '', '']
  ],
  new Cell(2, 2)))

test('Blocks opponent if game cannot be won on current move', () => 
  assertReturnsCell([
    ['O', 'X', ''],
    ['O', '', 'X'],
    ['', '', '']
  ],
  new Cell(2, 0)))