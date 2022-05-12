export default class Cell {
  constructor(row, col) {
    this.row = row;
    this.col = col;
  }

  equals = cell => 
    cell &&
    this.row === cell.row &&
    this.col === cell.col;
}