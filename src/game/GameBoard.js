export default class GameBoard {
  board = [
    ['', '', ''],
    ['', '', ''],
    ['', '', '']
  ]

  placeToken = (token, cell) =>
    this.board[cell.row][cell.col] = token;

  getToken = cell => 
    this.board[cell.row][cell.col];
}