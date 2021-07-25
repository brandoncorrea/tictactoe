import { Component } from "react";
import { Container } from "semantic-ui-react";
import TicTacToeMinimax from "../algorithms/TicTacToeMinimax";
import ScoreRepository from "../data/ScoreRepository";
import SettingsRepository from "../data/SettingsRepository";
import { FirstPlayer } from "../enums/FirstPlayer";
import { GameResult } from "../enums/GameResult";
import GameBoard from "../game/GameBoard";
import GameBoardAnalyzer from "../helpers/GameBoardAnalyzer";
import TicTacToeTable from "./TicTacToeTable";

export default class Game extends Component {

  constructor(props) {
    super(props);
    this.state = {
      game: props.game,
      analyzer: new GameBoardAnalyzer(),
    }

    this.settings = new SettingsRepository();
    this.score = new ScoreRepository();
    this.onCellClick = this.onCellClick.bind(this);
    if (this.settings.getFirstPlayer() === FirstPlayer.Computer)
      this.playComputerTurn();
  }

  gameOver = () =>
    this.state.game.getGameResult(this.settings.getUserIcon()) !== GameResult.None

  playComputerTurn() {
    var userToken = this.settings.getUserIcon();
    var computerToken = this.settings.getComputerIcon();
    var minimax = new TicTacToeMinimax(computerToken, userToken);
    var cell = minimax.getNextBestCell(this.state.game.getTable());
    this.state.game.placeToken(computerToken, cell);
    this.setState({ });
  }

  onCellClick(cell) {
    if (this.gameOver() || this.state.game.getToken(cell)) return;

    // Play user's turn
    var userToken = this.settings.getUserIcon();
    this.state.game.placeToken(userToken, cell);
    this.setState({ });

    // Play computer's turn
    if (!this.gameOver())
      this.playComputerTurn()
      
    this.setState({ });

    // Update score
    var result = this.state.game.getGameResult(userToken);
    if (result === GameResult.Draw)
      this.score.addDraw();
    else if (result === GameResult.Loss)
      this.score.addLoss();
    else if (result === GameResult.Win)
      this.score.addWin();
  }

  render = () => 
    <Container>
      <TicTacToeTable 
        table={this.state.game.board}
        onCellClick={this.onCellClick} />
    </Container>
} 