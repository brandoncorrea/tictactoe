import { StorageNames } from '../enums/StorageNames';
import ScoreRepository from './ScoreRepository';

// PvE implementation of ScoreRepository
export default class ScoreRepositoryPvE extends ScoreRepository {
  constructor() {
    super()
    this.storageNames.wins = StorageNames.WinsPvE;
    this.storageNames.losses = StorageNames.LossesPvE;
    this.storageNames.draws = StorageNames.DrawsPvE;
  }
}