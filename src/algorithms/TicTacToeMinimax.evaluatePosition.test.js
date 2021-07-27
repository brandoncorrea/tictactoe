import { GameResult } from "../enums/GameResult";
import { Players } from "../enums/Players";
import { newTable } from "../helpers/TableHelper";
import TicTacToeMinimax from "./TicTacToeMinimax"

const minimax = new TicTacToeMinimax(Players.Player1, Players.Player2);

test('Evaluates to undefined: new table', () => {
  var result = minimax.evaluatePosition(newTable());
  expect(result).toBe(undefined);
})

test('Evaluates to undefined: one empty cell', () => {
  var none = Players.None;
  var p1 = Players.Player1;
  var p2 = Players.Player2;

  expect(minimax.evaluatePosition([
    [p1, p1, p2],
    [p2, p1, p1],
    [p2, none, p2],
  ])).toBe(undefined);
})

test('Evaluates to 1: full row', () => {
  var table = newTable();
  table[0][0] = Players.Player1;
  table[0][1] = Players.Player1;
  table[0][2] = Players.Player1;
  expect(minimax.evaluatePosition(table)).toBe(1);
})

test('Evaluates to 1: full column', () => {
  var table = newTable();
  table[0][0] = Players.Player1;
  table[1][0] = Players.Player1;
  table[2][0] = Players.Player1;
  expect(minimax.evaluatePosition(table)).toBe(1);
})

test('Evaluates to 1: top diagonal', () => {
  var table = newTable();
  table[0][0] = Players.Player1;
  table[1][1] = Players.Player1;
  table[2][2] = Players.Player1;
  expect(minimax.evaluatePosition(table)).toBe(1);
})

test('Evaluates to 1: bottom diagonal', () => {
  var table = newTable();
  table[2][0] = Players.Player1;
  table[1][1] = Players.Player1;
  table[0][2] = Players.Player1;
  expect(minimax.evaluatePosition(table)).toBe(1);
})

test('Evaluates to 0: full table, no wins', () => {
  var p1 = Players.Player1;
  var p2 = Players.Player2;
  var table = [
    [p1, p2, p1],
    [p2, p1, p2],
    [p2, p1, p2]
  ]
  
  expect(minimax.evaluatePosition(table)).toBe(0);
})

test('Evaluates to -1: full row', () => {
  var table = newTable();
  table[0][0] = Players.Player2;
  table[0][1] = Players.Player2;
  table[0][2] = Players.Player2;
  expect(minimax.evaluatePosition(table)).toBe(-1);
})

test('Evaluates to -1: full column', () => {
  var table = newTable();
  table[0][0] = Players.Player2;
  table[1][0] = Players.Player2;
  table[2][0] = Players.Player2;
  expect(minimax.evaluatePosition(table)).toBe(-1);
})

test('Evaluates to -1: top diagonal', () => {
  var table = newTable();
  table[0][0] = Players.Player2;
  table[1][1] = Players.Player2;
  table[2][2] = Players.Player2;
  expect(minimax.evaluatePosition(table)).toBe(-1);
})

test('Evaluates to -1: bottom diagonal', () => {
  var table = newTable();
  table[2][0] = Players.Player2;
  table[1][1] = Players.Player2;
  table[0][2] = Players.Player2;
  expect(minimax.evaluatePosition(table)).toBe(-1);
})
