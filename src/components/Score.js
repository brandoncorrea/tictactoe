import { Component } from "react";
import { Statistic } from "semantic-ui-react";
import ScoreRepository from "../data/ScoreRepository";

export default class Score extends Component {
  score = new ScoreRepository();
  items = [
    { key: 'wins', label: 'Wins', value: this.score.getWins().toLocaleString() },
    { key: 'draws', label: 'Draws', value: this.score.getDraws().toLocaleString() },
    { key: 'losses', label: 'Losses', value: this.score.getLosses().toLocaleString() },
  ]

  render = () => 
    <Statistic.Group size='huge' horizontal items={this.items}/>
}