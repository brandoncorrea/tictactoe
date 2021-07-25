import { Players } from "../enums/Players";
import { newTable } from "../helpers/TableHelper";
import Cell from "../models/Cell";
import TicTacToeMinimax from "./TicTacToeMinimax"

const assertReturnsCell = (table, expected) => {
  var cell = new TicTacToeMinimax(Players.Player1, Players.Player2).getNextBestCell(table);
  expect(cell.row).toBe(expected.row);
  expect(cell.col).toBe(expected.col);
}

test('Returns last empty cell', () => {
  var table = newTable()
  table[0][0] = Players.Player2;
  table[0][1] = Players.Player1;
  table[0][2] = Players.Player2;

  table[1][0] = Players.Player2;
  table[1][1] = Players.Player1;
  table[1][2] = Players.Player2;

  table[2][0] = Players.Player1;
  table[2][1] = Players.Player2;
  table[2][2] = Players.None;
  
  assertReturnsCell(table, new Cell(2,2))
})

test('Returns winning cell', () => {
  var table = newTable()
  table[0][0] = Players.Player2;
  table[0][1] = Players.Player1;
  table[0][2] = Players.Player2;

  table[1][0] = Players.None;
  table[1][1] = Players.Player1;
  table[1][2] = Players.Player2;

  table[2][0] = Players.Player1;
  table[2][1] = Players.None;
  table[2][2] = Players.None;
  
  assertReturnsCell(table, new Cell(2, 1))
})
  

test('Returns blocking cell', () => {
  var table = newTable()
  table[0][0] = Players.Player2;
  table[0][1] = Players.Player1;
  table[2][2] = Players.Player2;
  assertReturnsCell(table, new Cell(1, 1))
})

test('Returns first cell on empty table', () =>
  assertReturnsCell(newTable(), new Cell(0, 0)))
