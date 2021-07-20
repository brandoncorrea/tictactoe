import GameBoard from './GameBoard';

test('Returns nothing for new game', () => {
  var game = new GameBoard()
  var tokens = game.getActiveTokens();
  expect(tokens.length).toBe(0);
})

test('Returns one X when all Xs on board', () => {
  var game = new GameBoard()
  game.setBoard([
    ['X', 'X', 'X'],
    ['X', 'X', 'X'],
    ['X', 'X', 'X']
  ])
  var tokens = game.getActiveTokens();
  expect(tokens.length).toBe(1);
  expect(tokens[0]).toBe('X');
})

test('Returns X and O for Xs and Os', () => {
  var game = new GameBoard()
  game.setBoard([
    ['X', 'X', 'O'],
    ['O', 'O', 'X'],
    ['', 'O', 'X']
  ])
  var tokens = game.getActiveTokens();
  expect(tokens.length).toBe(2);
  expect(tokens.some(i => i === 'X')).toBeTruthy();
  expect(tokens.some(i => i === 'O')).toBeTruthy();
})
