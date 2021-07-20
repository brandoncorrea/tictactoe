import GameBoard from './GameBoard';

const assertIsDraw = (board, expected) => {
  var game = new GameBoard();
  game.setBoard(board);
  expect(game.isDraw()).toBe(expected);
}

test('False when new game', () => {
  var game = new GameBoard();
  expect(game.isDraw()).toBeFalsy();
})

test('True when game board is full', () => 
  assertIsDraw([
    ['X', 'X', 'O'],
    ['O', 'O', 'X'],
    ['X', 'O', 'X']], 
    true))

test('False when board is full but a player won', () => 
  assertIsDraw([
    ['X', 'X', 'O'],
    ['X', 'O', 'O'],
    ['X', 'O', 'X']], 
    false))

test('False when board has one empty space', () => 
  assertIsDraw([
    ['X', 'X', 'O'],
    ['', 'O', 'O'],
    ['X', 'O', 'X']], 
    false))


test('False when board has one empty space and a player won', () => 
  assertIsDraw([
    ['X', 'X', 'O'],
    ['O', 'O', 'O'],
    ['X', '', 'X']], 
    false))