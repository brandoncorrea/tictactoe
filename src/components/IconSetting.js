import { Component } from "react";
import { Form, Header, Input } from 'semantic-ui-react';
import SettingsRepository from "../data/SettingsRepository";
import { PlayerIcon } from "../enums/PlayerIcon";


export default class IconSetting extends Component {
  settings = new SettingsRepository();
  options = [
    { key: PlayerIcon.X, text: PlayerIcon.X, value: PlayerIcon.X },
    { key: PlayerIcon.O, text: PlayerIcon.O, value: PlayerIcon.O },
  ]

  constructor(props) {
    super(props);
    this.state = {
      player1: this.settings.getIconPlayer1(),
      player2: this.settings.getIconPlayer2()
    }

    this.handleChangePlayer1 = this.handleChangePlayer1.bind(this);
    this.handleChangePlayer2 = this.handleChangePlayer2.bind(this);
  }

  handleChangePlayer1(e, target) {
    if (target.value.length > 2)
      return

    // Only save items with a value
    if (target.value.length > 0)
      this.settings.setIconPlayer1(target.value);
    this.setState({ player1: target.value });
  }

  handleChangePlayer2(e, target) {
    if (target.value.length > 2)
      return

    // Only save items with a value
    if (target.value.length > 0)
      this.settings.setIconPlayer2(target.value);
    this.setState({ player2: target.value });
  }

  render = () =>
    <Form>
      <Header as='h3' content='Icons' />
      <Form.Group widths='equal'>
        <Form.Field>
          <label>Your Icon</label>
          <Input 
            value={this.state.player1}
            onChange={this.handleChangePlayer1} 
            />
        </Form.Field>
        <Form.Field>
          <label>Opponent Icon</label>
          <Input 
            value={this.state.player2}
            onChange={this.handleChangePlayer2} 
            />
        </Form.Field>
      </Form.Group>
    </Form>
}