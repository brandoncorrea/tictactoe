import { Players } from '../enums/Players';
import Cell from '../models/Cell';
import GameBoard from './GameBoard';

test('Player 1 can move a piece', () => {
  var board = new GameBoard(3);
  var cell = new Cell(0, 0);
  board.movePlayer1(cell)
  var actual = board.getToken(cell);
  expect(actual).toBe(Players.Player1);
})

test('Player 2 can move a piece', () => {
  var board = new GameBoard(3);
  var cell = new Cell(0, 0);
  board.movePlayer2(cell)
  var actual = board.getToken(cell);
  expect(actual).toBe(Players.Player2);
})

test('Player 1 cannot move a piece on top of another piece', () => {
  var board = new GameBoard(3);
  var cell = new Cell(0, 0);
  board.movePlayer2(cell);
  expect(() => board.movePlayer1(cell)).toThrowError("Cannot move to an occupied cell.");
})

test('Player 2 cannot move a piece on top of another piece', () => {
  var board = new GameBoard(3);
  var cell = new Cell(0, 0);
  board.movePlayer1(cell);
  expect(() => board.movePlayer2(cell)).toThrowError("Cannot move to an occupied cell.");
})