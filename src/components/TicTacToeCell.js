import { Component } from "react";
import { Table } from "semantic-ui-react";
import ScoreRepository from "../data/ScoreRepository";
import SettingsRepository from "../data/SettingsRepository";
import { GameResult } from "../enums/GameResult";
import Cell from '../models/Cell'

export default class TicTacToeCell extends Component {
  constructor(props) {
    super(props);
    this.state = {
      content: props.content,
    }

    this.settings = new SettingsRepository();
    this.score = new ScoreRepository();
    this.cell = new Cell(props.row, props.col);
    this.game = props.game;
    this.style = {
      cursor: 'pointer', 
      height: props.height,
    }

    this.handleClick = this.handleClick.bind(this);
  }

  handleClick() {
    var token = this.settings.getUserIcon();
    if (this.game.getGameResult(token) !== GameResult.None)
      return;

    this.game.placeToken(this.settings.getUserIcon(), this.cell);
    this.setState({ content: token })

    // Update score
    var result = this.game.getGameResult(token);
    if (result === GameResult.Draw)
      this.score.addDraw();
    else if (result === GameResult.Loss)
      this.score.addLoss();
    else if (result === GameResult.Win)
      this.score.addWin();
  }

  render = () =>
    <Table.Cell
      selectable
      style={this.style}
      content={this.state.content}
      onClick={this.handleClick} />
}