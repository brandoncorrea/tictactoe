import { Component } from "react";
import { Container } from "semantic-ui-react";
import ScoreRepository from "../data/ScoreRepository";
import SettingsRepository from "../data/SettingsRepository";
import { GameResult } from "../enums/GameResult";
import GameBoard from "../game/GameBoard";
import TicTacToeTable from "./TicTacToeTable";

export default class Game extends Component {

  constructor(props) {
    super(props);
    this.state = {
      game: new GameBoard()
    }

    this.settings = new SettingsRepository();
    this.score = new ScoreRepository();
    this.onCellClick = this.onCellClick.bind(this);
  }

  onCellClick(cell) {
    var token = this.settings.getUserIcon();
    if (this.state.game.getGameResult(token) !== GameResult.None)
      return;

    this.state.game.placeToken(token, cell);
    this.setState({ })

    // Update score
    var result = this.state.game.getGameResult(token);
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