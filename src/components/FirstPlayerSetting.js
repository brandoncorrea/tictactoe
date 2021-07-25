import { Component } from "react";
import { Form, Radio } from 'semantic-ui-react';
import SettingsRepository from "../data/SettingsRepository";
import { FirstPlayer } from "../enums/FirstPlayer";

export default class FirstPlayerSetting extends Component {
  settings = new SettingsRepository()

  constructor(props) {
    super(props);
    this.state = {
      value: this.settings.getFirstPlayer()
    }

    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(e, target) {
    this.settings.setFirstPlayer(target.value);
    this.setState({ value: this.settings.getFirstPlayer() });
  }

  render = () => 
  <Form.Group inline>
    <label>Who goes first?</label>
    <Form.Field
      control={Radio}
      label={`Me (${this.settings.getIconPlayer1()})`}
      value={FirstPlayer.User}
      checked={this.state.value === FirstPlayer.User}
      onChange={this.handleChange}
      />
    <Form.Field
      control={Radio}
      label={`Opponent (${this.settings.getIconPlayer2()})`}
      value={FirstPlayer.Computer}
      checked={this.state.value === FirstPlayer.Computer}
      onChange={this.handleChange}
      />
  </Form.Group>
}