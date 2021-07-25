import { Component } from "react";
import { Container } from "semantic-ui-react";
import TicTacToeMinimax from "../algorithms/TicTacToeMinimax";
import ScoreRepository from "../data/ScoreRepository";
import SettingsRepository from "../data/SettingsRepository";
import { FirstPlayer } from "../enums/FirstPlayer";
import { GameMode } from "../enums/GameMode";
import { GameResult } from "../enums/GameResult";
import { PlayerIcon } from "../enums/PlayerIcon";
import TicTacToeTable from "./TicTacToeTable";

export default class Game extends Component {
  settings = new SettingsRepository();
  score = new ScoreRepository();

  constructor(props) {
    super(props);
    this.state = {
      game: props.game,
      nextPlayer: this.settings.getIconPlayer1()
    }

    // Play computer's turn if they go first
    var playerIcon2 = this.settings.getIconPlayer2();
    if (this.settings.getGameMode() === GameMode.PvE && 
      this.settings.getFirstPlayer() === FirstPlayer.Computer &&
      !this.state.game.board.some(row => row.some(cell => cell === playerIcon2))) {
        this.playComputerTurn();
      }

    // Initialize onclick events
    if (this.settings.getGameMode() === GameMode.PvE)
      this.state.onCellClick = this.onCellClickPvE;
    else
      this.state.onCellClick = this.onCellClickPvP;
    this.state.onCellClick = this.state.onCellClick.bind(this);
  }

  gameOver = () =>
    this.state.game.getGameResult(this.settings.getIconPlayer1()) !== GameResult.None

  // Uses minimax to determine the computer's move and plays that cell
  playComputerTurn() {
    var userToken = this.settings.getIconPlayer1();
    var computerToken = this.settings.getIconPlayer2();
    var minimax = new TicTacToeMinimax(computerToken, userToken);
    var cell = minimax.getNextBestCell(this.state.game.getTable());
    this.state.game.placeToken(computerToken, cell);
    this.setState({ });
  }

  // Updates the score repository with the result of the game
  updateScore(token) {
    var result = this.state.game.getGameResult(token);
    if (result === GameResult.Draw)
      this.score.addDraw();
    else if (result === GameResult.Loss)
      this.score.addLoss();
    else if (result === GameResult.Win)
      this.score.addWin();
  }

  // When GameMode is PvE, use this
  onCellClickPvE(cell) {
    if (this.gameOver() || this.state.game.getToken(cell)) return;

    // Play user's turn
    var token = this.settings.getIconPlayer1();
    this.state.game.placeToken(token, cell);
    this.setState({ });

    // Play computer's turn
    if (!this.gameOver())
      this.playComputerTurn()
      
    this.setState({ });
    this.updateScore(token);
  }

  // When GameMode is PvP, use this
  onCellClickPvP(cell) {
    if (this.gameOver() || this.state.game.getToken(cell)) return;

    // Play user's turn
    var token = this.state.nextPlayer;
    this.state.game.placeToken(token, cell);

    // Switch the turn to the next player
    this.setState({ 
      nextPlayer: token === PlayerIcon.X 
        ? PlayerIcon.O 
        : PlayerIcon.X 
    });

    this.updateScore(token);
  }

  render = () => 
    <Container>
      <TicTacToeTable 
        table={this.state.game.board}
        onCellClick={this.state.onCellClick} />
    </Container>
} 