import { Component } from "react";
import { Table } from "semantic-ui-react";
import SettingsRepository from "../data/SettingsRepository";
import { GameResult } from "../enums/GameResult";
import GameBoard from "../game/GameBoard";
import Cell from '../models/Cell'

export default class TicTacToeCell extends Component {
  settings = new SettingsRepository();
  constructor(props) {
    super(props);
    this.state = {
      game: props.game,
      content: props.content,
      cell: new Cell(props.row, props.col),
      style: {
        cursor: 'pointer', 
        height: props.height,
      },
    }

    this.handleClick = this.handleClick.bind(this);
  }

  handleClick() {
    var token = this.settings.getUserIcon();
    if (this.state.game.getGameResult() !== GameResult.None)
      return;

    this.state.game.placeToken(this.settings.getUserIcon(), this.state.cell);
    this.setState({ content: token })
  }

  render = () =>
    <Table.Cell
      selectable
      style={this.state.style}
      content={this.state.content}
      onClick={this.handleClick} />
}