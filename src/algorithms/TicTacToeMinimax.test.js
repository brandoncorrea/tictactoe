import Cell from "../models/Cell";
import TicTacToeMinimax from "./TicTacToeMinimax"

const assertReturnsCell = (table, expected) => {
  var cell = new TicTacToeMinimax('O', 'X').getNextBestCell(table);
  expect(cell.row).toBe(expected.row);
  expect(cell.col).toBe(expected.col);
}

test('Returns last empty cell', () =>
  assertReturnsCell([
      ['X', 'O', 'X'],
      ['X', 'O', 'X'],
      ['O', 'X', ''],
    ],
    new Cell(2, 2)))

test('Returns winning cell', () =>
  assertReturnsCell([
      ['X', 'O', 'X'],
      ['', 'O', 'X'],
      ['O', '', ''],
    ],
    new Cell(2, 1)))

test('Returns blocking cell', () =>
  assertReturnsCell([
      ['X', 'O', ''],
      ['', '', ''],
      ['', '', 'X'],
    ],
    new Cell(1, 1)))

test('Returns first cell on empty table', () =>
  assertReturnsCell([
      ['', '', ''],
      ['', '', ''],
      ['', '', ''],
    ],
    new Cell(0, 0)))