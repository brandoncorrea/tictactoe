import { Component } from "react";
import { Table } from 'semantic-ui-react';
import Guid from "../data/Guid";
import SettingsRepository from "../data/SettingsRepository";
import { Players } from "../enums/Players";
import TicTacToeCell from "./TicTacToeCell";

export default class TicTacToeRow extends Component {
  settings = new SettingsRepository();

  constructor(props) {
    super(props);
    this.state = {
      rowNumber: props.rowNumber,
      row: props.row,
      height: props.height,
      onCellClick: props.onCellClick,
      iconPlayer1: this.settings.getIconPlayer1(),
      iconPlayer2: this.settings.getIconPlayer2(),
    }
  }

  render = () => 
    <Table.Row>
      {
        Array.from(Array(this.state.row.length).keys()).map(col => 
          <TicTacToeCell 
            key={Guid.newGuid()}
            onCellClick={this.state.onCellClick}
            height={this.state.height} 
            col={col}
            row={this.state.rowNumber}
            content={
              this.state.row[col] === Players.Player1 
                ? this.state.iconPlayer1
              : this.state.row[col] === Players.Player2
                ? this.state.iconPlayer2
              : '' } />)
      }
    </Table.Row>
}