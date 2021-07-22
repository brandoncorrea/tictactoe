import { Component } from "react";
import { Form, Dropdown } from 'semantic-ui-react';
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
      value: this.settings.getUserIcon()
    }

    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(e, target) {
    this.settings.setUserIcon(target.value);
    this.setState({ value: this.settings.getUserIcon() });
  }

  render = () =>
    <Form.Group inline>
      <Form.Field>
        <label>Choose an Icon</label>
        <Dropdown selection
          options={this.options}
          value={this.state.value}
          onChange={this.handleChange}/>
      </Form.Field>
    </Form.Group>
}