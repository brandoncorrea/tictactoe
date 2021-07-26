import { Component } from "react";
import { Form, Input } from "semantic-ui-react";
import SettingsRepository from "../data/SettingsRepository";

export default class TableSizeSetting extends Component {
  settings = new SettingsRepository();

  constructor(props) {
    super(props);
    this.state = {
      value: this.settings.getTableSize(),
      error: null
    }

    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(e, target) {
    if (target.value.length === 0) {
      this.setState({ value: target.value });
      return;
    }
    else if (target.value.length > 2 || isNaN(target.value))
      return;

    var value = Number(target.value);
    var error = null
    if (value < 1 || value > 10)
      error = {
        content: 'Size must be between 1 and 10.',
        pointing: 'above',
      };

    if (!error)
      this.settings.setTableSize(value);
    this.setState({ value, error });
  }

  render = () =>
    <Form>
      <Form.Field
        control={Input}
        label='Table Size'
        onChange={this.handleChange}
        value={this.state.value}
        error={this.state.error}
      />
    </Form> 
}