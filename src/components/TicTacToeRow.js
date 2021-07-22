import { Component } from "react";
import { Table } from 'semantic-ui-react';
import Guid from "../data/Guid";
import TicTacToeCell from "./TicTacToeCell";

export default class TicTacToeRow extends Component {
  column = 0;
  constructor(props) {
    super(props);
    this.state = {
      row: props.row,
      data: props.data,
      height: props.height,
      game: props.game
    }
  }

  render = () => 
    <Table.Row>
      {
        this.state.data.map(col => 
          <TicTacToeCell 
            key={Guid.newGuid()}
            game={this.state.game}
            height={this.state.height} 
            col={this.column++}
            row={this.state.row}
            content={col} />)
      }
    </Table.Row>
}