import GameBoard from './GameBoard';

const assertCloneEquivalency = (board) => {
  var game = new GameBoard();
  game.setBoard(board);
  var clone = game.clone();
  expect(clone.board.length).toBe(game.board.length);
  
  for (var r = 0; r < game.board.length; r++) {
    expect(clone.board[r].length).toBe(game.board[r].length);
    for (var c = 0; c < game.board[r].length; c++)
      expect(clone.board[r][c]).toBe(game.board[r][c]);
  }
}

test('Creates a member-wise clone', () =>
  assertCloneEquivalency([
    ['X', '', ''],
    ['', 'O', ''],
    ['', '', 'X']
  ]))

test('Creates clone for 2x2 board', () => 
  assertCloneEquivalency([
    ['O', ''],
    ['', 'X']
  ]))