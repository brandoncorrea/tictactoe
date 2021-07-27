import { Players } from "../enums/Players"
import Cell from "../models/Cell";
import { hasBottomLeftDiagonal, hasCol, hasRow, hasTopLeftDiagonal, isTableFull, newTable, fillTable, enumerateEmptyCells } from "./TableHelper"

test('hasRow is true when any row is occupied by one token', () => {
  for (var r = 0; r < 3; r++) {
    var table = newTable();
    for (var c = 0; c < 3; c++)
      table[r][c] = Players.Player1;
    expect(hasRow(Players.Player1, table, r)).toBeTruthy()
  }
})

test('hasCol is true when any column is occupied by one token', () => {
  for (var c = 0; c < 3; c++) {
    var table = newTable();
    for (var r = 0; r < 3; r++)
      table[r][c] = Players.Player1;
    expect(hasCol(Players.Player1, table, c)).toBeTruthy()
  }
})

test('hasRow returns false for empty tables', () =>
  expect(
    hasRow(
      Players.Player1, 
      newTable(), 
      0))
  .toBeFalsy())

test('hasCol returns false for empty tables', () => 
  expect(
    hasCol(
      Players.Player1, 
      newTable(), 
      0))
  .toBeFalsy())

test('hasTopLeftDiagonal returns false for new table', () => 
  expect(
    hasTopLeftDiagonal(
      Players.Player1, 
      newTable(), 
      0))
  .toBeFalsy())

test('hasBottomLeftDiagonal returns false for new table', () => 
  expect(
    hasBottomLeftDiagonal(
      Players.Player1, 
      newTable(), 
      0))
  .toBeFalsy())

test('hasTopLeftDiagonal returns true when diagonal is occupied', () => {
  var table = newTable();
  for (var i = 0; i < table.length; i++)
    table[i][i] = Players.Player1;
  expect(hasTopLeftDiagonal(Players.Player1, table)).toBeTruthy()
  expect(hasTopLeftDiagonal(Players.Player2, table)).toBeFalsy()
})

test('hasBottomLeftDiagonal returns true when diagonal is occupied', () => {
  var table = newTable();
  table[2][0] = Players.Player1;
  table[1][1] = Players.Player1;
  table[0][2] = Players.Player1;
  expect(hasBottomLeftDiagonal(Players.Player1, table)).toBeTruthy()
  expect(hasBottomLeftDiagonal(Players.Player2, table)).toBeFalsy()
})

test('isTableFull: Empty, One Filed Cell, Full, and one empty cell', () =>{
  var table = newTable();
  expect(isTableFull(newTable())).toBeFalsy()

  table[0][0] = Players.Player1;
  expect(isTableFull(newTable())).toBeFalsy()

  fillTable(table, Players.Player1);
  expect(isTableFull(table)).toBeTruthy();

  table[2][2] = Players.None;
  expect(isTableFull(table)).toBeFalsy();
})

test('fillTable: Player1, Player2, None', () => {
  var table = newTable();
  var values = [
    Players.Player1,
    Players.Player2,
    Players.None
  ]

  for (var value in values) {
    fillTable(table, value);
    var filled = table.every(row => row.every(col => col === value));
    expect(filled).toBeTruthy();
  }
})

test('enumerateEmptyCells: empty table', () => {
  var enumerator = enumerateEmptyCells(newTable());
  var next = enumerator.next();
  var cells = [];

  while (!next.done) {
    cells.push(next.value);
    next = enumerator.next();
  }
  
  expect(cells.length).toBe(9);
  while(cells.length) {
    var cell = cells.pop();
    expect(cells.some(c => c.equals(cell))).toBeFalsy();
  }
})

test('enumerateEmptyCells: one empty cell', () => {
  var table = newTable(3);
  var cell = new Cell(1, 1);

  fillTable(table, Players.Player1);
  table[cell.row][cell.col] = Players.None;

  var enumerator = enumerateEmptyCells(table);
  var next = enumerator.next();
  expect(cell.equals(next.value)).toBeTruthy();
  next = enumerator.next();
  expect(next.done).toBeTruthy();
})

test('enumerateEmptyCells: full table', () => {
  var table = newTable(3);
  fillTable(table, Players.Player1);
  var done = enumerateEmptyCells(table).next().done;
  expect(done).toBeTruthy();
})
