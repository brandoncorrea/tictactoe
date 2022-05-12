import { GameMode } from "../enums/GameMode";
import { StorageNames } from "../enums/StorageNames";
import SettingsRepository from "./SettingsRepository"

test('GetGameMode defaults to PvE when not set', () => {
  var settings = new SettingsRepository();
  localStorage.removeItem(StorageNames.GameMode);
  var mode = settings.getGameMode();
  expect(mode).toBe(GameMode.PvE);
})

test('GetGameMode can be set to PvP', () => {
  var settings = new SettingsRepository();
  localStorage.removeItem(StorageNames.GameMode);
  settings.setGameMode(GameMode.PvP);
  var mode = settings.getGameMode(StorageNames.GameMode);
  expect(mode).toBe(GameMode.PvP);
})

test('GetGameMode can be set to PvE', () => {
  var settings = new SettingsRepository();
  localStorage.removeItem(StorageNames.GameMode);
  settings.setGameMode(GameMode.PvE);
  var mode = settings.getGameMode(StorageNames.GameMode);
  expect(mode).toBe(GameMode.PvE);
})

test('GetGameMode can be set to PvP after being set to PvE', () => {
  var settings = new SettingsRepository();
  localStorage.removeItem(StorageNames.GameMode);
  settings.setGameMode(GameMode.PvE);
  settings.setGameMode(GameMode.PvP);
  var mode = settings.getGameMode();
  expect(mode).toBe(GameMode.PvP);
})

test('GetGameMode can be set to PvE after being set to PvP', () => {
  var settings = new SettingsRepository();
  localStorage.removeItem(StorageNames.GameMode);
  settings.setGameMode(GameMode.PvP);
  settings.setGameMode(GameMode.PvE);
  var mode = settings.getGameMode();
  expect(mode).toBe(GameMode.PvE);
})
