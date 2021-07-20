import GameBoard from './GameBoard';

const assertTokenWins = (board, token, tokenWins) => {
  var game = new GameBoard();
  game.setBoard(board);
  expect(game.tokenWon(token)).toBe(tokenWins);
}

test('Returns false for new game', () => {
  var game = new GameBoard();
  expect(game.tokenWon('X', game)).toBeFalsy();
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