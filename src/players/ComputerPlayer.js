export default class ComputerPlayer {
  
  constructor(token) {
    if (!token) 
      token = 'X';
    this.token = token;
  }

  getToken = () => this.token;
}