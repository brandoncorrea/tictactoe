import Cell from '../models/Cell';
import GameBoard from './GameBoard';

const assertGetSize = (board, expectedSize) => {
  expect(board.getSize()).toBe(expectedSize);
  expect(board.board.length).toBe(expectedSize);

  for (var r = 0; r < expectedSize; r++) {
    // Each row should have length = expectedSize
    expect(board.board[r].length).toBe(expectedSize);

    // Each cell should have an empty token
    for (var c = 0; c < expectedSize; c++)
     expect(board.getToken(new Cell(r, c))).toBe('');
  }
}

test('Game board defaults to size of 3', () =>
  assertGetSize(new GameBoard(), 3))

test('Game board can be initialized with a size', () =>
  assertGetSize(new GameBoard(4), 4))

test('Game board cannot be initialized to a value less than 1', () =>
  expect(() => new GameBoard(0)).toThrowError("Size cannot be less than 1."))

test('Game board must receive a numeric value in the constructor', () =>
  expect(() => new GameBoard('FAIL')).toThrowError("Size must be numeric."))