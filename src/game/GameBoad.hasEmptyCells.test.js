import GameBoard from "./GameBoard"

const assertHasEmptyCells = (board, expected) => {
  var game = new GameBoard();
  game.setBoard(board);
  expect(game.hasEmptyCells()).toBe(expected);
}

test('Returns true for new game', () => {
  var game = new GameBoard()
  expect(game.hasEmptyCells()).toBeTruthy();
})

test('Returns false when no cells are empty', () =>
  assertHasEmptyCells([
    ['X', 'X', 'X'],
    ['X', 'X', 'X'],
    ['X', 'X', 'X']
  ],
  false))


test('Returns true when one cell is taken', () => 
  assertHasEmptyCells([
    ['', '', ''],
    ['X', '', ''],
    ['', '', '']
  ],
  true))

test('Returns true when one cell is empty', () => 
  assertHasEmptyCells([
    ['X', 'X', 'X'],
    ['X', 'X', ''],
    ['X', 'X', 'X']
  ],
  true))