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
  setUserIcon = icon => 
    setIcon(icon, StorageNames.UserIcon, StorageNames.ComputerIcon);
  setComputerIcon = icon => 
    setIcon(icon, StorageNames.ComputerIcon, StorageNames.UserIcon);
  setFirstPlayer = player => 
    setItem(StorageNames.FirstPlayer, player);
  setGameMode = mode =>
    setItem(StorageNames.GameMode, mode);

  // Getters
  getUserIcon = () => {
    var userIcon = getItem(StorageNames.UserIcon);
    if (userIcon) 
      return userIcon;

    var computerIcon = getItem(StorageNames.ComputerIcon);
    if (computerIcon && computerIcon !== PlayerIcon.O)
      return PlayerIcon.O;
    return PlayerIcon.X;
  }

  getComputerIcon = () => {
    var computerIcon = getItem(StorageNames.ComputerIcon);
    if (computerIcon) 
      return computerIcon;

    var userIcon = getItem(StorageNames.UserIcon);
    if (userIcon && userIcon !== PlayerIcon.X)
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
