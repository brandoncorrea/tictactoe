import { Players } from '../enums/Players';
import Cell from '../models/Cell';
import GameBoard from './GameBoard';

test('Player 1 can move a piece', () => {
  var board = new GameBoard(3, 'X', 'O');
  var cell = new Cell(0, 0);
  board.movePlayer1(cell)
  var actual = board.getToken(cell);
  expect(actual).toBe(Players.Player1);
})

test('Player 2 can move a piece', () => {
  var board = new GameBoard(3, 'X', 'O');
  var cell = new Cell(0, 0);
  board.movePlayer2(cell)
  var actual = board.getToken(cell);
  expect(actual).toBe(Players.Player2);
})
