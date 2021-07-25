import { FirstPlayer } from '../enums/FirstPlayer';
import { StorageNames } from '../enums/StorageNames';
import SettingsRepository from './SettingsRepository'

test('GetFirstPlayer defaults to Computer when no value exists', () => {
  var settings = new SettingsRepository();
  localStorage.removeItem(StorageNames.FirstPlayer);
  expect(settings.getFirstPlayer()).toBe(FirstPlayer.Computer);
})

test('SetFirstPlayer can be assigned to Computer', () => {
  var settings = new SettingsRepository();
  settings.setFirstPlayer(FirstPlayer.Computer);
  expect(settings.getFirstPlayer()).toBe(FirstPlayer.Computer);
})

test('SetFirstPlayer can be assigned to User', () => {
  var settings = new SettingsRepository();
  settings.setFirstPlayer(FirstPlayer.User);
  expect(settings.getFirstPlayer()).toBe(FirstPlayer.User);
})