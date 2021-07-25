import { Component } from "react";
import { Form, Radio } from "semantic-ui-react";
import SettingsRepository from "../data/SettingsRepository";
import { GameMode } from "../enums/GameMode";

export default class GameModeSetting extends Component {
  settings = new SettingsRepository()
  constructor(props) {
    super(props);
    this.state = {
      value: this.settings.getGameMode()
    }

    this.handleChange = this.handleChange.bind(this)
  }

  handleChange(e, target) {
    this.settings.setGameMode(target.value);
    this.setState({ value: this.settings.getGameMode() })
  }

  render = () => 
    <Form.Group inline>
      <label>Game Mode:</label>
      <Form.Field
        control={Radio}
        label='Player vs Computer'
        value={GameMode.PvE}
        checked={this.state.value === GameMode.PvE}
        onChange={this.handleChange}
        />
      <Form.Field
        control={Radio}
        label='Player vs Player'
        value={GameMode.PvP}
        checked={this.state.value === GameMode.PvP}
        onChange={this.handleChange}
        />
    </Form.Group>
}