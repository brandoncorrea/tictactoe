import GameBoard from './GameBoard';
import AssertionHelper from '../helpers/AssertionHelper';
import { newTable } from '../helpers/TableHelper';
import { Players } from '../enums/Players';

test('Get table returns the current board value', () => {
  var game = new GameBoard();
  var table = newTable();
  table[0][1] = Players.Player1;
  table[2][2] = Players.Player1;
  table[1][0] = Players.Player2;
  game.setBoard(table)
  var table = game.getTable();
  AssertionHelper.assertArraysEqual_2d(game.board, table);
})

test('Get table returns an empty table on new game', () => {
  var expected = newTable();
  var table = new GameBoard().getTable();
  AssertionHelper.assertArraysEqual_2d(expected, table);
})