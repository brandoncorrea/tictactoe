import { PlayerIcon } from '../enums/PlayerIcon';
import { StorageNames } from '../enums/StorageNames';
import SettingsRepository from './SettingsRepository'

test('Player 2 icon can be set to O', () => {
  var settings = new SettingsRepository();
  settings.setIconPlayer2(PlayerIcon.O);
  var icon = settings.getIconPlayer2();
  expect(icon).toBe(PlayerIcon.O);
})

test('Player 2 Icon can be set to X', () => {
  var settings = new SettingsRepository();
  settings.setIconPlayer2(PlayerIcon.X);
  var icon = settings.getIconPlayer2();
  expect(icon).toBe(PlayerIcon.X);
})

test('Player 2 Icon changes to O when Player 1 is set to X', () => {
  var settings = new SettingsRepository();
  settings.setIconPlayer2(PlayerIcon.X);
  settings.setIconPlayer1(PlayerIcon.X);
  expect(settings.getIconPlayer2()).toBe(PlayerIcon.O);
  expect(settings.getIconPlayer1()).toBe(PlayerIcon.X);
})

test('Player 2 Icon changes to X when Player 1 is set to O', () => {
  var settings = new SettingsRepository();
  settings.setIconPlayer2(PlayerIcon.O);
  settings.setIconPlayer1(PlayerIcon.O);
  expect(settings.getIconPlayer2()).toBe(PlayerIcon.X);
  expect(settings.getIconPlayer1()).toBe(PlayerIcon.O);
})

test('Player 2 Icon defaults to X when no value exists Player 1 is not X', () => {
  var settings = new SettingsRepository();
  settings.setIconPlayer1(PlayerIcon.O);
  localStorage.removeItem(StorageNames.IconPlayer2);
  expect(settings.getIconPlayer2()).toBe(PlayerIcon.X);
})

test('Player 2 Icon defaults to O when no value exists and Player 1 is X', () => {
  var settings = new SettingsRepository();
  settings.setIconPlayer1(PlayerIcon.X);
  localStorage.removeItem(StorageNames.IconPlayer2);
  expect(settings.getIconPlayer2()).toBe(PlayerIcon.O);
})

test('getIconPlayer2 defaults to O when neither Player 1 or 2 icons exist', () => {
  localStorage.removeItem(StorageNames.IconPlayer2);
  localStorage.removeItem(StorageNames.IconPlayer1);
  var settings = new SettingsRepository();
  var player2 = settings.getIconPlayer2();
  expect(player2).toBe(PlayerIcon.O);
})
