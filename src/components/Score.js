import { Component } from "react";
import { Statistic } from "semantic-ui-react";
import ScoreRepository from "../data/ScoreRepository";

export default class Score extends Component {
  constructor(props) {
    super(props);
    var score = new ScoreRepository();
    this.state = {
      items: [
        { key: 'wins', label: 'Wins', value: score.getWins().toLocaleString() },
        { key: 'draws', label: 'Draws', value: score.getDraws().toLocaleString() },
        { key: 'losses', label: 'Losses', value: score.getLosses().toLocaleString() },
      ]
    }
  }

  render = () => 
    <Statistic.Group size='huge' horizontal items={this.state.items}/>
}