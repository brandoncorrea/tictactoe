import Cell from "./Cell"

const assertAreEqual = (cell1, cell2) =>
  expect(cell1.equals(cell2)).toBeTruthy();
const assertAreNotEqual = (cell1, cell2) =>
  expect(cell1.equals(cell2)).toBeFalsy();

test('Equals false for null value', () =>
  assertAreNotEqual(new Cell(1, 1), null))
test('Equals false for undefined value', () => 
  assertAreNotEqual(new Cell(0, 1), undefined))
test('Equals false new Cell without parameters', () => 
  assertAreNotEqual(new Cell(0, 1), new Cell()))
test('Equals false new Cell with undefined col', () => 
  assertAreNotEqual(new Cell(0, 1), new Cell(0, undefined)))
test('Equals false new Cell with undefined row', () => 
  assertAreNotEqual(new Cell(0, 1), new Cell(undefined, 1)))
test('Equals false new Cell with null col', () => 
  assertAreNotEqual(new Cell(0, 1), new Cell(0, null)))
test('Equals false new Cell with null row', () => 
  assertAreNotEqual(new Cell(0, 1), new Cell(null, 1)))
test('Equals false with different row', () => 
  assertAreNotEqual(new Cell(0, 1), new Cell(1, 1)))
test('Equals false with different col', () => 
  assertAreNotEqual(new Cell(0, 1), new Cell(0, 0)))
test('Equals true with same row and col', () => 
  assertAreEqual(new Cell(0, 1), new Cell(0, 1)))