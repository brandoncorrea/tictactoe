import { Component } from "react";
import { Table } from 'semantic-ui-react';
import Guid from "../data/Guid";
import TicTacToeCell from "./TicTacToeCell";

export default class TicTacToeRow extends Component {
  constructor(props) {
    super(props);
    this.state = {
      rowNumber: props.rowNumber,
      row: props.row,
      height: props.height,
      onCellClick: props.onCellClick      
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
            content={this.state.row[col]} />)
      }
    </Table.Row>
}