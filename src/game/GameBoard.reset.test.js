import { Players } from "../enums/Players";
import GameBoard from "./GameBoard";

const assertReset = (game, size) => {
  // Validate number of rows
  expect(game.board.length).toBe(size);

  // Validate number of columns
  game.board.every(row => {
    expect(row.length).toBe(size)

    // Validate cell values
    row.forEach(col =>
      expect(col).toBe(Players.None))
  })
}

const newGame = size => {
  var game = new GameBoard(size);
  for (var r = 0; r < game.board.length; r++)
    for (var c = 0; c < game.board[r].length; c++)
      game.board[r][c] = Players.Player1;
  return game;
}

test('Sets all cells back to None', () => {
  var size = 3;
  var game = newGame(size)
  game.reset();
  assertReset(game, size);
})

test('Sets all cells back to none with the original size', () => {
  var size = 2;
  var game = newGame(size)
  game.reset();
  assertReset(game, size);
})

test('Resizes table when a size is passed in', () => {
  var size = 4;
  var game = newGame(size);
  game.reset(size);
  assertReset(game, size);
})

test('Reset rejects lengths less than 1', () => {
  var size = 4;
  var game = newGame(size);
  game.reset(1);
  expect(() => game.reset(0)).toThrowError();
  expect(() => game.reset(-1)).toThrowError();
})
