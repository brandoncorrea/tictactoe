import Cell from '../models/Cell';
import ComputerPlayer from '../players/ComputerPlayer';
import GameBoard from './GameBoard';

const assertTokenPlacement = (board, player, cell) => {
  board.placeToken(player.getToken(), cell);
  var actual = board.getToken(cell);
  expect(actual).toBe(player.getToken());
}

test('Game board accepts token placements', () => 
  assertTokenPlacement(
    new GameBoard(),
    new ComputerPlayer(),
    new Cell(0, 0)))

test('Game board accepts token placements for Os', () =>
  assertTokenPlacement(
    new GameBoard(),
    new ComputerPlayer('O'),
    new Cell(1, 1)))

test('Game board accepts token placements from multiple players', () => {
  var board = new GameBoard();
  assertTokenPlacement(
    board,
    new ComputerPlayer('X'),
    new Cell(0, 0))
  assertTokenPlacement(
    board,
    new ComputerPlayer('O'),
    new Cell(1, 1))
})