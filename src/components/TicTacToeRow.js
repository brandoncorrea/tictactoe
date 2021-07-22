import { Component } from "react";
import { Table } from 'semantic-ui-react';
import Guid from "../data/Guid";
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
            key={Guid.newGuid()}
            height={this.state.height} 
            content={col} />)
      }
    </Table.Row>
  }