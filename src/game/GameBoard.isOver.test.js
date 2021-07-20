import GameBoard from './GameBoard';

const assertIsOver = (board, expected) => {
  var game = new GameBoard();
  game.setBoard(board);
  expect(game.isOver()).toBe(expected);
}

test('Returns false on new games', () => {
  var game = new GameBoard()
  expect(game.isOver()).toBe(false);
})

test('Returns true when a player has won', () => 
  assertIsOver([
    ['', '', 'X'],
    ['', 'X', ''],
    ['X', '', '']],
    true))

test('Returns true when game is a draw', () => 
  assertIsOver([
    ['X', 'O', 'O'],
    ['O', 'X', 'X'],
    ['X', 'O', 'O']],
    true))