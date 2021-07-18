import Cell from '../models/Cell';
import ComputerPlayer from '../players/ComputerPlayer';
import GameBoard from './GameBoard';

test('Game board accepts token placements', () => {
  var board = new GameBoard();
  var player = new ComputerPlayer();
  var cell = new Cell(0, 0);
  board.placeToken(player.getToken(), cell);
  expect(board.getToken(cell)).toBe(player.getToken());
})

test('Game board accepts token placements for Os', () => {
  var board = new GameBoard();
  var player = new ComputerPlayer('O');
  var cell = new Cell(1, 1);
  board.placeToken(player.getToken(), cell);
  expect(board.getToken(cell)).toBe(player.getToken())
})

test('Game board accepts token placements from multiple players', () => {
  var board = new GameBoard();
  var player1 = new ComputerPlayer('X')
  var player2 = new ComputerPlayer('O')
  var player1Cell = new Cell(0, 0);
  var player2Cell = new Cell(1, 1);
  board.placeToken(player1.getToken(), player1Cell);
  board.placeToken(player2.getToken(), player2Cell);
  expect(board.getToken(player1Cell)).toBe(player1.getToken());
  expect(board.getToken(player2Cell)).toBe(player2.getToken());
})