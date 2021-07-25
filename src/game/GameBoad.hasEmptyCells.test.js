import { Table } from "semantic-ui-react";
import { Players } from "../enums/Players";
import { newTable } from "../helpers/TableHelper";
import GameBoard from "./GameBoard"

const assertHasEmptyCells = (board, expected) => {
  var game = new GameBoard();
  game.setBoard(board);
  expect(game.hasEmptyCells()).toBe(expected);
}

const fillTable = (table, value) => {
  for (var r = 0; r < table.length; r++)
    for (var c = 0; c < table.length; c++)
      table[r][c] = value;
  return table;
}

test('Returns true for new game', () => {
  var game = new GameBoard()
  expect(game.hasEmptyCells()).toBeTruthy();
})

test('Returns false when no cells are empty', () => 
  assertHasEmptyCells(
    fillTable(
      newTable(), 
      Players.Player1), 
    false))


test('Returns true when one cell is taken', () =>  {
  var table = newTable()
  table[1][0] = Players.Player1;
  assertHasEmptyCells(table, true)
})

test('Returns true when one cell is empty', () => {
  var table = fillTable(newTable(), Players.Player1);
  table[1][2] = Players.None;
  assertHasEmptyCells(table, true);
})