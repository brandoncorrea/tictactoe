import Cell from '../models/Cell';

export default class ComputerPlayer {
  
  constructor(token) {
    if (!token) 
      token = 'X';
    this.token = token;
  }

  getToken = () => this.token;

  getNextMove = board => new Cell(1, 1);
}