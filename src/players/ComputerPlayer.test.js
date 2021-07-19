import GameBoard from '../game/GameBoard';
import ComputerPlayer from './ComputerPlayer';

test('Initializes player with X token', () => {
  var player = new ComputerPlayer('X');
  var token = player.getToken();
  expect(token).toBe('X');
})

test('Initializes player with O token', () => {
  var player = new ComputerPlayer('O');
  var token = player.getToken();
  expect(token).toBe('O');
})

test('Defaults token to X', () => {
  var player = new ComputerPlayer();
  var token = player.getToken();
  expect(token).toBe('X');
})

test('Returns a cell when given a game board', () => {
  var player = new ComputerPlayer();
  var board = new GameBoard();
  var cell = player.getNextMove(board);
  expect(cell).not.toBe(undefined);
  expect(cell).not.toBe(null);
  expect(cell.row).not.toBe(undefined);
  expect(cell.row).not.toBe(null);
  expect(cell.col).not.toBe(undefined);
  expect(cell.col).not.toBe(null);
})
