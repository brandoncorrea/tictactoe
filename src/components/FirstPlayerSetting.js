import { Component } from "react";
import { Form, Header, Radio } from 'semantic-ui-react';
import SettingsRepository from "../data/SettingsRepository";
import { Players } from "../enums/Players";

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
  <Form>
    <Header as='h3' content='Who goes first?' />
    <Form.Group widths='equal'>
      <Form.Field
        control={Radio}
        label="I'll go first"
        value={Players.Player1}
        checked={this.state.value === Players.Player1}
        onChange={this.handleChange}
        />
      <Form.Field
        control={Radio}
        label='Opponent will go first'
        value={Players.Player2}
        checked={this.state.value === Players.Player2}
        onChange={this.handleChange}
        />
    </Form.Group>
  </Form>
}
