import { FirstPlayer } from "../enums/FirstPlayer";
import { PlayerIcon } from "../enums/PlayerIcon";
import { StorageNames } from "../enums/StorageNames";

// Shorthand localStorage accessors
const setItem = (key, value) =>
  localStorage.setItem(key, value);
const getItem = key =>
  localStorage.getItem(key);

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

  // Getters
  getUserIcon = () => 
    getItem(StorageNames.UserIcon) || 
    getItem(StorageNames.ComputerIcon) !== PlayerIcon.X && PlayerIcon.X ||
    PlayerIcon.O;
  getComputerIcon = () => 
    getItem(StorageNames.ComputerIcon) || 
    getItem(StorageNames.UserIcon) !== PlayerIcon.X && PlayerIcon.X ||
    PlayerIcon.O;
  getFirstPlayer = () =>
    Number(getItem(StorageNames.FirstPlayer)) || 
    FirstPlayer.Computer;
}