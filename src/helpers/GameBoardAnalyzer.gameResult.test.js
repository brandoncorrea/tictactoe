import { GameResult } from '../enums/GameResult';
import GameBoard from '../game/GameBoard'
import GameBoardAnalyzer from './GameBoardAnalyzer';

var assertGameResult = (board, expectedResult) => {
  var game = new GameBoard();
  var analyzer = new GameBoardAnalyzer();
  game.setBoard(board)
  analyzer.getAllPaths(game, 'X', 'O')
    .forEach(path => 
      expect(path.gameResult).toBe(expectedResult));
}

test('GameResult is win for winning paths', () =>
  assertGameResult([
    ['O', 'X', 'X'],
    ['X', 'O', 'X'],
    ['O', 'O', '']], 
  GameResult.Win))

test('GameResult is loss for losing paths', () => 
  assertGameResult([
    ['O', 'O', 'X'],
    ['', 'X', 'O'],
    ['O', '', 'O']], 
    GameResult.Loss))

test('GameResult is draw for draw paths', () => 
  assertGameResult([
    ['X', 'O', 'X'],
    ['', 'O', 'O'],
    ['O', 'X', 'X']], 
    GameResult.Draw))