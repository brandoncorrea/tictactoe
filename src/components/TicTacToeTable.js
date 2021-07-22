import { Component } from "react";
import { Container, Table } from "semantic-ui-react";
import Guid from "../data/Guid";
import TicTacToeRow from "./TicTacToeRow";

export default class TicTacToeTable extends Component {
  constructor(props) {
    super(props);
    this.state = {
      table: props.table,
      onCellClick: props.onCellClick
    }
    this.state.onCellClick = this.state.onCellClick.bind(this);
    this.state.style = {
      maxWidth: `${this.state.table.length * 100}px`, 
      fontSize: '32px',
      margin: '0 auto',
      textAlign: 'center'
    }
  }

  render = () =>
    <Container>
      <Table fixed unstackable celled style={this.state.style}>
        <Table.Body>
          {
            
            Array.from(Array(this.state.table.length).keys()).map(row =>
              <TicTacToeRow
                key={Guid.newGuid()}
                onCellClick={this.state.onCellClick}
                rowNumber={row}
                row={this.state.table[row]}
                height='100px' />)
          }
        </Table.Body>
      </Table>
    </Container>

}