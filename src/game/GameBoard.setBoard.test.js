import Cell from '../models/Cell';
import GameBoard from './GameBoard';

const assertSetBoard = (game, board) => {
  game.setBoard(board);

  // Number of rows should be equal
  expect(game.board.length).toBe(board.length);

  for (var r = 0; r < board.length; r++) {
    // length of each row should be equal
    expect(game.board[r].length).toBe(board[r].length);

    // All cells should be equal
    for (var c = 0; c < board[r].length; c++)
      expect(game.getToken(new Cell(r, c))).toBe(board[r][c])
  }
}

const assertThrows = (game, board, message) =>
  expect(() => game.setBoard(board)).toThrowError(message);

test('Set board accepts 3x3 table', () =>
  assertSetBoard(
    new GameBoard(), 
    [['', '', ''],
    ['', '', ''],
    ['', '', '']]))

test('Set board accepts 2x2 table', () =>
  assertSetBoard(
    new GameBoard(), 
    [['', ''],
    ['', '']]))

test('Set Board accepts 1x1 table', () => 
  assertSetBoard(new GameBoard(), [['']]))

test('Set Board throws on empty table', () =>
  assertThrows(
    new GameBoard(),
    [],
    "Game board cannot be empty."))

test('Set Board throws on null table', () =>
  assertThrows(
    new GameBoard(),
    null,
    "Game board must have a value."))

test('Set Board throws on undefined table', () =>
  assertThrows(
    new GameBoard(),
    undefined,
    "Game board must have a value."))

test('Set Board throws when the length of rows differ', () =>
  assertThrows(
    new GameBoard(),
    [['', '', ''],
      ['', ''],
      ['', '', '']],
    "Game board must have equally sized rows and columns."))

test('Set Board throws when the length of columns differ', () =>
  assertThrows(
    new GameBoard(),
    [['', '', ''],
      ['', '', '']],
    "Game board must have equally sized rows and columns."))

test('Set Board throws when a cell is not a string, null, or undefined', () => 
  assertThrows(
    new GameBoard(),
    [['', []],
    ['', '']],
    "Game board must only contain string values."))

test('Set Board initializes missing values with empty tokens', () => {
  var game = new GameBoard();
  var board = [
    [null, null, undefined],
    [null, undefined, null],
    [undefined, null, null]
  ]

  game.setBoard(board);

  for (var r = 0; r < board.length; r++)
    for (var c = 0; c < board[r].length; c++)
      expect(game.getToken(new Cell(r, c))).toBe('');
})

test('Set Board does not hold references to the original object', () => {
  var game = new GameBoard();
  var board = [
    ['X', '', ''],
    ['', '', ''],
    ['', '', '']
  ]

  game.setBoard(board);
  board[0][0] = '';
  expect(game.getToken(new Cell(0, 0))).toBe('X');
})
