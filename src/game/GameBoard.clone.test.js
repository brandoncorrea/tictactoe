import GameBoard from './GameBoard';
import AssertionHelper from '../helpers/AssertionHelper';
import { newTable } from '../helpers/TableHelper';
import { Players } from '../enums/Players';

const assertCloneEquivalency = (board) => {
  var game = new GameBoard();
  game.setBoard(board);
  AssertionHelper.assertArraysEqual_2d(game.board, game.clone().board);
}

test('Creates a member-wise clone', () => {
  var table = newTable()
  table[0][0] = Players.Player1;
  table[1][1] = Players.Player2;
  table[2][2] = Players.Player1;

  assertCloneEquivalency(table)
})
  
test('Creates clone for 2x2 board', () => {
  var table = newTable(2);
  table[0][0] = Players.Player2;
  table[1][1] = Players.Player1;
  assertCloneEquivalency(table);
})
