import Cell from '../models/Cell';
import GameBoard from './GameBoard';
import AssertionHelper from '../helpers/AssertionHelper';
import { newTable } from '../helpers/TableHelper';
import { Players } from '../enums/Players';

const assertSetBoard = (game, board) => {
  game.setBoard(board);
  AssertionHelper.assertArraysEqual_2d(game.board, board);
}

const assertThrows = (game, board, message) =>
  expect(() => game.setBoard(board)).toThrowError(message);

test('Set board accepts 3x3 table', () =>
  assertSetBoard(new GameBoard(), newTable(3)))

test('Set board accepts 2x2 table', () =>
  assertSetBoard(new GameBoard(), newTable(2)))

test('Set Board accepts 1x1 table', () => 
  assertSetBoard(new GameBoard(), newTable(1)))

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
    [[Players.None, Players.None, Players.None],
      [Players.None, Players.None],
      [Players.None, Players.None, Players.None]],
    "Game board must have equally sized rows and columns."))

test('Set Board throws when the length of columns differ', () =>
  assertThrows(
    new GameBoard(),
    [[Players.None, Players.None, Players.None],
      [Players.None, Players.None, Players.None]],
    "Game board must have equally sized rows and columns."))

test('Set Board throws when a cell is not a number, null, or undefined', () => 
  assertThrows(
    new GameBoard(),
    [[Players.None, []],
    [Players.None, Players.None]],
    "Game board must only contain numeric values."))

test('Set Board initializes missing values with empty tokens', () => {
  var game = new GameBoard();
  game.setBoard([
    [null, null, undefined],
    [null, undefined, null],
    [undefined, null, null]
  ]);

  AssertionHelper.assertArraysEqual_2d(newTable(3), game.board);
})

test('Set Board does not hold references to the original object', () => {
  var game = new GameBoard();
  var table = newTable()
  table[0][0] = Players.Player1;

  game.setBoard(table);
  table[0][0] = Players.None;
  expect(game.getToken(new Cell(0, 0))).toBe(Players.Player1);
})
