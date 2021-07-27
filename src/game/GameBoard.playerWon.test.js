import { Players } from '../enums/Players';
import GameBoard from './GameBoard';
import { newTable } from "../helpers/TableHelper";

const assertPlayerWins = (board, player, tokenWins) => {
  var game = new GameBoard();
  game.board = board;
  expect(game.playerWon(player)).toBe(tokenWins);
}

test('Returns false for new game', () => {
  var game = new GameBoard();
  expect(game.playerWon(Players.Player1)).toBeFalsy();
  expect(game.playerWon(Players.Player2)).toBeFalsy();
})

test('Returns false for one cell in row', () => {
  var table = newTable();
  table[0][0] = Players.Player1;
  assertPlayerWins(table, Players.Player1, false);
})

test('Returns false for two cells in row', () => {
  var table = newTable();
  table[0][0] = Players.Player1;
  table[0][1] = Players.Player1;
  assertPlayerWins(table, Players.Player1, false);
})

test('Returns false for cell in middle', () => {
  var table = newTable();
  table[1][1] = Players.Player1;
  assertPlayerWins(table, Players.Player1, false)
})

test('Returns true for three cells in row', () => {
  var table = newTable();
  table[0][0] = Players.Player1;
  table[0][1] = Players.Player1;
  table[0][2] = Players.Player1;
  assertPlayerWins(table, Players.Player1, true);
})

test('Returns true for three cells in column', () => {
  var table = newTable();
  table[0][1] = Players.Player1;
  table[1][1] = Players.Player1;
  table[2][1] = Players.Player1;
  assertPlayerWins(table, Players.Player1, true);
})

test('Returns true for top-left diagonal', () => {
  var table = newTable();
  table[0][0] = Players.Player1;
  table[1][1] = Players.Player1;
  table[2][2] = Players.Player1;
  assertPlayerWins(table, Players.Player1, true);
})

test('Returns true for top-left diagonal', () => {
  var table = newTable();
  table[2][0] = Players.Player1;
  table[1][1] = Players.Player1;
  table[0][2] = Players.Player1;
  assertPlayerWins(table, Players.Player1, true);
})