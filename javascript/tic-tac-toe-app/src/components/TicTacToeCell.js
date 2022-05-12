import { Component } from "react";
import { Table } from "semantic-ui-react";
import Cell from '../models/Cell'

export default class TicTacToeCell extends Component {
  constructor(props) {
    super(props);
    this.state = {
      content: props.content,
      onCellClick: props.onCellClick,
      cell: new Cell(props.row, props.col),
      style: {
        cursor: 'pointer', 
        height: props.height,
      }
    }

    this.handleClick = this.handleClick.bind(this);
  }

  handleClick = () =>
    this.state.onCellClick(this.state.cell);

  render = () =>
    <Table.Cell
      selectable
      style={this.state.style}
      content={this.state.content}
      onClick={this.handleClick} />
}