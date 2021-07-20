import GameBoard from './GameBoard';
import AssertionHelper from '../helpers/AssertionHelper';

test('Get table returns the current board value', () => {
  var game = new GameBoard();
  game.setBoard([
    ['', 'X', ''],
    ['O', '', ''],
    ['', '', 'X']
  ])
  var table = game.getTable();
  AssertionHelper.assertArraysEqual_2d(game.board, table);
})

test('Get table returns an empty table on new game', () => {
  var expected = [
    ['', '', ''],
    ['', '', ''],
    ['', '', '']
  ]
  var table = new GameBoard().getTable();
  AssertionHelper.assertArraysEqual_2d(expected, table);
})