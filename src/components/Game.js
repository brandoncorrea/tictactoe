import { Component } from "react";
import { Button, Container } from "semantic-ui-react";
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
      nextPlayer: this.settings.getIconPlayer1()
    }

    // Play computer's turn if they go first
    if (this.settings.getGameMode() === GameMode.PvE && 
      this.settings.getFirstPlayer() === Players.Player2 &&
      this.state.game.board.every(row => row.every(cell => cell === Players.None)))
        this.playComputerTurn();

    // Initialize onclick events
    if (this.settings.getGameMode() === GameMode.PvE)
      this.state.onCellClick = this.onCellClickPvE;
    else
      this.state.onCellClick = this.onCellClickPvP;
    this.state.onCellClick = this.state.onCellClick.bind(this);
    this.onResetClicked = this.onResetClicked.bind(this);
  }

  isGameOver = () =>
    this.state.game.getGameResult() !== GameResult.None

  // Uses minimax to determine the computer's move and plays that cell
  playComputerTurn() {
    var minimax = new TicTacToeMinimax(Players.Player2, Players.Player1);
    var cell = minimax.getNextBestCell(this.state.game.getTable());
    this.state.game.movePlayer2(cell);
    this.setState({ });
  }

  // Updates the score repository with the result of the game
  updateScore(repo) {
    var result = this.state.game.getGameResult();
    if (result === GameResult.Draw)
      repo.addDraw();
    else if (result === GameResult.Loss)
      repo.addLoss();
    else if (result === GameResult.Win)
      repo.addWin();
  }

  // When GameMode is PvE, use this
  onCellClickPvE(cell) {
    if (this.isGameOver() || this.state.game.getToken(cell)) return;

    // Play user's turn
    this.state.game.movePlayer1(cell);
    this.setState({ });

    // Play computer's turn
    if (!this.isGameOver())
      this.playComputerTurn()
      
    this.setState({ });
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
    
    // Switch the turn to the next player
    this.setState({ 
      nextPlayer: this.state.nextPlayer === Players.Player1
        ? Players.Player2
        : Players.Player1
    });

    this.updateScore(this.scorePvp);
  }

  onResetClicked() {
    this.state.game.reset(this.settings.getTableSize());
    this.setState({ });
  }

  render = () => 
    <Container>
      <TicTacToeTable 
        table={this.state.game.board}
        onCellClick={this.state.onCellClick} />
      
      <Button.Group fluid>
        <Button 
          content='Reset'
          onClick={this.onResetClicked} />
      </Button.Group>
    </Container>
} 