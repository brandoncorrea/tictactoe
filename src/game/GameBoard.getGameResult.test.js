import { GameResult } from '../enums/GameResult';
import GameBoard from './GameBoard';

var assertGameResult = (board, expectedResult) => {
  var game = new GameBoard();
  game.setBoard(board)
  expect(game.getGameResult('X')).toBe(expectedResult);
}

test('GameResult is win for winning board', () =>
  assertGameResult([
    ['O', 'X', 'X'],
    ['X', 'O', 'X'],
    ['O', 'O', 'X']],
  GameResult.Win))

test('GameResult is loss for losing board', () => 
  assertGameResult([
    ['O', 'O', 'X'],
    ['', 'X', 'O'],
    ['O', 'O', 'O']], 
    GameResult.Loss))

test('GameResult is draw for full board', () => 
  assertGameResult([
    ['X', 'O', 'X'],
    ['X', 'O', 'O'],
    ['O', 'X', 'X']], 
    GameResult.Draw))