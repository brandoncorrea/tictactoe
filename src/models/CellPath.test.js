import { GameResult } from "../enums/GameResult";
import CellPath from "./CellPath"

test('Cell path can create a memberwise clone', () => {
  var path = new CellPath('X')
  var clone = path.clone();
  expect(clone.token).toBe(path.token);
  expect(clone.gameResult).toBe(path.gameResult);
  expect(clone.path.length).toBe(path.path.length);
  for (var i = 0; i < clone.path; i++) {
    var cloneCell = clone.path[i];
    var origCell = path.path[i];
    expect(cloneCell.row).toBe(origCell.row)
    expect(cloneCell.col).toBe(origCell.col)
  }
})

test('Cell path initialization values', () => {
  var path = new CellPath('X');
  expect(path.token).toBe('X');
  expect(path.gameResult).toBe(GameResult.None);
  expect(path.path.length).toBe(0);
})