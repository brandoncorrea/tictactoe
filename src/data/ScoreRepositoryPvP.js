import { StorageNames } from "../enums/StorageNames";
import ScoreRepository from "./ScoreRepository";

export default class ScoreRepositoryPvP extends ScoreRepository {
  constructor() {
    super();
    this.storageNames.wins = StorageNames.WinsPvP;
    this.storageNames.losses = StorageNames.LossesPvP;
    this.storageNames.draws = StorageNames.DrawsPvP;
  }
}