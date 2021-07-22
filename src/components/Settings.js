import { Component } from "react";
import { Form } from "semantic-ui-react";
import FirstPlayerSetting from "./FirstPlayerSetting";
import IconSetting from "./IconSetting";

export default class Settings extends Component {

  render = () =>
    <Form>
      <FirstPlayerSetting />
      <IconSetting />
    </Form>

}