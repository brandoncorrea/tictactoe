import { Players } from '../enums/Players';
import { StorageNames } from '../enums/StorageNames';
import SettingsRepository from './SettingsRepository'

test('GetFirstPlayer defaults to Computer when no value exists', () => {
  var settings = new SettingsRepository();
  localStorage.removeItem(StorageNames.FirstPlayer);
  expect(settings.getFirstPlayer()).toBe(Players.Player2);
})

test('SetFirstPlayer can be assigned to Computer', () => {
  var settings = new SettingsRepository();
  settings.setFirstPlayer(Players.Player2);
  expect(settings.getFirstPlayer()).toBe(Players.Player2);
})

test('SetFirstPlayer can be assigned to User', () => {
  var settings = new SettingsRepository();
  settings.setFirstPlayer(Players.Player1);
  expect(settings.getFirstPlayer()).toBe(Players.Player1);
})