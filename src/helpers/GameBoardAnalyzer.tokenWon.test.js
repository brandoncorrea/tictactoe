import GameBoardAnalyzer from './GameBoardAnalyzer';
import GameBoard from '../game/GameBoard';

const assertTokenWins = (board, token, tokenWins) => {
  var game = new GameBoard();
  var analyzer = new GameBoardAnalyzer();
  game.setBoard(board);
  expect(analyzer.tokenWon(token, game)).toBe(tokenWins);
}

test('Returns false for new game', () => {
  var game = new GameBoard();
  var analyzer = new GameBoardAnalyzer();
  expect(analyzer.tokenWon('X', game)).toBeFalsy();
})

test('Returns false for one cell in row', () => 
  assertTokenWins(
    [['X', '', ''],
      ['', '', ''],
      ['', '', '']], 
    'X', 
    false))

test('Returns false for two cells in row', () => 
  assertTokenWins(
    [['X', 'X', ''],
      ['', '', ''],
      ['', '', '']], 
    'X', 
    false))

test('Returns false for cell in middle', () =>
  assertTokenWins(
    [['', '', ''],
    ['', 'X', ''],
    ['', '', '']], 
    'X', 
    false))

test('Returns true for three cells in row', () =>
  assertTokenWins(
    [['X', 'X', 'X'],
    ['', '', ''],
    ['', '', '']], 
    'X', 
    true))

test('Returns true for three cells in column', () =>
  assertTokenWins(
    [['', 'X', ''],
    ['', 'X', ''],
    ['', 'X', '']], 
    'X', 
    true))

test('Returns true for top-left diagonal', () =>
  assertTokenWins(
    [['X', '', ''],
    ['', 'X', ''],
    ['', '', 'X']], 
    'X', 
    true))

test('Returns true for top-left diagonal', () =>
  assertTokenWins(
    [['', '', 'X'],
    ['', 'X', ''],
    ['X', '', '']], 
    'X', 
    true))