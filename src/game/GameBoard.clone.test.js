import GameBoard from './GameBoard';
import AssertionHelper from '../helpers/AssertionHelper';

const assertCloneEquivalency = (board) => {
  var game = new GameBoard();
  game.setBoard(board);
  AssertionHelper.assertArraysEqual_2d(game.board, game.clone().board);
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