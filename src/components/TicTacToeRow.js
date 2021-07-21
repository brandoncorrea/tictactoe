import { Component } from "react";
import { Table } from 'semantic-ui-react';
import TicTacToeCell from "./TicTacToeCell";

export default class TicTacToeRow extends Component {
  constructor(props) {
    super(props);
    this.state = {
      row: props.row,
      height: props.height
    }
  }

  render = () => 
    <Table.Row>
      {
        this.state.row.map(col => 
          <TicTacToeCell 
            height={this.state.height} 
            content={col} />)
      }
    </Table.Row>
  }