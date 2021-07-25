import { FirstPlayer } from "../enums/FirstPlayer";
import { GameMode } from "../enums/GameMode";
import { PlayerIcon } from "../enums/PlayerIcon";
import { StorageNames } from "../enums/StorageNames";
import { getItem, setItem, getNumber } from '../helpers/LocalStorageHelper';

// Sets a storage's icon value and updates an opposing storage if the icons are the same
const setIcon = (icon, destinationStorageName, verifyStorageName) => {
  setItem(destinationStorageName, icon);
  if (icon !== getItem(destinationStorageName)) return;
  if (icon === PlayerIcon.X)
    setItem(verifyStorageName, PlayerIcon.O);
  else
    setItem(verifyStorageName, PlayerIcon.X);
}

export default class SettingsRepository {
  // Setters
  setIconPlayer1 = icon => 
    setIcon(icon, StorageNames.IconPlayer1, StorageNames.IconPlayer2);
  setIconPlayer2 = icon => 
    setIcon(icon, StorageNames.IconPlayer2, StorageNames.IconPlayer1);
  setFirstPlayer = player => 
    setItem(StorageNames.FirstPlayer, player);
  setGameMode = mode =>
    setItem(StorageNames.GameMode, mode);

  // Getters
  getIconPlayer1 = () => {
    var player1 = getItem(StorageNames.IconPlayer1);
    if (player1) 
      return player1;

    var player2 = getItem(StorageNames.IconPlayer2);
    if (player2 && player2 !== PlayerIcon.O)
      return PlayerIcon.O;
    return PlayerIcon.X;
  }

  getIconPlayer2 = () => {
    var player2 = getItem(StorageNames.IconPlayer2);
    if (player2) 
      return player2;

    var player1 = getItem(StorageNames.IconPlayer1);
    if (player1 && player1 !== PlayerIcon.X)
      return PlayerIcon.X;
    return PlayerIcon.O;
  }

  getFirstPlayer = () =>
    getNumber(StorageNames.FirstPlayer) || 
    FirstPlayer.Computer;

  getGameMode = () => 
    getNumber(StorageNames.GameMode) === GameMode.PvP 
      ? GameMode.PvP 
      : GameMode.PvE;
}
