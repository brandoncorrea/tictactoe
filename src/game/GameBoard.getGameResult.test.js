import { GameResult } from '../enums/GameResult';
import { Players } from '../enums/Players';
import { newTable } from '../helpers/TableHelper';
import GameBoard from './GameBoard';

var assertGameResult = (board, expectedResult) => {
  var game = new GameBoard();
  game.board = board;
  expect(game.getGameResult()).toBe(expectedResult);
}

test('GameResult is win for winning board', () => {
  var table = newTable();
  table[0][0] = Players.Player2;
  table[0][1] = Players.Player1;
  table[0][2] = Players.Player1;

  table[1][0] = Players.Player1;
  table[1][1] = Players.Player2;
  table[1][2] = Players.Player1;

  table[2][0] = Players.Player2;
  table[2][1] = Players.Player2;
  table[2][2] = Players.Player1;

  assertGameResult(table, GameResult.Win)
})

test('GameResult is loss for losing board', () => {
  var table = newTable();
  table[0][0] = Players.Player2;
  table[0][1] = Players.Player2;
  table[0][2] = Players.Player1;

  table[1][0] = Players.None;
  table[1][1] = Players.Player1;
  table[1][2] = Players.Player2;

  table[2][0] = Players.Player2;
  table[2][1] = Players.Player2;
  table[2][2] = Players.Player2;

  assertGameResult(table, GameResult.Loss);
})

test('GameResult is draw for full board', () => {
  var table = newTable();
  table[0][0] = Players.Player1;
  table[0][1] = Players.Player2;
  table[0][2] = Players.Player1;

  table[1][0] = Players.Player1;
  table[1][1] = Players.Player2;
  table[1][2] = Players.Player2;

  table[2][0] = Players.Player2;
  table[2][1] = Players.Player1;
  table[2][2] = Players.Player1;

  assertGameResult(table, GameResult.Draw)
})