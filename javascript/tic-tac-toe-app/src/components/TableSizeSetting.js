import { Component } from "react";
import { Form, Input } from "semantic-ui-react";
import SettingsRepository from "../data/SettingsRepository";

const MIN_SIZE = 1;
const MAX_SIZE = 4;

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
    else if (isNaN(target.value) || target.value.length > 1)
      return;

    var value = Number(target.value);
    var error = null
    if (value < MIN_SIZE || value > MAX_SIZE)
      error = {
        content: `Size must be between ${MIN_SIZE} and ${MAX_SIZE}.`,
        pointing: 'above',
      };
    else
      this.settings.setTableSize(value);
    this.setState({ value, error });
  }

  render = () =>
    <Form>
      <Form.Field
        control={Input}
        label='Table Size'
        size='massive'
        onChange={this.handleChange}
        value={this.state.value}
        error={this.state.error}
      />
    </Form> 
}