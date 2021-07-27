import { Component } from "react";
import { Button, Container, Modal, Header, Divider, Segment } from "semantic-ui-react";
import TicTacToeMinimax from "../algorithms/TicTacToeMinimax";
import SettingsRepository from "../data/SettingsRepository";
import { Players } from "../enums/Players";
import { GameMode } from "../enums/GameMode";
import { GameResult } from "../enums/GameResult";
import TicTacToeTable from "./TicTacToeTable";
import ScoreRepositoryPvP from "../data/ScoreRepositoryPvP";
import ScoreRepositoryPvE from "../data/ScoreRepositoryPvE";

export default class Game extends Component {
  settings = new SettingsRepository();
  scorePvp = new ScoreRepositoryPvP();
  scorePve = new ScoreRepositoryPvE();

  constructor(props) {
    super(props);
    this.state = {
      game: props.game,
      player1: this.settings.getIconPlayer1(),
      player2: this.settings.getIconPlayer2(),
      nextPlayer: this.getNextPlayer(props.game.board),
      message: '',
    }

    this.initializeGame();
    this.onResetClicked = this.onResetClicked.bind(this);
    this.onCellClickPvP = this.onCellClickPvP.bind(this);
    this.onCellClickPvE = this.onCellClickPvE.bind(this);
    this.onCellClick = this.onCellClick.bind(this);
  }

  initializeGame() {
    // Play computer's turn if they go first
    if (this.settings.getGameMode() === GameMode.PvE && 
      this.settings.getFirstPlayer() === Players.Player2 &&
      this.state.game.board.every(row => row.every(cell => cell === Players.None)))
        this.playComputerTurn();
  }

  // Returns the player who should go next
  getNextPlayer(table) {
    // Count the number of cells each player holds
    var firstPlayer = this.settings.getFirstPlayer();
    var secondPlayer = firstPlayer === Players.Player1
      ? Players.Player2
      : Players.Player1;

    var firstPlayerFlags = 0;
    var secondPlayerFlags = 0;
    table.forEach(row => row.forEach(cell => {
      if (cell === firstPlayer)
        firstPlayerFlags++
      else if (cell === secondPlayer)
        secondPlayerFlags++
    }))

    // If the counts are equal, then it is the first players turn
    return firstPlayerFlags <= secondPlayerFlags 
      ? firstPlayer
      : secondPlayer;
  }

  // True if the game is over
  isGameOver = () =>
    this.state.game.getGameResult() !== GameResult.None

  // Uses minimax to determine the computer's move and plays that cell
  playComputerTurn() {
    var minimax = new TicTacToeMinimax(Players.Player2, Players.Player1);
    var cell = minimax.getNextBestCell(this.state.game.board);
    this.state.game.movePlayer2(cell);
    this.setState({ nextPlayer: Players.Player1 });
  }

  // Updates the score repository with the result of the game
  updateScore(repo) {
    var result = this.state.game.getGameResult();
    var message = '';
    if (result === GameResult.Draw) {
      message = 'Draw!';
      repo.addDraw();
    }
    else if (result === GameResult.Loss){
      message = `Opponent Won! ${this.settings.getIconPlayer2()}`;
      repo.addLoss();
    }
    else if (result === GameResult.Win){
      message = `You Won! ${this.settings.getIconPlayer1()}`;  
      repo.addWin();
    }

    this.setState({ message });
  }

  // Delegate onclick events based on the game mode
  onCellClick =
    this.settings.getGameMode() === GameMode.PvE
    ? this.onCellClickPvE
    : this.onCellClickPvP

  // When GameMode is PvE, use this
  onCellClickPvE(cell) {
    if (this.isGameOver() || this.state.game.getToken(cell)) return;
    this.state.game.movePlayer1(cell);
    this.setState({ nextPlayer: Players.Player2 });
    if (!this.isGameOver())
      this.playComputerTurn()
    this.updateScore(this.scorePve);
  }

  // When GameMode is PvP, use this
  onCellClickPvP(cell) {
    if (this.isGameOver() || this.state.game.getToken(cell)) return;

    // Play user's turn
    if (this.state.nextPlayer === Players.Player1)
      this.state.game.movePlayer1(cell);
    else
      this.state.game.movePlayer2(cell);
    
    // Toggle between player turns
    this.setState({
      nextPlayer: this.state.nextPlayer === Players.Player1
      ? Players.Player2
      : Players.Player1
    })

    this.updateScore(this.scorePvp);
  }

  // Resets the game board
  onResetClicked() {
    this.state.game.reset(this.settings.getTableSize());
    this.initializeGame();
    this.setState({ });
  }

  render = () => 
    <>
      <Segment>
        <Button.Group fluid>
          <Button 
            positive={this.state.nextPlayer === Players.Player1} 
            content={`Player 1: ${this.state.player1}`} />
          <Button 
            positive={this.state.nextPlayer === Players.Player2} 
            content={`Player 2: ${this.state.player2}`} />
        </Button.Group>
        <Divider />
        <TicTacToeTable 
          table={this.state.game.board}
          onCellClick={this.onCellClick} />
        <Divider />
        <Button.Group fluid>
          <Button 
            negative
            content='Reset'
            onClick={this.onResetClicked} />
        </Button.Group>
      </Segment>

      <Modal
        basic
        onClose={() => this.setState({ message: '' })}
        open={this.state.message.length > 0}
        size='small' >
        <Header as='h1' textAlign='center' content={this.state.message} />
      </Modal>
    </>
} 