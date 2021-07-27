import { Component } from "react";
import { Form, Header, Radio } from "semantic-ui-react";
import Guid from "../data/Guid";
import SettingsRepository from "../data/SettingsRepository";
import { GameMode } from "../enums/GameMode";

export default class GameModeSetting extends Component {
  settings = new SettingsRepository()
  options = [
    { label: 'Player vs Computer', value: GameMode.PvE },
    { label: 'Player vs Player', value: GameMode.PvP }
  ]

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
    <Form>
      <Header as='h3' content='Game Mode' />
      <Form.Group widths='equal'>
        {
          this.options.map(option => 
            <Form.Field
              key={Guid.newGuid()}
              control={Radio}
              label={option.label}
              value={option.value}
              checked={this.state.value === option.value}
              onChange={this.handleChange}
              />)
        }
      </Form.Group>
    </Form>
}